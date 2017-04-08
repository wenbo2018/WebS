package tinymvc.handler;




import tinymvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaulHandlerInvokerImpl implements HandlerInvoker{
	@Override
	public ModelAndView invokeHandler(HttpServletRequest request, HttpServletResponse response,
									  Handler handler, Object[] args) throws Exception {
		   return (ModelAndView) handler.getMethod().invoke(handler.getInstance(),args);
	}
}
