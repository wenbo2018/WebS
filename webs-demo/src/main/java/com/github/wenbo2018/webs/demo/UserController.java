package com.github.wenbo2018.webs.demo;

import com.github.wenbo2018.webs.annotation.RequestMapping;

/**
 * Created by shenwenbo on 2017/4/8.
 */
public class UserController {

    @RequestMapping("/index")
    public String userController() {
        System.out.println("test");
        return null;
    }

}
