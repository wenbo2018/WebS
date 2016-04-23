package com.scut.controller;

import java.lang.String;
import java.util.HashMap;
import java.util.Map;

import com.tinymvc.annotation.Controller;
import com.tinymvc.annotation.RequestMapping;
import com.tinymvc.view.ModelAndView;

@Controller
public class UserController {
	@RequestMapping(value = "/user/index")
	public ModelAndView index() {
		Map<String, String> par = new HashMap<String, String>();
		par.put("username", "wenbo2018");
		par.put("password", "wenbo19910518");
		return new ModelAndView("user/index", par);
	}
}
