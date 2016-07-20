package com.tinymvc.ioc.spring;

import com.tinymvc.ioc.DefaultWebApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import java.util.*;

/**
 * Created by shenwenbo on 16/7/16.
 */
public abstract class AbstractTinyMvcApplicationContext implements TinyMvcApplicationContext{

    private static final Log logger = LogFactory.getLog(DefaultWebApplicationContext.class);

    protected  static String CONFIGURATION_FILE = "";

    protected Set<String> beanDefinitionNames = new HashSet<String>();

    protected List<Object> controllerBeans = new ArrayList<Object>();

    protected List<String> urlMapping = new ArrayList<String>();

    protected String rootPath;

    protected Map<String, Object> interceptorBeans = new HashMap<String, Object>();

    public AbstractTinyMvcApplicationContext() throws DocumentException {
        try {
            initBeans();
        } catch (ClassNotFoundException e) {
            logger.error("找不到类");
        }
        initUrlMapping();
    }

    protected abstract void initBeans() throws ClassNotFoundException;

    public abstract void initUrlMapping();

    @Override
    public List<Object> getController() {
        return controllerBeans;
    }

    @Override
    public List<String> getResponse() {
        return urlMapping;
    }
}
