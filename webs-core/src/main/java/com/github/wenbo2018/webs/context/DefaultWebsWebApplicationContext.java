package com.github.wenbo2018.webs.context;

import com.github.wenbo2018.webs.annotation.WebsController;
import com.github.wenbo2018.webs.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletContext;
import java.util.*;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public class DefaultWebsWebApplicationContext extends AbstractWebsWebApplicationContext {

    private static Logger logger = LoggerFactory.getLogger(DefaultWebsWebApplicationContext.class);

    private List<Object> beans = new ArrayList<>();

    private volatile static boolean init = false;


    @Override
    public Object getBean(String name) {

        return null;
    }

    @Override
    public List<Object> getObjects() {
        if (this.beans == null || this.beans.size() <= 0) {
            init();
        }
        return this.beans;
    }

    @Override
    public void init() {
        synchronized (this) {
            if (!init) {
                try {
                    if (beans == null || beans.size() <= 0) {
                        List<String> classNameList = ClassUtils.findClassNameByAnnotation(WebsController.class);
                        for (String className : classNameList) {
                            Object object = Class.forName(className).newInstance();
                            beans.add(object);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    logger.error("class not find" + WebsController.class, e);
                } catch (InstantiationException e) {
                    logger.error("class Instantiation erroe", e);
                } catch (IllegalAccessException e) {
                    logger.error("not has class access", e);
                }
            }
        }
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }
}
