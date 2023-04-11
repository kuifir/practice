package com.kuifir.web.servlet.view;

import com.kuifir.web.servlet.View;
import com.kuifir.web.servlet.ViewResolver;

public class InternalResourceViewResolver implements ViewResolver {

    private Class viewClass = null;
    private String viewClassName = "";
    private String prefix = "";
    private String suffix = "";
    private String contentType;

    public InternalResourceViewResolver() {
        if (getViewClass() == null) {
            setViewClass(JstlView.class);
        }
    }

    @Override
    public View resolveViewName(String viewName) throws Exception {
        return buildView(viewName);
    }

    protected View buildView(String viewName) throws Exception {
        Class viewClass = getViewClass();
        View view = (View) viewClass.newInstance();
        view.setUrl(getPrefix() + viewName + getSuffix());
        String contentType = getContentType();
        view.setContentType(contentType);
        return view;
    }

    public Class getViewClass() {
        return viewClass;
    }

    public void setViewClass(Class viewClass) {
        this.viewClass = viewClass;
    }

    public String getViewClassName() {
        return viewClassName;
    }

    public void setViewClassName(String viewClassName) {
        this.viewClassName = viewClassName;
        Class clz = null;
        try {
            clz = Class.forName(viewClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setViewClass(clz);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
