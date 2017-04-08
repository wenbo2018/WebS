package tinymvc.interceptor;



import tinymvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shenwenbo on 16/7/3.
 */
public interface HandlerInterceptor {

    boolean preHandle(HttpServletRequest request,
                      HttpServletResponse response, Object handler, ModelAndView modelAndView)throws Exception;

    void postHandle(HttpServletRequest request,
                    HttpServletResponse response, Object handler) throws Exception;

}
