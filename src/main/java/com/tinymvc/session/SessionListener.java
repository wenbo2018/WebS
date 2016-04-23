/**
 * Copyright@xiaocong.tv 2012
 */
package com.tinymvc.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author weijun.ye
 * @version
 * @date 2012-5-2
 */
public class SessionListener implements HttpSessionListener{
	
	private static final int MAX_INACTIVE_INTERVAL = 1000*60*60*8;

    public void sessionCreated(HttpSessionEvent event) {
    	//ignore
    }
    public void sessionDestroyed(HttpSessionEvent event) {
    	//ignore
    }
}
