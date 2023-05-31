package com.kuifir.normal.enums;

/**
 * 当你要处理一组类型时，接口往往就不如枚举有用。
 * 如果要创建"由枚举组成的枚举",你可以为Food中的每个枚举类型都创建一个外部枚举类型。
 * <p></p>
 * 代码中，每个枚举类型都接收相应的Class对象以作为构造参数，从而可以使用 getEnumConstants()来提取所有枚举实例并存储起来。
 * 好了，现在可以从每个Course(菜项，如"前菜/主菜/甜点咖啡")中选择一种Food，生成一份随即配好的午餐了{@link Meal}
 *<p></p>
 * 此处创建一个有枚举类型组成的枚举的意义在于，可以方便地遍历每个Course.
 * 另一种由不同约束条件指定的分类方法。{@link VendingMachine}
 * <p></p>
 * 另一种更简洁的分类方法是在枚举类内嵌套枚举{@link SecurityCategory}
 *
 * @see Food
 * @see Meal
 * @see VendingMachine
 * @see SecurityCategory
 * @author kuifir
 * @date 2023/5/31 23:30
 */
public enum Course {
    /**
     * 开胃菜
     */
    Appetizer(Food.Appetizer.class),
    /**
     * 主菜
     */
    MainCourse(Food.MainCourse.class),
    /**
     * 甜点
     */
    Dessert(Food.Dessert.class),
    Coffee(Food.Coffee.class);
    private Food[] values;
    private Course(Class<? extends Food> kind){
        values = kind.getEnumConstants();
    }
    public Food randomSelection(){
        return Enums.random(values);
    }
}
