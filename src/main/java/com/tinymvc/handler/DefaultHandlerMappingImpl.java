package com.tinymvc.handler;

import com.tinymvc.annotation.RequestMapping;
import com.tinymvc.ioc.DefaultWebApplicationContext;
import com.tinymvc.ioc.WebApplicationContext;
import org.dom4j.DocumentException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认处理器映射
 *
 * @author huangyong
 * @since 2.3
 */
public class DefaultHandlerMappingImpl implements HandlerMapping {

	private static Map<String,Handler> handlerMapping= new HashMap<String, Handler>();

	static{
		    WebApplicationContext webApplicationContext = null;
			try {
				webApplicationContext = new DefaultWebApplicationContext();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		    List<Object> controllerBean =webApplicationContext.getController();
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
}
