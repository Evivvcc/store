package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.entity.Product;
import com.cy.store.mapper.OrderMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.ICartService;
import com.cy.store.service.IOrderService;
import com.cy.store.service.IProductService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.ProductNotEnoughException;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.vo.CartVO;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.repository.cdi.CdiBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    IAddressService addressService;
    @Autowired
    ICartService cartService;
    @Autowired
    OrderMapper orderMapper;

    /**
     * 创建订单
     *
     * @param aid      收货地址的id
     * @param cids     即将购买的商品数据在购物车表中的id
     * @param uid      当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 成功创建的订单数据
     */
    @Override
    public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
        // 创建当前时间对象
        Date now = new Date();

        // 根据cids查询所勾选的购物车列表中的数据
        List<CartVO> carts = cartService.getVOByCids(uid, cids);

        // 计算这些商品的总价
        long totalPrice = 0;
        for (CartVO cart : carts) {
            totalPrice += cart.getPrice() * cart.getNum();
        }
        // 创建订单数据对象
        Order order = new Order();
        // 补全数据：uid
        order.setUid(uid);
        // 查询收货地址数据
        Address address = addressService.getByAid(aid, uid);
        // 补全数据：收货地址相关的6项
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        // 补全数据：totalPrice
        order.setTotalPrice(totalPrice);
        // 补全数据：status
        order.setStatus(0);
        // 补全数据：下单时间
        order.setOrderTime(now);
        // 补全数据：日志
        order.setCreatedUser(username);
        order.setCreatedTime(now);
        order.setModifiedUser(username);
        order.setModifiedTime(now);
        // 插入订单数据
        if (!save(order)) {
            throw new InsertException("插入订单数据时出现未知错误");
        }
        // 遍历carts，循环插入订单商品数据
        for (CartVO cartVO : carts) {
            // 创建订单商品数据
            OrderItem orderItem = new OrderItem();
            // 补全数据：oid(order.getOid())
            orderItem.setOid(order.getOid());
            // 补全数据：pid, title, image, price, num
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setImage(cartVO.getImage());
            orderItem.setPrice(cartVO.getPrice());
            orderItem.setNum(cartVO.getNum());
            // 补全数据：4项日志
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(now);
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(now);
            // 插入订单商品数据
            if (orderMapper.insertOrderItem(orderItem) != 1) {
                throw new InsertException("插入订单数据时出现未知错误，请联系系统管理员");
            }
        }
        // 返回
        return order;
    }

    @Override
    public List<OrderItem> getByUid(Integer uid) {

        return null;
    }
}
