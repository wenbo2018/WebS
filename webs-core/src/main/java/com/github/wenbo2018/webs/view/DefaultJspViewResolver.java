package com.github.wenbo2018.webs.view;

import com.github.wenbo2018.webs.context.ApplicationContext;
import com.github.wenbo2018.webs.ioc.spring.WebApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultJspViewResolver implements ViewResolver {

	private static final Log logger =LogFactory.getLog(DefaultJspViewResolver.class);
	
	private static List<String> jspPage=new ArrayList<String>();

	private HashMap<String,String> viewMap=new HashMap<>();


	@Resource
	private WebApplicationContext webApplicationContext;

	public DefaultJspViewResolver() {
		jspPage= webApplicationContext.getResponse();
	}

	@Override
	public void resolveView(ModelAndView m, HttpServletRequest req, HttpServletResponse resp) {
		if(m!=null) {
			try {
			Pattern pattern =Pattern.compile("redict:");
			Matcher matcher=pattern.matcher(m.getObject().toString());
			//匹配成功说明是重定向;
			if (matcher.matches()) {
               String path=matcher.replaceFirst("");
				resp.sendRedirect("/WEB-INF/jsp/" +path+".jsp");
			}
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/" + m.getObject().toString()+".jsp");
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

	@Override
	public void init(ApplicationContext applicationContext) {

	}


}
