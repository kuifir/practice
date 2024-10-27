package com.kuifir.algorithm.backtrack.eightQueens;

/**
 * 我们有一个 8x8 的棋盘，希望往里放 8 个棋子（皇后），每个棋子所在的行、列、对角线都不能有另一个棋子。
 */

public class EightQueens {
    public static void main(String[] args) {
        EightQueens queens = new EightQueens(8);
        queens.cal8queens(1);
    }

    /**
     * 行列个数
     */
    private int n;
    /**
     * 每行位置，下标为行，值为列
     */
    private Integer[] result;

    public EightQueens(int n) {
        this.n = n;
        this.result = new Integer[n + 1];
    }

    public void cal8queens(int row) {
        if (row == n + 1) {
            printQueens();
            return;
        }
        for (int column = 1; column <= n; column++) {
            if (isOk(row, column)) {
                result[row] = column;
                cal8queens(row + 1);
            }
        }

    }

    /**
     * 判断该位置是否可以放棋子
     *
     * @param row
     * @param column
     */
    boolean isOk(int row, int column) {
        for (int i = 1; i < row; i++) {
            if (result[i] == column) {
                return false;
            }
            if (Math.abs(row - i) == Math.abs(column - result[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 打印矩阵
     */
    private void printQueens() {
        for (int row = 1; row <= n; row++) {
            for (int column = 1; column <= n; column++) {
                if (result[row] == column) System.out.print("Q ");
                else System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
