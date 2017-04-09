package com.github.wenbo2018.webs.util;

import com.github.wenbo2018.webs.annotation.WebsController;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.annotation.AnnotationType;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Created by shenwenbo on 2017/4/9.
 */
public class ClassUtils {

    private static Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    //class缓存
    private static ConcurrentMap<String, Class> classCache = new ConcurrentHashMap<>();
    //src下所有类名
    private static List<String> classNameList = new ArrayList<>();

    static {
        String packageName = "";
        String dirs = Thread.currentThread().getContextClassLoader().getResource("").getFile();
        File root = new File(dirs);
        try {
            loop(root, packageName, dirs);
        } catch (Exception e) {
            logger.error("scan class file error", e);
        }
    }

    public static List<String> findClassNameByAnnotation(Class<?> clazz) throws ClassNotFoundException {
        List<String> list = new ArrayList<>();
        for (String className : classNameList) {
            if (!Class.forName(className).isAnnotation()) {
                if (Class.forName(className).isAnnotationPresent((Class<? extends Annotation>) clazz)) {
                    list.add(className);
                }
            }
        }
        return list;
    }


    public static void loop(File folder, String packageName, String dir) throws Exception {
        File[] files = folder.listFiles();
        for (int fileIndex = 0; fileIndex < files.length; fileIndex++) {
            File file = files[fileIndex];
            if (file.isDirectory()) {
                loop(file, packageName + file.getName() + ".", dir);
            } else {
                String className = file.getPath().substring(dir.length() - 1);
                String name = className.replaceAll("\\\\", ".");
                if (!name.contains("$")) {
                    if (name.contains(".class")) {
                        classNameList.add(name.replaceAll(".class", ""));
                    }
                }
            }
        }
    }
//
//    public static void listMethodNames(String filename, String packageName) {
//        try {
//            String name = filename.substring(0, filename.length() - 5);
//            Object obj = Class.forName(packageName + name);
//            Method[] methods = obj.getClass().getDeclaredMethods();
//            System.out.println(filename);
//            for (int i = 0; i < methods.length; i++) {
//                System.out.println("\t" + methods[i].getName());
//            }
//        } catch (Exception e) {
//            System.out.println("exception = " + e.getLocalizedMessage());
//        }
//    }

    public static void main(String[] args) throws Exception {
//        loadClasses("com.github.wenbo2018.webs.context");
//        String packageName = "";
//        String dirs = Thread.currentThread().getContextClassLoader().getResource("").getFile();
//        File root = new File(dirs);
//        loop(root, packageName, dirs);
//        ClassUtils.classUtilsInit();
        List<String> list = findClassNameByAnnotation(WebsController.class);
        for (String li : list) {
            System.out.println(li);
        }

    }


}
