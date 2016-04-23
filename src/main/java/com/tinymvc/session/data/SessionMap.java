package com.tinymvc.session.data;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

public class SessionMap implements Serializable {

	private static final long serialVersionUID = 3455295045889076281L;
	
	private String id;
	private Map<String, Object> sessionAttrs = new HashMap<String, Object>();
	private long creationTime = System.currentTimeMillis();
	private long lastAccessedTime = creationTime;
	private int maxInactiveInterval;
	private boolean invalidated;
	
	
	public SessionMap(HttpSession session) {
		if(session == null) {
			throw new IllegalArgumentException("session cannot be null");
		}
		//String uuid = UUID.randomUUID().toString();
		//this.id = uuid.replaceAll("-", "").toUpperCase();
		this.id = session.getId();
		this.sessionAttrs = new HashMap<String, Object>();
		Enumeration<?> names = session.getAttributeNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			Object attrValue = session.getAttribute(name);
			if(StringUtils.isNotEmpty(name) && attrValue != null){
				this.sessionAttrs.put(name, attrValue);
			}
		}
		this.lastAccessedTime = session.getLastAccessedTime();
		this.creationTime = session.getCreationTime();
		this.maxInactiveInterval = session.getMaxInactiveInterval();
	}
	

	public String getId() {
		return id;
	}

	public Map<String, Object> getSessionAttrs() {
		return sessionAttrs;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public boolean isExpired() {
		return isExpired(System.currentTimeMillis());
	}

	boolean isExpired(long now) {
		if(maxInactiveInterval < 0) {
			return false;
		}
		return now - TimeUnit.SECONDS.toMillis(maxInactiveInterval) >= lastAccessedTime;
	}

	public boolean equals(Object obj) {
		return obj instanceof SessionMap && id.equals(((SessionMap) obj).getId());
	}

	public int hashCode() {
		return id.hashCode();
	}

	public Object getAttribute(String attributeName) {
		return sessionAttrs.get(attributeName);
	}

	public Set<String> getAttributeNames() {
		return sessionAttrs.keySet();
	}
	
	public void setAttribute(String attributeName, Object attributeValue) {
		sessionAttrs.put(attributeName, attributeValue);
		
	}

	public void removeAttribute(String attributeName) {
		sessionAttrs.remove(attributeName);
	}
	
	public void setMaxInactiveInterval(int interval) {
		this.maxInactiveInterval = interval;
	}

	public void setId(String id) {
		this.id = id;
	}


	public boolean isInvalidated() {
		return invalidated;
	}


	public void setInvalidated(boolean invalidated) {
		this.invalidated = invalidated;
	}
	
	

}
