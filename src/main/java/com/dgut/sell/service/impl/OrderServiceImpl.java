package com.dgut.sell.service.impl;

import com.dgut.sell.DTO.CartDTO;
import com.dgut.sell.DTO.OrderDTO;
import com.dgut.sell.Excellption.SellException;
import com.dgut.sell.convertor.OrderMaster2OrderDTOConvertor;
import com.dgut.sell.dao.OrderDetailDao;
import com.dgut.sell.dao.OrderMasterDao;
import com.dgut.sell.dataobject.OrderDetail;
import com.dgut.sell.dataobject.OrderMaster;
import com.dgut.sell.dataobject.ProductInfo;
import com.dgut.sell.enums.OrderStatusEnums;
import com.dgut.sell.enums.PayStatusEnums;
import com.dgut.sell.enums.ResultEnums;
import com.dgut.sell.service.*;
import com.dgut.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.jws.WebService;
import javax.smartcardio.CardTerminal;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderMasterDao orderMasterDao;
    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = KeyUtil.genUniqueKey();
//        1.查商品（数量，价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnums.PRODUCT_NOT_EXITS);

            }
//            计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
//            订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailDao.save(orderDetail);
        }
//        2.订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());

        orderMasterDao.save(orderMaster);

//        3.扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

//        发送wensocket消息
        webSocket.sendMessage("有新的订单 订单Id = ".concat(orderDTO.getOrderId()));
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnums.ORDER_NOT_EXITS);
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnums.ORDER_DETAIL_NOT_EXITS);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findAll(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConvertor.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());


        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO finshed(OrderDTO orderDTO) {
//        判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("【订单完结】订单状态不正确: orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
//        修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnums.FINSHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        if (result == null) {
            log.error("【订单完结】订单完结更新失败: orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
//        发送模板消息
        pushMessageService.orderStatus(orderDTO);
        return orderDTO;

    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();

        //        判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("【取消订单】 订单状态不正确：orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }


        //        修改订单
        orderDTO.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】 更新订单状态失败： orderMaster={}", orderMaster);
            throw new SellException(ResultEnums.ORDER_UPDATE_FAIL);
        }


//        返回库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
//        退款
        if (orderDTO.getPayStatus().equals(PayStatusEnums.WAIT)) {
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
//        判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("【订单支付】 订单状态不正确;orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
//        判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnums.WAIT.getCode())) {
            log.error("【订单支付】 订单状态不正确;orderId={},payStatus={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnums.ORDER_PAY_STATUS_ERROR);
        }

//        修改支付状态
        orderDTO.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        if (result == null) {
            log.error("【订单支付】 支付更新失败：orderMaster=[]", orderMaster);
            throw new SellException(ResultEnums.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConvertor.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;

    }
}
