package com.cy.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.store.entity.Address;
import com.cy.store.entity.Order;

public interface IOrderService extends IService<Order> {
    /**
     * 创建订单
     * @param aid 收货地址的id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 成功创建的订单数据
     */
    Order create(Integer aid,Integer pid, Integer uid, String username);
}
