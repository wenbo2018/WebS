package tinymvc.ioc.spring;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import tinymvc.annotation.Controller;
import tinymvc.annotation.InterceptorUrl;

/**
 * Created by shenwenbo on 16/7/16.
 */

/**
 * MVC上下文初始化中心
 */
public class WebProxyBean extends AbstractWebApplicationContext implements FactoryBean,ApplicationContextAware{

    private static final Log Logger = LogFactory.getLog(WebProxyBean.class);

    /**
     * Spring上下文
     */
    private ApplicationContext applicationContext;
    /**
     * Contoller 所在包的名字,通过参数进行注入
     */
    private  String contollerPackageName;
    /**
     * 模板类型参数,通过参数进行注入
     */
    private  String templateParameterConfig;
    /**
     * 构造函数,Bean注入进行初始化MVC上下文
     * @throws DocumentException
     */
    public WebProxyBean() throws DocumentException {
        super();
    }

    /**
     * Spring初始化方法init,需要在配置Spring bean时进行配置;
     */
    public void init() {
        try {
            initBeans();
        } catch (ClassNotFoundException e) {
            Logger.error("not find class exception");
        }
    }



    /**
     * MVC控制器参数注入;
     * @throws ClassNotFoundException
     */
    @Override
    protected void initBeans() throws ClassNotFoundException {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        if (beanNames != null) {
            for (String beanName : beanNames) {
                //根据Annotation获取控制器bean
                if (applicationContext.getBean(beanName).getClass().isAnnotationPresent(Controller.class) == true) {
                    controllerBeans.add(applicationContext.getBean(beanName));
                }
                //拦截器bean
                if (applicationContext.getBean(beanName).getClass().isAnnotationPresent(InterceptorUrl.class) == true) {
                    interceptorBeans.put(beanName,applicationContext.getBean(beanName));
                }
            }
        }
    }
    /**
     * 获取Spring上下文实现类
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public String getContollerPackageName() {
        return contollerPackageName;
    }

    public void setContollerPackageName(String contollerPackageName) {
        this.contollerPackageName = contollerPackageName;
    }

    public String getTemplateParameterConfig() {
        return templateParameterConfig;
    }

    public void setTemplateParameterConfig(String templateParameterConfig) {
        this.templateParameterConfig = templateParameterConfig;
    }
}
