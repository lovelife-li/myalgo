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

    public static void main(String[] args) {
        int[] arr = {3, 4, 5, 2, 4, 6, 1, 3, 6, 7};
        sort(0, arr.length - 1, arr);
        System.out.println(Arrays.toString(arr));
    }
}