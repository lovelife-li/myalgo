package com.study.staticPlan;

/**
 * @author ldb
 * @date 2019-11-27 17:47
 */
public class MostShortPath {

    private int minDist = Integer.MAX_VALUE; // 全局变量或者成员变量

    // 调用方式：minDistBacktracing(0, 0, 0, w, n);
    public void minDistBT(int i, int j, int dist, int[][] w, int n) {
        // 到达了n-1, n-1这个位置了,这里看着有点奇怪哈,你自己举个例子看下
        dist = dist + w[i][j];
        if (i == n - 1 && j == n - 1) {
            if (dist < minDist) {
                minDist = dist;
            }
            return;
        }
        if (i < n - 1) { // 往下走,更新i=i+1, j=j
            minDistBT(i + 1, j, dist, w, n);
        }
        if (j < n - 1) { // 往右走,更新i=i, j=j+1
            minDistBT(i, j + 1, dist, w, n);
        }
    }


    public static void main(String[] args) {
        int[] line1 = {1, 3, 5, 9};
        int[] line2 = {2, 1, 3, 4};
        int[] line3 = {5, 2, 6, 7};
        int[] line4 = {6, 8, 4, 3};
        int[][] w = {line1, line2, line3, line4};
        MostShortPath m = new MostShortPath();
        m.minDistBT(0, 0, 0, w, 4);
        System.out.println(m.minDist);
//
//        System.out.println(m.minDistDP(w, 4));
//        System.out.println(m.minDist(3, 3));
//        System.out.println(m.minCoins(9));
//
        int arr[] = {1, 3, 5};
        m.countMoneyMin(arr, 9);

    }

    // 状态转移表
    public int minDistDP(int[][] matrix, int n) {
        int[][] states = new int[n][n];
        int sum = 0;
        for (int j = 0; j < n; ++j) { // 初始化 states 的第一行数据
            sum += matrix[0][j];
            states[0][j] = sum;
        }
        sum = 0;
        for (int i = 0; i < n; ++i) { // 初始化 states 的第一列数据
            sum += matrix[i][0];
            states[i][0] = sum;
        }
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < n; ++j) {
                states[i][j] = matrix[i][j] + Math.min(states[i][j - 1], states[i - 1][j]);
            }
        }
        return states[n - 1][n - 1];
    }


    private int[][] matrix = {{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};
    private int n = 4;
    private int[][] mem = new int[4][4];

    // 状态转移方程,递归+备忘录
    public int minDist(int i, int j) {
        // 调用 minDist(n-1, n-1);
        if (i == 0 && j == 0) {
            return matrix[0][0];
        }
        if (mem[i][j] > 0) {
            return mem[i][j];
        }
        int minLeft = Integer.MAX_VALUE;
        if (j - 1 >= 0) {
            minLeft = minDist(i, j - 1);
        }
        int minUp = Integer.MAX_VALUE;
        if (i - 1 >= 0) {
            minUp = minDist(i - 1, j);
        }

        int currMinDist = matrix[i][j] + Math.min(minLeft, minUp);
        mem[i][j] = currMinDist;
        return currMinDist;
    }


    public int countMoneyMin(int[] moneyItems, int resultMemory) {
        if (null == moneyItems || moneyItems.length < 1) {
            return -1;
        }
        if (resultMemory < 1) {
            return -1;
        }
        // 计算遍历的层数,此按最小金额来支付即为最大层数
        int levelNum = resultMemory / moneyItems[0];
        int leng = moneyItems.length;

        int[][] status = new int[levelNum][resultMemory + 1];

        // 初始化状态数组
        for (int i = 0; i < levelNum; i++) {
            for (int j = 0; j < resultMemory + 1; j++) {
                status[i][j] = -1;
            }
        }

        // 将第一层的数数据填充
        for (int i = 0; i < leng; i++) {
            status[0][moneyItems[i]] = moneyItems[i];
        }

        int minNum = -1;

        // 计算推导状态
        for (int i = 1; i < levelNum; i++) {
            // 推导出当前状态
            for (int j = 0; j < resultMemory; j++) {
                if (status[i - 1][j] != -1) {
                    // 遍历元素,进行累加
                    for (int k = 0; k < leng; k++) {
                        if (j + moneyItems[k] <= resultMemory) {
                            status[i][j + moneyItems[k]] = moneyItems[k];
                        }
                    }
                }
                // 找到最小的张数
                if (status[i][resultMemory] >= 0) {
                    minNum = i + 1;
                    break;
                }
            }
            if (minNum > 0) {
                break;
            }
        }

        int befValue = resultMemory;

        // 进行反推出,币的组合
        for (int i = minNum - 1; i >= 0; i--) {
            for (int j = resultMemory; j >= 0; j--) {
                if (j == befValue) {
                    System.out.println("当前的为:" + status[i][j]);
                    befValue = befValue - status[i][j];
                    break;
                }
            }
        }

        return minNum;
    }

}