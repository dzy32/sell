package com.dgut.sell.controller;


import com.dgut.sell.Excellption.SellException;
import com.dgut.sell.Form.ProductForm;

import com.dgut.sell.dataobject.ProductCategory;
import com.dgut.sell.dataobject.ProductInfo;
import com.dgut.sell.enums.ResultEnums;
import com.dgut.sell.service.CategoryService;
import com.dgut.sell.service.ProductService;
import com.dgut.sell.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
public class SellProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "5") Integer size,
                                    Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size",size);
        return new ModelAndView("/product/list", map);
    }
    @GetMapping("/on_sale")
    public ModelAndView on_sale(@RequestParam(value = "productId") String productId, Map<String, Object> map ){
        try {

            productService.on_sale(productId);

        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);

        }
        map.put("msg", ResultEnums.SUCCESS);
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
    @GetMapping("/off_sale")
    public ModelAndView off_sale(@RequestParam(value = "productId") String productId, Map<String, Object> map ){
        try {

            productService.off_sale(productId);

        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);

        }
        map.put("msg", ResultEnums.SUCCESS);
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId" ,required=false) String productId, Map<String, Object> map ){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo=productService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        List<ProductCategory> productCategoryList=categoryService.findAll();

        map.put("productCategoryList", productCategoryList);
        return new ModelAndView("product/index", map);
    }
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult,Map<String, Object> map ){
//       如果是修改，1.从数据库查找出商品，2.在复制属性， 3.save
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        ProductInfo productInfo=new ProductInfo();
        if(!StringUtils.isEmpty(productForm.getProductId())){
            productInfo=productService.findOne(productForm.getProductId());
            BeanUtils.copyProperties(productForm,productInfo);

        }
        else{
            productForm.setProductId(KeyUtil.genUniqueKey());
            BeanUtils.copyProperties(productForm,productInfo);
        }
        try{
            productService.save(productInfo);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnums.SUCCESS);
        map.put("url", "/sell/seller/product/list");

        return new ModelAndView("common/success", map);
    }

}
