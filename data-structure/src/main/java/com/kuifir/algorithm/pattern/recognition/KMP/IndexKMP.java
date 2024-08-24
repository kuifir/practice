package com.kuifir.algorithm.pattern.recognition.KMP;

import java.util.Objects;

public class IndexKMP {
    public static void main(String[] args) {
        String a = "babbbbbabb";
        String b = "aaaab";
        System.out.println(a.indexOf(b, 0));
        System.out.println(indexKMP(a, b, 0));
    }

    public static int indexKMP(String a, String b, int pos) {
        String[] S = a.split("");
        String[] T = b.split("");
        int[] next = new int[T.length];
        nextOptimized(T, next);
        int i = pos, j = 0;
        while (i < S.length && j < T.length) {
            if (!Objects.equals(S[i], T[j])) {
                if (next[j] == -1) {
                    i++;
                    j = 0;
                } else {
                    j = next[j];
                }
            } else {
                i++;
                j++;
            }
        }
        if (j == T.length) {
            return i - j;
        }
        return -1;
    }

    public static void next(String[] split, int[] next) {
        next[0] = -1;
        int k = 0;
        int j = next[k];
        // 根据next[i-1]=next[k] 推算next[i]
        while (k < split.length - 1) {
            if ((j == -1) || (Objects.equals(split[k], split[j]))) {
                if (j != -1) {
                    next[k + 1] = j + 1;
                } else {
                    next[k + 1] = 0;
                }
                k++;
                j = next[k];
            } else {
                j = next[j];
            }
        }
    }

    public static void next_simple(String[] split, int[] next) {
        next[0] = -1;
        int k = 0;
        int j = next[k];
        // 根据next[i-1]=next[k] 推算next[i]
        while (k < split.length - 1) {
            if ((j == -1) || (Objects.equals(split[k], split[j]))) {
                k++;
                j++;
                next[k] = j;
            } else {
                j = next[j];
            }
        }
    }
    public static void nextOptimized(String[] split, int[] next) {
        next[0] = -1;
        int k = 0;
        int j = next[k];
        // 根据next[i-1]=next[k] 推算next[i]
        while (k < split.length - 1) {
            if ((j == -1) || (Objects.equals(split[k], split[j]))) {
                k++;
                j++;
                if(Objects.equals(split[k], split[j])){
                    next[k] = next[j];
                }else {
                    next[k] = j;
                }

            } else {
                j = next[j];
            }
        }
    }
}

