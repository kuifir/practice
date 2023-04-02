package com.kuifir.web.servlet;

import com.kuifir.beans.BeansException;
import com.kuifir.web.RequestMapping;
import com.kuifir.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class RequestMappingHandlerMapping implements HandlerMapping {
    private WebApplicationContext wac;
    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext wac) {
        this.wac = wac;
        initMapping();
    }

    //建立URL与调用方法和实例的映射关系，存储在mappingRegistry中
    private void initMapping() {
        String[] controllerNames = this.wac.getBeanDefinitionNames();
        //扫描WAC中存放的所有bean
        for (String controllerName : controllerNames) {
            Object obj = null;
            Class<?> clazz;
            try {
                clazz = Class.forName(controllerName);
                obj = this.wac.getBean(controllerName);
            } catch (BeansException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                if (isRequestMapping) {
                    String methodName = method.getName();
                    String urlmapping = method.getAnnotation(RequestMapping.class).value();
                    this.mappingRegistry.getUrlMappingNames().add(urlmapping);
                    this.mappingRegistry.getMappingObjs().put(urlmapping, obj);
                    this.mappingRegistry.getMappingMethods().put(urlmapping, method);
                }

            }
        }
    }

    //根据访问URL查找对应的调用方法
    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        String servletPath = request.getServletPath(); //获取请求的path
        if (!this.mappingRegistry.getUrlMappingNames().contains(servletPath)) {
            return null;
        }
        Method method = this.mappingRegistry.getMappingMethods().get(servletPath);
        Object obj = this.mappingRegistry.getMappingObjs().get(servletPath);
        HandlerMethod handlerMethod = new HandlerMethod(method, obj);
        return handlerMethod;
    }
}
