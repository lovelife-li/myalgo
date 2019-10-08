package com.study.linkedlist;

import lombok.Data;

public class Josephs {
    static class Node {
        Node next;
        int data;

        public Node(Node next, int data) {
            this.data = data;
            this.next = next;
        }
    }

    public static int josephs(Node head, int n) {
        if (head.next == null) {
            return head.data;
        }

        // 找到最后一个节点
        Node last = head;
        while (last.next != head) {
            last = last.next;
        }

        int count = 0;
        while (head != last) {
            if (++count == n) {
                last.next = head.next;
                System.out.print(head.data + " ");
                count = 0;
            } else {
                last = last.next;
            }
            head = last.next;
        }
        return last.data;
    }

    public static void main(String[] args) {
        System.out.println(josephs(createList(7), 1));
        System.out.println(josephs2(createList(7), 1));
    }

    // 创建一个循环链表
    public static Node createList(int size) {
        Node first = new Node(null, 1);
        Node temp = first;
        Node end;
        for (int i = 1; i < size; i++) {
            end = new Node(null, i + 1);
            temp.next = end;
            temp = end;
            end.next = first;
        }
        return first;
    }

    public static int josephs2(Node head, int n) {
        if (head.next == null) {
            return head.data;
        }
        while (head.next != head) {
            for (int i = 1; i < n - 1; i++) {
                head = head.next;
            }
            // 数到第4次
            System.out.print(head.next.data + " ");
            head.next = head.next.next;
            head = head.next;
        }
        return head.data;
    }
}