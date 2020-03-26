package com.study;

/**
 * @author ldb
 * @date 2019-09-29 9:27
 */
public class Test {
    public static void main(String[] args) {

        int n = 4;
        n |= n >>> 1;
        System.out.println(n);
        n |= n >>> 2;
        System.out.println(n);
        n |= n >>> 4;
        System.out.println(n);
        n |= n >>> 8;
        System.out.println(n);
        n |= n >>> 16;
        System.out.println(n);
        System.out.println(tableSizeFor(5));

        System.out.println(Integer.numberOfLeadingZeros(1));

        System.out.println("-1>>>1" + (-1 >>> 1));

        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(5));
        System.out.println(Integer.toBinaryString(5).length());
        System.out.println((1 << 31) - 1);

        System.out.println(test2(15));

    }

    public static int test(int n) {
//        int k=1;
//        while ((n=n>>1)>0){
//            ++k;
//        }
//
        int i = 1;
        while ((i <<= 1) < n) {
        }
        return i;

    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= 100) ? 100 : n + 1;
    }

    public static int test2(int n) {
        int k = numberOfLeadingZeros2(n);
//        int res = 32-k;
//        return 1<<res;
        return 1;
    }

    public static int numberOfLeadingZeros2(int i) {
        // HD, Figure 5-6
        if (i == 0)
            return 32;
        int n = 1;
        if (i >>> 16 == 0) {
            System.out.println(Integer.toBinaryString(i) + " ," + i);
            n += 16;
            i <<= 16;
        }
        if (i >>> 24 == 0) {
            System.out.println(Integer.toBinaryString(i) + " ," + i);
            n += 8;
            i <<= 8;
        }
        if (i >>> 28 == 0) {
            System.out.println(Integer.toBinaryString(i) + " ," + i);
            n += 4;
            i <<= 4;
        }
        System.out.println(Integer.toBinaryString(i) + " ," + i);
        System.out.println(Integer.toBinaryString(i >>> 30) + " ," + (i >>> 30));
        if (i >>> 30 == 0) {
            n += 2;
            System.out.println(Integer.toBinaryString(i) + " ," + i);
            i <<= 2;
        }
        n -= i >>> 31;
        return n;
    }

}
