package com.github.wenbo2018.webs.view;

import com.github.wenbo2018.webs.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {

	public void resolveView(ModelAndView m, HttpServletRequest req, HttpServletResponse resp);
	public void init(ApplicationContext applicationContext) throws IllegalAccessException, InstantiationException;
}
