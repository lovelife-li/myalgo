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
        if (list == null) {
            return false;
        }
        Node p = list;
        Node q = list;
        while (p.next != null && p.next.next != null) {
            p = p.next.next;
            q = q.next;
            if (p == q) {
                return true;
            }
        }
        return false;
    }


    // 删除倒数第K个结点
    public static Node deleteLastKth(Node list, int k) {

        Node fast = list;
        Node slow = list;
        Node prev = null;
        int i = 1;
        while (fast != null && i < k) {
            fast = fast.next;
            ++i;
        }
        if (fast == null) {
            return list;
        }
        while (fast.next != null) {
            fast = fast.next;
            prev = slow;
            slow = slow.next;
        }
        if (prev == null) {
            return list.next;
        } else {
            prev.next = prev.next.next;
        }

        return list;

    }

    // 有序链表合并
    public static Node mergeTwoLists(Node l1, Node l2) {
        Node head = new Node(null, 0);
        Node tmp = head;
        while (l1 != null && l2 != null) {
            if (l1.value > l2.value) {
                tmp.next = l2;
                l2 = l2.next;
            } else {
                tmp.next = l1;
                l1 = l1.next;
            }
            tmp = tmp.next;

        }
        if (l1 != null) tmp.next = l1;
        if (l2 != null) tmp.next = l2;

        return head.next;

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
        Node head5 = createLinkedList2(new int[]{1, 2, 3, 4, 5});
        print(deleteLastKth(head1, 2));
        print(deleteLastKth(head2, 2));
        print(deleteLastKth(head3, 2));
        System.out.println(checkCircle(head5));
        System.out.println("--------------");
        print(mergeTwoLists(head4, head3));
    }
}
