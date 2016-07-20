package com.tinymvc.ioc.spring;

import java.util.List;

/**
 * Created by shenwenbo on 16/7/16.
 */
public interface TinyMvcApplicationContext{

    public List<Object> getController();

    public List<String> getResponse();

    public String gett();

}
