package com.study.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


/**
 * @author ldb
 * @date 2019-12-02 9:49
 */
public class Graph2 {
    private int v;
    private HashMap<Integer, String> adj[];// 邻接表

    public Graph2(int v) {
        this.v = v;
        adj = new HashMap[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new HashMap<>();
        }

    }

    public void addEdge(int s, int t) {
        adj[s].put(t, "");
        adj[t].put(s, "");
    }

    // 找定点s到t的路径
    public void bfs(int s, int t) {
        if (s == t) return;
        boolean[] visited = new boolean[v];
        visited[s] = true;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        int[] prev = new int[v];
        for (int i = 0; i < prev.length; i++) {
            prev[i] = -1;
        }

        while (queue.size() != 0) {
            int w = queue.pop();
            for (Map.Entry<Integer, String> entry : adj[w].entrySet()) {
                int q = entry.getKey();
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

    private void print(int[] prev, int s, int t) {
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }

    public static void main(String[] args) {
        Graph2 graph2 = new Graph2(8);
        graph2.addEdge(0, 1);
        graph2.addEdge(0, 3);
        graph2.addEdge(1, 2);
        graph2.addEdge(1, 4);
        graph2.addEdge(2, 5);
        graph2.addEdge(4, 5);
        graph2.addEdge(3, 4);
        graph2.addEdge(4, 6);
        graph2.addEdge(5, 7);
        graph2.addEdge(6, 7);

        graph2.bfs(0, 6);
        System.out.println("------------------");

        graph2.dfs(0, 6);
        System.out.println("------------------");

        LinkedList<Integer> res = graph2.bfs_friend3(0, 2);
        Iterator<Integer> ite = res.iterator();
        while (ite.hasNext()){
            System.out.print(ite.next()+" ");
        }


    }

    boolean found = false;

    public void dfs(int s, int t) {
        found = false;
        boolean[] visited = new boolean[v];
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }
        recurdfs(s, t, visited, prev);
        print(prev, s, t);
    }

    private void recurdfs(int w, int t, boolean[] visited, int[] prev) {
        if (found) {
            return;
        }
        if (w == t) {
            found = true;
            return;
        }
        visited[w] = true;
        for (Map.Entry<Integer, String> entry : adj[w].entrySet()) {
            int q = entry.getKey();
            if (!visited[q]) {
                prev[q] = w;
                recurdfs(q, t, visited, prev);
            }
        }
    }

    public LinkedList<Integer> bfs_friend3(int s, int n) {
        boolean[] visited = new boolean[v];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        int[] prev = new int[v];
        LinkedList<Integer> res = new LinkedList<>();

        while (n-- > 0) {
            int size = queue.size();
            while ( size-- > 0) {
                int q = queue.pop();
                for (Map.Entry<Integer, String> entry : adj[q].entrySet()) {
                    int p = entry.getKey();
                    if (!visited[p]) {
                        visited[p] = true;
                        queue.add(p);
                        res.add(p);
                    }
                }
            }
        }
        return res;

    }
}
