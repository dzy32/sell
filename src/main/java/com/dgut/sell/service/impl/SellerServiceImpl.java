package com.dgut.sell.service.impl;

import com.dgut.sell.dao.SellerInfoDao;
import com.dgut.sell.dataobject.SellerInfo;
import com.dgut.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YS
 * @data 2019/12/16 21:42
 */
@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoDao sellerInfoDao;



    @Override
    public void save(SellerInfo sellerInfo) {
        sellerInfoDao.save(sellerInfo);
    }

    @Override
    public SellerInfo findByOpenId(String openId) {
        return sellerInfoDao.findByOpenid(openId).
                orElseThrow(()->new RuntimeException("SellerUser [openId="+openId+"] not find!"));

    }
}
