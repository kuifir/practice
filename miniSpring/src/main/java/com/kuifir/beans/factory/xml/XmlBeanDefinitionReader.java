package com.kuifir.beans.factory.xml;

import com.kuifir.beans.*;
import com.kuifir.beans.factory.config.ConstructorArgumentValue;
import com.kuifir.beans.factory.config.ConstructorArgumentValues;
import com.kuifir.beans.factory.config.BeanDefinition;
import com.kuifir.beans.factory.support.AbstractBeanFactory;
import com.kuifir.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class XmlBeanDefinitionReader {
    private AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void LoadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            String initMethod = element.attributeValue("init-method");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            beanDefinition.setInitMethodName(initMethod);
            //handle properties
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if (pValue != null && !pValue.equals("")) {
                    isRef = false;
                    pV = pValue;
                } else if (pRef != null && !pRef.equals("")) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                PVS.addPropertyValue(new PropertyValue(pType, pName, pV, isRef));
            }
            beanDefinition.setPropertyValues(PVS);
            //end of handle properties

            //get constructor
            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues AVS = new ConstructorArgumentValues();
            for (Element e : constructorElements) {
                String pValue = e.attributeValue("value");
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pRef = e.attributeValue("ref");
                boolean isRef = false;
                String pV = "";
                if (pValue != null && !pValue.equals("")) {
                    isRef = false;
                    pV = pValue;
                } else if (pRef != null && !pRef.equals("")) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                ConstructorArgumentValue value = new ConstructorArgumentValue(pV, pType, pName,isRef);
                AVS.addArgumentValue(constructorElements.indexOf(e), value);
                AVS.addGenericArgumentValue(value);
            }
            beanDefinition.setConstructorArgumentValues(AVS);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);
            this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
            //end of handle constructor
        }
    }
}
