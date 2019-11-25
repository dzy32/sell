package com.dgut.sell.service;

import com.dgut.sell.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {

    public ProductCategory findOne(Integer id);
    public List<ProductCategory> findAll();
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> type);
    public void save(ProductCategory productCategory);
}
