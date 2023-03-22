package com.kuifir.beans.factory.support;

import com.kuifir.beans.BeansException;
import com.kuifir.beans.PropertyValue;
import com.kuifir.beans.PropertyValues;
import com.kuifir.beans.factory.BeanFactory;
import com.kuifir.beans.factory.config.BeanDefinition;
import com.kuifir.beans.factory.config.ConstructorArgumentValue;
import com.kuifir.beans.factory.config.ConstructorArgumentValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory
        extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNames = new ArrayList<>();
    private Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);

    public AbstractBeanFactory() {
    }

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿Bean实例
        Object singleton = this.getSingleton(beanName);
        //如果此时还没有这个Bean的实例，则获取它的定义来创建实例
        if (null == singleton) {
            // 如果没有实例，则尝试从毛坯实例中获取
            singleton = this.earlySingletonObjects.get(beanName);
            if (null == singleton) {
                // 如果毛坯都没有，则创建bean实例，并注册
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (null == beanDefinition) {
                    throw new BeansException("No bean." + beanName);
                }
                singleton = createBean(beanDefinition);
                this.registerSingleton(beanDefinition.getId(), singleton);
                // 进行beanpostprocessor处理
                // step 1: postProcessBeforeInitialization
                applyBeanPostProcessorBeforeInitialization(singleton, beanName);
                // step 2: afterPropertiesSet
                // step 3: init-method
                if (beanDefinition.getInitMethodName() != null && !beanDefinition.equals("")) {
                    invokeInitMethod(beanDefinition, singleton);
                }
                // step 4: postProcessAfterInitialization
                applyBeanPostProcessorAfterInitialization(singleton, beanName);
            }
        }
        return singleton;
    }

    private void invokeInitMethod(BeanDefinition beanDefinition, Object obj) {
        Class clz = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Method method = null;
        try {
            method = clz.getMethod(beanDefinition.getInitMethodName());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        try {
            method.invoke(obj);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz;
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Object obj = this.doCreateBean(beanDefinition, clz);
        //存放到毛胚实例缓存中
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        //handle properties
        populateBean(beanDefinition, clz, obj);

        return obj;
    }

    private Object doCreateBean(BeanDefinition bd, Class<?> clz) {
        Constructor<?> con;
        Object obj = null;
        try {
            // handle constructor
            ConstructorArgumentValues argumentValues = bd.getConstructorArgumentValues();
            if (!argumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
                Object[] paramValues = new Object[argumentValues.getArgumentCount()];
                for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
                    boolean isRef = argumentValue.getIsRef();
                    String pType = argumentValue.getType();
                    Object pValue = argumentValue.getValue();
                    if (!isRef) {
                        if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
                            paramTypes[i] = String.class;
                            paramValues[i] = argumentValue.getValue();
                        } else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
                            paramTypes[i] = Integer.class;
                            paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                        } else if ("int".equals(argumentValue.getType())) {
                            paramTypes[i] = int.class;
                            paramValues[i] = Integer.valueOf((String) argumentValue.getValue()).intValue();
                        } else {
                            paramTypes[i] = String.class;
                            paramValues[i] = argumentValue.getValue();
                        }
                    } else { // is ref, create the dependent beans
                        try {
                            paramTypes[i] = Class.forName(pType);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        try { //再次调用getBean创建ref的bean实例
                            paramValues[i] = getBean((String) pValue);
                        } catch (BeansException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                try {
                    con = clz.getConstructor(paramTypes);
                    obj = con.newInstance(paramValues);
                } catch (NoSuchMethodException | SecurityException | IllegalArgumentException |
                         InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                obj = clz.newInstance();
            }

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(bd.getId() + " bean created. " + bd.getClassName() + " : " + obj.toString());
        return obj;
    }

    private void populateBean(BeanDefinition bd, Class<?> clz, Object obj) {
        handleProperties(bd, clz, obj);
    }
    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
        PropertyValues propertyValues = bd.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pName = propertyValue.getName();
                String pType = propertyValue.getType();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();

                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];

                if (!isRef) {
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                    } else {
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = pValue;
                } else { // is ref, create the dependent beans
                    try {
                        paramTypes[0] = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try { //再次调用getBean创建ref的bean实例
                        paramValues[0] = getBean((String) pValue);
                    } catch (BeansException e) {
                        throw new RuntimeException(e);
                    }
                }


                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);

                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                } catch (NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(obj, paramValues);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name,bd);
        this.beanDefinitionNames.add(name);
//        if (!bd.isLazyInit()) {
//            try {
//                getBean(name);
//            } catch (BeansException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
    }
    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);

    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }


    abstract public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    abstract public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
