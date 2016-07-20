package com.tinymvc.ioc;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinymvc.exception.ResourceLocationException;
import com.tinymvc.util.WebUtils;


/** 
* @ClassName: WebApplicationContext 
* @Description: TODO
* @author Wenbo Shen 
* @date Oct 22, 2015 9:00:43 PM 
*  
*/
public interface WebApplicationContext extends ApplicationContext{
	

	public List<Object> getController();
	
	public List<String> getResponse();
	 
	
}
