package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.entity.Product;
import com.cy.store.mapper.OrderMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IOrderService;
import com.cy.store.service.IProductService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.ProductNotEnoughException;
import com.cy.store.service.ex.ServiceException;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    IAddressService addressService;
    @Autowired
    IProductService productService;

    /**
     * 创建秒杀订单
     *
     * @param aid      收货地址的id
     * @param pid      当前商品的id
     * @param uid      当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 成功创建的订单数据
     */
    @Override
    public Order create(Integer aid, Integer pid, Integer uid, String username) {

        // 判断用户是否登录 （拦截到控制层）

        // 判断库存是否足够
        Product product = productService.getById(pid);
        if (product.getNum() < 0) {
            throw new ProductNotEnoughException("库存不足");
        }
        // 判断是否重复抢购
        if (getOne(new QueryWrapper<Order>().eq("uid", uid).eq("pid", pid)) != null) {
            throw new ServiceException("商品没人限购一件");
        }

        // 生成订单
        // 创建当前时间对象
        Date now = new Date();
        // 查询收货地址数据
        Address address = addressService.getByAid(aid, uid);
        // 创建订单数据对象
        Order order = new Order();
        // 补全数据：uid
        order.setUid(uid);
        order.setPid(pid);
        // 补全数据：收货地址相关的6项
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        // 补全数据：totalPrice
        order.setTotalPrice(0l);
        // 补全数据：status
        order.setStatus(0);
        // 补全数据：下单时间
        order.setOrderTime(now);
        // 补全数据：日志
        order.setCreatedUser(username);
        order.setCreatedTime(now);
        order.setModifiedUser(username);
        order.setModifiedTime(now);
        if (!save(order)) {
            throw new InsertException("插入订单数据时出现未知错误，请联系系统管理员");
        }
        // 插入订单数据
        product.setNum(product.getNum() - 1);
        productService.updateById(product);
        // 返回
        return order;
    }
}
