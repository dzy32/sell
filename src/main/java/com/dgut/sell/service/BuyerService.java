package com.dgut.sell.service;

import com.dgut.sell.DTO.OrderDTO;

public interface BuyerService {

//  查询用户订单
    OrderDTO findOrderOne(String openId,String orderId);

//    取消用户订单
    OrderDTO cancelOrder(String openId,String orderI);

//    验证用户openId与订单的opneId一致
    OrderDTO checkOrderOwner(String openId,String orderId);
}
