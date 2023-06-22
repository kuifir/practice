package com.kuifir.normal.annotations.atunit;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kuifir
 * @date 2023/6/22 21:16
 */
public class AtUnit implements ProcessFiles.Strategy {
    static Class<?> testClass;
    static List<String> failedTests = new ArrayList<>();
    static long testsRun = 0;
    static long failures = 0;

    public static void main(String[] args) throws Exception {
        // 启用断言
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
        new ProcessFiles(new AtUnit(), "class").start(args);
        if (failures == 0) {
            System.out.println("OK (" + testsRun + " tests)");
        } else {
            System.out.println("(" + testsRun + " tests)");
            System.out.println("\n>>> " + failures + " FAILURE"
                    + (failures > 1 ? "S" : "") + "　<<<");
            for (String failedTest : failedTests) {
                System.out.println(" " + failedTest);
            }
        }
    }

    @Override
    public void process(File cfile) throws RuntimeException {
        try {
            String cName = ClassNameFinder.thisClass(Files.readAllBytes(cfile.toPath()));
            if (!cName.startsWith("public:")) {
                return;
            }
            cName = cName.split(":")[1];
            if (!cName.contains(".")) {
                // 忽略未包装的类
                return;
            }
            testClass = Class.forName(cName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TestMethods testMethods = new TestMethods();
        Method creator = null;
        Method cleanup = null;
        for (Method m : testClass.getDeclaredMethods()) {
            testMethods.addIfTestMethod(m);
            if (creator == null) {
                creator = checkForCreatorMethod(m);
            }
            if (cleanup == null) {
                cleanup = checkForCleanupMethod(m);
            }
        }
        if (testMethods.size() > 0) {
            if (creator == null) {
                try {
                    if (!Modifier.isPublic(testClass.getDeclaredConstructor().getModifiers())) {
                        System.out.println("Error: " + testClass + " zero-argument constructor must be public");
                        System.exit(1);
                    }
                } catch (NoSuchMethodException e) {
                    // 同步的无参构造器
                }
                System.out.println(testClass.getName());
            }
        }
        for (Method m : testMethods) {
            System.out.println("  ." + m.getName() + "　");
            try {
                Object testObject = createTestObject(creator);
                boolean success = false;
                try {
                    if (m.getReturnType().equals(boolean.class)) {
                        success = (boolean) m.invoke(testObject);
                    } else {
                        m.invoke(testObject);
                        // 如果没有断言失败
                        success = true;
                    }
                } catch (InvocationTargetException | IllegalAccessException e) {
                    // 实际的异常在e中
                    System.out.println(e.getCause());
                }
                System.out.println(success ? "" : "(failed)");
                testsRun++;
                if (!success) {
                    failures++;
                    failedTests.add(testClass.getName() + ":" + m.getName());
                }
                if (cleanup != null){
                    cleanup.invoke(testObject,testObject);
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class TestMethods extends ArrayList<Method> {
        void addIfTestMethod(Method m) {
            if (m.getAnnotation(Test.class) == null) {
                return;
            }
            if (!(m.getReturnType().equals(boolean.class) || m.getReturnType().equals(void.class))) {
                throw new RuntimeException("@Test method must return boolean or void");
            }
            // 如果是private的
            m.setAccessible(true);
            add(m);
        }
    }

    private static Method checkForCreatorMethod(Method m) {
        if (m.getAnnotation(TestObjectCreate.class) == null) {
            return null;
        }
        if (!m.getReturnType().equals(Test.class)) {
            throw new RuntimeException("@TestObjectCreate must be return instance of Class to be Tested");
        }
        if ((m.getModifiers() & Modifier.STATIC) < 1) {
            throw new RuntimeException("@TestObjectCreate must be static.");
        }
        m.setAccessible(true);
        return m;
    }

    private static Method checkForCleanupMethod(Method m) {
        if (m.getAnnotation(TestObjectCleanUp.class) == null) {
            return null;
        }
        if (!m.getReturnType().equals(void.class)) {
            throw new RuntimeException("@TestObjectCleanup must return void");
        }
        if ((m.getModifiers() & Modifier.STATIC) < 1) {
            throw new RuntimeException("@TestObjectCleanup must be static.");
        }
        if (m.getParameterTypes().length == 0 || m.getParameterTypes()[0] != testClass) {
            throw new RuntimeException("TestObjectCleanup must take an argument of the tested type.");
        }
        m.setAccessible(true);
        return m;
    }

    private static Object createTestObject(Method creator) {
        if (creator != null) {
            try {
                return creator.invoke(testClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Couldn't run @TestObject(creator) method.");
            }
        } else {
            // 使用无参数的构造器
            try {
                return testClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException("Couldn't create a test object. Try using a @TestObject method.");
            }
        }
    }
}
