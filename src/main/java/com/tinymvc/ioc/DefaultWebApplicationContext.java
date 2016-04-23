package com.tinymvc.ioc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinymvc.annotation.Controller;
import com.tinymvc.core.InstanceFactory;
import com.tinymvc.util.WebUtils;

public class DefaultWebApplicationContext implements WebApplicationContext{
	
	private static final Log logger =LogFactory.getLog(DefaultWebApplicationContext.class);
	 
    private static final String DEFAULT_CONFIGURATION_FILE="tiny-mvc.xml";
    
    private static  String CONFIGURATION_FILE="";
    
    private String CONTROLLER_PACKAGER=null;
    
    private Set<String> beanDefinitionNames=new HashSet<String>();
    
	private List<Object> controllerBeans = new ArrayList<Object>();

	private List<String> urlMapping = new ArrayList<String>();

	private String rootPath;

    
    public DefaultWebApplicationContext() throws DocumentException {
		//获取Controller包名字; 
    	getControllerPackagerName();
		//获取Controller类名字;
    	getControllerName();
    	try {
			initBeans();
		} catch (ClassNotFoundException e) {
			logger.error("找不到类");
		}
    	initUrlMapping();
	 }

    private  void getControllerPackagerName() throws DocumentException {
   	 String resourcesPath =WebApplicationContext.class.getClassLoader().getResource("/tiny-mvc.xml").getPath();
   	 if(resourcesPath==null) {
   		 logger.error("Configuration file could not be found, please add the configuration file");
   	 }
   	 resourcesPath = resourcesPath.replaceAll("\\\\", "\\\\\\\\");
    	 try {
			SAXReader sax=new SAXReader();
			 File xmlFile=new File(resourcesPath);
			 Document document=sax.read(xmlFile);
			 Element root=document.getRootElement();
			 getNodes(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    public  void getNodes(Element node) {  
   	 if(node.getName()=="context-component-scan") {
    		List<Attribute> basePackage=node.attributes();
        	for(Attribute pak:basePackage) {
        		    if(pak.getName()=="base-package") {
        		    	this.CONTROLLER_PACKAGER=pak.getValue();
        		    }
        	}
        	return ;
    	  }
	      List<Element> listElement=node.elements();
	      for(Element e:listElement){
	        getNodes(e);
	      }  
    }  

    public void getControllerName() throws DocumentException {
   	    beanDefinitionNames=WebUtils.getAllClassName(CONTROLLER_PACKAGER);
    }
    
    
	@Override
	public Object getBean(String name) throws ClassNotFoundException {
		Class cl=Class.forName(name);
		Object object = null;
		try {
			object = cl.newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return object;
	}
	
	
	@SuppressWarnings("unused")
	private void initBeans() throws ClassNotFoundException {
			for (String beanName :beanDefinitionNames) {
				if (getBean(beanName).getClass().isAnnotationPresent(Controller.class) == true) {
					controllerBeans.add(getBean(beanName));
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
	
	public Set<String> getBeanDefinitionNames() {
		return beanDefinitionNames;
	}

	@Override
	public <T> Class<T> getType(String name) {
		return null;
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
