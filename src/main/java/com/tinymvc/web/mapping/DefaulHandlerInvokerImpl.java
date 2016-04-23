package com.tinymvc.web.mapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinymvc.view.HandlerInvoker;
import com.tinymvc.view.ModelAndView;
import com.tinymvc.web.mapping.Handler;

public class DefaulHandlerInvokerImpl implements HandlerInvoker{

	@Override
	public ModelAndView invokeHandler(HttpServletRequest request, HttpServletResponse response, 
			Handler handler,Object[] args) throws Exception {
		   return (ModelAndView) handler.getMethod().invoke(handler.getInstance(),args);
	}
}
