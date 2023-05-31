package com.kuifir.normal.enums;

/**
 * @author kuifir
 * @date 2023/6/1 0:06
 * @see Meal
 * @see Course
 * @see Food
 */
public enum Meal2 {
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

    Meal2(Class<? extends Food> kind) {
        values = kind.getEnumConstants();
    }
    public Food randomSelection(){
        return Enums.random(values);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            for (Meal2 meal : Meal2.values()) {
                Food food = meal.randomSelection();
                System.out.println(food);
            }
            System.out.println("*********");
        }
    }
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
}
