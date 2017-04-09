package com.github.wenbo2018.webs.handler;

import com.github.wenbo2018.webs.annotation.RequestMapping;
import com.github.wenbo2018.webs.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public class DefaultHandlerMapping extends AbstractHandlerMapping {

    @Override
    protected Handler getHandlerFrom(HttpServletRequest request) {
        return handlerMapping.get(request.getServletPath());
    }

    @Override
    public void init(ApplicationContext applicationContext) {
        List<Object> controllerBean = applicationContext.getObjects();
        int size = controllerBean.size();
        for (int i = 0; i < size; i++) {
            Object obj = controllerBean.get(i);
            Class clazz = obj.getClass();
            Method[] method = clazz.getMethods();
            for (int j = 0; j < method.length; j++) {
                if (method[j].isAnnotationPresent(RequestMapping.class)) {
                    String annotation = method[j].getAnnotation(RequestMapping.class).value();
                    Handler action = new Handler(obj, method[j]);
                    handlerMapping.put(annotation, action);
                }
            }
        }
    }
}
