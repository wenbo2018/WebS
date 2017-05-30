package com.github.wenbo2018.webs.handler;

import com.github.wenbo2018.webs.annotation.RequestMapping;
import com.github.wenbo2018.webs.bean.BeanDefinition;
import com.github.wenbo2018.webs.context.WebsWebApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public abstract class AbstractHandlerMapping implements HandlerMapping {

    private static Logger logger= LoggerFactory.getLogger(AbstractHandlerMapping.class);

    protected Map<String, Handler> handlerMapping = new HashMap<String, Handler>();

    protected List<BeanDefinition> beanDefinitions=new ArrayList<BeanDefinition>();

    @Override
    public void init(WebsWebApplicationContext websWebApplicationContext) throws IllegalAccessException, InstantiationException {
        if (!beanDefinitions.isEmpty()) {
            beanDefinitions.clear();
        }
        beanDefinitions=websWebApplicationContext.getBeanAsList();
        if (beanDefinitions!=null&&beanDefinitions.size()>0) {
            for (BeanDefinition beanDefinition:beanDefinitions) {
                Object bean=beanDefinition.getBean();
                Class clazz=beanDefinition.getBeanClass();
                String annotationControllerUrl = null;
                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    annotationControllerUrl = clazz.newInstance().getClass().getAnnotation(RequestMapping.class).controllerUrl();
                }
                Method[] method = clazz.getMethods();
                for (int j = 0; j < method.length; j++) {
                    if (method[j].isAnnotationPresent(RequestMapping.class)) {
                        String annotation = method[j].getAnnotation(RequestMapping.class).requestUrl();
                        Handler action = new Handler(bean, method[j]);
                        handlerMapping.put(annotationControllerUrl  + annotation, action);
                    }
                }
            }
        }
        logger.info("Webs init handler success");

    }

    @Override
    public Handler getHandler(String currentRequestPath) {
        return this.handlerMapping.get(currentRequestPath);
    }

    @Override
    public Handler getHandler(HttpServletRequest request) {
        return getHandlerFrom(request);
    }

    protected abstract Handler getHandlerFrom(HttpServletRequest request);

}
