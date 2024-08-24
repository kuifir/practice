package com.kuifir.algorithm.pattern.recognition.BF;

import java.util.Objects;

/**
 * 模式匹配
 */
public class IndexBF {
    public static void main(String[] args) {
        String a = "abdaeifheijnfabdae amdlk fadsfuwnjrg";
        String b = "abdae";
        System.out.println(a.indexOf(b,1));
        System.out.println(indexBF(a, b, 1));
    }


    public static int indexBF(String a, String b, int pos) {
        String[] S = a.split("");
        String[] T = b.split("");
        int i = pos, j = 0;
        while (i < S.length && j < T.length) {
            if (!Objects.equals(S[i], T[j])) {
                i = i - j + 1;
                j = 0;
            } else {
                i++;
                j++;
            }
        }
        if(j >=T.length){
            return i-j;
        }
        return -1;
    }
}
