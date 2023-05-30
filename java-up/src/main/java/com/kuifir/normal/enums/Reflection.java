package com.kuifir.normal.enums;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

/**
 * 枚举values()方法：
 * 所有的枚举类型都是由编译器通过继承{@link java.lang.Enum}类来创建.
 * 然而如果仔细查看{@link Enum}类的代码,你会发现并没有values()方法,我们却已经能直接使用它了。
 * 编写一个小的反射程序来一探究竟。
 * <p></p>
 * <pre>{@code
 * final class com.kuifir.normal.enums.Explore extends java.lang.Enum<com.kuifir.normal.enums.Explore> {
 *   public static final com.kuifir.normal.enums.Explore HERE;
 *   public static final com.kuifir.normal.enums.Explore THERE;
 *   public static com.kuifir.normal.enums.Explore[] values();
 *   public static com.kuifir.normal.enums.Explore valueOf(java.lang.String);
 *   static {};
 * }}
 * </pre>
 *<p></p>
 * 答案揭晓：values()方法是由编译器添加的一个静态方法。
 * 注意在创建枚举的过程中，valueOf()方法同样也被添加到了Explore枚举中。
 * Enum类中同样也有一个valueOf()方法，但是该方法又啷个参数，而新加入的只有一个。
 * <p><p/>
 * 打印结果显示Explore枚举被编译器限定为final类，所以你无法继承一个枚举类。
 * 此外还有一个static的初始化自子句，可以被重定义{@link }
 * <p></p>
 * 由于类型擦除的缘故，反编译器得不到Enum类的完成信息，
 * 因此只能将Explore类的基类作为一个原始的Enum来显示，而不是实际上的{@code Enum<Explore>}
 * <p></p>
 * 由于values()方法是由编译器在枚举类的定义中插入的一个静态方法，因此如果你将枚举类型向上转型为Enum,则values()方法将不可用。
 * 然而要注意的是，Class中有个getEnumConstants()方法，
 * 所以即使Enum的接口中没有values()方法，仍可以通过Class对象来的带enum的实例{@link UpcastEnum}
 *
 * @see UpcastEnum
 * @author kuifir
 * @date 2023/5/30 23:09
 */
public class Reflection {
    /**
     * 查看实现的接口，父类，和类中的方法。
     *
     * @param enumClass
     * @return
     */
    public static Set<String> analyze(Class<?> enumClass) {
        System.out.println("_______ Analyzing " + enumClass + "_______");
        System.out.println("Interfaces:");
        for (Type genericInterface : enumClass.getGenericInterfaces()) {
            System.out.println(genericInterface);
        }
        System.out.println("Base: " + enumClass.getSuperclass());
        System.out.println("Methods:");
        Set<String> methods = new TreeSet<>();
        for (Method method : enumClass.getMethods()) {
            methods.add(method.getName());
        }
        System.out.println(methods);
        return methods;
    }

    public static void main(String[] args) {
        Set<String> exploreMethods = analyze(Explore.class);
        Set<String> enumMethods = analyze(Enum.class);
        System.out.println("Explore.containsAll(Enum) ? "+ exploreMethods.containsAll(enumMethods));
        System.out.println("exploreMethods.removeAll(enumMethods):");
        exploreMethods.removeAll(enumMethods);
        System.out.println(exploreMethods);
        // 反编译
        String property = System.getProperty("user.dir");
        String path = property + "\\java-up\\target\\classes\\com\\kuifir\\normal\\enums";
        System.out.println(path);
        OSExecute.command("javap -cp " +path+ " Explore");
    }
}

enum Explore {HERE, THERE}
