package com.cy.store.service.Impl;

import com.cy.store.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.image.ImageProducer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    IProductService productService;

    @Test
    void getHotList() {
        productService.getHotList();
    }

    @Test
    public void getByid() {
        productService.getById(10000001);
    }
}