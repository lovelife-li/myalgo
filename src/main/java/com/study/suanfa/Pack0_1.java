package com.study.suanfa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ldb
 * @date 2019-11-25 17:48
 */
public class Pack0_1 {
    // 存储背包中物品总重量的最大值
    public static int maxW = Integer.MIN_VALUE;

    /**
     * @param i     表示考察到哪个物品了
     * @param cw    表示当前已经装进去的物品的重量和
     * @param items 表示每个物品的重量
     * @param n     表示物品个数
     * @param w     背包重量
     */
    public static void f(int i, int cw, int[] items, int n, int w) {
        // cw==w 表示装满了 ;i==n 表示已经考察完所有的物品
        if (cw == w || i == n) {
            if (cw > maxW) {
                maxW = cw;
            }
            return;
        }
        f(i + 1, cw, items, n, w);
        if (cw + items[i] <= w) {
            f(i + 1, cw + items[i], items, n, w);
        }
    }

    public static void main(String[] args) {

        int[] a = {1, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        // 假设背包可承受重量 10，物品个数 10，物品重量存储在数组 a 中，那可以这样调用函数：
        f(0, 0, a, a.length, 10);


    }
}
