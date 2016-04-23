package com.tinymvc.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinymvc.core.InstanceFactory;
import com.tinymvc.ioc.DefaultWebApplicationContext;
import com.tinymvc.ioc.WebApplicationContext;

public class DefaultJspViewResolver implements JspViewResolver {

	private static final Log logger =LogFactory.getLog(DefaultJspViewResolver.class);
	
	private static List<String> jspPage=new ArrayList<String>();
	
	static {
		WebApplicationContext webApplicationContext = null;
		try {
			webApplicationContext = new DefaultWebApplicationContext();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jspPage=webApplicationContext.getResponse();
	}
	
	@Override
	public void resolveView(ModelAndView m, HttpServletRequest req, HttpServletResponse resp) {
		if(m!=null) {
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/" + m.getObject().toString()+".jsp");
			try {
				rd.forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			logger.error("not find  jsp file");
		}
	}

	@Override
	public boolean containView(String path) {
		return jspPage.contains(path);
	}

}
