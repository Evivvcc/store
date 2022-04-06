package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.store.entity.Cart;
import com.cy.store.mapper.CartMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.IProductService;
import com.cy.store.service.ex.AccessDeniedException;
import com.cy.store.service.ex.CartNotFoundException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {
    @Autowired
    IProductService productService;
    @Autowired
    CartMapper cartMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {

        // 根据参数pid和uid查询购物车中的数据
        // 判断查询结果是否为null
        Cart result = getOne(new QueryWrapper<Cart>().eq("uid", uid).eq("pid", pid));
        // 是：表示该用户并未将该商品添加到购物车
        if (result == null) {
            // -- 创建Cart对象
            Cart cart = new Cart();
            // -- 封装数据：uid,pid,amount
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            // -- 调用productService.findById(pid)查询商品数据，得到商品价格,封装数据：price
            cart.setPrice(productService.getById(pid).getPrice());
            // -- 封装数据：4个日志
            Date now = new Date();
            cart.setCreatedTime(now);
            cart.setCreatedUser(username);
            cart.setModifiedTime(now);
            cart.setModifiedUser(username);
            // -- 调用insert(cart)执行将数据插入到数据表中
            save(cart);
        } else {
            // 否：表示该用户的购物车中已有该商品
            // -- 从查询结果中获取购物车数据的id
            // -- 从查询结果中取出原数量，与参数amount相加，得到新的数量
            Integer count = result.getNum() + amount;
            // -- 执行更新数量
            result.setNum(count);
            if (!updateById(result)) {
                throw new UpdateException("修改商品数量时出现未知错误，请联系系统管理员");
            }
        }

    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) throws AccessDeniedException {
        // 根据参数cid查询购物车数据, 判断查询结果是否为null
        // 是：抛出CartNotFoundException
        Cart result = getById(cid);
        if (result == null) {
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }
        // 判断查询结果中的uid与参数uid是否不一致
        // 是：抛出AccessDeniedException
        if (result.getUid() != uid) {
            throw new AccessDeniedException("非法访问");
        }

        // 根据查询结果中的原数量增加1得到新的数量num
        result.setNum( result.getNum() + 1);

        // 创建当前时间对象，作为modifiedTime
        Date now = new Date();
        updateById(result);

        return result.getNum();
    }
}
