package com.github.wenbo2018.webs.handler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public abstract class AbstractHandlerMapping implements HandlerMapping{

    protected static Map<String,Handler> handlerMapping= new HashMap<String, Handler>();



    @Override
    public Handler getHandler(String currentRequestPath) {
        return null;
    }

    @Override
    public Handler getHandler(HttpServletRequest request) {
        return getHandlerFrom(request);
    }

    protected abstract Handler getHandlerFrom(HttpServletRequest request);

}
