package com.dgut.sell.controller;

import com.dgut.sell.DTO.OrderDTO;
import com.dgut.sell.Excellption.SellException;
import com.dgut.sell.Form.OrderForm;
import com.dgut.sell.VO.ResultVO;
import com.dgut.sell.convertor.OrderForm2OrderDTO;
import com.dgut.sell.enums.ResultEnums;
import com.dgut.sell.service.BuyerService;
import com.dgut.sell.service.OrderService;
import com.dgut.sell.service.impl.OrderServiceImpl;
import com.dgut.sell.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private BuyerService buyerService;

//    创建订单

    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm ,
                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确 orderForm={}",orderForm);
            throw  new SellException(ResultEnums.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO= OrderForm2OrderDTO.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空");
            throw  new SellException(ResultEnums.CART_EMPTY);
        }
         OrderDTO ceateResult=orderService.create(orderDTO);
         Map<String,String> map=new HashMap<>();
         map.put("orderId",ceateResult.getOrderId());
         return  ResultVOUtil.Success(map);

    }
//    订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10")Integer size){

        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】 openid为空");
            throw new  SellException(ResultEnums.PARAM_ERROR);
        }
        PageRequest pageRequest=new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage=orderService.findAll(openid,pageRequest);

        return  ResultVOUtil.Success(orderDTOPage.getContent());
    }

//    订单详情
    @GetMapping("/detail")
    public  ResultVO<OrderDTO> detail(@RequestParam("openid")String openid,@RequestParam("orderid") String orderid){

        OrderDTO orderDTO=buyerService.findOrderOne(openid,orderid);
        return  ResultVOUtil.Success(orderDTO);
    }

//    取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("orderid") String orderid,@RequestParam("openid") String openid){
        buyerService.cancelOrder(openid,orderid);
        return  ResultVOUtil.Success();
    }
}
