package com.cy.store.mapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cy.store.entity.Address;
import com.sun.security.auth.UnixNumericUserPrincipal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AddressMapperTest {
    @Autowired
    AddressMapper addressMapper;

    @Test

    public void update() {
        // 更新一个字段
//        UpdateWrapper<Address> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("uid", 21);
//        updateWrapper.set("is_default", 0);
//        addressMapper.update(null, updateWrapper);

    }


}