package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicateException;
import com.cy.store.util.JsonResult;
import javafx.scene.control.TableView;
import org.apache.ibatis.executor.ResultExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.nio.channels.UnsupportedAddressTypeException;
import java.rmi.server.UID;

@RestController // @Controller + @ResponseBody
@RequestMapping("/users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;


    @RequestMapping("/reg")
    public JsonResult<Void> reg(User user) {

        userService.register(user);
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);
        // 向session对象中完成数据的绑定（session全局的）
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("/change_password")
    public JsonResult<User> changePassword(String oldPassword, String newPassword, HttpSession session) {
        // 调用session.getAttribute("")获取uid和username
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        // 调用业务对象执行修改密码
        userService.changePassword(uid, username, oldPassword, newPassword);
        // 返回成功
        return new JsonResult<User>(OK);
    }

    @GetMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        // 从HttpSession对象中获取uid
        // 调用业务对象执行获取数据
        // 响应成功和数据
        Integer uid = (Integer) session.getAttribute("uid");
        User data = userService.getByUid(uid);
        return new JsonResult<User>(OK, data);
    }
    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        // 从HttpSession对象中获取uid和username
        // 调用业务对象执行修改用户资料
        // 响应成功
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        userService.changeInfo(uid, username, user);
        return new JsonResult<Void>(OK);
    }
}
