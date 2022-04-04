package com.cy.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.store.entity.User;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.ibatis.plugin.Interceptor;

import java.util.AbstractQueue;

public interface IUserService extends IService<User> {
    void register(User user);

    User login(String username, String password);

    void changePassword(Integer uid, String username, String oldPassword, String newPassword);

}
