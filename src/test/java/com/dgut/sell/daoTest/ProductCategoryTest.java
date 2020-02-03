package com.dgut.sell.daoTest;

import com.dgut.sell.dao.ProductCategoryDao;
import com.dgut.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = productCategoryDao.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void setProductCategoryUpdateTest() {
        ProductCategory productCategory = productCategoryDao.findOne(1);
        productCategory.setCategoryName("女生最爱");
        productCategoryDao.save(productCategory);
    }

    @Test
    public void ProductCategorySaveTest() {
        ProductCategory productCategory = new ProductCategory("热销", 1);
        productCategoryDao.save(productCategory);

    }

    @Test
    public void ProductCategoryFindByTypeTest() {
        List<Integer> type = Arrays.asList(1, 3);
        List<ProductCategory> productCategories = productCategoryDao.findByCategoryTypeIn(type);
        Assert.assertNotEquals(0, productCategories.size());

    }
}
