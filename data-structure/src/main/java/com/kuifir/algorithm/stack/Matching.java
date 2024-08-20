package com.kuifir.algorithm.stack;

import com.kuifir.table.stack.LinkStack;

import java.util.Scanner;

/**
 * 括号匹配
 */
public class Matching {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            System.out.println(matching(scanner.nextLine()));
        }
    }

    public static boolean matching(String S) {
        LinkStack<Character> stack = new LinkStack<>();
        boolean b = S.chars().allMatch(c -> switch (c) {
            case '(', '{', '[' -> {
                stack.push((char) c);
                yield true;
            }
            case ')' -> {
                if (!stack.isEmpty()) {
                    Character pop = stack.pop();
                    if (pop.equals('(')) {
                        yield true;
                    }
                }
                yield false;

            }
            case '}' -> {
                if (!stack.isEmpty()) {
                    Character pop = stack.pop();
                    if (pop.equals('{')) {
                        yield true;
                    }
                }
                yield false;

            }
            case ']' -> {
                if (!stack.isEmpty()) {
                    Character pop = stack.pop();
                    if (pop.equals('[')) {
                        yield true;
                    }
                }
                yield false;
            }
            default -> false;
        });
        return b && stack.isEmpty();
    }
}
