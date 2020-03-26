package com.study.staticPlan;

/**
 * 动态规划：二位数组表格
 *
 * @author ldb
 * @date 2019-11-27 11:32
 */
public class Pack_demo3 {
    // weight: 物品重量，n: 物品个数，w: 背包可承载重量
    public int knapsack(int[] weight, int n, int w) {
        boolean[][] states = new boolean[n][w + 1]; // 默认值 false
        states[0][0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        states[0][weight[0]] = true;
        for (int i = 1; i < n; ++i) { // 动态规划状态转移
            for (int j = 0; j <= w; ++j) {
                // 不把第 i 个物品放入背包
                if (states[i - 1][j] == true) {
                    states[i][j] = true;
                }
            }
            // 把第 i 个物品放入背包
            for (int j = 0; j <= w - weight[i]; ++j) {
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

    public static void main(String[] args) {
        Pack_demo3 p = new Pack_demo3();
        int[] weight = {2, 2, 4, 6, 3};
        System.out.println(p.knapsack2(weight, 5, 9));
    }

    // 优化成一维数组
    public  int knapsack2(int[] items, int n, int w) {
        boolean[] states = new boolean[w + 1]; // 默认值 false
        states[0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
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
}
