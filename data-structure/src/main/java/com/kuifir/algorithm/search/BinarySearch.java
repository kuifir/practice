package com.kuifir.algorithm.search;

public class BinarySearch {
    public static void main(String[] args) {
        int[] l = {13, 24, 35, 45, 54, 54, 54, 54, 63, 74, 89};
        System.out.println(getFirstEquals(l, 54));
        System.out.println(getLastEquals(l, 54));
        System.out.println(getFirstGreaterThan(l, 54));
        System.out.println(getLastLessThan(l, 54));
    }

    /**
     * 查找第一个值等于的给定值的元素
     */
    public static int getFirstEquals(int[] l, int element) {
        int low = 0, high = l.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (l[mid] < element) {
                low = mid + 1;
            }
            if (l[mid] > element) {
                high = mid - 1;
            }
            if (l[mid] == element) {
                if (mid == 0 || l[mid - 1] < element) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 查找最后一个值等于给定值的元素
     */
    public static int getLastEquals(int[] l, int element) {
        int low = 0, high = l.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (l[mid] < element) {
                low = mid + 1;
            }
            if (l[mid] > element) {
                high = mid - 1;
            }
            if (l[mid] == element) {
                if (mid == l.length - 1 || l[mid + 1] > element) {
                    return mid;
                } else {
                    low = mid + 1;
                }
            }
        }
        return -1;
    }

    /**
     * 查找第一个大于等于给定值的元素
     */
    public static int getFirstGreaterThan(int[] l, int element) {
        int low = 0, high = l.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (l[mid] < element) {
                low = mid + 1;
            }
            if (l[mid] >= element) {
                if (mid == 0 || l[mid - 1] < element) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 查找最后一个小于等于给定值的元素
     */
    public static int getLastLessThan(int[] l, int element) {
       int low = 0, high = l.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (l[mid] > element) {
                high = mid - 1;
            }
            if (l[mid] <= element) {
                if (mid == l.length - 1 || l[mid + 1] > element) {
                    return mid;
                } else {
                    low = mid + 1;
                }
            }
        }
        return -1;
    }
}
