package com.github.wenbo2018.webs.util;

import com.github.wenbo2018.webs.context.WebsWebApplicationContext;
import org.springframework.util.Assert;

import javax.servlet.ServletContext;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public class WebsWebApplicationContextUtils {

    public static WebsWebApplicationContext getRequiredWebApplicationContext(ServletContext sc)
            throws IllegalStateException {

        WebsWebApplicationContext wac = getWebApplicationContext(sc);
        if (wac == null) {
            throw new IllegalStateException("No WebApplicationContext found: no ContextLoaderListener registered?");
        }
        return wac;
    }

    public static WebsWebApplicationContext getWebApplicationContext(ServletContext sc) {
        return getWebApplicationContext(sc, WebsWebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    }

    public static WebsWebApplicationContext getWebApplicationContext(ServletContext sc, String attrName) {
        Assert.notNull(sc, "ServletContext must not be null");
        Object attr = sc.getAttribute(attrName);
        if (attr == null) {
            return null;
        }
        if (attr instanceof RuntimeException) {
            throw (RuntimeException) attr;
        }
        if (attr instanceof Error) {
            throw (Error) attr;
        }
        if (attr instanceof Exception) {
            throw new IllegalStateException((Exception) attr);
        }
        if (!(attr instanceof WebsWebApplicationContext)) {
            throw new IllegalStateException("Context attribute is not of type WebApplicationContext: " + attr);
        }
        return (WebsWebApplicationContext) attr;
    }

}
