package com.dgut.sell.service.impl;

import com.dgut.sell.DTO.OrderDTO;
import com.dgut.sell.Excellption.SellException;
import com.dgut.sell.enums.ResultEnums;
import com.dgut.sell.service.OrderService;
import com.dgut.sell.service.PayService;
import com.dgut.sell.util.JsonUtil;
import com.dgut.sell.util.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PayServiceImpl implements PayService {
    @Autowired
    private BestPayServiceImpl bestPayService;


    @Autowired
    private OrderService orderService;

    private static final String ORDER_NAME = "微信订餐订单测试";

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】result={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】response={}", JsonUtil.toJson(payResponse));

        return payResponse;

    }

    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】 异步通知 payResponse={}", payResponse);
//        修改订单支付状态
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());

        if (orderDTO == null) {
            log.error("【微信支付】 异步通知，订单不存在 orderId={}", payResponse.getOrderId());
            throw new SellException(ResultEnums.ORDER_NOT_EXITS);
        }
        if (!MathUtil.equal(orderDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount())) {
            log.error("【微信支付】 异步通知 订单校验金额不一致 orderId={}, 系统订单金额={},微信支付金额={]",
                    payResponse.getOrderId(),
                    orderDTO.getOrderAmount(),
                    payResponse.getOrderAmount()
            );
        }

        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {

        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退还】 request={}", refundRequest);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退还】reponse={}", refundResponse);
        return refundResponse;

    }

}
