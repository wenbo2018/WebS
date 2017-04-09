package com.github.wenbo2018.webs.handler;

import com.github.wenbo2018.webs.annotation.RequestMapping;
import com.github.wenbo2018.webs.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public class DefaultHandlerMapping extends AbstractHandlerMapping {

    @Override
    protected Handler getHandlerFrom(HttpServletRequest request) {
        return handlerMapping.get(request.getServletPath());
    }
}
