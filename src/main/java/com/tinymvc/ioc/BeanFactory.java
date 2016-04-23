package com.tinymvc.ioc;

import java.util.Set;

public interface BeanFactory {

	public Object getBean(String name) throws ClassNotFoundException;
	
	public <T> Class<T> getType(String name);
}
