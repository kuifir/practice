package com.kuifir.beans;

import com.kuifir.core.Resource;
import org.dom4j.Element;

import java.util.List;

public class XmlBeanDefinitionReader {
    private SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }
    public void LoadBeanDefinition(Resource resource){
        while (resource.hasNext()){
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            //handle properties
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                PVS.addPropertyValue(new PropertyValue(pType, pName, pValue));
            }
            beanDefinition.setPropertyValues(PVS);
            //end of handle properties

            //get constructor
            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues AVS = new ArgumentValues();
            for (Element e : constructorElements) {
                String pValue = e.attributeValue("value");
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                ArgumentValue value = new ArgumentValue(pValue,pType, pName);
                AVS.addArgumentValue(constructorElements.indexOf(e),value);
                AVS.addGenericArgumentValue(value);
            }
            beanDefinition.setConstructorArgumentValues(AVS);
            this.simpleBeanFactory.registerBeanDefinition(beanID,beanDefinition);
            //end of handle constructor
        }
    }
}
