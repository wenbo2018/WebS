package com.github.wenbo2018.webs.demo;

import com.github.wenbo2018.webs.annotation.RequestMapping;
import com.github.wenbo2018.webs.annotation.WebsController;

/**
 * Created by shenwenbo on 2017/4/8.
 */
@RequestMapping(controllerUrl ="/index")
@WebsController
public class UserController {

    @RequestMapping(requestUrl ="/test",responseUrl = "index")
    public void userController(String username) {
        System.out.println(username);
    }

    @RequestMapping(requestUrl ="/user",responseUrl = "index")
    public void test2(int sp) {
        System.out.println(sp);
    }
}
