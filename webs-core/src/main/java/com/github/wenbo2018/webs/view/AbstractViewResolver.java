package com.github.wenbo2018.webs.view;

import com.alibaba.fastjson.JSON;
import com.github.wenbo2018.webs.annotation.Ftl;
import com.github.wenbo2018.webs.annotation.Json;
import com.github.wenbo2018.webs.annotation.Jsp;
import com.github.wenbo2018.webs.annotation.RequestMapping;
import com.github.wenbo2018.webs.bean.BeanDefinition;
import com.github.wenbo2018.webs.constants.ViewEnum;
import com.github.wenbo2018.webs.context.WebsWebApplicationContext;
import com.github.wenbo2018.webs.view.mapping.ViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractViewResolver implements ViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(AbstractViewResolver.class);

    private HashMap<String, String> uriViewMaps = new HashMap<>();

    protected List<Object> controllerBean = new ArrayList<Object>();

    protected List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();

    protected ConcurrentMap<String, ViewMapping> viewMappings = new ConcurrentHashMap<String, ViewMapping>();

    @Override
    public void resolveView(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURI();
        try {
            if (!viewMappings.containsKey(url)) {
                response.sendError(400, "NOT FOUND PAGE");
            }
            ViewMapping viewMapping = viewMappings.get(url);
            if (viewMapping.getView() == ViewEnum.JSON.getIndex()) {
                Object object = mv.getObject();
                String json = JSON.toJSONString(object);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                out = response.getWriter();
                out.append(json);
                logger.debug("return json data:{}", json);
                return;
            }
            if (viewMapping.getView() == ViewEnum.JSP.getIndex()) {

            }
        } catch (IOException e) {
            logger.error("view IO error:{}", e);
        }

    }


    @Override
    public void init(WebsWebApplicationContext websWebApplicationContext) throws IllegalAccessException, InstantiationException {
        if (!beanDefinitions.isEmpty()) {
            beanDefinitions.clear();
        }
        beanDefinitions = websWebApplicationContext.getBeanAsList();
        if (beanDefinitions != null && beanDefinitions.size() > 0) {
            for (BeanDefinition beanDefinition : beanDefinitions) {
                Object obj = beanDefinition.getBean();
                Class clazz = obj.getClass();
                String annotationControllerUrl = null;
                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    annotationControllerUrl = clazz.newInstance().getClass().getAnnotation(RequestMapping.class).controllerUrl();
                }
                Method[] method = clazz.getMethods();
                for (int j = 0; j < method.length; j++) {
                    if (method[j].isAnnotationPresent(RequestMapping.class)) {
                        ViewMapping viewMapping = new ViewMapping();
                        String request = method[j].getAnnotation(RequestMapping.class).requestUrl();
                        if (method[j].isAnnotationPresent(Json.class)) {
                            viewMapping.setView(ViewEnum.JSON.getIndex());
                        } else if (method[j].isAnnotationPresent(Jsp.class)) {
                            viewMapping.setView(ViewEnum.JSP.getIndex());
                            String response = method[j].getAnnotation(RequestMapping.class).responseUrl();
                            viewMapping.setPage(response);
                        } else if (method[j].isAnnotationPresent(Ftl.class)) {
                            viewMapping.setView(ViewEnum.FTL.getIndex());
                            String response = method[j].getAnnotation(RequestMapping.class).responseUrl();
                            viewMapping.setPage(response);
                        }
                        viewMappings.put(annotationControllerUrl + request, viewMapping);
                    }
                }
            }
        }
    }
}
