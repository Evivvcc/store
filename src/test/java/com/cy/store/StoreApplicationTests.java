package com.cy.store;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class StoreApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private DataSource dataSource;


    @Test
    void getConnection() throws SQLException {

        System.out.println(dataSource.getConnection());
    }

    @Autowired
    UserMapper userMapper;

    @Test
    public void testSelect() {
        List<User> users = userMapper.selectList(null);
        users.forEach((System.out::println));
    }



}
