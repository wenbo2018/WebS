package com.tinymvc.interceptor;

/**
 * Created by shenwenbo on 16/7/3.
 */
public interface Interceptor {
    /**
     * 销毁
     */
    void destory();

    /**
     * 初始化
     */
    void init();
    /**
     * 执行intercptor
     */
    boolean doInterceptor();


    void afterInterceptor();
}
