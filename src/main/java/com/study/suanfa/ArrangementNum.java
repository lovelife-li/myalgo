package com.study.suanfa;

import java.util.Arrays;

/**
 * 分析全排列的时间复杂度
 * 全排列的递归算法的时间复杂度大于 O(n!)，小于 O(n∗n!)，
 *
 * @author ldb
 * @date 2019-10-30 10:03
 */
public class ArrangementNum {

    // k 表示要处理的子数组的数据个数
    public static void printPermutations(int[] data, int n, int k) {
        if (k == 1) {
            for (int i = 0; i < n; ++i) {
                System.out.print(data[i] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < k; ++i) {
            int tmp = data[i];
            data[i] = data[k - 1];
            data[k - 1] = tmp;

            printPermutations(data, n, k - 1);

            tmp = data[i];
            data[i] = data[k - 1];
            data[k - 1] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        printPermutations(a, 4, 4);
//
//        f(a,4,0);
     //   Integer record[] = new Integer[data.length];
       // f(record, 0);
    }

    public static void f(int[] data, int n, int k) {
        if (k == n) {
            System.out.println(Arrays.toString(data));
        }

        for (int i = k; i < n; ++i) {
            int tmp = data[i];
            data[i] = data[k];
            data[k] = tmp;

            f(data, n, k + 1);

            tmp = data[i];
            data[i] = data[k];
            data[k] = tmp;
        }

    }

    static int[] data = {1, 2, 3,4};

    static void f(Integer[] record, int index) {
        if (index == data.length) {
            System.out.println(Arrays.toString(record));

            return;
        }
        for (int i = 0; i < data.length; i++) {
            if (!Arrays.asList(record).contains(data[i])) {
                record[index] = data[i];
                f(record, index + 1);
                record[index] = 0;

            }
        }
    }

}
