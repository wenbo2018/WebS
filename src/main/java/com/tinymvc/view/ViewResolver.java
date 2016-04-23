package com.tinymvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {

	void resolveView(ModelAndView m,HttpServletRequest req,HttpServletResponse resp);
}
