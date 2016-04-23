package com.tinymvc.session;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.tinymvc.session.data.SessionCache;
import com.tinymvc.session.data.SessionMap;

public class HttpSessionWrapper implements HttpSession {
	
	private final ServletContext servletContext;
	private SessionMap sessionMap;
	private SessionCache sessionCache;
	private boolean old;
	private int maxActiveTime;
	
	

	public HttpSessionWrapper(SessionMap sessionMap, SessionCache sessionCache, int maxActiveTime, ServletContext servletContext) {
		this.sessionMap = sessionMap;
		this.sessionCache = sessionCache;
		this.maxActiveTime = maxActiveTime;
		this.servletContext = servletContext;
	}
	
	public void setMaxInactiveInterval(int interval) {
		sessionMap.setMaxInactiveInterval(interval);
		sessionCache.setMaxInactiveInterval(sessionMap.getId(), interval);
	}
	
	public void setAttribute(String name, Object value) {
		//checkState();
		sessionMap.setAttribute(name, value);
		sessionCache.put(sessionMap.getId(), sessionMap, maxActiveTime);
	}
	
	public void removeAttribute(String name) {
		//checkState();
		sessionMap.removeAttribute(name);
		sessionCache.put(sessionMap.getId(), sessionMap, maxActiveTime);
	}

	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	public void removeValue(String name) {
		removeAttribute(name);
	}

	public long getCreationTime() {
		//checkState();
		return sessionMap.getCreationTime();
	}

	public String getId() {
		return sessionMap.getId();
	}

	public long getLastAccessedTime() {
		//checkState();
		return sessionMap.getLastAccessedTime();
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	
	public int getMaxInactiveInterval() {
		return sessionMap.getMaxInactiveInterval();
	}


	public Object getAttribute(String name) {
		//checkState();
		return sessionMap.getAttribute(name);
	}

	public Object getValue(String name) {
		return getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		//checkState();
		return Collections.enumeration(sessionMap.getAttributeNames());
	}

	public String[] getValueNames() {
		//checkState();
		Set<String> attrs = sessionMap.getAttributeNames();
		return attrs.toArray(new String[0]);
	}

	public void invalidate() {
		//checkState();
		sessionMap.setInvalidated(true);
		sessionCache.put(sessionMap.getId(), sessionMap, maxActiveTime);
		setCurrentSession(null);
	}

	public void setNew(boolean isNew) {
		this.old = !isNew;
	}

	public boolean isNew() {
		//checkState();
		return !old;
	}

	public boolean isInvalidated() {
		return sessionMap.isInvalidated();
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return null;
	}
	
	private void setCurrentSession(HttpSessionWrapper currentSession) {
		if(currentSession == null) {
			removeAttribute(SessionHttpServletRequestWrapper.CURRENT_SESSION_ATTR);
		} else {
			setAttribute(SessionHttpServletRequestWrapper.CURRENT_SESSION_ATTR, currentSession);
		}
	}
	
	private void checkState() {
		if(sessionMap.isInvalidated()) {
			throw new IllegalStateException("The HttpSession has already be invalidated.");
		}
	}

}
