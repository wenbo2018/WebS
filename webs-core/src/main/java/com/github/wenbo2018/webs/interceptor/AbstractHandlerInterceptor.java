package com.github.wenbo2018.webs.interceptor;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shenwenbo on 16/7/3.
 */
public abstract class AbstractHandlerInterceptor implements HandlerInterceptor {

    private String url;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return this.url;
    }
}
