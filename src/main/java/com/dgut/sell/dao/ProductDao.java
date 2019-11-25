package com.dgut.sell.dao;

import com.dgut.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<ProductInfo,String> {

    public List<ProductInfo> findByProductStatus(Integer productStatus);
}
