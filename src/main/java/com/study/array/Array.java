package com.study.array;

/**
 * 1) 数组的插入、删除、按照下标随机访问操作；
 * 2）数组中的数据是int类型的；
 * 手写集合
 * Author: ldb
 */
public class Array {
    // 定义整型数据data保存数据
    public int data[];

    // 定义数组长度
    private int n;

    // 实际元素个数
    private int count;

    public Array(int capacity) {
        data = new int[capacity];
        n = capacity;
    }

    // 根据索引，找到数据中的元素并返回
    public int find(int index) {
        if (index < 0 && index >= count) {
            return -1;
        }
        return data[index];
    }

    // 插入元素:头部插入，尾部插入
    public boolean insert(int index, int value) {
        if (index < 0 || index > count) {
            System.out.println("插入位置不合法");
            return false;
        }

        // 数组已满，扩容
        if (count == data.length) {
            int[] newArray = new int[2 * n];
            for (int i = 0; i < data.length; i++) {
                newArray[i] = data[i];
            }
            n = 2 * n;
            data = newArray;
        }
        for (int i = count; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        ++count;
        return true;

    }

    // 根据索引，删除数组中元素
    public boolean delete(int index) {
        if (index < 0 || index >= count) {
            return false;
        }

        for (int i = index + 1; i <= count; i++) {
            data[i - 1] = data[i];
        }
        --count;
        return true;


    }

    public void printAll() {
        for (int i = 0; i < count; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();

    }

    public static void main(String[] args) {
        Array array = new Array(5);
        array.insert(0,1);
        array.insert(1,2);
        array.insert(2,3);

//        array.delete(2);
        array.printAll();

        array.insert(3,4);
        array.printAll();
    }

}
