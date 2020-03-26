package com.study.staticPlan;

import java.util.ArrayList;

/**
 * 8 数的分解 1,2,4
 *
 * @author ldb
 * @date 2019-12-03 17:13
 */
public class Demo3 {
    public static void main(String[] args) {

        ArrayList<Integer> inlist = new ArrayList<>();
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        System.out.println(divNum(8, inlist, res));

    }

    public static ArrayList<ArrayList<Integer>> divNum(int n, ArrayList<Integer> inlist,
                                                       ArrayList<ArrayList<Integer>> res) {
        if (n == 1) {
            if (!inlist.contains(1)) {
                inlist.add(1);
            }
            res.add(inlist);
        }
        for (int i = n; i > 0; --i) {
            ArrayList<Integer> list = (ArrayList<Integer>) inlist.clone();
            if (i == 1 && !list.contains(1)) {
                list.add(i);
                divNum(n / i, list, res);
            }
            if (i != 1 && n % i == 0) {
                list.add(i);
                divNum(n / i, list, res);
            }
        }
        return res;
    }
}
