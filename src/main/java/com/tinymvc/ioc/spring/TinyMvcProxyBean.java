package com.tinymvc.ioc.spring;

import com.tinymvc.annotation.Controller;
import com.tinymvc.annotation.InterceptorUrl;
import org.dom4j.DocumentException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by shenwenbo on 16/7/16.
 */
public class TinyMvcProxyBean extends AbstractTinyMvcApplicationContext implements FactoryBean,ApplicationContextAware{

    private ApplicationContext applicationContext;

    public TinyMvcProxyBean() throws DocumentException {
        super();
    }

    /**
     * 初始化上下文环境
     */
    public void  init() {
        System.err.println();
    }

    @Override
    protected void getControllerPackagerName() {

    }

    @Override
    protected void getControllerName() {

    }

    @Override
    protected void initBeans() throws ClassNotFoundException {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        if (beanNames != null) {
                for (String beanName : beanDefinitionNames) {
                    if (applicationContext.getBean(beanName).getClass().isAnnotationPresent(Controller.class) == true) {
                        controllerBeans.add(applicationContext.getBean(beanName));
                    }
                    if (applicationContext.getBean(beanName).getClass().isAnnotationPresent(InterceptorUrl.class) == true) {
                        Annotation[] classAnnotation = applicationContext.getBean(beanName).getClass().getAnnotations();
                        for (Annotation annotation : classAnnotation) {
                            //interceptorBeans.put(annotation.annotationType(InterceptorUrl.class).getClass().getName());
                        }
                    }
                }
        }
    }
    private String getRootPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
        path = path.replace('/', '\\');
        path = path.replace("file:", "");
        path = path.replace("classes\\", "");
        path = path.substring(1);
        return path;
    }

    public void getAllFileName(String path, ArrayList<String> fileName) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getAbsolutePath();
        }
        if (names != null)
            fileName.addAll(Arrays.asList(names));
        for (File a : files) {
            if (a.isDirectory()) {
                getAllFileName(a.getAbsolutePath(), fileName);
            }
        }
    }


    @Override
    public void initUrlMapping() {
        try {
            this.rootPath = getRootPath() + "\\jsp";
            ArrayList<String> listFileName = new ArrayList<String>();
            getAllFileName(rootPath, listFileName);
            for (String name : listFileName) {
                String temp = name.substring(rootPath.length(), name.length());
                urlMapping.add(temp.replace('\\', '/'));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
}
