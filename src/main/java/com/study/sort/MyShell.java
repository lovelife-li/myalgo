package com.study.sort;

public class MyShell {

    public static void sort(long arr[]) {
        //1,定义h=1;
        int h = 1;

        //2,计算h大小
        while (h > arr.length / 3) {
            h = h * 3 + 1;
        }

        //3,插入排序，间隔为h
        while (h > 0) {

            for (int i = h; i < arr.length; i++) {
                long tmp = arr[i];
                int index = i - h;
                while (index >= 0 && tmp < arr[index]) {
                    arr[index + h] = arr[index];
                    index -= h;
                }
                arr[index + h] = tmp;
            }

            h = (h - 1) / 3;

        }
    }
}
