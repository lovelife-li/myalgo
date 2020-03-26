package com.study.heap;

/**
 * @author ldb
 * @date 2019-10-23 9:31
 */
public class Heap {
    // 数组，从下标 1 开始存储数据
    private int[] a;
    // 堆可以存储的最大数据个数
    private int n;
    // 堆中已经存储的数据个数
    private int count;

    public Heap(int capacity) {
        this.a = new int[capacity + 1];
        this.n = capacity;
        this.count = 0;
    }

    /**
     * 插入数据先放在最后，再从下向上比较
     *
     * @param data
     */
    public void insert(int data) {
        if (count >= n) {
            return;
        }
        ++count;
        a[count] = data;
        int i = count;
        while (i >> 1 > 0 && a[i] > a[i >> 1]) {
            swap(a, i, i >> 1);
            i = i >> 1;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    /**
     * 删除堆顶元素
     */
    public void removeMax() {
        if (count > 0) {
            a[1] = a[count];
            count--;
            heapify(a, count, 1);
        }
    }

    /**
     * 从上往下堆化
     *
     * @param a 数组
     * @param n 堆中元数个数
     * @param i 堆化元素下标
     */
    private static void heapify(int[] a, int n, int i) {
        while (true) {
            int pos = i;
            if (i * 2 <= n && a[i] < a[i * 2]) {
                pos = i * 2;
            }
            if (i * 2 + 1 <= n && a[pos] < a[i * 2 + 1]) {
                pos = i * 2 + 1;
            }
            // 没有交换了
            if (pos == i) {
                break;
            }
            swap(a, i, pos);
            i = pos;
        }
    }

    /**
     * 建堆
     *
     * @param a 数组
     * @param n 堆中元数个数
     */
    private static void buildHeap(int[] a, int n) {
        for (int i = n / 2; i > 0; i--) {
            heapify(a, n, i);
        }
    }

    /**
     * 排序
     *
     * @param a  数组
     * @param n  堆中元素最后下标
     */
    private static void sort(int[] a, int n) {
        buildHeap(a, n);
        int i = n;
        while (i > 1) {
            swap(a, i, 1);
            --i;
            heapify(a, i, 1);
        }
    }

    public static void main(String[] args) {

    }
}

