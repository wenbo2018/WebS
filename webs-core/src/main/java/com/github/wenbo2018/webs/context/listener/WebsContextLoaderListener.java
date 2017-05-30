package com.github.wenbo2018.webs.context.listener;

import com.github.wenbo2018.webs.context.WebsContextLoader;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public class WebsContextLoaderListener extends WebsContextLoader implements ServletContextListener {

    private WebsContextLoader websContextLoader;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.websContextLoader = createContextLoader();
        if (this.websContextLoader == null) {
            this.websContextLoader = this;
        }
        this.websContextLoader.initWebApplicationContext(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    protected WebsContextLoader createContextLoader() {
        return this;
    }


}
