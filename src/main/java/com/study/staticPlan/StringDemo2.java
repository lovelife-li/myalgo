package com.study.staticPlan;

/**
 * @author ldb
 * @date 2019-11-28 13:58
 */
public class StringDemo2 {
    private char[] a = "mitcmu".toCharArray();
    private char[] b = "mtacnu".toCharArray();
    private int n = 6;
    private int m = 6;
    private int maxlcs = 0; // 存储结果

    // 调用方式 lcsBT(0, 0, 0);
    public void lcsBT(int i, int j, int max_lcs) {
        if (i == n || j == m) {
            if (max_lcs > maxlcs) {
                maxlcs = max_lcs;
            }
            return;
        }
        if (a[i] == b[j]) { // 两个字符匹配
            lcsBT(i + 1, j + 1, max_lcs + 1);
        } else { // 两个字符不匹配
            lcsBT(i + 1, j, max_lcs); // 删除 a[i] 或者 b[j] 前添加一个字符
            lcsBT(i, j + 1, max_lcs); // 删除 b[j] 或者 a[i] 前添加一个字符
        }
    }

    public static void main(String[] args) {
        StringDemo2 s = new StringDemo2();
        s.lcsBT(0, 0, 0);
        System.out.println(s.maxlcs);

        char[] a = "mitcmu".toCharArray();
        char[] b = "mtacnu".toCharArray();
        System.out.println(s.lwstDP(a, a.length, b, b.length));
    }


    public int lwstDP(char[] a, int n, char[] b, int m) {
        int[][] maxlcs = new int[n][m];
        for (int i = 0; i < n; i++) {
            if (a[i] == b[0]) {
                maxlcs[0][i] = 1;
            } else if (i != 0) {
                maxlcs[0][i] = maxlcs[0][i - 1];
            } else {
                maxlcs[0][0] = 0;
            }
        }
        for (int i = 0; i < m; i++) {
            if (b[i] == a[0]) {
                maxlcs[i][0] = 1;
            } else if (i != 0) {
                maxlcs[i][0] = maxlcs[i - 1][0];
            } else {
                maxlcs[0][0] = 0;
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (a[i]==b[j]){
                    maxlcs[i][j] =max(maxlcs[i-1][j],maxlcs[i][j-1],maxlcs[i-1][j-1]+1);
                }else {
                    maxlcs[i][j] =max(maxlcs[i-1][j],maxlcs[i][j-1],maxlcs[i-1][j-1]);
                }

            }
        }
        return maxlcs[n-1][m-1];

    }

    private int max(int x, int y, int z) {
        int max = 0;
        if (x > max) max = x;
        if (y > max) max = y;
        if (z > max) max = z;
        return max;
    }
}
