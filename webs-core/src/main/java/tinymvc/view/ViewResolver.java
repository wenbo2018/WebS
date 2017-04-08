package tinymvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {

	public void resolveView(ModelAndView m, HttpServletRequest req, HttpServletResponse resp);
	public boolean containView(String path);
}
