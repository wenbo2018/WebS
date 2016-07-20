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

    private static final String DEFAULT_CONFIGURATION_FILE = "tiny-mvc.xml";

    protected  static String CONFIGURATION_FILE = "";

    protected String CONTROLLER_PACKAGER = null;

    protected Set<String> beanDefinitionNames = new HashSet<String>();

    protected List<Object> controllerBeans = new ArrayList<Object>();

    protected List<String> urlMapping = new ArrayList<String>();

    protected String rootPath;

    protected Map<String, Object> interceptorBeans = new HashMap<String, Object>();

    public AbstractTinyMvcApplicationContext() throws DocumentException {
        //获取Controller包名字;
        getControllerPackagerName();
        //获取Controller类名字;
        getControllerName();
        try {
            initBeans();
        } catch (ClassNotFoundException e) {
            logger.error("找不到类");
        }
        initUrlMapping();
    }

    protected abstract void getControllerPackagerName();

    protected abstract void getControllerName();

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

    @Override
    public String gett() {
        return "123";
    }
}
