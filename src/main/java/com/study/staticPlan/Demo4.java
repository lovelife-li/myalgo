package com.study.staticPlan;

import java.util.ArrayList;

/**
 * 如果我们要支付 w 元，求最少需要多少个硬币。
 * 比如，我们有 3 种不同的硬币，1 元、3 元、5 元，我们要支付 9 元，最少需要 3 个硬币（3 个 3 元的硬币）。
 *
 * @author ldb
 * @date 2019-12-03 17:13
 */
public class Demo4 {
    public static void main(String[] args) {

        int[] items = {1, 3, 5};
        int w = 9;
        // f(new ArrayList<Integer>(),w,items);
//        System.out.println(f(9));
//        System.out.println(f2(9));
        Demo4 d = new Demo4();
        d.getLeastCoinNumByBackTracking(items, w, 0);
        System.out.println(d.minNum);

        System.out.println(d.getLeastCoinNumByDP(items,w));
    }


    public static void f(ArrayList<Integer> cw, int w, int[] items) {
        if (w == 0) {
            System.out.println(cw);
            return;
        }
        for (int i = 0; i < items.length; i++) {
            ArrayList<Integer> temp = (ArrayList<Integer>) cw.clone();
            temp.add(items[i]);
            if (w - items[i] >= 0) {
                f(temp, w - items[i], items);
            }

        }
    }

    public static int f(int n) {
        if (n == 0 || n == 1)
            return 1;
        int pre = 1;
        int now = 1;
        for (int i = 2; i <= n; i++) {
            int tmp = now;
            now = pre + now;
            pre = tmp;
        }
        return now;
    }

    // 爬楼梯 1,2
    public static int f2(int n) {
        if (n == 1 || n == 0) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return f2(n - 1) + f2(n - 2);
    }

    int minNum = Integer.MAX_VALUE;

    /**
     * 使用回溯法获取给定金额最小的硬币数量，调用时num为0
     *
     * @param coinVal 硬币值数组
     * @param total   指定的金额
     * @param num     每个解法所得到的硬币数量
     */
    public void getLeastCoinNumByBackTracking(int[] coinVal, int total, int num) {
        if (total == 0) {
            if (num < minNum)
                minNum = num;
            return;
        }
        for (int i = 0; i < coinVal.length; i++) {
            if (total - coinVal[i] >= 0) {
                getLeastCoinNumByBackTracking(coinVal, total - coinVal[i],
                        num + 1);
            }
        }
    }

    /**
     * 使用动态规划法获取给定金额下最小的硬币数量
     *
     * @param coinVal 硬币值数组
     * @param total   给定金额
     * @return 给定金额下最小的硬币数量
     */
    public int getLeastCoinNumByDP(int[] coinVal, int total) {
        // coinNum存放的是每个对应金额下最少硬币的最优解
        int coinNum[] = new int[total + 1];
        coinNum[0] = 0;
        //初始化coinNum数组，硬币值数组对应的值的硬币数量都为1
        for (int i = 0; i < coinVal.length; i++) {
            coinNum[coinVal[i]] = 1;
        }
        for (int i = 1; i <= total; i++) {
            if (coinNum[i] == 0) {
                int minTemp = Integer.MAX_VALUE; // 获取每个i对应的最小硬币数值
                for (int j = 0; j < coinVal.length; j++) {
                    if (i - coinVal[j] > 0) {
                        int v1 = coinNum[i - coinVal[j]] + 1;
                        if (v1 < minTemp) {
                            minTemp = v1;
                        }
                    }
                }
                coinNum[i] = minTemp;
            }
        }
        return coinNum[total];
    }

}
