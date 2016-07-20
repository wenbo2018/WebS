package com.tinymvc.web.mapping;

/**
 * 处理器映射
 *
 * @author huangyong
 * @since 2.3
 */
public interface HandlerMapping {

    /**
     * 获取 Handler
     * @param currentRequestPath   当前请求路径
     * @return Handler
     */
    Handler getHandler(String currentRequestPath);
}
