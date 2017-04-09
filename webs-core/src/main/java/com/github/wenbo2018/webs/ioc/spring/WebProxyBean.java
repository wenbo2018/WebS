package com.github.wenbo2018.webs.ioc.spring;


import com.github.wenbo2018.webs.annotation.Controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.github.wenbo2018.webs.annotation.InterceptorUrl;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created by shenwenbo on 16/7/16.
 */


public class WebProxyBean extends AbstractWebApplicationContext
        implements FactoryBean, ApplicationContextAware {

    private static final Log Logger = LogFactory.getLog(WebProxyBean.class);

    private ApplicationContext applicationContext;

    private String contollerPackageName;

    private String templateParameterConfig;

    public WebProxyBean() throws DocumentException {
        super();
    }

    public void init() {
        try {
            initBeans();
        } catch (ClassNotFoundException e) {
            Logger.error("not find class exception");
        }
    }


    @Override
    protected void initBeans() throws ClassNotFoundException {
//        WebApplicationContext rootContext =
//                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        if (beanNames != null) {
            for (String beanName : beanNames) {
                //根据Annotation获取控制器bean
                if (applicationContext.getBean(beanName).
                        getClass().isAnnotationPresent(Controller.class) == true) {
                    controllerBeans.add(applicationContext.getBean(beanName));
                }
                //拦截器bean
                if (applicationContext.getBean(beanName).
                        getClass().isAnnotationPresent(InterceptorUrl.class) == true) {
                    interceptorBeans.put(beanName, applicationContext.getBean(beanName));
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public String getContollerPackageName() {
        return contollerPackageName;
    }

    public void setContollerPackageName(String contollerPackageName) {
        this.contollerPackageName = contollerPackageName;
    }

    public String getTemplateParameterConfig() {
        return templateParameterConfig;
    }

    public void setTemplateParameterConfig(String templateParameterConfig) {
        this.templateParameterConfig = templateParameterConfig;
    }
}
