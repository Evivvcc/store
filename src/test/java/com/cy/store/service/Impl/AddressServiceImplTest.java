package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
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


    @Test
    public void setDefault() {
        addressService.setDefault(1,21,"Evivv");
    }

    @Test
    public void update() {

//        Address address = addressService.getById(1);
//        address.setIs_default(0);
//        addressService.updateById(address);
//        addressService.updateById(address);

        // 全更新
//        addressService.update(null, new UpdateWrapper<Address>().set("is_default", 0));
        // 全更新
        addressService.update(addressService.getById(1), new UpdateWrapper<Address>().set("is_default", 0));

    }

    @Test
    public void page() {
        addressService.page(new Page<Address>(0,1), new QueryWrapper<Address>().orderByDesc("modified_time"));
    }

    @Test
    public void delete() {
//        Address result = addressService.getOne(new QueryWrapper<Address>().eq("aid", 6));
//        System.out.println( result.getIsDefault());

        addressService.delete(7,21,"xiao");
    }
}