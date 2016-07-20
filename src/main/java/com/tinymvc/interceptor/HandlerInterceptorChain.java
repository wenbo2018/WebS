package com.tinymvc.interceptor;


import com.tinymvc.handler.Handler;
import com.tinymvc.handler.HandlerInvoker;
import com.tinymvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shenwenbo on 16/7/3.
 */
public class HandlerInterceptorChain {

    private HandlerInterceptor[] handlerInterceptors;

    private HandlerInvoker handlerInvoker;

    private Object[] parameters;

    private ModelAndView modelAndView;

    private int HANDLER_INTERCEPTOR_SIZE;

    public HandlerInterceptorChain(HandlerInvoker handlerInvoker,HandlerInterceptor[] handlerInterceptors,Object[] parameters) {
        this.handlerInterceptors=handlerInterceptors;
        this.handlerInvoker=handlerInvoker;
        this.parameters=parameters;
    }

    /**
     * 执行preHandle
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */
    public void exeInterceptor(HttpServletRequest request, HttpServletResponse response,
                               Handler handler)throws Exception{
        boolean flag=true;
        //依次执行，遇到false跳出，否则放开执行下一个拦截器
        for (int i = 0; i < handlerInterceptors.length; i++) {
            HANDLER_INTERCEPTOR_SIZE=i;
            if(!handlerInterceptors[i].preHandle(request,response,handler,modelAndView)){
                flag=false;
                break;
            }
        }
        if (flag) {
            handlerInvoker.invokeHandler(request,response,handler,parameters);
        }
    }

    /***
     * 执行postHandle
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */
    public void exeAfterInterceptor(HttpServletRequest request, HttpServletResponse response,
                                    Object handler) throws  Exception{
        if(handlerInterceptors.length!=0){//有拦截器才执行，不然会抛数组越界
            for (int i =HANDLER_INTERCEPTOR_SIZE; i>= 0; i--) {
                handlerInterceptors[i].postHandle(request,response,handler);
            }
        }
    }
}
