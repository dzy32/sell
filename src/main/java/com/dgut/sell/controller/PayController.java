package com.dgut.sell.controller;


import com.dgut.sell.DTO.OrderDTO;
import com.dgut.sell.Excellption.SellException;
import com.dgut.sell.enums.ResultEnums;
import com.dgut.sell.service.OrderService;
import com.dgut.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {


        //1.查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);

        if (orderDTO == null) {
            throw new SellException(ResultEnums.ORDER_NOT_EXITS);
        }
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
//2.发起支付
        return new ModelAndView("pay/create", map);
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        payService.notify(notifyData);

        return new ModelAndView("pay/success");
    }

}
