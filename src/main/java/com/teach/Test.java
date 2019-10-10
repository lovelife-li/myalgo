package com.teach;

/**
 * @author ldb
 * @date 2019-09-30 9:04
 */
public class Test {
    public static void main(String[] args) {
        float a = 1.74f;
        long b = (long) a;
        System.out.println(b);


    }

    public static void Swap(int[] A, int i, int j) {
        int tmp;
        tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    public static void test1(int[] A, int L, int R) {
        int pivot = A[L];//最左边的元素作为中轴，L表示left, R表示right
        int i = L + 1, j = R;
        //当i == j时，i和j同时指向的元素还没有与中轴元素判断，
        //小于等于中轴元素，i++,大于中轴元素j--,
        //当循环结束时，一定有i = j+1, 且i指向的元素大于中轴，j指向的元素小于等于中轴
        while (i <= j) {
            while (i <= j && A[i] <= pivot) {
                i++;
            }
            while (i <= j && A[j] > pivot) {
                j--;
            }
            //当 i > j 时整个切分过程就应该停止了，不能进行交换操作
            //这个可以改成 i < j， 这里 i 永远不会等于j， 因为有上述两个循环的作用
            if (i <= j) {
                Swap(A, i, j);
                i++;
                j--;
            }
        }
    }
}
