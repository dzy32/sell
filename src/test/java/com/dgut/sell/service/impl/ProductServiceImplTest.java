package com.dgut.sell.service.impl;

import com.dgut.sell.dataobject.ProductInfo;
import com.dgut.sell.enums.ProductStatusEnums;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("66666");
        Assert.assertEquals("66666", productInfo.getProductId());

    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> productInfos = productService.findAll(pageRequest);
        Assert.assertNotEquals(0, productInfos.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("鸭腿");
        productInfo.setProductId("12345");
        productInfo.setCategoryType(3);
        productInfo.setProductDescription("好吃");
        productInfo.setProductIcon("http://www.bilibili.com");
        productInfo.setProductPrice(new BigDecimal(66.6));
        productInfo.setProductStatus(ProductStatusEnums.UP.getCode());
        productInfo.setProductStock(66);
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}