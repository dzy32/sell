package com.dgut.sell.dao;

import com.dgut.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerInfoDao extends JpaRepository<SellerInfo, String> {

    Optional<SellerInfo> findByOpenid(String openid);
}
