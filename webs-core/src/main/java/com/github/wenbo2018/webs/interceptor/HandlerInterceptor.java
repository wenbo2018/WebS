package com.github.wenbo2018.webs.interceptor;



import com.github.wenbo2018.webs.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shenwenbo on 16/7/3.
 */
public interface HandlerInterceptor {

    boolean preHandle(HttpServletRequest request, HttpServletResponse response)throws Exception;

    void postHandle(HttpServletRequest request, HttpServletResponse response) throws Exception;

    void setUrl(String url);

    String getUrl();

}
