package com.study.advanced;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

/**
 * 拓扑排序算法
 * 要求：有向无环图
 * 可以检测环是否存在
 *
 * @author ldb
 * @date 2020/09/03
 */
public class Graph01 {
    private int v;
    private HashMap<String, HashMap<String, String>> adj;

    public Graph01(int v) {
        this.v = v;
        adj = new HashMap<>();
    }

    public void addEdge(String i, String j) {
        adj.get(i).put(j, "");
    }

    public void addData() {
        adj.put("a", new HashMap<>());
        adj.put("b", new HashMap<>());
        adj.put("c", new HashMap<>());
        adj.put("d", new HashMap<>());
        adj.put("e", new HashMap<>());
        adj.put("f", new HashMap<>());
        // a->c->d
        // b->c->e
        // c->f
        addEdge("a", "c");
        addEdge("b", "c");
        addEdge("c", "d");
        addEdge("c", "e");
        addEdge("c", "f");

    }

    public void sortKahn() {
        // 1 统计所有顶点的入度
        HashMap<String, Integer> degrees = new HashMap<>();
        degrees.put("a",0);
        degrees.put("b",0);
        degrees.put("c",0);
        degrees.put("d",0);
        degrees.put("e",0);
        degrees.put("f",0);
        for (Map.Entry<String, HashMap<String, String>> item : adj.entrySet()) {
            for (Map.Entry<String, String> w : item.getValue().entrySet()) {
                degrees.merge(w.getKey(), 1, (oldVal, newVal) -> {
                    return oldVal + newVal;
                });
            }
        }
        // 2 广度遍历,从入度为0开始,使邻接表节点点减一，再把入度为0的节点加入
        LinkedList<String> queue = new LinkedList<>();
        // 加入入度为0顶点到queue
        for (Map.Entry<String, Integer> entry : degrees.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            String q = queue.poll();
            System.out.print(q + "->");
            for (Map.Entry<String, String> p : adj.get(q).entrySet()) {
                degrees.computeIfPresent(p.getKey(), (key, oldVal) -> {
                    if (oldVal - 1 == 0) {
                        queue.offer(p.getKey());
                    }
                    return oldVal - 1;
                });
            }

        }
    }

//    public void sortDfs() {
//        // 1,构建逆邻接表
//        LinkedList<Integer>[] reverseAdj = new LinkedList[v];
//        for (int i = 0; i < v; i++) {
//            reverseAdj[i] = new LinkedList<>();
//        }
//        for (int i = 0; i < v; i++) {
//            for (Integer j : adj[i]) {
//                reverseAdj[j].add(i);
//            }
//        }
//        // 2,递归逆邻接，直到没有逆邻接表，输出该节点
//        boolean[] visited = new boolean[v];
//        for (int i = 0; i < v; i++) {
//            if (!visited[i]) {
//                visited[i] = true;
//                dfs(reverseAdj, visited, i);
//            }
//        }
//
//    }
//
//    private void dfs(LinkedList<Integer>[] reverseAdj, boolean[] visited, int i) {
//        for (Integer k : reverseAdj[i]) {
//            if (!visited[k]) {
//                visited[k] = true;
//                dfs(reverseAdj, visited, k);
//            }
//        }
//        System.out.print(i + "->");
//    }

    public static void main(String[] args) {
        Graph01 graph = new Graph01(6);
        graph.addData();
        graph.sortKahn();
        System.out.println();
//        graph.sortDfs();
    }
}
