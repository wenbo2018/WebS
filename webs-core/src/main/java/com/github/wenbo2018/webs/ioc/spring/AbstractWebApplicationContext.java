package com.github.wenbo2018.webs.ioc.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.*;

/**
 * Created by shenwenbo on 16/7/16.
 */
public abstract class AbstractWebApplicationContext implements WebApplicationContext {

    private static final Log logger = LogFactory.getLog(AbstractWebApplicationContext.class);

    protected List<Object> controllerBeans = new ArrayList<Object>();

    protected List<String> urlMapping = new ArrayList<String>();

    protected String rootPath;

    protected Map<String, Object> interceptorBeans = new HashMap<String, Object>();

    public AbstractWebApplicationContext(){
        try {
            initBeans();
        } catch (ClassNotFoundException e) {
            logger.error("找不到类");
        }
        initUrlMapping();
    }

    protected abstract void initBeans() throws ClassNotFoundException;

    private String getRootPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
        path = path.replace('/', '\\');
        path = path.replace("file:", "");
        path = path.replace("classes\\", "");
        path = path.substring(1);
        return path;
    }

    public void getAllFileName(String path, ArrayList<String> fileName) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getAbsolutePath();
        }
        if (names != null)
            fileName.addAll(Arrays.asList(names));
        for (File a : files) {
            if (a.isDirectory()) {
                getAllFileName(a.getAbsolutePath(), fileName);
            }
        }
    }

    public void initUrlMapping() {
        try {
            this.rootPath = getRootPath() + "\\jsp";
            ArrayList<String> listFileName = new ArrayList<String>();
            getAllFileName(rootPath, listFileName);
            for (String name : listFileName) {
                String temp = name.substring(rootPath.length(), name.length());
                urlMapping.add(temp.replace('\\', '/'));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Object> getController() {
        return controllerBeans;
    }

    @Override
    public List<String> getResponse() {
        return urlMapping;
    }
}
