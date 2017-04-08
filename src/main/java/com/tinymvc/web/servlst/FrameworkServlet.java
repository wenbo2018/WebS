package com.tinymvc.web.servlst;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class FrameworkServlet extends HttpServletBean {

	private static final long serialVersionUID = 1L;

	protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
    	
	
	protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doService(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	@Override
	protected void initServletBean(ServletConfig config) throws ServletException {
	     onRefresh(config);
	}
	
	protected abstract void onRefresh(ServletConfig config);
}
