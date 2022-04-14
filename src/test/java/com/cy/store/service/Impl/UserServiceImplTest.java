package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.ServiceException;
import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
        user.setUid(7);
        user.setPassword("234");
        try {
            userService.changePassword(user.getUid(), user.getUsername(), user.getPassword(), "123");
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getByUid() {
        User user = userService.getByUid(8);
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setUid(9);
        user.setUsername("小1");
        user.setPhone("18658707293");
        user.setEmail("www.123@qq.com");
        userService.changeInfo(9, "cc", user);
    }

    @Test
    public void update() {

        /**
         *         updateById(T entity)
         *         根据实体类信息，通过查询id进行修改
         */
//        User user = userService.getOne(new QueryWrapper<User>().eq("uid", 8));
//        user.setPhone("1231231");
//        userService.updateById(user);

        /**
         *  update(T entity, Wrapper<T> updateWrapper)
         */


    }


}