package com.dgut.sell.service.impl;

import com.dgut.sell.DTO.CartDTO;
import com.dgut.sell.Excellption.SellException;
import com.dgut.sell.dao.ProductDao;
import com.dgut.sell.dataobject.ProductInfo;
import com.dgut.sell.enums.ProductStatusEnums;
import com.dgut.sell.enums.ResultEnums;
import com.dgut.sell.service.ProductService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    public ProductServiceImpl() {
        super();
    }

    @Override
    public ProductInfo findOne(String productId) {
        return productDao.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productDao.findByProductStatus(ProductStatusEnums.UP.getCode());
    }

    @Override
    public void on_sale(String productId) {
        ProductInfo productInfo=productDao.findOne(productId);
        if(productInfo==null){
            throw new SellException(ResultEnums.PRODUCT_NOT_EXITS);
        }

        if(productInfo.getProductStatusEnum().getMessage()=="上架"){
            throw  new SellException(ResultEnums.PRODUCT_STATUS_ERROR);

        }
        productInfo.setProductStatus(ProductStatusEnums.UP.getCode());
        productDao.save(productInfo);

    }

    @Override
    public void off_sale(String productId) {
        ProductInfo productInfo=productDao.findOne(productId);
        if(productInfo==null){
            throw new SellException(ResultEnums.PRODUCT_NOT_EXITS);
        }

        if(productInfo.getProductStatusEnum().getMessage()=="下架"){
            throw  new SellException(ResultEnums.PRODUCT_STATUS_ERROR);

        }
        productInfo.setProductStatus(ProductStatusEnums.DOWN.getCode());
        productDao.save(productInfo);

    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productDao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productDao.save(productInfo);

    }

    @Override

    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productDao.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnums.PRODUCT_NOT_EXITS);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductStock();
            productInfo.setProductStock(result);
            productDao.save(productInfo);
        }


    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productDao.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnums.PRODUCT_NOT_EXITS);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductStock();
            if (result < 0) {
                throw new SellException(ResultEnums.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productDao.save(productInfo);

        }
    }
}
