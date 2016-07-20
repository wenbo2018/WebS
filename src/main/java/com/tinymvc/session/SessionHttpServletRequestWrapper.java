package com.tinymvc.session;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import com.tinymvc.session.data.SessionCacheManager;
import com.tinymvc.session.data.SessionMap;

public class SessionHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	public static final String CURRENT_SESSION_ATTR = SessionHttpServletRequestWrapper.class.getName();
	
	
	private ServletContext servletContext;
	private int maxActiveTime;
	
	

	public SessionHttpServletRequestWrapper(HttpServletRequest request, int maxActiveTime, ServletContext servletContext) {
		super(request);
		this.servletContext = servletContext;
		this.maxActiveTime = maxActiveTime;
	}
	
	@Override
	public HttpSession getSession() {
		return getSession(true);
	}
	
    @Override
    public HttpSession getSession(boolean create) {
    	
    	HttpSessionWrapper currentSession = getCurrentSession();
		if(currentSession != null) {
			return currentSession;
		}
    	
    	String sessionId = null;
    	Cookie[] cookies = this.getCookies();

    	if(cookies != null){
    		for(Cookie cookie:cookies){
    			if("JSESSIONID".equals(cookie.getName().toUpperCase())){
    				sessionId = cookie.getValue();
    			}
    		}
    	}

		if(sessionId != null) {
			SessionMap sessionMap = SessionCacheManager.getSessionCache().get(sessionId);
			if(sessionMap != null && sessionMap.isInvalidated() == false) {
				currentSession = new HttpSessionWrapper(sessionMap, SessionCacheManager.getSessionCache(), maxActiveTime, servletContext);
				currentSession.setNew(false);
				setCurrentSession(currentSession);
				return currentSession;
			}
		}
		
		if(!create) {
			return null;
		}
/*		if(StringUtils.isNotEmpty(sessionId)){
			SessionCacheManager.getSessionCache().destroy(sessionId);
		}*/
		HttpSession httpSession = super.getSession();
		SessionMap sessionMap = new SessionMap(httpSession);
		currentSession = new HttpSessionWrapper(sessionMap, SessionCacheManager.getSessionCache(), maxActiveTime, servletContext);
		SessionCacheManager.getSessionCache().put(sessionMap.getId(), sessionMap, maxActiveTime);
		setCurrentSession(currentSession);
		return currentSession;
    }
    
	private HttpSessionWrapper getCurrentSession() {
		return (HttpSessionWrapper) getAttribute(CURRENT_SESSION_ATTR);
	}

	private void setCurrentSession(HttpSessionWrapper currentSession) {
		if(currentSession == null) {
			removeAttribute(CURRENT_SESSION_ATTR);
		} else {
			setAttribute(CURRENT_SESSION_ATTR, currentSession);
		}
	}
	
    public boolean isRequestedSessionIdValid() {
    	HttpSessionWrapper httpSessionWrapper = (HttpSessionWrapper) this.getSession(false);
    	if(httpSessionWrapper == null){
    		return true;
    	}
		return httpSessionWrapper.isInvalidated();
    }
}
