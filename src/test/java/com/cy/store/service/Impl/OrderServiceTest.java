package com.cy.store.service.Impl;



import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.mapper.OrderMapper;
import com.cy.store.service.IOrderService;
import com.cy.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderServiceTest {

    @Autowired
    IOrderService orderService;

    @Test
    public void create() {
        try {
            Integer aid = 1;
            Integer[] cids = {3, 7, 9};
            Integer uid = 21;
            String username = "订单管理员";
            Order order = orderService.create(aid, cids, uid, username);
            System.out.println(order);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }




}