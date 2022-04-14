package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {
    @Value("${userinfo.address.maxCount}")
    Integer maxCount;
    @Autowired
    IDistrictService districtService;
    @Autowired
    AddressMapper addressMapper;


    /**
     * 创建新的收货地址
     *
     * @param uid      当前登录的用户的id
     * @param username 当前登录的用户名
     * @param address  用户提交的收货地址数据
     */
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
        address.setIsDefault(isDefault);
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


    /**
     * 查询某用户的收货地址列表数据
     *
     * @param uid 收货地址归属的用户id
     * @return 该用户的收货地址列表数据
     */
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


    /**
     * 根据收货地址数据的id，查询收货地址详情
     *
     * @param aid 收货地址id
     * @param uid 归属的用户id
     * @return 匹配的收货地址详情
     */
    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = getById(aid);
        if (address == null) {
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);

        return address;
    }

    @Transactional
    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        // 根据参数aid，调用addressMapper中的findByAid()查询收货地址数据
        // 判断查询结果是否为null
        // 是：抛出AddressNotFoundException
        Address result = getById(aid);
        if (result == null) {
            throw new AddressNotFoundException("尝试访问的地址数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致(使用equals()判断)
        // 是：抛出AccessDeniedException：非法访问
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        // 调用addressMapepr的updateNonDefaultByUid()将该用户的所有收货地址全部设置为非默认，并获取返回的受影响的行数
        // 判断受影响的行数是否小于1(不大于0)
        // 是：抛出UpdateException
        if (!update(null, new UpdateWrapper<Address>().set("is_default", 0))) {
            throw new UpdateException("设置默认收货地址时出现位置错误[1]");
        }

        // 调用addressMapepr的updateDefaultByAid()将指定aid的收货地址设置为默认，并获取返回的受影响的行数
        // 判断受影响的行数是否不为1
        // 是：抛出UpdateException
        Date now = new Date();
        result.setIsDefault(1);
        result.setModifiedTime(now);
        result.setModifiedUser(username);
        if (!updateById(result)) {
            throw new UpdateException("设置默认收货地址时出现位置错误[1]");
        }

    }


    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = getOne(new QueryWrapper<Address>().eq("aid", aid));
        if (result == null) {
            throw new AddressNotFoundException("尝试访问的地址数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        if (!removeById(aid)) {
            throw new DeleteException("删除收货地址时出现位置错误，请联系管理员");
        }
        if (result.getIsDefault() == null) {
            return;
        }
        if (count(new QueryWrapper<Address>().eq("uid",uid)) == 0) {
            return;
        }
        Page<Address> lastModified = page(new Page<Address>(0, 1), new QueryWrapper<Address>().orderByDesc("modified_time"));
        List<Address> records = lastModified.getRecords();
        Address address = records.get(0);
        address.setIsDefault(1);
        if (!updateById(address)) {
            throw new UpdateException("更新收货地址数据时出现未知错误，请联系管理员");
        }

    }
}
