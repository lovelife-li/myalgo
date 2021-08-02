package com.title.n01stack;

import java.util.Stack;

/**
 * @author ldb
 * @date 2021/8/3
 * 柱状图中最大的矩形
 * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/description/
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 * 以上是柱状图的示例，其中每个柱子的宽度为 1，给定的高度为 [2,1,5,6,2,3]。
 * 图中阴影部分为所能勾勒出的最大矩形面积，其面积为 10 个单位。
 * 示例:
 * 输入: [2,1,5,6,2,3]
 * 输出: 10
 */
public class LargestRectangle {

    /**
     * 思路：
     * 在选择的时候，由于要构造出最大的矩形。一种暴力的做法是：
     * for (int i = 0; i < N; i++) {
     *     ans = max(ans, A[i]参与构造的最大矩形面积);
     * }
     *
     * 那么在求A[i]参与构造的最大矩形的时候。A[i]左边与右边的值
     * 肯定都要 >= A[i]才可以。
     *
     * 那么也就是说，我们需要找到左边第一个比A[i]小的数, leftPos
     * 也需要找到右边第一个比A[i]小的数。rightPos
     *
     * 那么前面暴力的代码就可以写成：
     * for (int i = 0; i < N; i++) {
     *     leftPos = findLeftSmall(A[i]);
     *     rightPos = findRightSmall(A[i]);
     *     ans = max(ans, A[i] * (rightPos - leftPos - 1));
     * }
     * 而leftPos和rightPos我们都可以先通过单调栈得到。那么到这里，问题就解决了。
     */
    public int largestRectangleArea(int[] A) {
        final int N = A == null ? 0 : A.length;
        int[] leftSmall = LeftSmall.findLeftSmall(A);
        int[] rightSmall = RightSmall.findRightSmall(A);
        int ans = 0;
        for (int i = 0; i < N; i++) {
            final int height = A[i];
            // 左边比我小的位置
            // 右边比我小的位置
            final int leftPos = leftSmall[i];
            final int rightPos = rightSmall[i] == -1 ? N : rightSmall[i];

            // 现在我们确定区间(leftPos, rightPos)
            // 注意两边都是开区间。在这个区间里面，所有的数肯定都是 >= A[i]的。
            // 那么底部的宽度就是
            final int width = rightPos - leftPos - 1;
            final int area = height * width;

            ans = Math.max(ans, area);
        }
        return ans;
    }

    /*
     * 思路：
     * 2个重要的性质：
     * 一个递增栈里面存放的是数组的下标 stack = [i, j]
     * 性质1: 对于j而言，下标[i+1 ... j-1] 这里面的元素的A[x]值都 >= A[j]
     *       对于i而言，(-1, i-1] 这里面元素的值都 >= A[i];
     *
     * 如果此时A[k]要入栈。并且A[k] < A[j]，要将A[j]出栈。
     * 性质2: A[j+1 ... k-1]这个区间里面的元素都大于A[j]
     *
     */
    public int largestRectangleArea2(int[] A) {
        final int N = A == null ? 0 : A.length;

        // 虽然可以用Stack<Integer>，但是这里我们为了更快地操作，我们用
        // 数组模拟栈来运行，因为我们知道最多存放的内容实际上就是N个
        int top = 0;
        // s[top-1]表示栈顶元素
        int[] s = new int[N];

        int ans = 0;

        // 注意，这里我们取到了i == N
        // 按理说，不应该取到i == N的。但是这时候，主要是为了处理这种数组
        // A = [1, 2, 3]
        // 没有任何元素会出栈。
        // 那么最后我们用一个0元素，把所有的元素都削出栈。
        // 这样代码就可以统一处理掉。
        for (int i = 0; i <= N; i++) {
            // 注意：当i == N的时候，x = -1;
            // 比数组中的元素都要小。
            final int x = i == N ? -1 : A[i];
            while (top > 0 && A[s[top - 1]] > x) {
                // 计算以A[s[top]]的元素的高度的矩形。
                final int height = A[s[--top]];
                // i元素要将index = s[top-1]的元素出栈。
                // 那么根据性质2：
                // 此时A[s[top-1] .... i) 这个区间里面的元素都是
                // 大于A[s[top-1]]的
                final int rightPos = i;
                // 这里需要使用性质1.
                // 注意：当栈中一个元素都没有的时候，要取-1
                final int leftPos = top > 0 ? s[top - 1] : -1;
                final int width = rightPos - leftPos - 1;
                final int area = height * width;
                ans = Math.max(ans, area);
            }

            s[top++] = i;
        }
        return ans;
    }
}
class LeftSmall {
    // 当我们要找左边比我小的元素的时候，需要用递增栈
    public static int[] findLeftSmall(int[] A) {
        if (A == null || A.length == 0) {
            return new int[0];
        }
        // 结果数组
        int[] ans = new int[A.length];
        // 注意，栈中的元素记录的是下标
        Stack<Integer> t = new Stack<>();
        // 注意这里的遍历方向发生了变化，因为我们是要找到左边比我小的元素的位置
        for (int i = A.length - 1; i >= 0; i--) {
            final int x = A[i];
            // 每个元素都遍历栈中的元素完成消除动作
            // 这里是递增栈
            // 如果发现进来的元素x与栈中元素相比
            // 如果大于栈中的元素，那么要把栈中的元素弹出去
            while (!t.empty() && A[t.peek()] > x) {
                // 消除的时候，记录一下被谁消除了
                ans[t.peek()] = i;
                // 消除时候，值更大的需要从栈中消失
                t.pop();
            }
            // 剩下的入栈
            t.push(i);
        }
        // 栈中剩下的元素，由于没有人能消除他们，因此，只能将结果设置为-1。
        while (!t.empty()) {
            ans[t.peek()] = -1;
            t.pop();
        }

        return ans;
    }
}
class RightSmall {
    public static int[] findRightSmall(int[] A) {
        // 结果数组
        int[] ans = new int[A.length];
        // 注意，栈中的元素记录的是下标
        Stack<Integer> t = new Stack<>();
        for (int i = 0; i < A.length; i++) {
            final int x = A[i];
            // 每个元素都向左遍历栈中的元素完成消除动作
            while (!t.empty() && A[t.peek()] > x) {
                // 消除的时候，记录一下被谁消除了
                ans[t.peek()] = i;
                // 消除时候，值更大的需要从栈中消失
                t.pop();
            }
            // 剩下的入栈
            t.push(i);
        }
        // 栈中剩下的元素，由于没有人能消除他们，因此，只能将结果设置为-1。
        while (!t.empty()) {
            ans[t.peek()] = -1;
            t.pop();
        }
        return ans;
    }
}