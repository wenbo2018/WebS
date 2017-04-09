package com.github.wenbo2018.webs.interceptor;

import com.github.wenbo2018.webs.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenwenbo on 2017/4/9.
 */
public class DefaultInterceptor implements WebsInterceptor {

    List<HandlerInterceptor> handlerInterceptors=new ArrayList<>();

    private List<Object> controllerBean=new ArrayList<>();

    @Override
    public List<HandlerInterceptor> getInterceptor() {
        return this.handlerInterceptors;
    }

    @Override
    public void init(ApplicationContext applicationContext) throws IllegalAccessException, InstantiationException {
        if (!controllerBean.isEmpty())
            controllerBean = null;
        controllerBean = applicationContext.getObjects();
        int size = controllerBean.size();
        for (int i = 0; i < size; i++) {
            Object obj = controllerBean.get(i);
            Class clazz = obj.getClass();
            String url = null;
            if (clazz.isAnnotationPresent(com.github.wenbo2018.webs.annotation.WebsInterceptor.class)) {
                url = clazz.newInstance().getClass().getAnnotation(com.github.wenbo2018.webs.annotation.WebsInterceptor.class).Url();
                HandlerInterceptor handlerInterceptor= (HandlerInterceptor) clazz.newInstance();
                handlerInterceptor.setUrl(url.substring(0,url.length()-1));
                handlerInterceptors.add(handlerInterceptor);
            }
        }
    }


}
