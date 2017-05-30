package com.github.wenbo2018.webs.web.servlst;


import com.github.wenbo2018.webs.context.ApplicationContext;
import com.github.wenbo2018.webs.context.WebsWebApplicationContext;
import com.github.wenbo2018.webs.extension.ExtensionServiceLoader;
import com.github.wenbo2018.webs.interceptor.HandlerInterceptor;
import com.github.wenbo2018.webs.interceptor.WebsInterceptor;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import com.github.wenbo2018.webs.handler.Handler;
import com.github.wenbo2018.webs.handler.HandlerInvoker;
import com.github.wenbo2018.webs.handler.HandlerMapping;
import com.github.wenbo2018.webs.interceptor.HandlerInterceptorWrapper;
import com.github.wenbo2018.webs.util.SwitcherFactory;
import com.github.wenbo2018.webs.view.ModelAndView;
import com.github.wenbo2018.webs.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


public class WebSDispatchServlet extends FrameworkServlet {

    private static Logger logger = LoggerFactory.getLogger(WebSDispatchServlet.class);

    private static final long serialVersionUID = -7148957052480828252L;

    private ViewResolver viewResolver;
    private HandlerInvoker handlerInvoker;
    private HandlerMapping handlerMapping;
    private WebsInterceptor interceptor;
    private SwitcherFactory switcherFactory;
    private HandlerInterceptor[] handlerInterceptors;
    private List<HandlerMapping> handlerMappings;
    private List<HandlerInvoker> handlerInvokers;

    @Override
    protected void onRefresh(WebsWebApplicationContext websWebApplicationContext) {
        logger.info("WebSDispatchServlet init");
        initWebs(websWebApplicationContext);
    }

    private void initWebs(WebsWebApplicationContext websWebApplicationContext) {
        initHandlerMappings(websWebApplicationContext);
        initViewResolvers(websWebApplicationContext);
        initHandlerInvoker(websWebApplicationContext);
        initInterceptor(websWebApplicationContext);
    }


    private void initHandlerInvoker(WebsWebApplicationContext websWebApplicationContext) {
        this.handlerInvoker = null;
        handlerInvoker = ExtensionServiceLoader.getExtension(HandlerInvoker.class);
    }

    private void initHandlerMappings(WebsWebApplicationContext websWebApplicationContext) {
        this.handlerMapping = null;
        handlerMapping = ExtensionServiceLoader.getExtension(HandlerMapping.class);
        try {
            handlerMapping.init(websWebApplicationContext);
        } catch (IllegalAccessException e) {
            logger.error("handlerMapping init fail", e);
        } catch (InstantiationException e) {
            logger.error("handlerMapping init fail", e);
        }
    }

    private void initViewResolvers(WebsWebApplicationContext websWebApplicationContext) {
        this.viewResolver = null;
        viewResolver = ExtensionServiceLoader.getExtension(ViewResolver.class);
        try {
            viewResolver.init(websWebApplicationContext);
        } catch (IllegalAccessException e) {
            logger.error("viewResolver init fail", e);
        } catch (InstantiationException e) {
            logger.error("viewResolver init fail", e);
        }
    }

    private void initInterceptor(WebsWebApplicationContext websWebApplicationContext) {
        interceptor = null;
        interceptor = ExtensionServiceLoader.getExtension(WebsInterceptor.class);
        try {
            interceptor.init(websWebApplicationContext);
        } catch (IllegalAccessException e) {
            logger.error("interceptor init fail", e);
        } catch (InstantiationException e) {
            logger.error("interceptor init fail", e);
        }
    }


    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String url = request.getRequestURI();
        Handler handler = handlerMapping.getHandler(url);
        if (handler == null) {
            logger.warn("not found request mapping");
            response.sendError(404, "The corresponding request was not found");
            return;
        }
        ModelAndView mView = new ModelAndView();
        Method method = handler.getMethod();
        Object[] parameters = null;
        Map<String, String[]> parameters_name_args = request.getParameterMap();
        if (parameters_name_args.size() == 0) {
            mView = invokeHandlerInterceptors(request, response, handler, handlerInvoker, null);
        } else {
            Class<?>[] clazz = method.getParameterTypes();
            List<String> ParametersName=new ArrayList<String>();
            try {
                ParametersName = getMethodParametersName(method);
            } catch (Exception e) {
                logger.error("get Method Parameters Name error:{}", e);
            }
            parameters = new Object[clazz.length];
            for (int i = 0; i < ParametersName.size(); i++) {
                String name = ParametersName.get(i);
                String args = (String) parameters_name_args.get(name)[0];
                if (clazz[i].equals(String.class)) {
                    parameters[i] = args;
                } else {
                    try {
                        parameters[i] = SwitcherFactory.switcher(clazz[i], args);
                    } catch (Exception e) {
                        logger.error("server parameter parse error:{}",e);
                        response.sendError(500,"server parameter parse error");
                        break;
                    }
                }
            }
            if (handler != null) {
                mView = invokeHandlerInterceptors(request, response, handler, handlerInvoker, parameters);
            }
        }
        doDispatch(mView, request, response);
    }


    private ModelAndView invokeHandlerInterceptors(HttpServletRequest request, HttpServletResponse response, Handler handler,
                                                   HandlerInvoker handlerInvoker, Object[] parameters) {
        List<HandlerInterceptor> handlerInterceptors = interceptor.getInterceptor();
        HandlerInterceptorWrapper handlerInterceptorChain =
                new HandlerInterceptorWrapper(handlerInvoker, handlerInterceptors, parameters);
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView = handlerInterceptorChain.exeInterceptor(request, response, handler);
            handlerInterceptorChain.exeAfterInterceptor(request, response);
        } catch (Exception e) {
            logger.error("handler chain invoker fail", e);
        }
        return modelAndView;
    }


    protected void doDispatch(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (mv.getMode() != null) {
            Map<String, String> parMap = mv.getMode();
            Iterator iter = parMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                request.setAttribute((String) entry.getKey(), entry.getValue());
            }
        }
        viewResolver.resolveView(mv, request, response);
    }

    private List<String> getMethodParametersName(Method method) throws Exception {
        List<String> name = new LinkedList<String>();
        Class clazz = method.getDeclaringClass();
        String methodName = method.getName();
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(clazz));
        CtClass cc = pool.get(clazz.getName());
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++) {
            name.add(attr.variableName(i + pos));
        }
        return name;
    }
}
