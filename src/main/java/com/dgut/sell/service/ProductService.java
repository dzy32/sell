package com.dgut.sell.service;

import com.dgut.sell.DTO.CartDTO;
import com.dgut.sell.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    public ProductInfo findOne(String productId);

    public List<ProductInfo> findUpAll();

    public Page<ProductInfo> findAll(Pageable pageable);

    public ProductInfo save(ProductInfo productInfo);

//    上架商品
    void on_sale(String productId);


//    下架商品
    void off_sale(String productId);
    //  加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //    减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
