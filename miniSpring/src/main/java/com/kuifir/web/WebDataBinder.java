package com.kuifir.web;

import com.kuifir.beans.PropertyEditor;
import com.kuifir.beans.PropertyValues;
import com.kuifir.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 1. 把 Request 里的参数解析成 PropertyValues。
 * 2. 把 Request 里的参数值添加到绑定参数中。
 * 3. 把两者绑定在一起。
 */
public class WebDataBinder {
    private Object target;
    private Class<?> clz;
    private String objectName;

    public WebDataBinder(Object target) {
        this(target, "");
    }

    public WebDataBinder(Object target, String targetName) {
        this.target = target;
        this.objectName = targetName;
        this.clz = target.getClass();
    }

    // 核心绑定方法，将request里面的参数值绑定到目标对象的属性上
    public void bind(HttpServletRequest request) {
        // 把 Request 里的参数解析成 PropertyValues。
        PropertyValues mpvs = assignParameters(request);
        // 把 Request 里的参数值添加到绑定参数中。
        addBindValues(mpvs, request);
        // 把两者绑定在一起。
        doBind(mpvs);
    }

    private void doBind(PropertyValues mpvs) {
        applyPropertyValues(mpvs);
    }

    //实际将参数值与对象属性进行绑定的方法
    private void applyPropertyValues(PropertyValues mpvs) {
        getPropertyAccessor().setPropertyValues(mpvs);
    }

    //设置属性值的工具
    protected BeanWrapperImpl getPropertyAccessor() {
        return new BeanWrapperImpl(this.target);
    }

    protected void addBindValues(PropertyValues mpvs, HttpServletRequest request) {

    }

    //将Request参数解析成PropertyValues
    private PropertyValues assignParameters(HttpServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "");
        return new PropertyValues(map);
    }


    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }

}
