package com.github.wenbo2018.webs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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
    private static List<String> classNameList=new ArrayList<>();

    static  {
        String packageName = "";
        String dirs = Thread.currentThread().getContextClassLoader().getResource("").getFile();
        File root = new File(dirs);
        try {
            loop(root, packageName, dirs);
        } catch (Exception e) {
            logger.error("scan class file error", e);
        }
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
                    classNameList.add(name);
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
        for (String li:classNameList) {
            System.out.println(li);
        }
    }


}
