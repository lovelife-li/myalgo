package com.study.suanfa;

/**
 * 0-1背包问题
 *
 * @author ldb
 * @date 2019-10-29 9:33
 */
public class Package0_1 {
    // 回溯算法实现。注意：我把输入的变量都定义成了成员变量。
    // 结果放到 maxW 中
    private int maxW = Integer.MIN_VALUE;
    // int[] weight = {2, 2, 4, 6, 3};  // 物品重量
    private int[] weight = {1, 2, 3, 10, 12};
    private int n = 5; // 物品个数
    private int w = 11; // 背包承受的最大重量

    // 调用 f(0, 0)
    public void f(int i, int cw) {
        System.out.println("f:" + i + "," + cw);
        // cw==w 表示装满了,i==n 表示物品都考察完了
        if (cw == w || i == n) {
            if (cw > maxW) {
                maxW = cw;
            }
            return;
        }
        // 选择不装第 i 个物品
        f(i + 1, cw);
        if (cw + weight[i] <= w) {
            // 选择装第 i 个物品
            f(i + 1, cw + weight[i]);
        }
    }

    public static void main(String[] args) {
        Package0_1 p = new Package0_1();
        p.f(0, 0);
    }

    /**
     * 动态规划
     * weight: 物品重量,n: 物品个数,w: 背包可承载重量
     */
    public int knapsack(int[] weight, int n, int w) {
        boolean[][] states = new boolean[n][w + 1]; // 默认值 false
        states[0][0] = true;  // 第一行的数据要特殊处理,可以利用哨兵优化
        states[0][weight[0]] = true;
        for (int i = 1; i < n; ++i) { // 动态规划状态转移
            for (int j = 0; j <= w; ++j) {// 不把第 i 个物品放入背包
                if (states[i - 1][j] == true) {
                    states[i][j] = states[i - 1][j];
                }
            }
            for (int j = 0; j <= w - weight[i]; ++j) {// 把第 i 个物品放入背包
                if (states[i - 1][j] == true) {
                    states[i][j + weight[i]] = true;
                }
            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[n - 1][i] == true) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 优化动态规划
     *
     * @param items
     * @param n
     * @param w
     * @return
     */
    public static int knapsack2(int[] items, int n, int w) {
        boolean[] states = new boolean[w + 1]; // 默认值 false
        states[0] = true;  // 第一行的数据要特殊处理,可以利用哨兵优化
        states[items[0]] = true;
        for (int i = 1; i < n; ++i) { // 动态规划
            for (int j = w - items[i]; j >= 0; --j) {// 把第 i 个物品放入背包
                if (states[j] == true) {
                    states[j + items[i]] = true;
                }
            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[i] == true) {
                return i;
            }
        }
        return 0;
    }


    /**
     * 0-1 背包问题升级版
     * 对于一组不同重量、不同价值、不可分割的物品，我们选择将某些物品装入背包，在满足背包最大重量限制的前提下,
     * 背包中可装入物品的总价值最大是多少呢？
     */
    private int maxV = Integer.MIN_VALUE; // 结果放到 maxV 中
    private int[] items = {2, 2, 4, 6, 3};  // 物品的重量
    private int[] value = {3, 4, 8, 9, 6}; // 物品的价值

    public void f(int i, int cw, int cv) { // 调用 f(0, 0, 0)
        if (cw == w || i == n) { // cw==w 表示装满了,i==n 表示物品都考察完了
            if (cv > maxV) {
                maxV = cv;
            }
            return;
        }
        f(i + 1, cw, cv); // 选择不装第 i 个物品
        if (cw + weight[i] <= w) {
            f(i + 1, cw + weight[i], cv + value[i]); // 选择装第 i 个物品
        }
    }

    /**
     * 动态规划3
     * @param weight
     * @param value
     * @param n
     * @param w
     * @return
     */
    public static int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w+1];
        for (int i = 0; i < n; ++i) { // 初始化 states
            for (int j = 0; j < w+1; ++j) {
                states[i][j] = -1;
            }
        }
        states[0][0] = 0;
        states[0][weight[0]] = value[0];
        for (int i = 1; i < n; ++i) { // 动态规划,状态转移
            for (int j = 0; j <= w; ++j) { // 不选择第 i 个物品
                if (states[i-1][j] >= 0) states[i][j] = states[i-1][j];
            }
            for (int j = 0; j <= w-weight[i]; ++j) { // 选择第 i 个物品
                if (states[i-1][j] >= 0) {
                    int v = states[i-1][j] + value[i];
                    if (v > states[i][j+weight[i]]) {
                        states[i][j+weight[i]] = v;
                    }
                }
            }
        }
        // 找出最大值
        int maxvalue = -1;
        for (int j = 0; j <= w; ++j) {
            if (states[n-1][j] > maxvalue) maxvalue = states[n-1][j];
        }
        return maxvalue;
    }
}
