package com.github.wenbo2018.webs.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by shenwenbo on 16/7/20.
 */
public class FreeMakerViewResolverFactory extends ViewResolverFactory{
    private static final Log logger = LogFactory.getLog(JspViewResolverFactory.class);

    @Override
    public void init() {
        logger.info("jspViewResolverFactory init ");
    }

    @Override
    public ViewResolver getViewResolverInstance() {
        init();
        return null;
    }
}
