package com.github.wenbo2018.webs.view.mapping;

/**
 * Created by shenwenbo on 16/7/20.
 */
public class ViewMapping {
    /**
     * 页面路径
     */
    private String viewPath;
    /**
     * 视图类型,1表示jsp,2表示ftl
     */
    private int viewType;
    /**
     * 跳转类型
     */
    private String dispatcherType;

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getDispatcherType() {
        return dispatcherType;
    }

    public void setDispatcherType(String dispatcherType) {
        this.dispatcherType = dispatcherType;
    }
}
