package com.tinymvc.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import com.tinymvc.ioc.DefaultWebApplicationContext;
import com.tinymvc.ioc.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

public class DefaultJspViewResolver implements ViewResolver {

	private static final Log logger =LogFactory.getLog(DefaultJspViewResolver.class);
	
	private static List<String> jspPage=new ArrayList<String>();


	public DefaultJspViewResolver() {
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

}
