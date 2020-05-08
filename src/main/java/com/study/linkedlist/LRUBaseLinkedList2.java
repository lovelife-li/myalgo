package com.study.linkedlist;

/**
 * @author ldb
 * @date 2020/05/08
 * @description 基于链表LRU算法
 * 1，最近使用的最先找到，在链表中找到数据后，把他放在头节点
 * 2，在链表中没找到，放入头结点。如果链表满了，删除尾节点。
 */
public class LRUBaseLinkedList2<T> {

    // 容量
    private int size;

    private Node<T> head;

    // 链表长度
    private int length;

    public LRUBaseLinkedList2(int size) {
        this.size = size;
    }

    public LRUBaseLinkedList2() {
        this(10);
    }

    // 添加元素
    public void put(T e) {
        Node<T> tNode = new Node<>(e);
        // 如果链表满了，删除尾节点
        if (length == size) {
            Node<T> prev = getLastPrev();
            prev.next = null;
            --length;
        }
        tNode.next = head;
        head = tNode;
        ++length;
    }

    // 查找
    public Node<T> find(T e) {
        if (head == null) {
            put(e);
            return null;
        }
        if (head.data == e) {
            return head;
        }
        Node<T> prev = head;
        Node<T> cur = head;
        while (cur != null) {
            if (cur.data == e) {
                break;
            }
            prev = cur;
            cur = cur.next;
        }
        // 删除当前节点
        prev.next = cur.next;
        // 把当前节点变为头节点
        cur.next = head;
        head = cur;
        return head;
    }

    public Node<T> getLastPrev() {
        Node<T> cur = head;
        Node<T> prev = head;
        while (cur.next != null) {
            prev = cur;
            cur = cur.next;
        }
        return prev;

    }

    public void print() {
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.data + "->");
            cur = cur.next;
        }
        System.out.print("null");
        System.out.println();
    }

    static class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        LRUBaseLinkedList2<Integer> list = new LRUBaseLinkedList2<>();
        list.put(1);
        list.put(2);
        list.put(3);
        list.put(4);
        list.put(5);
        list.put(6);
        list.put(7);
        list.put(8);
        list.put(9);
        list.put(10);
        list.put(11);
        list.find(5);
        list.find(4);
        list.put(13);
        list.print();
    }
}
