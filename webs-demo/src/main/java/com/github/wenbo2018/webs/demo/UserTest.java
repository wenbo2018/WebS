package com.github.wenbo2018.webs.demo;

import com.github.wenbo2018.webs.annotation.WebsController;
import com.github.wenbo2018.webs.util.ClassUtils;

import java.util.List;

/**
 * Created by shenwenbo on 2017/4/9.
 */
public class UserTest {
    public static void main(String[] args) throws ClassNotFoundException {
        List<String> list= ClassUtils.findClassNameByAnnotation(WebsController.class);
        for (String li : list) {
            System.out.println(li);
        }
    }
}
