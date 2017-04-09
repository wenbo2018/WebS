package com.github.wenbo2018.webs.core;


import com.github.wenbo2018.webs.handler.DefaulHandlerInvokerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.github.wenbo2018.webs.handler.HandlerInvoker;
import com.github.wenbo2018.webs.handler.HandlerMapping;
import com.github.wenbo2018.webs.util.SwitcherFactory;
import com.github.wenbo2018.webs.view.ViewResolverFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class InstanceFactory {
	
	private static final Log logger =LogFactory.getLog(InstanceFactory.class);
	/**
     * 用于缓存对应的实例
     */
    private static final Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    /**
     * HandlerMapping
     */
    private static final String HANDLER_MAPPING = "com.github.wenbo2018.webs.handler.DefaultHandlerMappingImpl";

    /**
     * HandlerInvoker
     */
    private static final String HANDLER_INVOKER = "com.github.wenbo2018.webs.handler.DefaulHandlerInvokerImpl";
    /**
     * ViewResolver
     */
    private static final String VIEW_RESOLVER = "com.github.wenbo2018.webs.view.ViewResolverFactory";

    /**
     * 参数转换工厂
     */
    
    private static final String SWITCH_FACTORY="com.github.wenbo2018.webs.util.SwitcherFactory";
    
    
    /**
     * 获取 HandlerMapping
     */
    public static HandlerMapping getHandlerMapping() {
        return getInstance(HANDLER_MAPPING, null);
    }

    /**
     * 获取 HandlerInvoker
     */
    public static HandlerInvoker getHandlerInvoker() {
        return getInstance(HANDLER_INVOKER, DefaulHandlerInvokerImpl.class);
    }

    public static ViewResolverFactory getViewResolver(String viewConfig) {
        if (viewConfig.equals("")||viewConfig==null) {
            logger.info("默认使用Jsp视图");
            return getInstance(VIEW_RESOLVER,ViewResolverFactory.class);
        }
        //使用freeMaker视图
        if(viewConfig.equals("com.FreeMakerViewResolverFactory"))
            return null;
        return null;
    }

    public static SwitcherFactory getSwitcherFactory() {
    	return getInstance(SWITCH_FACTORY,SwitcherFactory.class);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
        if (cache.containsKey(cacheKey)) {
            return (T) cache.get(cacheKey);
        }
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
        if (instance != null) {
            cache.put(cacheKey, instance);
        }
        return instance;
    }
}
