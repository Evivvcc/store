package com.cy.store.controller;

import com.cy.store.entity.Order;
import com.cy.store.service.IOrderService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController{

    @Autowired
    IOrderService orderService;

    @RequestMapping("create")
    public JsonResult<Order> create(Integer aid, Integer pid, HttpSession session) {
        // 从Session中取出uid和username
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        // 调用业务对象执行业务
        Order data = orderService.create(aid, pid, uid, username);
        // 返回成功与数据
        return new JsonResult<Order>(OK, data);
    }
}
