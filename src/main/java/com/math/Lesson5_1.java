package com.math;

import java.util.ArrayList;
import java.util.List;

public class Lesson5_1 {

    public static long[] rewards = {1, 2, 5, 10};    // 四种面额的纸币

    /**
     * @param totalReward-奖赏总金额，result-保存当前的解
     * @return void
     * @Description: 使用函数的递归（嵌套）调用，找出所有可能的奖赏组合
     */


    public static void get(long totalReward, ArrayList<Long> result) {
        // 当totalReward = 0时，证明它是满足条件的解，结束嵌套调用，输出解
        if (totalReward == 0) {
         //  System.out.println(result.toString());
            return;
        }
        // 当totalReward < 0时，证明它不是满足条件的解，不输出
        else if (totalReward < 0) {
            return;
        } else {
            for (int i = 0; i < rewards.length; i++) {
                // 剩下的问题，留给嵌套调用去解决
                if (totalReward - rewards[i]>=0){
                    // 由于有4种情况，需要clone当前的解并传入被调用的函数
                    ArrayList<Long> newResult = (ArrayList<Long>) (result.clone());
                    // 记录当前的选择，解决一点问题
                    newResult.add(rewards[i]);
                    get(totalReward - rewards[i], newResult);
                }
            }
        }

    }

    public static void main(String[] args) {
        int totalReward = 10;
        ArrayList<Long> list = new ArrayList<>();
        Lesson5_1.get(totalReward, list);
        System.out.println(list);
        Lesson5_1.get2(8, new ArrayList<Long>());


    }


    public static void get2(long n, ArrayList<Long> result) {
        if (n == 1) {
            if (!result.contains(1L)) {
                result.add(1L);
            }
            System.out.println(result);
            return;
        }
        // 当totalReward < 0时，证明它不是满足条件的解，不输出
        else {
            for (long i = n; i >= 1; i--) {
                ArrayList<Long> newResult = (ArrayList<Long>) (result.clone());
                // 处理1的情况
                if (i == 1 && !result.contains(1L)) {
                    newResult.add(i);
                    get2(n / i, newResult);
                }
                if (i != 1 && n % i == 0) {
                    newResult.add(i);
                    // 剩下的问题，留给嵌套调用去解决
                    get2(n / i, newResult);
                }
            }
        }
    }

    public static List<Long> getNum(long n) {
        ArrayList<Long> res = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                res.add((long) i);
            } else {
                continue;
            }
        }
        return res;
    }


}