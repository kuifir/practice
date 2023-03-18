package com.kuifir.beans;

public class PropertyValue {
    private final String name;
    private final String type;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this(null,name,value);
    }
    public PropertyValue( String type,String name, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }



    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
