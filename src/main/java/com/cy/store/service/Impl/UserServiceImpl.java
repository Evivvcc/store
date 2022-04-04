package com.cy.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.smartcardio.TerminalFactory;
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

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        // 根据参数uid查询用户数据,检查查询结果是否为null
        // 是：抛出UserNotFoundException异常
        User result = getOne(new QueryWrapper<User>().eq("uid", uid));
        if (result == null) {
            throw new UserNotFoundException("用户名不存在");
        }
        // 检查查询结果中的isDelete是否为1
        // 是：抛出UserNotFoundException异常
        if (result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户名不存在");
        }

        // 从查询结果中取出盐值
        // 将参数oldPassword结合盐值加密，得到oldMd5Password
        // 判断查询结果中的password与oldMd5Password是否不一致
        // 是：抛出PasswordNotMatchException异常
        String salt = result.getSalt();
        String oldMd5Password = getMd5Password(oldPassword, salt);
        if (!oldMd5Password.equals(result.getPassword())) {
            throw new PasswordNotMatchException("用户密码不正确");
        }
        // 将参数newPassword结合盐值加密，得到newMd5Password
        // 创建当前时间对象
        // 调用userMapper的updatePasswordByUid()更新密码，并获取返回值
        // 判断以上返回的受影响行数是否不为1
        // 是：抛了UpdateException异常
        String newMd5Password = getMd5Password(newPassword, salt);
        if (!update(result, new UpdateWrapper<User>().eq("uid",uid).set("password", newMd5Password).set("created_time", new Date()))) {
            throw new UpdateException("更新失败");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        // 根据参数uid查询用户数据,判断查询结果是否为null
        // 是：抛出UserNotFoundException异常
        User result = getById(uid);
        if (result == null) {
            throw new UserNotFoundException("用户名不存在");
        }

        // 判断查询结果中的isDelete是否为1
        // 是：抛出UserNotFoundException异常


        // 创建新的User对象
        // 将以上查询结果中的username/phone/email/gender封装到新User对象中
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        // 返回新的User对象
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        // 根据参数uid查询用户数据,判断查询结果是否为null
        // 是：抛出UserNotFoundException异常
        User result = getById(uid);
        if (result == null) {
            throw new UserNotFoundException("用户名不存在");
        }

        // 判断查询结果中的isDelete是否为1
        // 是：抛出UserNotFoundException异常

        // 向参数user中补全数据：uid
        // 向参数user中补全数据：modifiedUser(username)
        // 向参数user中补全数据：modifiedTime(new Date())
        // 调用userMapper的updateInfoByUid(User user)方法执行修改，并获取返回值
        // 判断以上返回的受影响行数是否不为1
        // 是：抛出UpdateException异常
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        user.setUid(result.getUid());
        if (!updateById(user)) {
            throw new UpdateException("更新失败");
        }
    }



    /** ---------- util ----------*/
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
