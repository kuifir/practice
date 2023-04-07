package com.kuifir.switchExpression.resolve;

import java.util.Calendar;

/**
 * <p>
 * 我们最先看到的变化，就是 switch 代码块出现在了赋值运算符的右侧。
 * 这也就意味着，这个 switch 代码块表示的是一个数值，或者是一个变量。换句话说，这个 switch 代码块是一个表达式。
 * <p>
 *
 * <p>
 * 我们看到的第二个变化，是多情景的合并。
 * 也就是说，一个 case 语句，可以处理多个情景。这些情景，使用逗号分隔开来，共享一个代码块。
 * 而传统的 switch 代码，一个 case 语句只能处理一种情景。
 * 多情景的合并的设计，满足了共享代码片段的需求。
 * 而且，由于只使用一个 case 语句，也就不再需要使用 break 语句来满足这个需求了。
 * 所以，break 语句从 switch 表达式里消失了。
 * <p>
 * 下一个变化，是一个新的情景操作符，“->”，它是一个箭头标识符。
 * 这个符号使用在 case 语句里，一般化的形式是“case L ->”。
 * 这里的 L，就是要匹配的一个或者多个情景。
 * 如果目标变量和情景匹配，那么就执行操作符右边的表达式或者代码块。
 * 如果要匹配的情景有两个或者两个以上，就要使用逗号“,”用分隔符把它们分割开来。
 * <p>
 * 下一个我们看到的变化，是箭头标识符右侧的数值。
 * 这个数值，代表的就是该匹配情景t下，switch 表达式的数值。
 * 需要注意的是，箭头标识符右侧可以是表达式、代码块或者异常抛出语句，而不能是其他的形式。
 * 如果只需要一个语句，这个语句也要以代码块的形式呈现出来。
 * <p>
 * 下一个能够看到的变化，是出现了一个新的关键字“yield”。
 * 大多数情况下，switch 表达式箭头标识符的右侧是一个数值或者是一个表达式。
 * 如果需要一个或者多个语句，我们就要使用代码块的形式。
 * 这时候，我们就需要引入一个新的 yield 语句来产生一个值，这个值就成为这个封闭代码块代表的数值。
 * <p>
 * 为了便于理解，我们可以把 yield 语句产生的值看成是 switch 表达式的返回值。
 * 所以，yield 只能用在 switch 表达式里，而不能用在 switch 语句里。
 * <p>
 * 在 switch 表达式里，所有的情景都要列举出来，不能多、也不能少（这也就是我们常说的穷举）。
 * 比如说，在上面的例子里，如果没有最后的 default 情景分支，编译器就会报错。
 * </p>
 * <p>
 *     break 语句只能出现在 switch 语句里，不能出现在 switch
 *     表达式里；yield 语句只能出现在 switch 表达式里，不能出现在 switch 语句里；
 *     switch 表达式需要穷举出所有的情景，而 switch 语句不需要情景穷举；
 *     使用冒号标识符的 swtich 形式，支持情景间的 fall-through；而使用箭头标识符的 swtich 形式不支持 fall-through；
 *     使用箭头标识符的 swtich 形式，一个 case 语句支持多个情景；而使用冒号标识符的 swtich 形式不支持多情景的 case 语句。
 * </p>
 */

public class Resolve {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        int daysInMonth = switch (month) {
            case Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER ->
                    31;
            case Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30;
            case Calendar.FEBRUARY -> {
                if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0)) {
                    yield 29;
                } else {
                    yield 28;
                }
            }
            default -> throw new RuntimeException("Calendar in JDK does not work");
        };
        System.out.println("There are " + daysInMonth + " days in this month.");
    }
}
