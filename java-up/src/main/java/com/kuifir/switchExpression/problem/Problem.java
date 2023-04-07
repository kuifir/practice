package com.kuifir.switchExpression.problem;

import java.util.Calendar;

/**
 * <p>
 * 第一个容易犯错的地方，就是在 break 关键字的使用上。
 * 代码里，如果多使用一个 break 关键字，代码的逻辑就会发生变化；
 * 同样的，少使用一个 break 关键字也会出现问题。
 * <p>
 * 第二个容易犯错的地方，是反复出现的赋值语句。
 * 在代码中，daysInMonth 这个本地变量的变量声明和实际赋值是分开的。
 * 赋值语句需要反复出现，以适应不同的情景。
 * 如果在 switch 语句里，daysInMonth 变量没有被赋值，编译器也不会报错，缺省的或者初始的变量值就会被使用。
 * <p>
 */
public class Problem {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int daysInMonth;
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                daysInMonth = 31;
                break;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                daysInMonth = 30;
                break;
            case Calendar.FEBRUARY:
                if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0)) {
                    daysInMonth = 29;
                } else {
                    daysInMonth = 28;
                }
                break;
            default:
                throw new RuntimeException("Calendar in JDK does not work");
        }
        System.out.println("There are " + daysInMonth + " days in this month.");
    }
}
