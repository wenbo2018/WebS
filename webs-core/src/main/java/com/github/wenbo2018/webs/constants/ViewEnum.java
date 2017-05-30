package com.github.wenbo2018.webs.constants;

/**
 * Created by wenbo.shen on 2017/5/29.
 */
public enum ViewEnum {
    JSON("Json", 1), FTL("Freemaker", 2), JSP("Jsp", 3);
    private String name;
    private int index;
    private ViewEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public static String getName(int index) {
        for (ViewEnum c : ViewEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
