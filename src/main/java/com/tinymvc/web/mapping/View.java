package com.tinymvc.web.mapping;

import java.util.Map;

public class View {
	
	private String url;//文件地址;
	private Map<String, Object> data; // 相关数据
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public View(String url) {
		this.url = url;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
