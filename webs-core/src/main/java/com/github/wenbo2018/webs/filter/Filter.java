package com.github.wenbo2018.webs.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shenwenbo on 2017/4/9.
 */
public interface Filter {
    /***
     * invoke befor
     * invoke
     * invoke after
     * @param request
     * @param response
     */
    void invoke(HttpServletRequest request,HttpServletResponse response);

}
