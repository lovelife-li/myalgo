package com.study.advanced;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 最短路径算法 Dijkstra
 * 单源最短路径算法（一个顶点到一个顶点）
 * 时间复杂度就是 O(E*logV)。
 * @author ldb
 * @date 2020/09/03
 */
public class Graph02 {

    public LinkedList<Edge> adj[];
    public int v;

    public Graph02(int v) {
        this.adj = new LinkedList[v];
        this.v = v;
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int i, int j, int w) {
        adj[i].offer(new Edge(i, j, w));
        adj[j].offer(new Edge(j, i, w));
    }

    public void createData() {
        addEdge(0, 1, 4);
        addEdge(0, 2, 6);
        addEdge(0, 6, 8);
        addEdge(1, 4, 3);
        addEdge(1, 3, 3);
        addEdge(2, 3, 2);
        addEdge(3, 5, 1);
        addEdge(3, 9, 5);
        addEdge(4, 5, 2);
        addEdge(4, 8, 3);
        addEdge(5, 9, 3);
        addEdge(6, 7, 3);
        addEdge(6, 4, 2);
        addEdge(7, 8, 1);
        addEdge(8, 9, 4);
    }

    public static Map<Integer, String> map = new HashMap<>(16);

    public static void main(String[] args) {
        Graph02 graph02 = new Graph02(10);

        for (int i = 0; i < 10; i++) {
            if (i + 1 >= 10) {
                map.put(i, "p" + (i + 1));
            } else {
                map.put(i, "p0" + (i + 1));
            }

        }
        graph02.createData();
        graph02.dijkstra(0, 9);
    }

    public void print(int i, int j, int[] prev) {
        if (i == j) {
            return;
        }
        print(i, prev[j], prev);
        System.out.print("->" + map.get(j));
    }


    public void dijkstra(int s, int t) {
        // 标记是否进入过队列
        boolean[] visited = new boolean[v];
        // 初始化顶点的每个值
        Vertex[] vertexes = new Vertex[this.v];
        for (int i = 0; i < this.v; ++i) {
            vertexes[i] = new Vertex(i, Integer.MAX_VALUE);
        }
        // 用来还原最短路径
        int[] prev = new int[v];
        PriorityQueue queue = new PriorityQueue(v);
        queue.add(vertexes[s]);
        vertexes[s].dist = 0;
        visited[0] = true;
        while (!queue.isEmpty()) {
            Vertex p = queue.poll();
            if (p.id == t) {
                System.out.println("最短路径：" + p.dist);
                // 找到最短路径
                break;
            }
            for (Edge edge : adj[p.id]) {
                int q = edge.tid;
                if (vertexes[q].dist > vertexes[p.id].dist + edge.w) {
                    vertexes[q].dist = vertexes[p.id].dist + edge.w;
                    prev[q] = p.id;
                    if (visited[q]) {
                        // 更新值
                        queue.update(vertexes[q]);
                    } else {
                        visited[q] = true;
                        queue.add(vertexes[q]);
                    }
                }

            }
        }
        print(s, t, prev);

    }

    static class Edge {
        // 起点边
        public int sid;
        // 终点边
        public int tid;
        // 边长度
        public int w;

        public Edge(int sid, int tid, int w) {
            this.sid = sid;
            this.tid = tid;
            this.w = w;
        }
    }

    static class Vertex {
        // 顶点编号
        public int id;
        // // 从起始顶点到这个顶点的距离
        public int dist;

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }
    }

    static class PriorityQueue {
        private Vertex[] vertices;
        // 容量大小
        private int size;
        // 当前元素个数
        private int count;

        public PriorityQueue(int size) {
            this.vertices = new Vertex[size + 1];
            this.size = size;
            this.count = 0;
        }

        public boolean isEmpty() {
            return count == 0;
        }

        public boolean add(Vertex vertex) {
            if (count >= size) {
                return false;
            }
            vertices[++count] = vertex;
            int i = count;
            while (i / 2 > 0 && vertices[i / 2].dist > vertices[i].dist) {
                swap(vertices, i, i / 2);
                i = i / 2;
            }
            return true;
        }

        public boolean update(Vertex vertex) {
            for (int i = 1; i < vertices.length; i++) {
                if (vertex.id == vertices[i].id) {
                    vertices[i].dist = vertex.dist;
                    return true;
                }
            }
            return false;
        }

        public void swap(Vertex[] nodes, int i, int j) {
            Vertex tmp = vertices[i];
            vertices[i] = vertices[j];
            vertices[j] = tmp;
        }

        /**
         * 移除堆头元素
         */
        public Vertex poll() {
            if (count <= 0) {
                return null;
            }
            Vertex res = vertices[1];
            vertices[1] = vertices[count--];
            if (count > 0) {
                heapfiy(1, count);
            }

            return res;
        }

        /**
         * 堆化,从上到下
         *
         * @param i 下标
         */
        public void heapfiy(int i, int count) {
            for (;;) {
                int pos = i;
                if (i * 2 <= count && vertices[i].dist > vertices[i * 2].dist) {
                    pos = i * 2;
                }
                if (i * 2 + 1 <= count && vertices[pos].dist > vertices[i * 2 + 1].dist) {
                    pos = i * 2 + 1;
                }
                if (pos == i) {
                    break;
                }
                swap(vertices, i, pos);
                i = pos;
            }

        }
    }
}
