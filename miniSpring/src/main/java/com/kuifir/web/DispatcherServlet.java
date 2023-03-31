package com.kuifir.web;

import com.kuifir.beans.BeansException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class DispatcherServlet extends HttpServlet {

    private List<String> packageNames = new ArrayList<>();
    private final Map<String, Object> controllerObjs = new HashMap<>();
    private List<String> controllerNames = new ArrayList<>();
    private final Map<String, Class<?>> controllerClasses = new HashMap<>();
    private final List<String> urlMappingNames = new ArrayList<>();
    private final Map<String, Method> mappingMethods = new HashMap<>();
    private Map<String, MappingValue> mappingValues;
    private final Map<String, Class<?>> mappingClz = new HashMap<>();
    private final Map<String, Object> mappingObjs = new HashMap<>();
    private String sContextConfigLocation;
    private WebApplicationContext webApplicationContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        this.webApplicationContext = (WebApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        URL xmlPath = null;
        try {
            xmlPath = config.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        Refresh();
    }


    //对所有的mappingValues中注册的类进行实例化，默认构造函数
    protected void Refresh() {
        initController();// 初始化 controller
        initMapping();// 初始化 url 映射

    }

    protected void initMapping() {
        for (String controllerName : this.controllerNames) {
            if(!controllerClasses.containsKey(controllerName)){
                continue;
            }
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                if (isRequestMapping) {
                    String urlmapping = method.getAnnotation(RequestMapping.class).value();
                    this.urlMappingNames.add(urlmapping);
                    this.mappingObjs.put(urlmapping, obj);
                    this.mappingMethods.put(urlmapping, method);
                }

            }
        }
    }


    protected void initController() {
        //扫描包，获取所有类名
        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : this.controllerNames) {
            Object obj;
            Class<?> clz = null;
            try {
                clz = Class.forName(controllerName); //加载类
                if(clz.isInterface()){
                    continue;
                }
                this.controllerClasses.put(controllerName, clz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                obj = clz.getDeclaredConstructor().newInstance(); //实例化bean
                this.controllerObjs.put(controllerName, obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<String> scanPackages(List<String> packages) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packages) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private Collection<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath(); //获取请求的path
        String msg = "http.method_get_not_found";
        if (!this.urlMappingNames.contains(servletPath)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, msg);
            return;
        }
        Object obj;
        Object objResult = null;
        try {
            Method method = this.mappingMethods.get(servletPath);
            obj = this.mappingObjs.get(servletPath);
            String[] beanNamesForType = webApplicationContext.getBeanNamesForType(obj.getClass());
            try {
                Object bean = webApplicationContext.getBean(beanNamesForType[0]);
                if(null != bean){
                    obj = obj.getClass().cast(bean);
                }
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
            objResult = method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.getWriter().append(objResult.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
