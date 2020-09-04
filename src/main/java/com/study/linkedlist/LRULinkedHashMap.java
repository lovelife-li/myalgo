package com.study.linkedlist;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ldb;
 * @date 2020/5/7
 * @description 在Java中，其实LinkedHashMap已经实现了LRU缓存淘汰算法，需要在构造函数第三个参数传入true，
 * 表示按照时间顺序访问。可以直接继承LinkedHashMap来实现。
 * <p>
 * 但是LinkedHashMap会自动扩容，如果想实现限制容量删除队列顶端元素，需要重写removeEldestEntry()方法，
 * 当map里面的元素个数大于了缓存最大容量，删除链表的顶端元素。
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private int capacity;

    LRULinkedHashMap(int capacity) {
        // 初始大小，0.75是装载因子，true是表示按照访问时间排序
        super(capacity, 0.75f, true);
        //传入指定的缓存最大容量
        this.capacity = capacity;
    }

    /**
     * 实现LRU的关键方法，如果map里面的元素个数大于了缓存最大容量，则删除链表的顶端元素
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {
        LRULinkedHashMap<String, Integer> lru = new LRULinkedHashMap<>(4);
        lru.put("d",4);
        lru.put("a",1);
        lru.put("b",2);
        lru.put("c",3);
        lru.put("e",5);
        lru.put("f",6);
        // 2->3->5->6->null
        // 队列尾部就是缓存头部（频繁访问的部分）
        for (Map.Entry<String, Integer> entry : lru.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }

        System.out.println("---------------------");
        testHashMap();

    }

    private static void testHashMap() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>() {
            {
                put("d", 4);
                put("a", 1);
                put("b", 2);
                put("c", 3);

            }
        };
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
    }
}