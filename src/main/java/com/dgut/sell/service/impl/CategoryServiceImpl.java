package com.dgut.sell.service.impl;

import com.dgut.sell.dao.ProductCategoryDao;
import com.dgut.sell.dataobject.ProductCategory;
import com.dgut.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Override
    public ProductCategory findOne(Integer id) {
        return productCategoryDao.findOne(id);
    }
    @Override
    public List<ProductCategory> findAll() {
        return productCategoryDao.findAll();
    }


    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> type) {
        return productCategoryDao.findByCategoryTypeIn(type);
    }

    @Override
    public void save(ProductCategory productCategory) {
        productCategoryDao.save(productCategory);

    }
}
