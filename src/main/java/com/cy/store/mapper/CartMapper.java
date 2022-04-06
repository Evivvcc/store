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

}
