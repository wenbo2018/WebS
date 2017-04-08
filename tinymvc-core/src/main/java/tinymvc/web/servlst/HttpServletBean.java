package tinymvc.web.servlst;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class HttpServletBean extends HttpServlet{
      

	private static final long serialVersionUID = 4103046448849004387L;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		initServletBean(config);
	}

	protected void initServletBean(ServletConfig config) throws ServletException {}
}
