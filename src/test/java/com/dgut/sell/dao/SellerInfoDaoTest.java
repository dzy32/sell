package com.dgut.sell.dao;

import com.dgut.sell.dataobject.SellerInfo;
import com.dgut.sell.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDaoTest {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Test
    public void save(){
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("123456");
        sellerInfo.setOpenid("123456");
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        SellerInfo result=sellerInfoDao.save(sellerInfo);
        Assert.assertNotNull(result);
    }
//    @Test
//    public void findByOpneid() {
//        SellerInfo sellerInfo=sellerInfoDao.findByOpenid("123456");
//        Assert.assertNotNull(sellerInfo);
//
//    }
}