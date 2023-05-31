package com.kuifir.normal.enums;

/**
 * 从每个Course(菜项，如"前菜/主菜/甜点咖啡")中选择一种Food，生成一份随即配好的午餐
 *
 * @see Course
 * @author kuifir
 * @date 2023/5/31 23:46
 */
public class Meal {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            for (Course course : Course.values()) {
                Food food = course.randomSelection();
                System.out.println(food);
            }
            System.out.println("***********");
        }
    }
}
