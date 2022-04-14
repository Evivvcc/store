package com.cy.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartVO;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IOrderService extends IService<Order> {
    /**
     * 创建订单
     * @param aid 收货地址的id
     * @param cids 即将购买的商品数据在购物车表中的id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 成功创建的订单数据
     */
    Order create(Integer aid, Integer[] cids, Integer uid, String username);


    List<OrderItem> getByUid(Integer uid);

}
