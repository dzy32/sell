package com.dgut.sell.service.impl;

import com.dgut.sell.DTO.OrderDTO;
import com.dgut.sell.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author YS
 * @data 2020/2/5 23:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private PushMessageService pushMessageService;


    @Test
    public void orderStatus() {
        OrderDTO orderDTO= orderService.findOne("1574004592668532389");
        pushMessageService.orderStatus(orderDTO);
    }
}