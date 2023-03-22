package com.kuifir.beans.factory.config;

public class ConstructorArgumentValue {
    private Object value;
    private String type;
    private String name;
    private boolean isRef;

    public ConstructorArgumentValue(Object value, String type, String name, boolean isRef) {
        this.value = value;
        this.type = type;
        this.name = name;
        this.isRef = isRef;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public boolean getIsRef() {
        return isRef;
    }

}
