package com.study.linkedlist;

import lombok.Data;

/**
 * 约瑟夫问题是个有名的问题：N个人围成一圈，从第一个开始报数，第M个将被杀掉，最后剩下一个，其余人都将被杀掉。
 * 例如N=6，M=5，被杀掉的顺序是：5，4，6，2，3，1。
 *
 * @author ldb
 * @date 2019-09-29 10:51
 */
public class Practice01 {

    // 创建一个循环链表
    public static Person createList(int size) {
        Person first = new Person(null, 1);
        if (size == 1) {
            return first;
        }
        Person temp = first;
        Person end = null;
        for (int i = 1; i < size; i++) {
            end = new Person(null, i + 1);
            temp.next = end;
            end.prev = temp;
            temp = end;
        }
        end.next = first;
        first.prev = end;
        return first;
    }

    // 最后活下来的人
    public static int alive(Person person, int m) {
        int i = 0;
        // 当前节点
        Person temp = person;
        while (temp.next != temp) {
            if (++i == m) {
                System.out.print(temp.num + " ");
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                i = 0;
            }
            temp = temp.next;

        }
        return temp.num;

    }

    /**
     * f(n)=(f(n-1)+q)%n
     *
     * @param n
     * @param m
     */
    public static int alive2(int n, int m) {
        if (n == 1) {
            return 0;
        } else {
            return (alive2(n - 1, m) + m) % n;
        }

    }

    public static int alive3(int n, int m) {
        {
            if (n == 0)
                return 0;
            int result = 0;
            for (int i = 2; i <= n; i++) {
                result = (result + m) % i;
            }
            return result + 1;
        }
    }

    public static void main(String[] args) {
        System.out.println(alive(createList(6), 5)); // 1
        System.out.println(alive(createList(7), 1)); // 7
        System.out.println(alive(createList(10), 3));// 4
        System.out.println(alive(createList(100), 5));// 47

        System.out.println(alive3(6, 5));
        System.out.println(alive2(6, 5) + 1);

    }
}

@Data
class Person {
    Person prev;
    Person next;
    int num;

    public Person(Person next, int num) {
        this.next = next;
        this.num = num;
    }
}

