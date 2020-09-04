package com.study.advanced;

import java.util.LinkedList;

/**
 * 拓扑排序算法
 * 要求：有向无环图
 * 可以检测环是否存在
 *
 * @author ldb
 * @date 2020/09/03
 */
public class Graph {
    private int v;
    private LinkedList<Integer>[] adj;

    public Graph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }

    }

    public void addEdge(int i, int j) {
        adj[i].add(j);
    }

    public void addData() {
        // 1->2->3->6
        // 0->2->4->7
        addEdge(0, 2);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(3, 5);
        addEdge(2, 4);
        addEdge(4, 6);
    }

    public void sortKahn() {
        // 1 统计所有顶点的入度
        int[] degrees = new int[v];
        for (int i = 0; i < v; i++) {
            for (Integer j : adj[i]) {
                degrees[j]++;
            }
        }
        // 2 广度遍历,从入度为0开始,使邻接表节点点减一，再把入度为0的节点加入
        LinkedList<Integer> queue = new LinkedList<>();
        // 加入顶点到queue
        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            int q = queue.poll();
            System.out.print(q + "->");
            for (Integer p : adj[q]) {
                degrees[p]--;
                if (degrees[p] == 0) {
                    queue.add(p);
                }
            }

        }
    }

    public void sortDfs() {
        // 1,构建逆邻接表
        LinkedList<Integer>[] reverseAdj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            reverseAdj[i] = new LinkedList<>();
        }
        for (int i = 0; i < v; i++) {
            for (Integer j : adj[i]) {
                reverseAdj[j].add(i);
            }
        }
        // 2,递归逆邻接，直到没有逆邻接表，输出该节点
        boolean[] visited = new boolean[v];
        for (int i = 0; i < v; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(reverseAdj, visited, i);
            }
        }

    }

    private void dfs(LinkedList<Integer>[] reverseAdj, boolean[] visited, int i) {
        for (Integer k : reverseAdj[i]) {
            if (!visited[k]) {
                visited[k] = true;
                dfs(reverseAdj, visited, k);
            }
        }
        System.out.print(i + "->");
    }

    public static void main(String[] args) {
        Graph graph = new Graph(7);
        graph.addData();
        graph.sortKahn();
        System.out.println();
        graph.sortDfs();
    }
}
