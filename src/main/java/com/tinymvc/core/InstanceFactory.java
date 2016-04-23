package com.tinymvc.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tinymvc.util.SwitcherFactory;
import com.tinymvc.view.DefaultJspViewResolver;
import com.tinymvc.view.HandlerInvoker;
import com.tinymvc.view.JspViewResolver;
import com.tinymvc.web.mapping.DefaulHandlerInvokerImpl;
import com.tinymvc.web.mapping.DefaultHandlerMapping;
import com.tinymvc.web.mapping.HandlerMapping;


public class InstanceFactory {
	
	private static final Log logger =LogFactory.getLog(InstanceFactory.class);
	/**
     * 用于缓存对应的实例
     */
    private static final Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    /**
     * HandlerMapping
     */
    private static final String HANDLER_MAPPING = "com.tinymvc.web.mapping.DefaultHandlerMapping";

    /**
     * HandlerInvoker
     */
    private static final String HANDLER_INVOKER = "com.tinymvc.web.mapping.DefaulHandlerInvokerImpl";

    /**
     * HandlerExceptionResolver
     */
    private static final String HANDLER_EXCEPTION_RESOLVER = "smart.framework.custom.handler_exception_resolver";

    /**
     * ViewResolver
     */
    private static final String VIEW_RESOLVER = "com.tinymvc.view.DefaultJspViewResolver";

    
    private static final String SWITCH_FACTORY="com.tinymvc.util.SwitcherFactory";
    
    
    /**
     * 获取 HandlerMapping
     */
    public static HandlerMapping getHandlerMapping() {
        return getInstance(HANDLER_MAPPING, DefaultHandlerMapping.class);
    }

    /**
     * 获取 HandlerInvoker
     */
    public static HandlerInvoker getHandlerInvoker() {
        return getInstance(HANDLER_INVOKER, DefaulHandlerInvokerImpl.class);
    }

    public static JspViewResolver getViewResolver() {
    	return getInstance(VIEW_RESOLVER,DefaultJspViewResolver.class);
    }

    public static SwitcherFactory getSwitcherFactory() {
    	return getInstance(SWITCH_FACTORY,SwitcherFactory.class);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
        // 若缓存中存在对应的实例，则返回该实例
        if (cache.containsKey(cacheKey)) {
            return (T) cache.get(cacheKey);
        }
        // 通过反射创建该实现类对应的实例
        T instance=null;
		try {
			instance = (T) Class.forName(cacheKey).newInstance();
		} catch (InstantiationException e) {
			 logger.error("初始化实例出错！", e);
	         throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			logger.error("类获取失败！", e);
	         throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			 logger.error("找不到类对象！", e);
	         throw new RuntimeException(e);
		}
        // 若该实例不为空，则将其放入缓存
        if (instance != null) {
            cache.put(cacheKey, instance);
        }
        // 返回该实例
        return instance;
    }
}
