package com.study.staticPlan;

/**
 * 0-1 背包问题升级版,回溯解决
 * 我们刚刚讲的背包问题,只涉及背包重量和物品重量。我们现在引入物品价值这一变量。对于一组不同重量、不同价值、不可分割的物品,
 * 我们选择将某些物品装入背包,在满足背包最大重量限制的前提下,背包中可装入物品的总价值最大是多少呢？
 *
 * @author ldb
 * @date 2019-11-27 11:32
 */
public class Pack_demo4 {

    private int maxV = Integer.MIN_VALUE; // 结果放到 maxV 中
    private int[] items = {2, 2, 4, 6, 3};  // 物品的重量
    private int[] value = {3, 4, 8, 9, 6}; // 物品的价值
    private int n = 5; // 物品个数
    private int w = 9; // 背包承受的最大重量

    public void f(int i, int cw, int cv) { // 调用 f(0, 0, 0)
        if (cw == w || i == n) { // cw==w 表示装满了,i==n 表示物品都考察完了
            if (cv > maxV) maxV = cv;
            return;
        }
        f(i + 1, cw, cv); // 选择不装第 i 个物品
        if (cw + items[i] <= w) {
            f(i + 1, cw + items[i], cv + value[i]); // 选择装第 i 个物品
        }
    }

    public static void main(String[] args) {
        Pack_demo4 p = new Pack_demo4();
        p.f(0, 0, 0);
        System.out.println(p.maxV);
    }


}
