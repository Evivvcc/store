package com.cy.store.mapper;

import com.cy.store.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderMapperTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void insertOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(1);
        orderItem.setPid(2);
        orderItem.setTitle("高档铅笔");
        Integer rows = orderMapper.insertOrderItem(orderItem);
        System.out.println("rows=" + rows);
    }
}