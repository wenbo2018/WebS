package com.github.wenbo2018.webs.view;

import com.github.wenbo2018.webs.context.ApplicationContext;
import com.github.wenbo2018.webs.context.WebsWebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {

	public void resolveView(ModelAndView m, HttpServletRequest req, HttpServletResponse resp);
	public void init(WebsWebApplicationContext websWebApplicationContext) throws IllegalAccessException, InstantiationException;
}
