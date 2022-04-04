package com.cy.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.ibatis.plugin.Interceptor;

import java.util.AbstractQueue;

public interface IUserService extends IService<User> {
    void register(User user);

    User login(String username, String password);

    void changePassword(Integer uid, String username, String oldPassword, String newPassword);


    /**
     * 获取当前登录的用户的信息
     * @param uid 当前登录的用户的id
     * @return 当前登录的用户的信息
     */
    User getByUid(Integer uid);

    /**
     * 修改用户资料
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @param user 用户的新的数据
     */
    void changeInfo(Integer uid, String username, User user);

}
