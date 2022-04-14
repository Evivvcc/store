package com.cy.store.controller;

import com.cy.store.entity.Product;
import com.cy.store.service.IProductService;
import com.cy.store.util.JsonResult;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("products")
public class ProductController extends BaseController{

    @Autowired
    IProductService productService;

    @GetMapping("/products/hot_list")
    public JsonResult<List<Product>> getHotList() {
        return new JsonResult<List<Product>>(OK, productService.getHotList());
    }

    @GetMapping("/products/{id}/details")
    public JsonResult<Product> findByid(@PathVariable("id") Integer id) {
       return new JsonResult<Product>(OK, productService.getById(id));
    }

    @GetMapping("/seckill/{id}/details")
    public JsonResult<Product> findSeckillByid(@PathVariable("id") Integer id) {
        return new JsonResult<Product>(OK, productService.getById(id));
    }

}
