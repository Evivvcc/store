package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制层类的基类，主要做异常的处理
 */
public class BaseController {
    public static final  int OK = 200;

    // 请求处理方法,这个方法的返回值就是需要传递给前端的数据
    // 自动将异常对象传递给此方法的参数列表上
    // 当项目中产生了异常，被统一拦截到此方法中，这个方法此时就充当的是请求处理的方法，方法的返回值就直接给前端
    @ExceptionHandler(ServiceException.class) // 用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>();
        if (e instanceof UsernameDuplicateException){
            result.setState(4000);
            result.setMessage("用户名已经被占用");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMessage("用户名不存在");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("用户密码错误");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新错误");
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("地址数量达到上限制");
        }
        return result;
    }

    protected final Integer getuidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
