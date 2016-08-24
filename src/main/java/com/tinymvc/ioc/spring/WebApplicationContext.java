package com.tinymvc.ioc.spring;

import java.util.List;

/**
 * Created by shenwenbo on 16/7/16.
 */
public interface WebApplicationContext {
    /**
     * 获取控制器对象
     * @return
     */
    public List<Object> getController();

    /**
     * 获取视图对象
     * @return
     */
    public List<String> getResponse();

}
