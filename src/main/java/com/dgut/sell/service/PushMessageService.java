package com.dgut.sell.service;

import com.dgut.sell.DTO.OrderDTO;

/**
 * @author YS
 * @data 2020/2/5 22:03
 */
public interface PushMessageService {

    void orderStatus(OrderDTO orderDTO);
}
