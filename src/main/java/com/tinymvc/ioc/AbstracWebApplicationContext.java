package com.tinymvc.ioc;


import java.io.File;
import java.util.*;
import com.tinymvc.util.WebUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Created by shenwenbo on 16/7/14.
 */
public abstract class AbstracWebApplicationContext implements WebApplicationContext {


    private static final Log logger = LogFactory.getLog(DefaultWebApplicationContext.class);

    private static final String DEFAULT_CONFIGURATION_FILE = "tiny-mvc.xml";

    protected  static String CONFIGURATION_FILE = "";

    protected String CONTROLLER_PACKAGER = null;

    protected Set<String> beanDefinitionNames = new HashSet<String>();

    protected List<Object> controllerBeans = new ArrayList<Object>();

    protected List<String> urlMapping = new ArrayList<String>();


    protected String rootPath;

    protected Map<String, Object> interceptorBeans = new HashMap<String, Object>();


    public AbstracWebApplicationContext() throws DocumentException{
        //获取Controller包名字;
        getControllerPackagerName();
        //获取Controller类名字;
        getControllerName();
        try {
            initBeans(beanDefinitionNames);
        } catch (ClassNotFoundException e) {
            logger.error("找不到类");
        }
        initUrlMapping();
    }


    protected abstract void initBeans(Set<String> beanDefinitionNames) throws ClassNotFoundException;




    public void initUrlMapping() {
        try {
            this.rootPath = getRootPath() + "\\jsp";
            ArrayList<String> listFileName = new ArrayList<String>();
            getAllFileName(rootPath, listFileName);
            for (String name : listFileName) {
                String temp = name.substring(rootPath.length(), name.length());
                urlMapping.add(temp.replace('\\', '/'));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public  void getNodes(Element node) {
        if (node.getName() == "context-component-scan") {
            List<Attribute> basePackage = node.attributes();
            for (Attribute pak : basePackage) {
                if (pak.getName() == "base-package") {
                    this.CONTROLLER_PACKAGER = pak.getValue();
                }
            }
            return;
        }
        List<Element> listElement = node.elements();
        for (Element e : listElement) {
            getNodes(e);
        }


    }


    private  void  getControllerPackagerName() {
        String resourcesPath =AbstracWebApplicationContext.class.getClassLoader().getResource("/tiny-mvc.xml").getPath();
        if(resourcesPath==null) {
            logger.error("Configuration file could not be found, please add the configuration file");
        }
        resourcesPath = resourcesPath.replaceAll("\\\\", "\\\\\\\\");
        try {
            SAXReader sax=new SAXReader();
            File xmlFile=new File(resourcesPath);
            Document document=sax.read(xmlFile);
            Element root=document.getRootElement();
            getNodes(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected abstract void getControllerName() throws DocumentException ;

    @Override
    public Object getBean(String name) throws ClassNotFoundException {
        Class cl=Class.forName(name);
        Object object = null;
        try {
            object = cl.newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        return object;
    }


    private String getRootPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
        path = path.replace('/', '\\');
        path = path.replace("file:", "");
        path = path.replace("classes\\", "");
        path = path.substring(1);
        return path;
    }

    public void getAllFileName(String path, ArrayList<String> fileName) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getAbsolutePath();
        }
        if (names != null)
            fileName.addAll(Arrays.asList(names));
        for (File a : files) {
            if (a.isDirectory()) {
                getAllFileName(a.getAbsolutePath(), fileName);
            }
        }
    }

    public Set<String> getBeanDefinitionNames() {
        return beanDefinitionNames;
    }

    @Override
    public <T> Class<T> getType(String name) {
        return null;
    }

    @Override
    public List<Object> getController() {
        return controllerBeans;
    }

    @Override
    public List<String> getResponse() {
        return urlMapping;
    }
}
