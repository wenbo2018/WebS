package com.tinymvc.view;


/**
 * Created by shenwenbo on 16/7/16.
 * 抽象工厂
 */

public abstract class ViewResolverFactory {

    private static ViewResolverFactory instance;

    public static ViewResolverFactory getInstance() {
        return instance;
    }

    public static void setInstance(ViewResolverFactory instance) {
        ViewResolverFactory.instance = instance;
    }

    /**
     * 初始化
     */
    public abstract void init();

    public abstract ViewResolver getViewResolverInstance();


}
