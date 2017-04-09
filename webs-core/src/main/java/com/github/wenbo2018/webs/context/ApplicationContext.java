package com.github.wenbo2018.webs.context;

import java.util.List;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public interface ApplicationContext {

    Object getBean(String name);
    List<Object> getObjects();
    void init();

}
