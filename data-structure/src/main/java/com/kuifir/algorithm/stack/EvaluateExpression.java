package com.kuifir.algorithm.stack;

import com.kuifir.table.stack.LinkStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 表达式计算
 */
public class EvaluateExpression {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            System.out.println(evaluate(scanner.nextLine()));
        }
    }

    public static BigDecimal evaluate(String expression) {
        // 操作数
        LinkStack<BigDecimal> operandStack = new LinkStack<>();
        // 运算符
        LinkStack<String> operatorStack = new LinkStack<>();
        String[] split = expression.split("");
        boolean b = Arrays.stream(split).allMatch(c -> switch (c.trim()) {
            case "+", "-", "*", "/", "(", ")", "[", "]", "{", "}" -> {
                if (operatorStack.isEmpty()) {
                    operatorStack.push(c);
                    yield true;
                }
                String top = operatorStack.getTop();
                int compare = compare(top, c);
                if (compare > 0) {
                    while (!operatorStack.isEmpty()) {
                        int compare1 = compare(operatorStack.getTop(), c);
                        if (compare1 > 0) {
                            evaluate(operandStack, operatorStack.pop());
                        } else if (compare1 == 0) {
                            operatorStack.pop();
                            break;
                        } else {
                            break;
                        }
                    }
                    if (!Arrays.asList(")", "]", "}").contains(c)) {
                        operatorStack.push(c);
                    }
                } else if (compare == 0) {
                    operatorStack.pop();
                } else {
                    operatorStack.push(c);
                }
                yield true;
            }
            case "" -> true;
            default -> {
                if (c.trim().chars().allMatch(Character::isDigit)) {
                    operandStack.push(new BigDecimal(c));
                    yield true;
                }
                yield false;
            }
        });
        while (!operatorStack.isEmpty()) {
            evaluate(operandStack, operatorStack.pop());
        }
        // 校验是否合法
        if (b && !operandStack.isEmpty() && operatorStack.isEmpty()) {
            return operandStack.pop();
        }
        throw new RuntimeException("表达式错误");
    }

    /**
     * 比较优先级
     * "+", "-", "*", "/", "(", ")", "[", "]", "{", "}"
     *
     * @param source
     * @param target
     * @return
     */
    public static int compare(String source, String target) {
        List<String> graterThanList = new ArrayList<>();
        List<String> lessThanList = new ArrayList<>();
        List<String> equalsThanList = new ArrayList<>();
        switch (source) {
            case "+", "-" -> {
                graterThanList = Arrays.asList("+", "-", "}", "]", ")");
                lessThanList = Arrays.asList("*", "/", "{", "[", "(");
            }

            case "*", "/" -> {
                graterThanList = Arrays.asList("+", "-", "*", "/", "}", "]", ")");
                lessThanList = Arrays.asList("{", "[", "(");
            }
            case "{", "[", "(" -> {
                lessThanList = Arrays.asList("+", "-", "*", "/", "{", "[", "(");
                equalsThanList = Arrays.asList("}", "]", ")");
            }
            case "}", "]", ")" -> {
                graterThanList = Arrays.asList("+", "-", "*", "/", "}", "]", ")");
                equalsThanList = Arrays.asList("{", "[", "(");
            }

            default -> throw new RuntimeException();
        }
        if (graterThanList.contains(target)) {
            return 1;
        }
        if (lessThanList.contains(target)) {
            return -1;
        }
        if (equalsThanList.contains(target)) {
            return 0;
        }
        throw new RuntimeException("未设定优先级比较：" + source + target);
    }

    /**
     * 计算数值
     *
     * @param operandStack
     * @param operator
     * @return
     */
    public static void evaluate(LinkStack<BigDecimal> operandStack, String operator) {

        switch (operator.trim()) {
            case "+" -> {
                BigDecimal sec = operandStack.pop();
                BigDecimal fir = operandStack.pop();
                operandStack.push(fir.add(sec));
            }
            case "-" -> {
                BigDecimal sec = operandStack.pop();
                BigDecimal fir = operandStack.pop();
                operandStack.push(fir.subtract(sec));
            }
            case "*" -> {
                BigDecimal sec = operandStack.pop();
                BigDecimal fir = operandStack.pop();
                operandStack.push(fir.multiply(sec));
            }
            case "/" -> {
                BigDecimal sec = operandStack.pop();
                BigDecimal fir = operandStack.pop();
                operandStack.push(fir.divide(sec, 2, RoundingMode.HALF_UP));
            }
            case ")", "[", "]", "{", "}", "(" -> {

            }
            case null, default -> throw new IllegalStateException("表达式非法 ");
        }
        ;
    }
}
