package com.github.wenbo2018.webs.handler;

import com.github.wenbo2018.webs.annotation.RequestMapping;
import com.github.wenbo2018.webs.context.ApplicationContext;

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

    protected Map<String, Handler> handlerMapping = new HashMap<String, Handler>();

    protected List<Object> controllerBean = new ArrayList<Object>();

    @Override
    public void init(ApplicationContext applicationContext) throws IllegalAccessException, InstantiationException {
        if (!controllerBean.isEmpty())
            controllerBean = null;
        controllerBean = applicationContext.getObjects();
        int size = controllerBean.size();
        for (int i = 0; i < size; i++) {
            Object obj = controllerBean.get(i);
            Class clazz = obj.getClass();
            String annotationControllerUrl = null;
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                annotationControllerUrl = clazz.newInstance().getClass().getAnnotation(RequestMapping.class).controllerUrl();
            }
            Method[] method = clazz.getMethods();
            for (int j = 0; j < method.length; j++) {
                if (method[j].isAnnotationPresent(RequestMapping.class)) {
                    String annotation = method[j].getAnnotation(RequestMapping.class).requestUrl();
                    Handler action = new Handler(obj, method[j]);
                    handlerMapping.put(annotationControllerUrl  + annotation, action);
                }
            }
        }
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
