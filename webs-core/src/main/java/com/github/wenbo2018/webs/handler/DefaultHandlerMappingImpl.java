package com.github.wenbo2018.webs.handler;



import com.github.wenbo2018.webs.annotation.RequestMapping;
import com.github.wenbo2018.webs.context.ApplicationContext;
import com.github.wenbo2018.webs.ioc.spring.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DefaultHandlerMappingImpl implements HandlerMapping {

	private static Map<String,Handler> handlerMapping= new HashMap<String, Handler>();

	@Resource
	private WebApplicationContext webApplicationContext;

	public DefaultHandlerMappingImpl() {

		List<Object> controllerBean = webApplicationContext.getController();
		int size = controllerBean.size();
		for (int i = 0; i < size; i++) {
			Object obj = controllerBean.get(i);
			Class clazz = obj.getClass();
			Method[] method = clazz.getMethods();
			for (int j = 0; j < method.length; j++) {
				if (method[j].isAnnotationPresent(RequestMapping.class)) {
					String annotation = method[j].getAnnotation(RequestMapping.class).value();
					Handler action = new Handler(obj, method[j]);
					handlerMapping.put(annotation, action);
				}
			}
		}
	}

	@Override
	public Handler getHandler(String currentRequestPath) {
		return handlerMapping.get(currentRequestPath);
	}

	@Override
	public Handler getHandler(HttpServletRequest request) {
		return null;
	}

	@Override
	public void init(ApplicationContext applicationContext) {

	}
}
