package com.tinymvc.handler;


import com.tinymvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerInvoker {

    /**
     * 调用 Handler
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  Handler
     * @throws Exception 异常
     */
    ModelAndView invokeHandler(HttpServletRequest request, HttpServletResponse response,
                               Handler handler, Object[] args) throws Exception;
}
