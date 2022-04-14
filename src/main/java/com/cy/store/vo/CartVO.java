package com.cy.store.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
/** 购物车数据的Value Object类 */
public class CartVO implements Serializable {
    private Integer cid; //'购物车数据id',
    private Integer uid; //'用户id',
    private Integer pid; //'商品id',
    private Long price; //'加入时商品单价',
    private Integer num; //'商品数量',
    private String title;
    private Long realPrice;
    private String image;
}
