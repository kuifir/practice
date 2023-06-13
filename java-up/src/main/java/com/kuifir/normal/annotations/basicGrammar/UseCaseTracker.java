package com.kuifir.normal.annotations.basicGrammar;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 如果没有工具来读取注解，那它实际并不会比猪是带来更多帮助。
 * Java为反射API提供了扩展，以帮助创建这些工具。Java同时还提供了一个javac的编译器钩子，用来在编译时使用注解。
 * <p></p>
 * 以下示例是一个非常简单的注解处理器，它读取被注解的{@link PasswordUtils}(密码工具)类，
 * 然后利用反射来查找{@link UseCase}标签。通过给定的id值列表，该注解列出了它找到的所有用例，并报告所有丢失的用例。
 *
 * @author kuifir
 * @date 2023/6/12 22:51
 * @see UseCase
 */
public class UseCaseTracker {
    public static void trackUseCases(List<Integer> useCases, Class<?> cl) {
        for (Method m : cl.getDeclaredMethods()) {
            UseCase uc = m.getAnnotation(UseCase.class);
            if (uc != null) {
                System.out.println("Found Use Case" + uc.id() + "\n" + uc.description());
                useCases.remove(Integer.valueOf(uc.id()));
            }
        }
        useCases.forEach(i -> System.out.println("Missing use case " + i));
    }

    public static void main(String[] args) {
        List<Integer> useCases = IntStream.range(47, 51).boxed().collect(Collectors.toList());
        trackUseCases(useCases, PasswordUtils.class);

    }
}
