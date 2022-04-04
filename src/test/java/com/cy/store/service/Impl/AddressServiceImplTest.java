package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AddressServiceImplTest {
    @Autowired
    IAddressService addressService;


    @Test
    void addNewAddress() {


    }

    @Test
    public void list() {
        addressService.list(new QueryWrapper<Address>().eq("uid", 21));
    }

    @Test
    public void getByUid() {
        Integer uid = 21;
        List<Address> list = addressService.getByUid(uid);
        System.out.println("count=" + list.size());
        for (Address item : list) {
            System.out.println(item);
        }
    }


}