package com.cy.store.service.Impl;

import com.cy.store.mapper.CartMapper;
import com.cy.store.service.ICartService;
import com.cy.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.lang.model.element.ElementVisitor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceImplTest {
    @Autowired
    ICartService cartService;

    @Autowired
    CartMapper cartMapper;

    @Test
    void addToCart() {
        cartService.addToCart(1, 10000014, 1, "evi");
    }

    @Test
    public void getVOByUid() {
        List<CartVO> voByUid = cartService.getVOByUid(21);
        for (CartVO cart : voByUid) {
            System.out.println(cart);
        }
    }

    @Test
    public void addNum() {
        cartService.addNum(2, 21, "Evivv");
    }
}