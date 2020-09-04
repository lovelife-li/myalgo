package com.study.graph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author ldb
 * @date 2019-10-23 15:10
 */
public class Graph {
    // 顶点个数
    private int v;
    private LinkedList<Integer> adj[]; // 邻接表

    public Graph(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    /**
     * 添加边
     *
     * @param s 顶点
     * @param t 顶点
     */
    public void addEdge(int s, int t) { // 无向图一条边存两次
        adj[s].add(t);
        adj[t].add(s);

    }

    /**
     * 广度优先搜索（Breadth-First-Search）
     * 时间复杂度O(E)
     * 空间复杂度O(V)
     *
     * @param s
     * @param t
     */
    public void bfs(int s, int t) {
        if (s == t) return;
        // 准备3个变量
        // visited是用来记录已经被访问的顶点，用来避免顶点被重复访问。
        boolean[] visited = new boolean[v];
        visited[s] = true;
        // queue是一个队列，用来存储已经被访问、但相连的顶点还没有被访问的顶点。
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        // prev用来记录搜索路径。
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }

        // 实际
        while (queue.size() != 0) {
            // 删除头元素，取值
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); ++i) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }

    }

    /**
     * 找好友关系
     *
     * @param s
     * @param n
     */
    public LinkedList<Integer> bfs_find(int s, int n) {
        // 准备3个变量
        // visited是用来记录已经被访问的顶点，用来避免顶点被重复访问。
        boolean[] visited = new boolean[v];
        visited[s] = true;
        // queue是一个队列，用来存储已经被访问、但相连的顶点还没有被访问的顶点。
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        LinkedList<Integer> res = new LinkedList<>();
        // 实际
        while (n-- > 0) {
            int size = queue.size();
            while (size-- > 0) {
                int k = queue.poll();
                int m = adj[k].size();
                for (int i = 0; i < m; ++i) {
                    int q = adj[k].get(i);
                    if (!visited[q]) {
                        visited[q] = true;
                        queue.add(q);
                        res.add(q);
                    }
                }
            }
        }
        return res;

    }

    private void print(int[] prev, int s, int t) { // 递归打印 s->t 的路径
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }

    public static void main(String[] args) {
        Graph graph = new Graph(8);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(4, 6);
        graph.addEdge(5, 7);
        graph.addEdge(6, 7);
        graph.bfs(0,6);
        System.out.println("--------------------");
        // 深度优先
        graph.dfs(0, 6);

        System.out.println();
        LinkedList<Integer> res = graph.bfs_find(0, 3);

        for (Iterator<Integer> ite = res.iterator(); ite.hasNext(); ) {
            System.out.println(ite.next());
        }


    }

    boolean found = false; // 全局变量或者类成员变量

    /**
     * 深度优先搜索（Depth-First-Search）
     * 时间复杂度是 O(E)，E 表示边的个数。
     * 空间复杂度就是 O(V)。
     *
     * @param s
     * @param t
     */
    public void dfs(int s, int t) {
        found = false;
        boolean[] visited = new boolean[v];
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }
        recurDfs(s, t, visited, prev);
        print(prev, s, t);
    }

    private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
        if (found == true) return;
        visited[w] = true;
        if (w == t) {
            found = true;
            return;
        }
        for (int i = 0; i < adj[w].size(); ++i) {
            int q = adj[w].get(i);
            if (!visited[q]) {
                prev[q] = w;
                recurDfs(q, t, visited, prev);
            }
        }

    }


}


