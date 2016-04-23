package com.tinymvc.view;

public interface JspViewResolver extends ViewResolver{
	public boolean containView(String path);
}
