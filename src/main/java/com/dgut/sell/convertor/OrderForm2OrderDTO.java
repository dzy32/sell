package com.dgut.sell.convertor;

import com.dgut.sell.DTO.OrderDTO;
import com.dgut.sell.Excellption.SellException;
import com.dgut.sell.Form.OrderForm;
import com.dgut.sell.dataobject.OrderDetail;
import com.dgut.sell.enums.ResultEnums;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTO {
    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        Gson gson = new Gson();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenId());
        List<OrderDetail> orderDetailList = new ArrayList<>();

        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {

                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】 错误：string={}", orderForm.getItems());
            throw new SellException(ResultEnums.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;


    }
}
