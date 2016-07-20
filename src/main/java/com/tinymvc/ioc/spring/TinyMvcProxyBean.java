package com.tinymvc.ioc.spring;

import com.tinymvc.annotation.Controller;
import com.tinymvc.annotation.InterceptorUrl;
import org.dom4j.DocumentException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import java.lang.annotation.Annotation;

/**
 * Created by shenwenbo on 16/7/16.
 */
/**
 * MVC上下文初始化中心
 */
public class TinyMvcProxyBean extends AbstractTinyMvcApplicationContext implements FactoryBean,ApplicationContextAware{
    /**
     * Spring上下文
     */
    private ApplicationContext applicationContext;
    /**
     * Contoller 所在包的名字,通过参数进行注入
     */
    private  String contollerPackageName;
    /**
     * 模板类型参数,通过参数进行注入
     */
    private  String templateParameterConfig;

    /**
     * 构造函数,Bean注入进行初始化MVC上下文
     * @throws DocumentException
     */
    public TinyMvcProxyBean() throws DocumentException {
        super();
    }

    /**
     * MVC控制器参数注入;
     * @throws ClassNotFoundException
     */
    @Override
    protected void initBeans() throws ClassNotFoundException {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        if (beanNames != null) {
                for (String beanName : beanDefinitionNames) {
                    //根据Annotation获取控制器bean
                    if (applicationContext.getBean(beanName).getClass().isAnnotationPresent(Controller.class) == true) {
                        controllerBeans.add(applicationContext.getBean(beanName));
                    }
                    //拦截器bean
                    if (applicationContext.getBean(beanName).getClass().isAnnotationPresent(InterceptorUrl.class) == true) {
                        Annotation[] classAnnotation = applicationContext.getBean(beanName).getClass().getAnnotations();
                        for (Annotation annotation : classAnnotation) {
                            //interceptorBeans.put(annotation.annotationType(InterceptorUrl.class).getClass().getName());
                        }
                    }
                }
        }
    }
    /**
     * 获取Spring上下问实现类
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
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
