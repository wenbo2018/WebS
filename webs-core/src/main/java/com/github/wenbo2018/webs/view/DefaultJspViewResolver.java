package com.github.wenbo2018.webs.view;

import com.github.wenbo2018.webs.annotation.RequestMapping;
import com.github.wenbo2018.webs.context.ApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultJspViewResolver implements ViewResolver {

    private static final Log logger = LogFactory.getLog(DefaultJspViewResolver.class);


    private HashMap<String, String> uriViewMaps = new HashMap<>();

    protected List<Object> controllerBean = new ArrayList<Object>();

    @Override
    public void resolveView(ModelAndView m, HttpServletRequest req, HttpServletResponse resp) {
        try {
            viewCheck(m,req, resp);
            if (m != null) {
                Pattern pattern = Pattern.compile("redict:");
                Matcher matcher = pattern.matcher(req.getRequestURI());
                if (matcher.matches()) {
                    String path = matcher.replaceFirst("");
                    resp.sendRedirect("/WEB-INF/jsp/" + path + ".jsp");
                }
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/" + uriViewMaps.get(req.getRequestURI())+".jsp");
                rd.forward(req, resp);
                return;
            } else {
                logger.error("not find  jsp file");
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void viewCheck(ModelAndView m,HttpServletRequest req, HttpServletResponse response) throws IOException {
        if (!this.uriViewMaps.containsKey(req.getRequestURI())) {
            response.sendError(400);
        }
    }

    @Override
    public void init(ApplicationContext applicationContext) throws IllegalAccessException, InstantiationException {
        if (!controllerBean.isEmpty())
            controllerBean = null;
        controllerBean = applicationContext.getObjects();
        int size = controllerBean.size();
        for (int i = 0; i < size; i++) {
            Object obj = controllerBean.get(i);
            Class clazz = obj.getClass();
            String annotationControllerUrl = null;
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                annotationControllerUrl = clazz.newInstance().getClass().getAnnotation(RequestMapping.class).controllerUrl();
            }
            Method[] method = clazz.getMethods();
            for (int j = 0; j < method.length; j++) {
                if (method[j].isAnnotationPresent(RequestMapping.class)) {
                    String request = method[j].getAnnotation(RequestMapping.class).requestUrl();
                    String response = method[j].getAnnotation(RequestMapping.class).responseUrl();
                    uriViewMaps.put(annotationControllerUrl+request, response);
                }
            }
        }
    }


}
