package com.cy.store.controller;

import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.service.IOrderService;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController{

    @Autowired
    IOrderService orderService;

    @RequestMapping("create")
    public JsonResult<Void> create(Integer aid, Integer[] cids, HttpSession session) {
        // 从Session中取出uid和username
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        // 调用业务对象执行业务
        Order data = orderService.create(aid,cids, uid, username);
        // 返回成功与数据
        return new JsonResult<Void>(OK);
    }

//    @GetMapping({"", "/"})
//    public JsonResult<List<OrderItem>> getVOByUid(HttpSession session) {
//        Integer uid = (Integer) session.getAttribute("uid");
//        return new JsonResult<List<CartVO>>(OK, cartService.getVOByUid(uid));
//    }
}
