package com.github.wenbo2018.webs.context;


import com.github.wenbo2018.webs.annotation.WebsController;
import com.github.wenbo2018.webs.bean.BeanDefinition;
import com.github.wenbo2018.webs.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by shenwenbo on 2017/4/9.
 */

public abstract class AbstractWebsWebApplicationContext implements WebsWebApplicationContext {

    private static Logger logger = LoggerFactory.getLogger(AbstractWebsWebApplicationContext.class);

    protected ConcurrentMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    private ServletContext ServletContext;

    private volatile static boolean isInitContext = false;

    @Override
    public ServletContext getServletContext() {
        return this.ServletContext;
    }

    @Override
    public void initContext(ServletContext servletContext) {
        this.ServletContext = servletContext;
        synchronized (this) {
            if (!isInitContext) {
                try {
                    if (beanDefinitionMap != null || beanDefinitionMap.size() > 0) {
                        beanDefinitionMap.clear();
                    }
                    List<String> classNames = ClassUtils.findClassNameByAnnotation(WebsController.class);
                    if (classNames != null && classNames.size() > 0) {
                        for (String className : classNames) {
                            BeanDefinition beanDefinition = new BeanDefinition();
                            Object object = Class.forName(className).newInstance();
                            beanDefinition.setBean(object);
                            beanDefinition.setBeanClassName(className);
                            beanDefinition.setBeanClass(Class.forName(className));
                            beanDefinitionMap.put(className, beanDefinition);
                        }
                    }
                    logger.info("WebS init context success");
                    isInitContext = true;
                } catch (ClassNotFoundException e) {
                    logger.error("class not find WebsController.class:{}", e);
                } catch (InstantiationException e) {
                    logger.error("class Instantiation error:{}", e);
                } catch (IllegalAccessException e) {
                    logger.error("not has class access:{}", e);
                }
            }
        }
    }

    @Override
    public Object getBean(String className) {
        return beanDefinitionMap.get(className).getBean();
    }

    @Override
    public List<BeanDefinition> getBeanAsList() {
        List<BeanDefinition> beanDefinitions=new ArrayList<BeanDefinition>();
        Iterator<Map.Entry<String, BeanDefinition>> entries = beanDefinitionMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, BeanDefinition> entry = entries.next();
            beanDefinitions.add(entry.getValue());
        }
        return beanDefinitions;
    }
}

