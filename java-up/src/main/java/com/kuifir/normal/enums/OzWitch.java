package com.kuifir.normal.enums;

/**
 * 在枚举类型中增加自定义方法
 * 对于枚举类型来说，除了无法继承它以外，基本可以将它看作一个普通的类。
 * 这意味着你可以在里面增加自定义的方法，甚至可以增加一个main()方法。
 * <p></p>
 * 如果你想增加自定义方法，则必须先用分号结束枚举实例的序列。同时。Java会强制你在枚举中先定义实例。
 * 如果在定义实例之前定义了任何方法或字段，则会抛出编译时错误。
 * <p></p>
 * 构造方法只能创建你在枚举定义中声明的枚举实例；在枚举定义完之后，编译器不会允许你用它来创建任何新的类型。
 * @author kuifir
 * @date 2023/5/30 22:47
 */
public enum OzWitch {
    /**
     * 实例必须在方法之前定义
     */
    WEST("Miss Gulch, aka the Wicked Witch of the West"),
    NORTH("Glinda, the Good Witch of the North"),
    EAST("Wicked Witch of the East, wearer of the Ruby Slippers, crushed by Dorothy's house"),
    SOUTH("Good by inference, but missing");

    private String description;

    public String getDescription() {
        return description;
    }

    /**
     * 构造器的访问权限必须是包级或private
     *
     * @param description
     */
    OzWitch(String description) {
        this.description = description;
    }

    public static void main(String[] args) {
        for (OzWitch witch : OzWitch.values()) {
            System.out.println(witch +":" +witch.getDescription());
        }
    }
}
