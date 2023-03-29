package com.kuifir.web;


import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class XmlConfigReader {
    public XmlConfigReader() {
    }

    public Map<String,MappingValue> loadConfig(Resource resource){
        Map<String,MappingValue> mappings = new HashMap<>();
        while (resource.hasNext()){
            Element element = resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            String beanMethod = element.attributeValue("value");
            mappings.put(beanID, new MappingValue(beanID,beanClassName,beanMethod));
        }
        return mappings;
    }
}
