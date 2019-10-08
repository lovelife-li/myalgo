package com.study.linkedlist;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ldb
 * @date 2019-10-08 9:28
 * 1) 单链表反转
 * 2) 链表中环的检测
 * 3) 两个有序的链表合并
 * 4) 删除链表倒数第n个结点
 * 5) 求链表的中间结点
 */
public class Practice02 {


    // 单链表反转
    public static Node reverseLinkedList(Node head) {
        Node prev = null;
        Node next;
        while (head != null) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }


    @Data
    @AllArgsConstructor
    public static class Node {
        Node next;
        int value;

        public Node(int value) {
            this.value = value;
        }
    }

    // 创建单链表（无环）
    public static Node createLinkedList(int[] arr) {
        Node head = null;
        if (arr == null || arr.length == 0) {
            return head;
        }
        head = new Node(null, arr[0]);
        Node temp = head;
        for (int i = 1; i < arr.length; i++) {
            Node next = new Node(null, arr[i]);
            temp.next = next;
            temp = next;
        }
        return head;
    }

    // 创建单链表（有环）
    public static Node createLinkedList2(int[] arr) {
        Node head = null;
        if (arr == null || arr.length == 0) {
            return head;
        }
        head = new Node(null, arr[0]);
        Node temp = head;
        for (int i = 1; i < arr.length; i++) {
            Node next = new Node(null, arr[i]);
            temp.next = next;
            temp = next;
        }
        temp.next = head;
        return head;
    }

    public static void print(Node head) {
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }

    // 检测环
    public static boolean checkCircle(Node list) {
        if (list == null) return false;

        Node fast = list.next;
        Node slow = list;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (slow == fast) return true;
        }

        return false;
    }


    // 删除倒数第K个结点
    public static Node deleteLastKth(Node list, int k) {
        Node fast = list;
        int i = 1;
        while (fast != null && i < k) {
            fast = fast.next;
            ++i;
        }

        if (fast == null) return list;

        Node slow = list;
        Node prev = null;
        while (fast.next != null) {
            fast = fast.next;
            prev = slow;
            slow = slow.next;
        }

        if (prev == null) {
            list = list.next;
        } else {
            prev.next = prev.next.next;
        }
        return list;
    }

    // 有序链表合并
    public static Node mergeTwoLists(Node l1, Node l2) {
        Node soldier = new Node(0); //利用哨兵结点简化实现难度
        Node p = soldier;

        while ( l1 != null && l2 != null ){
            if ( l1.value < l2.value ){
                p.next = l1;
                l1 = l1.next;
            }
            else{
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }

        if (l1 != null) { p.next = l1; }
        if (l2 != null) { p.next = l2; }
        return soldier.next;
    }

    // 求链表的中间结点
    public static Node getMiddleNode(Node head) {
        if (head == null) {
            return null;
        }
        Node p = head;
        Node q = head;
        while (p.next != null && p.next.next != null) {
            p = p.next.next;
            q = q.next;
        }
        return q;
    }

    public static void main(String[] args) {
        Node head = createLinkedList(new int[]{1, 2, 3, 4, 5});
//        Node p = reverseLinkedList(head);
        System.out.println(getMiddleNode(head).value);
        Node head1 = createLinkedList(new int[]{1});
        Node head2 = createLinkedList(new int[]{1, 2});
        Node head3 = createLinkedList(new int[]{1, 2, 3});
        Node head4 = createLinkedList(new int[]{1, 2, 3, 4});
        Node head5 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        print(deleteLastKth(head1,1));
        print(deleteLastKth(head2,1));
        print(deleteLastKth(head3,1));
        print(deleteLastKth(head4,3 ));
        print(mergeTwoLists(head5,head4 ));
    }
}
