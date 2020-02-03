package com.dgut.sell.service;

import com.dgut.sell.DTO.OrderDTO;
import com.dgut.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    //   创建订单
    OrderDTO create(OrderDTO orderDTO);

    //    查找一个订单
    OrderDTO findOne(String orderId);

    //    查找列表订单
    Page<OrderDTO> findAll(String buyerOpenId, Pageable pageable);

    //    完结订单单
    OrderDTO finshed(OrderDTO orderDTO);

    //    取消订单
    OrderDTO cancel(OrderDTO orderDTO);

    //支付订单
    OrderDTO paid(OrderDTO orderDTO);

    Page<OrderDTO> findAll(Pageable pageable);
}
