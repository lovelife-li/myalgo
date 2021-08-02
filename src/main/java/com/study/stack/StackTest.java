package com.study.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author ldb
 * @date 2021/8/2
 * @dsscription
 */
public class StackTest {

    /**
     * 字符串中只有字符'('和')'。合法字符串需要括号可以配对。比如：
     * 输入："()"
     * 输出：true
     * 解释：()，()()，(())是合法的。)(，()(，(()是非法的。
     * 请你实现一个函数，来判断给定的字符串是否合法。
     */
    boolean isValid(String s) {
        // 当字符串本来就是空的时候，我们可以快速返回true
        if (s == null || s.length() == 0) {
            return true;
        }
        // 当字符串长度为奇数的时候，不可能是一个有效的合法字符串
        if (s.length() % 2 == 1) {
            return false;
        }
        // 消除法的主要核心逻辑:
        Stack<Character> t = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            // 取出字符
            char c = s.charAt(i);
            if (c == '(') {
                // 如果是'('，那么压栈
                t.push(c);
            } else if (c == ')') {
                // 如果是')'，那么就尝试弹栈
                if (t.empty()) {
                    // 如果弹栈失败，那么返回false
                    return false;
                }
                t.pop();
            }
        }
        return t.empty();
    }

    /**
     * 栈中元素都相同时，实际上没有必要使用栈，只需要记录栈中元素个数。
     *
     * @param s
     * @return
     */
    public boolean isValid2(String s) {
        // 当字符串本来就是空的时候，我们可以快速返回true
        if (s == null || s.length() == 0) {
            return true;
        }
        // 当字符串长度为奇数的时候，不可能是一个有效的合法字符串
        if (s.length() % 2 == 1) {
            return false;
        }
        // 消除法的主要核心逻辑:
        int leftBraceNumber = 0;
        for (int i = 0; i < s.length(); i++) {
            // 取出字符
            char c = s.charAt(i);
            if (c == '(') {
                // 如果是'('，那么压栈
                leftBraceNumber++;
            } else if (c == ')') {
                // 如果是')'，那么就尝试弹栈
                if (leftBraceNumber <= 0) {
                    // 如果弹栈失败，那么返回false
                    return false;
                }
                --leftBraceNumber;
            }
        }
        return leftBraceNumber == 0;
    }

    /**
     * 在水中有许多鱼，可以认为这些鱼停放在 x 轴上。再给定两个数组 Size，Dir，Size[i] 表示第 i 条鱼的大小，
     * Dir[i] 表示鱼的方向 （0 表示向左游，1 表示向右游）。这两个数组分别表示鱼的大小和游动的方向，并且两个数组的长度相等。
     * 鱼的行为符合以下几个条件:
     * 1,所有的鱼都同时开始游动，每次按照鱼的方向，都游动一个单位距离；
     * 2,当方向相对时，大鱼会吃掉小鱼；
     * 3,鱼的大小都不一样。
     * 输入：Size = [4, 2, 5, 3, 1], Dir = [1, 1, 0, 0, 0]
     * 输出：3
     * <p>
     * 思路：活下来的鱼的行为就是一个栈。每当有新的鱼要进来的时候，就会与栈顶的鱼进行比较，如果方向相对，比较大小，大鱼吃小鱼
     */
    int solution(int[] fishSize, int[] fishDirection) {
        // 首先拿到鱼的数量
        // 如果鱼的数量小于等于1，那么直接返回鱼的数量
        final int fishNumber = fishSize.length;
        if (fishNumber <= 1) {
            return fishNumber;
        }
        // 0表示鱼向左游
        final int left = 0;
        // 1表示鱼向右游
        final int right = 1;
        Stack<Integer> t = new Stack<>();
        for (int i = 0; i < fishNumber; i++) {
            // 当前鱼的情况：1，游动的方向；2，大小
            final int curFishDirection = fishDirection[i];
            final int curFishSize = fishSize[i];
            // 当前的鱼是否被栈中的鱼吃掉了
            boolean hasEat = false;
            // 如果栈中还有鱼，并且栈中鱼向右，当前的鱼向左游，那么就会有相遇的可能性
            while (!t.empty() && fishDirection[t.peek()] == right &&
                    curFishDirection == left) {
                // 如果栈顶的鱼比较大，那么把新来的吃掉
                if (fishSize[t.peek()] > curFishSize) {
                    hasEat = true;
                    break;
                }
                // 如果栈中的鱼较小，那么会把栈中的鱼吃掉，栈中的鱼被消除，所以需要弹栈。
                t.pop();
            }
            // 如果新来的鱼，没有被吃掉，那么压入栈中。
            if (!hasEat) {
                t.push(i);
            }
        }
        return t.size();
    }

    /**
     * 【题目】一个整数数组 A，找到每个元素：右边第一个比我小的下标位置，没有则用 -1 表示。
     * 输入：[5, 2]
     * 输出：[1, -1]
     *
     * 一个数总是想与左边比它大的数进行匹配，匹配到了之后，小的数会消除掉大的数。
     * 结论：数组中右边第一个比我小的元素的位置，求解用递增栈。
     */
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

    /*
     * 题目：给定一个数组，要找到这个数组里面每个元素右边比我大的元素的位置
     * - 注意：是右边第一个比我大的，如果有多个的话
     * - 如果没有，那么用-1表示。
     * 返回：一个数组，表示右边比我大的数的下标位置
     *
     * 输入：[5, 6]
     * 输出：[1, -1]
     * 解释：A[0] = 5，右边比我大的是A[1] = 6, 所以记录为 = 1
     *       A[1] = 6, 右边比我大的元素没有，所以记录为   = -1
     *       所以返回值是[1, -1]
     */
    // 当我们要找右边比我大的元素的时候，需要用递减栈
    public static int[] findRightLarge(int[] A) {
        if (A == null || A.length == 0) {
            return new int[0];
        }

        // 结果数组
        int[] ans = new int[A.length];
        // 注意，栈中的元素记录的是下标
        Stack<Integer> t = new Stack<>();
        for (int i = 0; i < A.length; i++) {
            final int x = A[i];
            // 每个元素都向左遍历栈中的元素完成消除动作
            // 这里是递减栈
            // 如果发现进来的元素x与栈中元素相比
            // 如果大于栈中的元素，那么要把栈中的元素弹出去
            while (!t.empty() && A[t.peek()] < x) {
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

    /*
     * 题目：给定一个数组，要找到这个数组里面每个元素左边比我小的元素的位置
     * - 注意：是左边第一个比我小的，如果有多个的话
     * - 如果没有，那么用-1表示。
     *
     * 返回：一个数组，表示左边比我小的数的下标位置
     *
     * 输入：[5, 6]
     * 输出：[-1, 0]
     * 解释：A[0] = 5，左边比我小的元素没有, 所以记录为 = -1
     *       A[1] = 6, 左边比我小的元素为A[0] = 5，所以记录为   = 0
     *       所以返回值是[-1, 0]
     */
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
            // 这里是递减栈
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

    /*
     * 题目：给定一个数组，要找到这个数组里面每个元素左边比我大的元素的位置
     * - 注意：是左边第一个比我大的，如果有多个的话
     * - 如果没有，那么用-1表示。
     *
     * 返回：一个数组，表示左边比我大的数的下标位置
     *
     * 输入：[5, 6]
     * 输出：[-1, -1]
     * 解释：A[0] = 5，左边比我大的元素没有, 所以记录为 = -1
     *       A[1] = 6, 左边比我大的元素为没有，所以记录为 = -1
     *       所以返回值是[-1, -1]
     */
    // 当我们要找左边比我大的元素的时候，需要用递减栈
    public static int[] findLeftLarge(int[] A) {
        if (A == null || A.length == 0) {
            return new int[0];
        }
        // 结果数组
        int[] ans = new int[A.length];
        // 注意，栈中的元素记录的是下标
        Stack<Integer> t = new Stack<>();
        // 注意这里的遍历方向发生了变化，因为我们是要找到左边比我大的元素的位置
        for (int i = A.length - 1; i >= 0; i--) {
            final int x = A[i];
            // 每个元素都遍历栈中的元素完成消除动作
            // 这里是递减栈
            // 如果发现进来的元素x与栈中元素相比
            // 如果大于栈中的元素，那么要把栈中的元素弹出去
            while (!t.empty() && A[t.peek()] < x) {
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

    /**
     * 题目】给定一个正整数数组和 k，要求依次取出 k 个数，输出其中数组的一个子序列，需要满足：1. 长度为 k；2.字典序最小。
     * 输入：nums = [3,5,2,6], k = 2
     * 输出：[2,6]
     * 解释：在所有可能的解：{[3,5], [3,2], [3,6], [5,2], [5,6], [2,6]} 中，[2,6] 字典序最小。
     * 所谓字典序就是，给定两个数组：x = [x1,x2,x3,x4]，y = [y1,y2,y3,y4]，
     * 如果 0 ≤ p < i，xp == yp 且 xi < yi，那么我们认为 x 的字典序小于 y。
     * @param nums
     * @param k
     * @return
     */
    public static int[] findSmallSeq(int[] nums, int k) {
        int[] ans = new int[k];
        Stack<Integer> s = new Stack<>();
        // 这里生成单调栈
        for (int i = 0; i < nums.length; i++) {
            final int x = nums[i];
            final int left = nums.length - i;
            // 注意我们想要提取出k个数，所以注意控制扔掉的数的个数
            while (!s.empty() && (s.size() + left > k) && s.peek() > x) {
                s.pop();
            }
            s.push(x);
        }
        // 如果递增栈里面的数太多，那么我们只需要取出前k个就可以了。
        // 多余的栈中的元素需要扔掉。
        while (s.size() > k) {
            s.pop();
        }
        // 把k个元素取出来，注意这里取的顺序!
        for (int i = k - 1; i >= 0; i--) {
            ans[i] = s.peek();
            s.pop();
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = findRightSmall(new int[]{1, 2, 4, 9, 4, 0, 5});
        System.out.println(Arrays.toString(arr));
    }
}