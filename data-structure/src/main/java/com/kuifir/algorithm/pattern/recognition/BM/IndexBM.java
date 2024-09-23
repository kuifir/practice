package com.kuifir.algorithm.pattern.recognition.BM;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IndexBM {
    public static void main(String[] args) {
        String a = "babbbbbabbb";
        String b = "babbb";
        System.out.println(a.indexOf(b, 1));
        System.out.println(indexBM(a, b, 1));
    }

    public static int indexBM(String str, String pattern, int pos) {
        String[] strings = str.split("");
        String[] patterns = pattern.split("");
        int stringPos = pos, patternPos;
        // 坏字符预处理
        Map<String, Integer> lastIndexMap = getLastIndex(pattern);
        // 好前缀预处理

        int[] suffix = new int[patterns.length - 1];
        boolean[] prefix = new boolean[patterns.length - 1];
        generateGoodSuffix(patterns, suffix, prefix);

        while (stringPos <= strings.length - patterns.length && stringPos >= pos) {
            // 从右向左比对
            for (patternPos = patterns.length - 1; patternPos >= 0; patternPos--) {
                if (!Objects.equals(strings[stringPos + patternPos], patterns[patternPos])) {
                    break;
                }
            }

            if (patternPos == -1) {
                // 匹配成功
                return stringPos;
            }

            // 坏字符或好后缀的移动最大值
            // 坏字符的移动距离
            int badCharacterShiftNum = getBadCharacterShiftNum(patternPos, strings[stringPos + patternPos], lastIndexMap);
            // 好前缀移动的距离
            int goodSuffixShiftNum = 0;
            if (patternPos < patterns.length - 1) {
               goodSuffixShiftNum = getGoodSuffixShiftNum(patternPos, patterns.length, suffix, prefix);
            }
            stringPos += Math.max(badCharacterShiftNum, goodSuffixShiftNum);
        }
        return -1;
    }

    /**
     * 坏字符移动字符数
     */
    static int getBadCharacterShiftNum(int currentIndex, String badWord, Map<String, Integer> lastIndexMap) {
        return currentIndex - lastIndexMap.getOrDefault(badWord, -1);
    }

    /**
     * 好前缀的移动距离
     *
     * @param
     * @return
     */
    static int getGoodSuffixShiftNum(int currentIndex, int patternLength, int[] suffix, boolean[] prefix) {
        int goodSuffixLength = patternLength - 1 - currentIndex;
        // 好后缀存在前面序列中
        if (suffix[goodSuffixLength] != -1) {
            return currentIndex - suffix[goodSuffixLength] + 1;
        } else {
            // 模式前缀包含好后缀的子串
            for (int i = goodSuffixLength; i >= 0; i--) {
                if (prefix[i]) {
                    return i;
                }
            }
        }
        // 模式前缀不包含好后缀的子串
        return patternLength;
    }

    // 字符最后一次出现的位置
    static Map<String, Integer> getLastIndex(String pattern) {
        String[] split = pattern.split("");
        Map<String, Integer> lastIndexMap = new HashMap<>(split.length);
        for (int i = 0; i < split.length; i++) {
            lastIndexMap.put(split[i], i);
        }
        return lastIndexMap;
    }

    /**
     * 获取好后缀对应的suffix数组
     *
     * @param patterns 模式字符
     * @param suffix   好后缀在模式中当前位置前出现的位置,下标为后缀的长度
     * @param prefix   好后缀是否有子串为前缀字串，下表为后缀的长度
     * @return
     */
    static void generateGoodSuffix(String[] patterns, int[] suffix, boolean[] prefix) {
        Arrays.fill(suffix, -1);
        Arrays.fill(prefix, false);
        int length = patterns.length;
        for (int i = 0; i < length - 1; i++) {
            int j = i;
            int k = 0; // 公共后缀子串长度
            while (j >= 0 && patterns[j].equals(patterns[length - 1 - k])) {
                j--;
                k++;
                suffix[k] = j + 1; // j+1表示公共后缀子串在b[0, i]中的起始下标
            }
            if (j == -1) {
                // 如果公共后缀子串也是模式串的前缀子串
                prefix[k] = true;
            }
        }
    }
}
