package com.github.wenbo2018.webs.web.servlst;

import com.github.wenbo2018.webs.context.WebsWebApplicationContext;
import com.github.wenbo2018.webs.context.utils.WebsWebApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class FrameworkServlet extends HttpServletBean {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(FrameworkServlet.class);
    private WebsWebApplicationContext websWebApplicationContext;
    private boolean publishContext = true;

    public static final String SERVLET_CONTEXT_PREFIX = FrameworkServlet.class.getName() + ".CONTEXT.";

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


    protected final void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doService(request, response);
        } catch (Exception e) {
            logger.error("servlet invoke fail", e);
        } finally {

        }
    }


    @Override
    protected void initServletBean(ServletConfig servletConfig) throws ServletException {
        this.websWebApplicationContext = initWebApplicationContext();
        onRefresh(this.websWebApplicationContext);
    }


    protected void onRefresh(WebsWebApplicationContext context) {

    }

    protected WebsWebApplicationContext initWebApplicationContext() {
        WebsWebApplicationContext rootContext =
                WebsWebApplicationContextUtils.getWebApplicationContext(getServletContext());
        if (publishContext) {
            String attrName = getServletContextAttributeName();
            getServletContext().setAttribute(attrName, rootContext);
        }
        return rootContext;
    }

    public String getServletContextAttributeName() {
        return SERVLET_CONTEXT_PREFIX + getServletName();
    }
}
