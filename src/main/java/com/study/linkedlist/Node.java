package com.study.linkedlist;

/**
 * @author ldb
 * @date 2020/05/07
 * @description 节点
 */
public class Node {

    public int val;
    public Node next;

    public Node(int val) {
        this.val = val;
    }

    public void print(Node node) {
        Node head = node;
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }
}
