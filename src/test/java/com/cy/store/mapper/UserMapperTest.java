package com.cy.store.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.service.ex.UsernameDuplicateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest // 表示当前类是一个测试类，不会随同项目一起发送
class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void insert() {
        // 新增一个用户信息
        User user = new User();
        user.setPassword("123");
        user.setUsername("小7");
        userMapper.insert(user);
    }

    @Test
    public void delete() {
        // 根据map中的条件进行删除
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("uid", 1);
//        userMapper.deleteByMap(map);

//        List<Long> longs = Arrays.asList(3L, 6L);
//        userMapper.deleteBatchIds(longs);

        userMapper.deleteById(7);
    }

    @Test
    public void select() {
        User user = userMapper.selectById(7);
        System.out.println(user == null);

    }

}