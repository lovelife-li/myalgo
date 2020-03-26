package com.study.staticPlan;

/**
 * 我们有一个数字序列包含 n 个不同的数字,如何求出这个序列中的最长递增子序列长度？
 * 比如 2, 9, 3, 6, 5, 1, 7 这样一组数字序列,它的最长递增子序列就是 2, 3, 5, 7,所以最长递增子序列的长度是 4。
 *
 * @author ldb
 * @date 2019-11-28 14:27
 */
public class Demo1 {
    public int longestIncreaseSubArrayDP(int[] array) {
        if (array.length < 2) return array.length;
        int[] state = new int[array.length];
        state[0] = 1;
        for (int i = 1; i < state.length; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (array[j] < array[i]) {
                    if (state[j] > max) {
                        max = state[j];
                    }
                }
            }
            state[i] = max + 1;
        }
        int result = 0;
        for (int i = 0; i < state.length; i++) {
            if (state[i] > result) result = state[i];
        }
        return result;
    }

    public static void main(String[] args) {
        Demo1 d = new Demo1();
        int[] a = {2, 9, 3, 6, 5, 1, 7};
        System.out.println(d.longestIncreaseSubArrayDP(a));
//        d.f(0, -1, 0);
//        System.out.println(d.maxLen);
//        System.out.println(d.f2(a, a.length));
        System.out.println(d.f3(a, a.length));
    }

    int maxLen = 0;

    private int[] nums = new int[]{2, 1, 5, 3, 6, 4, 8, 9, 7};

    private void f(int i, int lastIdx, int len) {
        if (i == nums.length) {
            maxLen = Math.max(maxLen, len);
            return;
        }
        if (lastIdx == -1 || nums[i] > nums[lastIdx]) {
            //加入到最长递增子数组中
            f(i + 1, i, len + 1);
        }
        //不加入到最长递增子数组中
        f(i + 1, lastIdx, len);
    }

    public int f2(int[] items, int n) {
        if (n == 1) {
            return 1;
        }
        int[] nums = new int[n];
        nums[0] = 1;
        for (int i = 1; i < n; i++) {
            int max = 1;
            for (int j = i - 1; j >= 0; --j) {
                if (items[j] < items[i]) {
                    max = Math.max(max, nums[j] + 1);
                }
            }
            nums[i] = max;

        }
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (max < nums[i]) {
                max = nums[i];
            }
        }
        return max;
    }

    public int f3(int[] items, int n) {
        int[] temp = new int[n];
        temp[0] = items[0];
        int len = 1;
        int pos = 0;
        for (int i = 1; i < n; i++) {
            if (items[i] > temp[len - 1]) {
                temp[len] = items[i];
                ++len;
            } else {
                pos = bsearch(temp, len, items[i]);
                temp[pos] = items[i];
            }
        }
        return len;
    }

    public int bsearch(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] == value) {
                return mid;
            } else if (a[mid] > value) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;

    }
}
