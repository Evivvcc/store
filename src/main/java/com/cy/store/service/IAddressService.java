package com.cy.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.store.entity.Address;

import java.util.List;

/** 处理收货地址数据的业务层接口 */
public interface IAddressService extends IService<Address> {

    /**
     * 创建新的收货地址
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @param address 用户提交的收货地址数据
     */
    void addNewAddress(Integer uid, String username, Address address);
    /**
     * 查询某用户的收货地址列表数据
     * @param uid 收货地址归属的用户id
     * @return 该用户的收货地址列表数据
     */
    List<Address> getByUid(Integer uid);

}
