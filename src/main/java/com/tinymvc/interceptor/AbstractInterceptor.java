package com.tinymvc.interceptor;

/**
 * Created by shenwenbo on 16/7/3.
 */
public abstract class AbstractInterceptor implements  Interceptor{
    @Override
    public void destory() {

    }

    @Override
    public void init() {

    }

    @Override
    public boolean doInterceptor() {
        return false;
    }

    @Override
    public void afterInterceptor() {

    }
}
