package com.dgut.sell.dao;

import com.dgut.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Test
    public void save() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456");
        orderDetail.setOrderId("12345");
        orderDetail.setProductIcon("www.");
        orderDetail.setProductId("12345");
        orderDetail.setProductName("嘤嘤嘤");
        orderDetail.setProductPrice(new BigDecimal(66.6));
        orderDetail.setProductQuantity(66);
        OrderDetail result = orderDetailDao.save(orderDetail);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId("12345");
        Assert.assertNotEquals(0, orderDetailList.size());

    }
}