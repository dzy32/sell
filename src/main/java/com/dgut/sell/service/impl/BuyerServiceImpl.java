package com.dgut.sell.service.impl;

import com.dgut.sell.DTO.OrderDTO;
import com.dgut.sell.Excellption.SellException;
import com.dgut.sell.enums.ResultEnums;
import com.dgut.sell.service.BuyerService;
import com.dgut.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;



    @Override
    public OrderDTO findOrderOne(String openId, String orderId) {
        return checkOrderOwner(openId,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openId, String orderId) {
        OrderDTO orderDTO=checkOrderOwner(openId,orderId);
        if(orderDTO==null){
            log.error("【取消订单】 查不到该订单，orderId={}",orderId);
            throw  new SellException(ResultEnums.ORDER_NOT_EXITS);
        }
        return orderService.cancel(orderDTO);

    }

    @Override
    public OrderDTO checkOrderOwner(String openId, String orderId) {
        OrderDTO orderDTO=orderService.findOne(orderId);
        if(orderDTO==null){
            return  null;
        }
//        判断是否是自己的订单
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openId)){
            log.error("【查询订单】 订单的openid不一致 openid={},orderDTO={}",openId,orderDTO);
            throw  new SellException(ResultEnums.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
