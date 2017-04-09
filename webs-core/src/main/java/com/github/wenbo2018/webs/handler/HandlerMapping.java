package com.github.wenbo2018.webs.handler;

import com.github.wenbo2018.webs.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;


public interface HandlerMapping {

    Handler getHandler(String currentRequestPath);

    Handler getHandler(HttpServletRequest request);

    void init(ApplicationContext applicationContext);

}
