package com.github.wenbo2018.webs.interceptor;


import com.github.wenbo2018.webs.handler.HandlerInvoker;
import com.github.wenbo2018.webs.view.ModelAndView;
import com.github.wenbo2018.webs.handler.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by shenwenbo on 16/7/3.
 */
public class HandlerInterceptorWrapper {

    private Logger logger= LoggerFactory.getLogger(HandlerInterceptorWrapper.class);

    private List<HandlerInterceptor> handlerInterceptors;

    private HandlerInvoker handlerInvoker;

    private Object[] parameters;

    private ModelAndView modelAndView;

    private int HANDLER_INTERCEPTOR_SIZE;

    public HandlerInterceptorWrapper(HandlerInvoker handlerInvoker,
                                     List<HandlerInterceptor> handlerInterceptors, Object[] parameters) {
        this.handlerInterceptors = handlerInterceptors;
        this.handlerInvoker = handlerInvoker;
        this.parameters = parameters;
    }

    public ModelAndView exeInterceptor(HttpServletRequest request, HttpServletResponse response,Handler handler) throws Exception {
        boolean flag = true;
        if (handlerInterceptors != null) {
            String url = request.getRequestURI();
            for (int i = 0; i < handlerInterceptors.size(); i++) {
                HANDLER_INTERCEPTOR_SIZE = i;
                if (url.startsWith(handlerInterceptors.get(i).getUrl())) {
                    if (!handlerInterceptors.get(i).preHandle(request, response)) {
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag) {
          modelAndView = handlerInvoker.invokeHandler(request, response, handler, parameters);
          logger.debug("handlerInvoker success:{}",modelAndView.toString());
        }
        return modelAndView;
    }

    public void exeAfterInterceptor(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (handlerInterceptors != null) {
            if (handlerInterceptors.size() != 0) {
                String url = request.getRequestURI();
                for (int i = HANDLER_INTERCEPTOR_SIZE; i >= 0; i--) {
                    if (url.startsWith(handlerInterceptors.get(i).getUrl())) {
                        handlerInterceptors.get(i).postHandle(request, response);
                    }
                }
            }
        }

    }
}
