package com.study.linkedlist;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCacheBeta<K, V> {
    int capacity;
    Map<K, V> map;
    LinkedList<K> list;

    public LRUCacheBeta(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.list = new LinkedList<>();
    }

    public static void main(String[] args) {
        LRUCacheBeta<String, Integer> lru = new LRUCacheBeta<String, Integer>(3);
        lru.put("c", 2);
        lru.put("a", 1);
        lru.put("b", 3);
        lru.print();
        lru.get("a");
        lru.print();


    }

    public void print() {
        list.forEach(x -> System.out.print(x + "->"));
        System.out.println("null");
    }

    /**
     * 添加元素
     * 1.元素存在，放到队尾
     * 2.不存在，判断链表是否满。
     * 如果满，则删除队首元素，放入队尾元素，删除更新哈希表
     * 如果不满，放入队尾元素，更新哈希表
     */
    public void put(K key, V value) {
        V v = map.get(key);
        if (v != null) {
            list.remove(key);
            list.addLast(key);
            map.put(key, value);
            return;
        }

        //队列未满，添加到尾部
        if (list.size() < capacity) {
            list.addLast(key);
            map.put(key, value);
        } else {
            //队列已满，移除队首
            K firstKey = list.removeFirst();
            map.remove(firstKey);
            list.addLast(key);
            map.put(key, value);
        }
    }

    /**
     * 访问元素
     * 元素存在，放到队尾
     */
    public V get(K key) {
        V v = map.get(key);
        if (v != null) {
            list.remove(key);
            list.addLast(key);
            return v;
        }
        return null;
    }
}

