package com.cy.store.mapper;

import com.cy.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartMapperTest {
    @Autowired
    CartMapper cartMapper;

    @Test
    void findVOByUid() {
        List<CartVO> list = cartMapper.findVOByUid(21);
        System.out.println(list);
    }
}