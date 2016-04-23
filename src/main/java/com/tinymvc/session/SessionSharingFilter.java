package com.tinymvc.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.tinymvc.session.data.SessionCacheManager;

public class SessionSharingFilter implements Filter {
	
	//最大存活时间;
	private static final int MAX_ACTIVE_TIME = 60*30;//秒
	private static final String MAX_ACTIVE_TIME_KEY = "maxActiveTime";
	
	private ServletContext servletContext;
	private int maxActiveTime = MAX_ACTIVE_TIME;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
		String matString = filterConfig.getInitParameter(MAX_ACTIVE_TIME_KEY);
		if(StringUtils.isNotEmpty(matString)){
			maxActiveTime = Integer.valueOf(matString);
		}
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse HttpServletResponse = (HttpServletResponse)response;
		SessionHttpServletRequestWrapper sessionHttpServletRequestWrapper = new SessionHttpServletRequestWrapper(httpServletRequest, maxActiveTime, servletContext);
        try{
        	chain.doFilter(sessionHttpServletRequestWrapper, HttpServletResponse);
		}catch(Exception e){
			e.printStackTrace();
		}
        finally {
			if(sessionHttpServletRequestWrapper.isRequestedSessionIdValid()){
				Cookie cookie = new Cookie("JSESSIONID", null);
				cookie.setMaxAge(0);
				HttpServletResponse.addCookie(cookie);
				HttpSession httpSession = sessionHttpServletRequestWrapper.getSession(false);
				if(httpSession != null){
					SessionCacheManager.getSessionCache().put(httpSession.getId(), null, 0);
				}
			}
		}
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
