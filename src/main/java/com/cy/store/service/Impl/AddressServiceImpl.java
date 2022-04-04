package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.AddressCountLimitException;
import com.cy.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {
    @Value("${userinfo.address.maxCount}")
    Integer maxCount;
    @Autowired
    IDistrictService districtService;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        // 根据参数uid调用addressMapper的countByUid()方法，统计当前用户的收货地址数据的数量,判断数量是否达到上限值
        // 是：抛出AddressCountLimitException
        long count = count(new QueryWrapper<Address>().eq("uid", uid));
        if (count > maxCount) {
            throw new AddressCountLimitException("地址数量达到上限制" + maxCount);
        }

        // 补全数据：将参数uid封装到参数address中
        address.setUid(uid);
        // 补全数据：根据以上统计的数量，得到正确的isDefault值(是否默认：0-不默认，1-默认)，并封装
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIs_default(isDefault);
        // 补全数据：4项日志
        Date now = new Date();
        address.setCreatedUser(username);
        address.setCreatedTime(now);
        address.setModifiedUser(username);
        address.setModifiedTime(now);
        // 补全数据：省、市、区的名称
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        // 插入收货地址数据，插入失败抛出InsertException
        if (!save(address)) {
            throw new InsertException("插入失败");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> result = list(new QueryWrapper<Address>().eq("uid", uid));
        for (Address address : result) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return result;
    }
}
