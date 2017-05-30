package com.github.wenbo2018.webs.context;

import com.github.wenbo2018.webs.bean.BeanDefinition;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public interface WebsWebApplicationContext extends ApplicationContext{

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebsWebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();

    void initContext(ServletContext servletContext);

    List<Object> getObjects();

    void init();

    List<BeanDefinition> getBeanAsList();

}
