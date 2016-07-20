package com.tinymvc.session.data;



/**
 * 
 * @author yeweijun
 */
public interface SessionCache {
	
	/**
	 * 存储session到分布式缓存
	 * @param sessionId 当前会话id
	 * @param sessionMap 值对象
	 * @param timeout 过期时间
	 */
	public void put(String sessionId, SessionMap sessionMap, int timeout);
	
	
	/**
	 * 从分布式缓存获取会话
	 * @param sessionId 当前会话id
	 * @return 会话对象
	 */
	public SessionMap get(String sessionId);
	
	
	/**
	 * 设置会话有效时间
	 * @param sessionId 当前会话id
	 * @param interval 有效时间，单位秒
	 */
	public void setMaxInactiveInterval(String sessionId, int interval);
	
	
	/**
	 * 销毁当前会话
	 * @param sessionId 当前会话id
	 */
	public void destroy(String sessionId);
	
	
	

}
