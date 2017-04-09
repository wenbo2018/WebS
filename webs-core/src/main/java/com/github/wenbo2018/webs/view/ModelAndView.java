package com.github.wenbo2018.webs.view;

import java.util.Map;

public class ModelAndView {
	
	private Object object;
	private Map<String, String> mode;

	public ModelAndView() {
	}

	public ModelAndView(String viewName) {
		this.object = viewName;
	}

	public ModelAndView(String viewName, Map<String, String> mode) {
		this.object = viewName;
		this.mode = mode;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Map<String, String> getMode() {
		return mode;
	}

	public void setMode(Map<String, String> mode) {
		this.mode = mode;
	}
}
