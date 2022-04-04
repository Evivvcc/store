package com.cy.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.store.entity.User;
import org.apache.catalina.realm.UserDatabaseRealm;

import java.util.AbstractQueue;

public interface IUserService extends IService<User> {
    void register(User user);

    User login(String username, String password);

}
