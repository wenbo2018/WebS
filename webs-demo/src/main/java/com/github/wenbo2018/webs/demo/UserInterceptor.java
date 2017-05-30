package com.github.wenbo2018.webs.demo;

import com.github.wenbo2018.webs.annotation.WebsController;
import com.github.wenbo2018.webs.annotation.WebsInterceptor;
import com.github.wenbo2018.webs.interceptor.AbstractHandlerInterceptor;
import com.github.wenbo2018.webs.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shenwenbo on 2017/4/9.
 */
@WebsController
@WebsInterceptor(Url ="/user/*")
public class UserInterceptor extends AbstractHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("进入到了拦截器");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("出拦截器");
    }
}
