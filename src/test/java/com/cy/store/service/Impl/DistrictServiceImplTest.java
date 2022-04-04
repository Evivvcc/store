package com.cy.store.service.Impl;

import com.cy.store.entity.District;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DistrictServiceImplTest {

    @Autowired
    IDistrictService districtService;

    @Test
    void getByParent() {
        try {
            String parent = "110100";
            List<District> list = districtService.getByParent(parent);
            System.out.println("count=" + list.size());
            for (District item : list) {
                System.out.println(item);
            }
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void getNameByCode() {
        System.out.println(districtService.getNameByCode("330000"));
    }
}