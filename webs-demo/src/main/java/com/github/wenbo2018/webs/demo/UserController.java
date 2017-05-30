package com.github.wenbo2018.webs.demo;

import com.github.wenbo2018.webs.annotation.Json;
import com.github.wenbo2018.webs.annotation.RequestMapping;
import com.github.wenbo2018.webs.annotation.WebsController;

import java.util.ArrayList;
import java.util.List;

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

    @Json
    @RequestMapping(requestUrl ="/user/json")
    public List<User> test3() {
        List<User> list=new ArrayList<>();

      for (int i=0;i<100;i++) {
          User user=new User();
          user.setUserName("wenbo");
          user.setPassWord("1234"+i);
          list.add(user);
      }
      return list;
    }
}
