package com.github.wenbo2018.webs.context;

import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public interface WebsWebApplicationContext extends ApplicationContext{

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();

}
