package com.github.wenbo2018.webs.handler;

import com.github.wenbo2018.webs.context.WebsWebApplicationContext;

import javax.servlet.http.HttpServletRequest;


public interface HandlerMapping {

    Handler getHandler(String currentRequestPath);

    Handler getHandler(HttpServletRequest request);

    void init(WebsWebApplicationContext websWebApplicationContext) throws IllegalAccessException, InstantiationException;

}
