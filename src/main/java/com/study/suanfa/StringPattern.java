package com.study.suanfa;

import java.util.Arrays;

/**
 * @author ldb
 * @date 2019-10-28 13:53
 */
public class StringPattern {

    /**
     * BF 算法中的 BF 是 Brute Force 的缩写，中文叫作暴力匹配算法，也叫朴素匹配算法。
     *
     * @param a 主串
     * @param n 主串长度
     * @param b 模式串
     * @param m 模式串长度
     * @return 子串位置
     */
    public int bf(char[] a, int n, char[] b, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= m; j++) {
                if (j == m) {
                    return i;
                }
                if (a[i + j] != b[j]) {
                    break;
                }
            }
        }
        return -1;
    }

    /**
     * RK 算法的全称叫 Rabin-Karp 算法，是由它的两位发明者 Rabin 和 Karp 的名字来命名的。
     *
     * @param a 主串
     * @param n 主串长度
     * @param b 模式串
     * @param m 模式串长度
     * @return 子串位置
     */
    public int rk(char[] a, int n, char[] b, int m) {

        // 定义数值表
        int[] tab = new int[m];
        for (int i = 0; i < m; i++) {
            tab[i] = (int) Math.pow(26, i);
        }
        // 计算子串hash值
        int hashb = 0;

        // 临时变量
        int suba = 0;
        for (int i = 0; i < m; i++) {
            hashb += (b[i] - 'a') * tab[m - i - 1];
            suba += (a[i] - 'a') * tab[m - i - 1];
        }
        if (suba == hashb) {
            return 0;
        }
        for (int i = 1; i < n; i++) {
            if (i > (n - m)) {
                return -1;
            }
            // a[i] 和 a[i-1] 关系
            suba = suba - tab[m - 1] * (a[i - 1] - 'a') * 26 + tab[0] * (a[i + m - 1] - 'a');
            if (suba != hashb) {
                continue;
            }
            for (int j = 0; j <= m; j++) {
                if (j == m) {
                    return i;
                }
                if (a[i + j] != b[j]) {
                    break;
                }
            }
        }
        return -1;
    }


    private static final int SIZE = 256; // 全局变量或成员变量

    /**
     * @param b  b 是模式串
     * @param m  m 是模式串的长度
     * @param bc bc 表示散列表
     */
    private void generateBC(char[] b, int m, int[] bc) {
        for (int i = 0; i < SIZE; ++i) {
            bc[i] = -1; // 初始化 bc
        }
        for (int i = 0; i < m; ++i) {
            int ascii = (int) b[i]; // 计算 b[i] 的 ASCII 值
            bc[ascii] = i;
        }
    }

    /**
     * BM算法
     *
     * @param a 主串
     * @param n 主串长度
     * @param b 模式串
     * @param m 模式串长度
     * @return
     */
    public int bm(char[] a, int n, char[] b, int m) {
        int[] bc = new int[SIZE]; // 记录模式串中每个字符最后出现的位置
        generateBC(b, m, bc); // 构建坏字符哈希表
        boolean[] prefix = new boolean[m];
        int[] suffix = new int[m];
        generateGS(b, m, suffix, prefix);
        int i = 0; // i 表示主串与模式串对齐的第一个字符
        while (i <= n - m) {
            int j;
            for (j = m - 1; j >= 0; --j) { // 模式串从后往前匹配
                if (a[i + j] != b[j]) break; // 找到坏字符对应模式串中的下标是 j
            }
            if (j < 0) {
                return i; // 匹配成功，返回主串与模式串第一个匹配的字符的位置
            }
            // 这里等同于将模式串往后滑动 j-bc[(int)a[i+j]] 位
            int x = (j - bc[(int) a[i + j]]);
            int y = 0;
            // 如果有好后缀的话
            if (j < m - 1) {
                y = moveByGS(j, m, suffix, prefix);
            }
            i = i + Math.max(x, y);
        }
        return -1;
    }

    /**
     * @param j      表示坏字符对应的模式串中的字符下标
     * @param m      表示模式串长度
     * @param suffix
     * @param prefix
     * @return
     */
    private int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        int k = m - 1 - j; // 好后缀长度
        if (suffix[k] != -1) return j - suffix[k] + 1;
        // j+2表示从好后缀第二位开始，因为没有子串
        for (int r = j + 2; r <= m - 1; ++r) {
            if (prefix[m - r] == true) {
                return r;
            }
        }
        return m;
    }

    /**
     * @param b      b 表示模式串
     * @param m      m 表示长度
     * @param suffix 数组，记录子串下标
     * @param prefix 数组，记录子串是不是前缀
     */
    private void generateGS(char[] b, int m, int[] suffix, boolean[] prefix) {
        for (int i = 0; i < m; ++i) { // 初始化
            suffix[i] = -1;
            prefix[i] = false;
        }
        for (int i = 0; i < m - 1; ++i) { // b[0, i]
            int j = i;
            // 公共后缀子串长度
            int k = 0;
            // 与 b[0, m-1] 求公共后缀子串
            while (j >= 0 && b[j] == b[m - 1 - k]) {
                ++k;
                //j 表示公共后缀子串在 b[0, i] 中的起始下标
                suffix[k] = j;
                --j;
            }

            if (j == -1) {
                prefix[k] = true; // 如果公共后缀子串也是模式串的前缀子串
            }
        }
    }
    // a, b 分别是主串和模式串；n, m 分别是主串和模式串的长度。
    public  int kmp(char[] a, int n, char[] b, int m) {
        int[] next = getNexts(b, m);
        int j = 0;
        for (int i = 0; i < n; ++i) {
            while (j > 0 && a[i] != b[j]) { // 一直找到 a[i] 和 b[j]
                j = next[j - 1] + 1;
            }
            if (a[i] == b[j]) {
                ++j;
            }
            if (j == m) { // 找到匹配模式串的了
                return i - m + 1;
            }
        }
        return -1;
    }

    // b 表示模式串，m 表示模式串的长度
    private  int[] getNexts(char[] b, int m) {
        int[] next = new int[m];
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < m; ++i) {
            while (k != -1 && b[k + 1] != b[i]) {
                k = next[k];
            }
            if (b[k + 1] == b[i]) {
                ++k;
            }
            next[i] = k;
        }
        return next;
    }

    public static void main(String[] args) {
        StringPattern pattern = new StringPattern();
        String a = "ababaeabac";
        String b = "ababacd";
//        int index = pattern.bf(a.toCharArray(), a.length(), b.toCharArray(), b.length());
//        System.out.println(index);
//        int[] suffix = new int[b.length()];
//        boolean[] prefix = new boolean[b.length()];
//        pattern.generateGS(b.toCharArray(), b.length(), suffix, prefix);
//        int bm = pattern.kmp(a.toCharArray(), a.length(), b.toCharArray(), b.length());
//        System.out.println(bm);

        System.out.println(Arrays.toString(pattern.getNexts(b.toCharArray(),b.length())));
    }
}
