package com.github.wenbo2018.webs.web.servlst;


import com.github.wenbo2018.webs.context.ApplicationContext;
import com.github.wenbo2018.webs.core.InstanceFactory;
import com.github.wenbo2018.webs.extension.ExtensionServiceLoader;
import com.github.wenbo2018.webs.interceptor.HandlerInterceptor;
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
import com.github.wenbo2018.webs.interceptor.HandlerInterceptorChain;
import com.github.wenbo2018.webs.util.SwitcherFactory;
import com.github.wenbo2018.webs.view.ModelAndView;
import com.github.wenbo2018.webs.view.ViewResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class WebSDispatchServlet extends FrameworkServlet {

    private static final long serialVersionUID = -7148957052480828252L;

    private ViewResolver viewResolver;
    private HandlerInvoker handlerInvoker;
    private HandlerMapping handlerMapping;
    private SwitcherFactory switcherFactory;
    private HandlerInterceptor[] handlerInterceptors;

    private List<HandlerMapping> handlerMappings;
    private List<HandlerInvoker> handlerInvokers;

    @Override
    protected void onRefresh(ApplicationContext context) {
        initWebs(context);
    }

    private void initWebs(ApplicationContext context) {
        initHandlerMappings(context);
        initViewResolvers(context);
        initHandlerInvoker(context);
    }

    private void initHandlerInvoker(ApplicationContext context) {
        this.handlerInvoker=null;
        handlerInvoker=ExtensionServiceLoader.getExtension(HandlerInvoker.class);
    }

    private void initHandlerMappings(ApplicationContext context) {
        this.handlerMapping = null;
        handlerMapping = ExtensionServiceLoader.getExtension(HandlerMapping.class);
        handlerMapping.init(context);
    }

    private void initViewResolvers(ApplicationContext context) {
        this.viewResolver = null;
        viewResolver = ExtensionServiceLoader.getExtension(ViewResolver.class);
        viewResolver.init(context);
    }


    @Override
    protected void onRefresh(ServletConfig servletConfig, ServletContext servletContext) {
        handlerInvoker = InstanceFactory.getHandlerInvoker();
        handlerMapping = InstanceFactory.getHandlerMapping();
        //这里获取配置参数,判断获取模板
        viewResolver = InstanceFactory.getViewResolver(
                servletConfig.getInitParameter("viewConfig")).getViewResolverInstance();
    }


    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求编码方式
        request.setCharacterEncoding("UTF-8");
        // 获取当前请求相关数据
        String url = request.getServletPath();
        Handler handler = handlerMapping.getHandler(url);
        if (handler == null) {
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "");
            } catch (Exception e) {
                logger.error("发送错误代码出错！", e);
                throw new RuntimeException(e);
            }
            return;
        }
        ModelAndView mView = null;
        Method method = handler.getMethod();
        Object[] parameters = null;
        Map<String, String[]> parameters_name_args = request.getParameterMap();
        if (parameters_name_args.size() == 0) {
            invokeHandlerInterceptors(request, response, handler, mView, handlerInvoker, null);
        } else {
            Class<?>[] clazz = method.getParameterTypes();
            List<String> ParametersName;
            try {
                ParametersName = getMethodParametersName(method);
            } catch (Exception e1) {
                throw new ServletException(e1);
            }
            parameters = new Object[clazz.length];
            for (int i = 0; i < ParametersName.size(); i++) {
                String name = ParametersName.get(i);
                String args = (String) parameters_name_args.get(name)[0];
                if (clazz[i].equals(String.class)) {
                    parameters[i] = args;
                } else {
                    try {
                        parameters[i] = switcherFactory.switcher(clazz[i], args);
                    } catch (Exception e) {
                        response.sendError(400);
                        break;
                    }
                }
            }
        }
        if (handler != null) {
            invokeHandlerInterceptors(request, response, handler, mView, handlerInvoker, parameters);
        }
        doDispatch(mView, request, response);
    }


    private void invokeHandlerInterceptors(HttpServletRequest request, HttpServletResponse response,
                                           Handler handler, ModelAndView modelAndView,
                                           HandlerInvoker handlerInvoker, Object[] parameters) {
        HandlerInterceptorChain handlerInterceptorChain = new HandlerInterceptorChain(handlerInvoker, handlerInterceptors, parameters);
        try {
            handlerInterceptorChain.exeInterceptor(request, response, handler);
            handlerInterceptorChain.exeAfterInterceptor(request, response, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void doDispatch(ModelAndView mv, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        if (viewResolver.containView(mv.getObject().toString() + ".jsp")) {
            if (mv.getMode() != null) {
                Map<String, String> parMap = mv.getMode();
                Iterator iter = parMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    request.setAttribute((String) entry.getKey(), entry.getValue());
                }
            }
        } else {
            response.sendError(400);
        }
        //返回视图
        viewResolver.resolveView(mv, request, response);
    }

    private List<String> getMethodParametersName(Method method) throws Exception {
        List<String> name = new LinkedList<String>();
        try {
            Class clazz = method.getDeclaringClass();
            String methodName = method.getName();
            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new ClassClassPath(clazz));
            CtClass cc = pool.get(clazz.getName());
            CtMethod cm = cc.getDeclaredMethod(methodName);
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag);
            String[] paramNames = new String[cm.getParameterTypes().length];
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < paramNames.length; i++) {
                name.add(attr.variableName(i + pos));
            }
        } catch (Exception e) {
            throw e;
        }
        return name;
    }
}
