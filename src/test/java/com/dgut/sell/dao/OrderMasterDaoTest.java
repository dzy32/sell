package com.dgut.sell.dao;

import com.dgut.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao orderMasterDao;

    private final String openId = "12345";

    @Test
    public void save() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("ys");
        orderMaster.setBuyerAddress("dgut");
        orderMaster.setBuyerOpenid(openId);
        orderMaster.setBuyerPhone("12345678901");
        orderMaster.setOrderAmount(new BigDecimal(67.7));
        OrderMaster result = orderMasterDao.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = new PageRequest(0, 3);
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(openId, pageRequest);
        Assert.assertNotEquals(0, orderMasterPage.getTotalElements());
        System.out.println(orderMasterPage.getTotalElements());

    }
}