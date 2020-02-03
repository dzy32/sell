package com.dgut.sell.controller;

import com.dgut.sell.Excellption.SellException;
import com.dgut.sell.Form.CategoryForm;
import com.dgut.sell.Form.ProductForm;
import com.dgut.sell.dataobject.ProductCategory;
import com.dgut.sell.dataobject.ProductInfo;
import com.dgut.sell.enums.ResultEnums;
import com.dgut.sell.service.CategoryService;
import com.dgut.sell.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> productCategoryList=categoryService.findAll();
        map.put("productCategoryList",productCategoryList);
        return  new ModelAndView("category/list",map);
    }
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId" ,required=false) Integer categoryId, Map<String, Object> map ){
        if(null != categoryId){
            ProductCategory productCategory=categoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }

        return new ModelAndView("category/index", map);
    }
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult, Map<String, Object> map ){
//       如果是修改，1.从数据库查找出类目，2.在复制属性， 3.save
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }
        ProductCategory productCategory=new ProductCategory();
        if(null!= categoryForm.getCategoryId()){
            productCategory=categoryService.findOne(categoryForm.getCategoryId());
            BeanUtils.copyProperties(categoryForm,productCategory);

        }
        else{

            BeanUtils.copyProperties(categoryForm,productCategory);
        }
        try{
            categoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnums.SUCCESS);
        map.put("url", "/sell/seller/category/list");

        return new ModelAndView("common/success", map);
    }

}
