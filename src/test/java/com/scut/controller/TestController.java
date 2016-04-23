package com.scut.controller;

import com.tinymvc.annotation.Controller;
import com.tinymvc.annotation.RequestMapping;
import com.tinymvc.view.ModelAndView;

@Controller
public class TestController {
	@RequestMapping(value = "/index")
	public ModelAndView test(String s) {
		System.out.println(s);
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/123")
	public ModelAndView test2() {
		System.out.println("214");
		return new ModelAndView("index");
	}
}
