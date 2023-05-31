package com.kuifir.normal.enums;

/**
 * 使用接口来组织枚举
 * 枚举类型无法被继承，这一点可能有时会让人沮丧。
 * 想要继承枚举的动机，一部分源于希望拓展原始枚举中的元素，另一部分源自想要使用子类型来创建不同的子分组。
 * <p></p>
 * 你可以在一个接口内对元素进行分组，然后基于这个接口生成一个枚举，通过这样的方式来实现元素的分类。
 * 举个例子：假如你有一些类型互不相同的food,像创建若干enum来组织它们，但与希望他们仍然是Food类型。
 * 你可以这么做{@link Food}
 * <p></p>
 * 实现接口是唯一可子类化枚举的方式，因此所有嵌套在Food中的枚举类型都实现了Food接口。
 * 现在我们基本可以说“一切菜都是某种类型的Food”，
 * 对于每个实现了Food接口的枚举类型，都可以向上转型为Food.因此它们全都是Food类型
 * <p></p>
 * 但是，当你要处理一组类型时，接口往往就不如枚举有用。
 * 如果要创建"由枚举组成的枚举",你可以为Food中的每个枚举类型都创建一个外部枚举类型。
 * {@link Course}
 *
 * @see Course
 * @author kuifir
 * @date 2023/5/31 23:09
 */
public interface Food {
    /**
     * 开胃菜
     */
    enum Appetizer implements Food {
        /**
         * 沙拉、汤、春卷；
         */
        SALAD, SOUP, SPRING_ROLLS;
    }

    /**
     * 主菜
     */
    enum MainCourse implements Food {
        /**
         * 千层面、墨西哥卷饼、帕德泰、小扁豆、鹰嘴豆泥、葡萄酒；
         */
        LASAGNE, BURRITO, PAD_THAI, LENTILS, HUMMUS, VINDALOO;
    }

    /**
     * 甜点
     */
    enum Dessert implements Food {
        /**
         * 提拉米苏、冰淇淋、BLACK_FOREST_CAKE、水果、奶油蛋糕；
         */
        TIRAMISU, GELATO, BLACK_FOREST_CAKE, FRUIT, CREME_CARAMEL;
    }

    enum Coffee implements Food {
        /**
         * 黑咖啡、无咖啡因咖啡、意式浓缩咖啡、拿铁、卡布奇诺、茶、香草；
         */
        BLACK_COFFEE, DECAF_COFFEE, ESPRESSO, LATTE, CAPPUCCINO, TEA, HERB_TEA;
    }
}
