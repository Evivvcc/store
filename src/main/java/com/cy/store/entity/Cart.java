package com.cy.store.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 购物车数据的实体类 */
public class Cart extends BaseEntity{
    @TableId
    private Integer cid; //'购物车数据id',
    private Integer uid; //'用户id',
    private Integer pid; //'商品id',
    private Long price; //'加入时商品单价',
    private Integer num; //'商品数量',
}
