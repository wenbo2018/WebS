package com.github.wenbo2018.webs.demo;

import com.github.wenbo2018.webs.annotation.RequestMapping;
import com.github.wenbo2018.webs.annotation.WebsController;

/**
 * Created by shenwenbo on 2017/4/9.
 */
@WebsController
@RequestMapping(controllerUrl = "/user")
public class AdminController {
    @RequestMapping(requestUrl = "/index",responseUrl = "user/index")
    public void test1() {
        System.out.println("test1");
    }
}
