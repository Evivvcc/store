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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public JsonResult<User> login(String username, String password) {
        User data = userService.login(username, password);
        return new JsonResult<User>(OK, data);
    }
}