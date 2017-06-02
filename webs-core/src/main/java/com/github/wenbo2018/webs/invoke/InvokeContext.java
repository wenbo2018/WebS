package com.github.wenbo2018.webs.invoke;

import com.github.wenbo2018.webs.view.mapping.ViewMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wenbo.shen on 2017/5/30.
 */
public class InvokeContext {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private String url;
    private ViewMapping viewMapping;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ViewMapping getViewMapping() {
        return viewMapping;
    }

    public void setViewMapping(ViewMapping viewMapping) {
        this.viewMapping = viewMapping;
    }
}
