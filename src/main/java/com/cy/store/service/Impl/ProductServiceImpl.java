package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.store.entity.Product;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.IProductService;
import com.cy.store.service.ex.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Override
    public List<Product> getHotList() {
        Page<Product> page = new Page<>(0,4);
        Page<Product> result = page(page, new QueryWrapper<Product>().eq("status", 1).orderByDesc("priority"));
        List<Product> records = page.getRecords();
        for (Product product : records) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return records;
    }

    @Override
    public Product getById(Integer id) {
        Product result = super.getById(id);
        if (result == null) {
            throw new ProductNotFoundException("尝试访问的商品数据不存在");
        }
        // 将查询结果中的部分属性设置为null
        result.setPriority(null);
        result.setCreatedUser(null);
        result.setCreatedTime(null);
        result.setModifiedUser(null);
        result.setModifiedTime(null);
        // 返回查询结果
        return result;
    }
}
