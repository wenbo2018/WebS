package com.tinymvc.web.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @ClassName: SessionSharingFilter
 * @Description: TODO
 * @author Wenbo Shen
 * @date Apr 17, 2016 3:26:55 PM
 * 
 */
public class SessionSharingFilter implements Filter {

	// 最大存活时间;
	private static final int MAX_ACTIVE_TIME = 60 * 30;// 秒
	private static final String MAX_ACTIVE_TIME_KEY = "maxActiveTime";

	private ServletContext servletContext;
	private int maxActiveTime = MAX_ACTIVE_TIME;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
		String matString = filterConfig.getInitParameter(MAX_ACTIVE_TIME_KEY);
		if (matString == null) {
			maxActiveTime = Integer.valueOf(matString);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

	}

	@Override
	public void destroy() {

	}

}
