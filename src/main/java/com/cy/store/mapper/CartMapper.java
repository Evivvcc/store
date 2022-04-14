package com.cy.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper extends BaseMapper<Cart> {
    /**
     * 查询某用户的购物车数据
     * @param uid 用户id
     * @return 该用户的购物车数据的列表
     */
    List<CartVO> findVOByUid(Integer uid);


    /**
     * 根据若干个购物车数据id查询详情的列表
     * @param cids 若干个购物车数据id
     * @return 匹配的购物车数据详情的列表
     */
    List<CartVO> findVOByCids(Integer[] cids);



}
