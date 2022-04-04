package com.cy.store.mapper;

import com.cy.store.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DistrictMapperTest {
    @Autowired
    DistrictMapper districtMapper;

    @Test
    public void select() {
//        districtMapper.selectById(1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("parent", 110100);
        districtMapper.selectByMap(map);
    }

}