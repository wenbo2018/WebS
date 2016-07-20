package com.tinymvc.ioc;

import java.lang.annotation.Annotation;
import java.util.*;

import com.tinymvc.annotation.InterceptorUrl;


import com.tinymvc.annotation.Controller;
import com.tinymvc.util.WebUtils;
import org.dom4j.DocumentException;

/**
 *  默认使用自定义初始化IOC容器
 */
public class DefaultWebApplicationContext extends AbstracWebApplicationContext{

    public DefaultWebApplicationContext() throws DocumentException {

    }
    @Override
    protected void getControllerName() throws DocumentException {
        beanDefinitionNames= WebUtils.getAllClassName(super.CONTROLLER_PACKAGER);
    }
    @Override
    public void initBeans(Set<String> beanDefinitionNames) {
        try {
            for (String beanName :beanDefinitionNames) {
                if (getBean(beanName).getClass().isAnnotationPresent(Controller.class) == true) {
                    controllerBeans.add(getBean(beanName));
                }
                if(getBean(beanName).getClass().isAnnotationPresent(InterceptorUrl.class) == true) {
                    Annotation[] classAnnotation = getBean(beanName).getClass().getAnnotations();
                    for(Annotation annotation:classAnnotation) {
                        //interceptorBeans.put(annotation.annotationType(InterceptorUrl.class).getClass().getName());
                    }
                }
            }
        }catch (ClassNotFoundException e) {

        }
    }
}
