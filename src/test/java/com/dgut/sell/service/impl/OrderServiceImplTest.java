package com.dgut.sell.service.impl;

import com.dgut.sell.DTO.CartDTO;
import com.dgut.sell.DTO.OrderDTO;
import com.dgut.sell.dataobject.OrderDetail;
import com.dgut.sell.dataobject.OrderMaster;
import com.dgut.sell.enums.OrderStatusEnums;
import com.dgut.sell.enums.PayStatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private  OrderServiceImpl orderService;

    private  final  String BUYER_ID="12345";

    private  final  String ORDER_ID="1574004592668532389";

    @Test
    public void create() {
        OrderDTO orderDTO =new OrderDTO();
        orderDTO.setBuyerAddress("dgut");
        orderDTO.setBuyerName("ys");
        orderDTO.setBuyerOpenid(BUYER_ID);
        orderDTO.setBuyerPhone("123456789012");
        List<OrderDetail> orderDetailList=new ArrayList<>();
        OrderDetail o1=new OrderDetail();
        o1.setProductQuantity(2);
        o1.setProductId("123456");
        OrderDetail o2=new OrderDetail();
        o2.setProductId("66666");
        o2.setProductQuantity(3);
        orderDetailList.add(o1);
        orderDetailList.add(o2);
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result=orderService.create(orderDTO);

        log.info("【订单创建】：result ={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        log.info("【查找订单】 ：result={}",orderDTO);
        Assert.assertNotNull(orderDTO);

    }

    @Test
    public void findAll() {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage=orderService.findAll(BUYER_ID,pageRequest);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());

    }

    @Test
    public void finshed() {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.finshed(orderDTO);
        Assert.assertEquals(OrderStatusEnums.FINSHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnums.CANCEL.getCode(),result.getOrderStatus());

    }
    @Test
    public void paid(){
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnums.SUCCESS.getCode(),result.getPayStatus());

    }
}