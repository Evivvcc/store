package com.cy.store.interceptor;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    /**
     * @Author WuYi
     * @Description 
     *      检测全局session中是否有uid数据，如果有则放行，如果没有重定向到登陆界面
     * @Date 10:57 2022/4/4
     * @Param 
     * @return 如果返回值为true则放行当前请求，如果返回值为false则拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object obj = request.getSession().getAttribute("uid");
        if (obj == null) { // 说明用户没有登陆过系统，需要重定向到登陆页面
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
