package com.study.linkedlist;

/**
 * @author ldb
 * @date 2020/05/07
 * @description 约瑟夫问题是个有名的问题：N个人围成一圈，从第一个开始报数，第M个将被杀掉，最后剩下一个，其余人都将被杀掉。
 * 例如N=6，M=5，被杀掉的顺序是：5，4，6，2，3，1。
 */
public class Josephs {

    public static void main(String[] args) {
        Node head = createList(100);
        int m = 5;
        Node alive = alive(head, m);

        System.out.print(alive.val);

    }

    private static Node alive(Node head, int m) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        Node cur = head;
        // 找到尾节点，才能删除头节点，单链表
        Node tmp = head;
        while (tmp.next != head) {
            tmp = tmp.next;
        }
        // 尾节点
        Node prev = tmp;
        // 数数
        int i = 0;
        while (cur != prev) {
            if (++i == m) {
                System.out.print(cur.val + " ");
                // 删除当前节点，前一节点不变
                prev.next = cur.next;
                cur = cur.next;
                i = 0;
            } else {
                prev = prev.next;
                cur = cur.next;
            }

        }
        return cur;
    }

    /**
     * 创建一个环形链表
     *
     * @param n
     * @return
     */
    public static Node createList(int n) {
        Node head = new Node(0);
        Node cur = head;
        for (int i = 1; i <= n; i++) {
            cur.next = new Node(i);
            cur = cur.next;
        }
        cur.next = head.next;
        return head.next;
    }
}
