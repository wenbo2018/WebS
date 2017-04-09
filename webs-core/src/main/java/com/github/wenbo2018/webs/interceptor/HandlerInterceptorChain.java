package com.github.wenbo2018.webs.interceptor;



import com.github.wenbo2018.webs.handler.HandlerInvoker;
import com.github.wenbo2018.webs.view.ModelAndView;
import com.github.wenbo2018.webs.handler.Handler;

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

    public HandlerInterceptorChain(HandlerInvoker handlerInvoker,
                                   HandlerInterceptor[] handlerInterceptors,Object[] parameters) {
        this.handlerInterceptors=handlerInterceptors;
        this.handlerInvoker=handlerInvoker;
        this.parameters=parameters;
    }

    public void exeInterceptor(HttpServletRequest request, HttpServletResponse response,
                               Handler handler)throws Exception{
        boolean flag=true;
        if (handlerInterceptors!=null) {
            for (int i = 0; i < handlerInterceptors.length; i++) {
                HANDLER_INTERCEPTOR_SIZE = i;
                if (!handlerInterceptors[i].preHandle(request, response, handler, modelAndView)) {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            handlerInvoker.invokeHandler(request,response,handler,parameters);
        }
    }

    public void exeAfterInterceptor(HttpServletRequest request, HttpServletResponse response,
                                    Object handler) throws  Exception{
        if (handlerInterceptors!=null) {
            if(handlerInterceptors.length!=0){
                for (int i =HANDLER_INTERCEPTOR_SIZE; i>= 0; i--) {
                    handlerInterceptors[i].postHandle(request,response,handler);
                }
            }
        }

    }
}
