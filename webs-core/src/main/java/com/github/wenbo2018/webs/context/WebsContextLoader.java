package com.github.wenbo2018.webs.context;

import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public class WebsContextLoader {

    private WebsWebApplicationContext context;

    public WebsWebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        if (context==null) {
            this.context=createWebApplicationContext(servletContext);
        }
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
        return this.context;
    }

    protected WebsWebApplicationContext createWebApplicationContext(ServletContext sc) {
         WebsWebApplicationContext websWebApplicationContext=new DefaultWebsWebApplicationContext();
        return websWebApplicationContext;
    }

}
