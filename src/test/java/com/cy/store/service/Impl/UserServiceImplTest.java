package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.ex.UsernameDuplicateException;
import com.sun.tools.corba.se.idl.InterfaceGen;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private IUserService userService;

    @Test
    void register() {
        try {
            User user = new User();
            user.setUsername("小2");
            user.setPassword("123");
            userService.register(user);
        } catch (ServiceException e) {
            // 获取类的对象，获取类的名称
            System.out.println(e.getClass().getSimpleName());
            // 获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
        System.out.println("OK");
    }


    @Test
    public void login() {
        try {
            String username = "小米姑娘";
            String password = "123";
            User user = userService.login(username, password);
            System.out.println("登录成功！" + user);
        } catch (ServiceException e) {
            System.out.println("登录失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changePassword() {
        User user = new User();
        user.setUid(10);
        user.setPassword("234");
        try {
            userService.changePassword(user.getUid(), user.getUsername(), user.getPassword(), "123");
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }


}