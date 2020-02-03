package com.dgut.sell.service;

import com.dgut.sell.dataobject.SellerInfo;

/**
 * @author YS
 * @data 2019/12/16 21:41
 */
public interface SellerService {
    void save(SellerInfo sellerInfo);

    SellerInfo findByOpenId(String openId);
}
