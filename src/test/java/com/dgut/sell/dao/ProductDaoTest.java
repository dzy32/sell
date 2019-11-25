package com.dgut.sell.dao;

import com.dgut.sell.dataobject.ProductInfo;
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
public class ProductDaoTest {

    @Autowired
    private  ProductDao productDao;

    @Test
    public void save(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductName("鸡腿");
        productInfo.setProductId("66666");
        productInfo.setCategoryType(3);
        productInfo.setProductDescription("鸡腿好吃");
        productInfo.setProductIcon("http://www.bilibili.com");
        productInfo.setProductPrice(new BigDecimal(66.6));
        productInfo.setProductStatus(0);
        productInfo.setProductStock(66);
        ProductInfo result = productDao.save(productInfo);
        Assert.assertNotNull(result);


    }
    @Test
    public void findByProductStatus() {
        List<ProductInfo> product= productDao.findByProductStatus(0);
        Assert.assertNotEquals(0,product.size());

    }
}