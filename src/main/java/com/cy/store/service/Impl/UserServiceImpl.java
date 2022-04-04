package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.PasswordNotMatchException;
import com.cy.store.service.ex.UserNotFoundException;
import com.cy.store.service.ex.UsernameDuplicateException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.xml.crypto.Data;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Override
    public User login(String username, String password) {
        // 通过传递过来的username，并在数据库中查询是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User result = getOne(userQueryWrapper);

        // 判断结果集为null，则抛出异常
        if (result == null) {
            throw new UserNotFoundException("用户名不存在");
        }

        // 检测密码是否匹配
        /**
         * 1 先获取到数据库中到加密之后的密码
         * 2 和用户传递过来的密码进行比较
         *        a 先获取盐值：上一次在注册时自动生成的盐值
         *        b 将用户的密码按照相同的md5算法的规则进行加密
          */
        String oldPassword = result.getPassword();
        String salt = result.getSalt();
        String newMd5Password = getMd5Password(password, salt);
        if (!oldPassword.equals(newMd5Password)) {
            throw new PasswordNotMatchException("用户密码错误");
        }
        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建新的User对象
        User user = new User();
        // 将需要的信息封装，将查询结果中的uid、username、avatar封装到新的user对象中
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        // 返回新的user对象
        return user;

    }

    @Override
    public void register(User user) {
        // 通过user参数来获取传递过来的username，并在数据库中查询是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUsername());
        User result = getOne(userQueryWrapper);

        // 判断结果集是否为null， 如果不是抛出用户名被占用的异常
        if (result != null) {
            throw new UsernameDuplicateException("用户名被占用");
        }

        // 密码加密处理的实现： md5算法的形式
        // 串 + password + 串 ------- md5算法进行加密，连续加载三次
        // 盐值 + password + 盐值 ------ 盐值就是随机的一个字符串
        String oldPassword = user.getPassword();
        // 获取盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 将密码和盐值作为一个整体进行加密处理
        String md5Password = getMd5Password(oldPassword, salt);
        // 将密码重新补全到用户密码中
        user.setPassword(md5Password);



        // 对默认信息进行补全
        user.setSalt(salt); // 记录盐值
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        // 执行注册业务功能的实现
        if (!save(user)) {
            throw new InsertException("用户在注册过程中产生了未知的异常");
        }
    }

    private String getMd5Password(String password, String salt) {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
