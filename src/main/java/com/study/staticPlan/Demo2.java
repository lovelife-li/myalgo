package com.study.staticPlan;

import java.util.ArrayList;

/**
 * 1,2,5,10 数的分解
 *
 * @author ldb
 * @date 2019-12-03 17:13
 */
public class Demo2 {
    public static void main(String[] args) {

        int[] items = {1, 2, 5, 10};
        ArrayList<Integer> inlist = new ArrayList<>();
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        System.out.println(divNum(items, 10, inlist, res));

    }

    public static ArrayList<ArrayList<Integer>> divNum(int[] items, int n, ArrayList<Integer> inlist,
                                                       ArrayList<ArrayList<Integer>> res) {
        if (n == 0) {
            res.add(inlist);
        }
        if (n < 0) {
            return null;
        }
        for (int i = 0; i < items.length; i++) {
            if (n - items[i] >= 0) {
                ArrayList<Integer> list = (ArrayList<Integer>) inlist.clone();
                list.add(items[i]);
                divNum(items, n - items[i], list,res);
            }

        }
        return res;
    }
}
