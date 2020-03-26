package com.study.sort;

import java.util.Arrays;

class QuickSort {
    public static void sort(int left, int right, int[] array) {
        int l = left;
        int r = right;
        int pivot = array[(left + right) / 2];
        int temp = 0;
        while (l < r) {
            while (array[l] < pivot)
                l++;
            while (array[r] > pivot)
                r--;
            if (l >= r)
                break;
            temp = array[l];
            array[l] = array[r];
            array[r] = temp;
            if (array[l] == pivot)
                --r;
            if (array[r] == pivot)
                ++l;
        }
        if (l == r) {
            l++;
            r--;
        }
        if (left < r)
            sort(left, r, array);
        if (right > l)
            sort(l, right, array);
    }

    public static void sort2(int[] param,int l, int r){
        if (l >= r)
            return;
        int tmp = -1;
//  int middle = (l + r) / 2;
//  if (param[l] > param[r]) {
//   tmp = param[l];
//   param[l] = param[r];
//   param[r] = tmp;
//  }
//  if (param[middle] > param[r]) {
//   tmp = param[r];
//   param[r] = param[middle];
//   param[middle] = tmp;
//  }
//  if (param[middle] > param[l]) {
//   tmp = param[l];
//   param[l] = param[middle];
//   param[middle] = tmp;
//  }
        int i = l;
        for (int j = i + 1; j <= r; j++) {
            if (param[j] < param[l] && j != ++i) {
                tmp = param[j];
                param[j] = param[i];
                param[i] = tmp;
            }
        }
        tmp = param[l];
        param[l] = param[i];
        param[i] = tmp;
        sort2(param, l, i - 1);
        sort2(param, i + 1, r);
    }

    public static void main(String[] args) {
        int[] arr = {3, 4, 5, 2, 4, 6, 1, 3, 6, 7};
        sort(0, arr.length - 1, arr);
        System.out.println(Arrays.toString(arr));
    }
}