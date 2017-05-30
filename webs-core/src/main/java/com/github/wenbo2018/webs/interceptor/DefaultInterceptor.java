package com.github.wenbo2018.webs.interceptor;

import com.github.wenbo2018.webs.bean.BeanDefinition;
import com.github.wenbo2018.webs.context.WebsWebApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenwenbo on 2017/4/9.
 */
public class DefaultInterceptor implements WebsInterceptor {

    private static Logger logger = LoggerFactory.getLogger(DefaultInterceptor.class);

    List<HandlerInterceptor> handlerInterceptors = new ArrayList<>();

    private List<Object> controllerBean = new ArrayList<>();

    protected List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();

    @Override
    public List<HandlerInterceptor> getInterceptor() {
        return this.handlerInterceptors;
    }

    @Override
    public void init(WebsWebApplicationContext websWebApplicationContext) throws IllegalAccessException, InstantiationException {
        if (beanDefinitions != null && beanDefinitions.size() > 0) {
            beanDefinitions.clear();
        }
        beanDefinitions = websWebApplicationContext.getBeanAsList();
        int size = beanDefinitions.size();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Object obj = beanDefinition.getBean();
            Class clazz = beanDefinition.getBeanClass();
            String url = null;
            if (clazz.isAnnotationPresent(com.github.wenbo2018.webs.annotation.WebsInterceptor.class)) {
                url = clazz.newInstance().getClass().getAnnotation(com.github.wenbo2018.webs.annotation.WebsInterceptor.class).Url();
                HandlerInterceptor handlerInterceptor = (HandlerInterceptor) clazz.newInstance();
                handlerInterceptor.setUrl(url.substring(0, url.length() - 1));
                handlerInterceptors.add(handlerInterceptor);
            }
        }
        logger.info("Webs init WebsInterceptor success");
    }
}
