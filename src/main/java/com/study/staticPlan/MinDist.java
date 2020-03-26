package com.study.staticPlan;

/**
 * @author ldb
 * @date 2019-12-03 15:56
 */
public class MinDist {

    int minDist = Integer.MAX_VALUE;

    // 回溯法
    public void minDist(int[][] arr, int dist, int i, int j, int n) {

        dist = dist + arr[i][j];
        if (i == n - 1 && j == n - 1) {
            if (minDist > dist) {
                minDist = dist;
            }
            return;
        }
        if (i < n - 1) {
            minDist(arr, dist, i + 1, j, n);
        }
        if (j < n - 1) {
            minDist(arr, dist, i, j + 1, n);
        }

    }

    public static void main(String[] args) {
        int[] line1 = {1, 3, 5, 9};
        int[] line2 = {2, 1, 3, 4};
        int[] line3 = {5, 2, 6, 7};
        int[] line4 = {6, 8, 4, 3};
        int[][] w = {line1, line2, line3, line4};
        MinDist m = new MinDist();
        m.minDist(w, 0, 0, 0, 4);
        System.out.println(m.minDist);

        System.out.println(m.getMinDist(w, 4));
    }

    // 状态转移表法
    public int getMinDist(int[][] arr, int n) {
        int[][] status = new int[n][n];

        // 第一行
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[0][i];
            status[0][i] = sum;
        }
        sum = 0;
        // 第一列
        for (int i = 0; i < n; i++) {
            sum += arr[i][0];
            status[i][0] = sum;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                status[i][j] = arr[i][j] + Math.min(status[i - 1][j], status[i][j - 1]);
            }
        }

        return status[n - 1][n - 1];
    }

    // 状态转移方程
    private int[][] matrix = {{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};
    private int[][] mem = new int[4][4];

    public int getMinDist(int i, int j) {
        if (i == 0 && j == 0) {
            return matrix[0][0];
        }
        if (mem[i][j] > 0) {
            return mem[i][j];
        }
        int minLeft = Integer.MAX_VALUE;
        int minUp = Integer.MAX_VALUE;
        if (j - 1 >= 0) {
            minLeft = getMinDist(i, j - 1);
        }
        if (i - 1 >= 0) {
            minUp = getMinDist(i - 1, j);
        }
        int current = matrix[i][j] + Math.min(minLeft, minUp);
        mem[i][j] = current;

        return current;
    }
}
