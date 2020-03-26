package com.study.advanced;

import java.util.LinkedList;

public class Graph2 {
    private LinkedList<Edge> adj[]; // 邻接表
    private int v; // 顶点个数

    public Graph2(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            this.adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t, int w) { // 添加一条边
        this.adj[s].add(new Edge(s, t, w));
    }


    // 下面这个类是为了 dijkstra 实现用的
    private class Vertex {
        public int id; // 顶点编号 ID
        public int dist; // 从起始顶点到这个顶点的距离

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }
    }

    private class Edge {
        public int sid; // 边的起始顶点编号
        public int tid; // 边的终止顶点编号
        public int w; // 权重

        public Edge(int sid, int tid, int w) {
            this.sid = sid;
            this.tid = tid;
            this.w = w;
        }
    }

    // 因为 Java 提供的优先级队列，没有暴露更新数据的接口，所以我们需要重新实现一个
    private class PriorityQueue {
        // 根据 vertex.dist 构建小顶堆
        private Vertex[] nodes;
        // 堆可以存储的最大数据个数
        private int n;
        // 堆中已经存储的数据个数
        private int count;

        public PriorityQueue(int v) {
            this.nodes = new Vertex[v + 1];
            this.count = 0;
            this.n = v;
        }

        public Vertex poll() {
            Vertex vertex = nodes[1];
            if (count > 0) {
                nodes[1] = nodes[count];
                count--;
                heapify(nodes, count, 1);
            }
            return vertex;
        }

        public void add(Vertex vertex) {
            if (count >= n) {
                return;
            }
            ++count;
            nodes[count] = vertex;
            int i = count;
            while (i >> 1 > 0 && nodes[i].dist < nodes[i >> 1].dist) {
                swap(nodes, i, i >> 1);
                i = i >> 1;
            }
        }

        private void swap(Vertex[] a, int i, int j) {
            Vertex tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }

        // 更新结点的值，并且从下往上堆化，重新符合堆的定义。时间复杂度 O(logn)。
        public void update(Vertex vertex) {
            // 找到vertex的下标
            int index = 0;
            for (int i = 1; i < nodes.length; i++) {
                if (nodes[i] == vertex) {
                    index = i;
                    break;
                }
            }
            if (index != 0) {
                buildHeap(nodes,count);
            }

        }

        private  void buildHeap(Vertex[] a, int n) {
            for (int i = n / 2; i > 0; i--) {
                heapify(a, n, i);
            }
        }

        public void heapify(Vertex[] a, int n, int i) {
            while (true) {
                int pos = i;
                if (i * 2 <= n && a[i].dist > a[i * 2].dist) {
                    pos = i * 2;
                }
                if (i * 2 + 1 <= n && a[pos].dist > a[i * 2 + 1].dist) {
                    pos = i * 2 + 1;
                }
                // 没有交换了
                if (pos == i) {
                    break;
                }
                swap(a, i, pos);
                i = pos;
            }
        }

        public boolean isEmpty() {
            return count == 0;
        }

        private void dijkstra(int s, int t) { // 从顶点 s 到顶点 t 的最短路径
            int[] predecessor = new int[v]; // 用来还原最短路径
            Vertex[] vertexes = new Vertex[v];
            for (int i = 0; i < v; ++i) {
                vertexes[i] = new Vertex(i, Integer.MAX_VALUE);
            }
            PriorityQueue queue = new PriorityQueue(v);// 小顶堆
            boolean[] inqueue = new boolean[v]; // 标记是否进入过队列
            vertexes[s].dist = 0;
            queue.add(vertexes[s]);
            inqueue[s] = true;
            while (!queue.isEmpty()) {
                Vertex minVertex = queue.poll(); // 取堆顶元素并删除
                if (minVertex.id == t) {
                    break; // 最短路径产生了
                }
                for (int i = 0; i < adj[minVertex.id].size(); ++i) {
                    Edge e = adj[minVertex.id].get(i); // 取出一条 minVetex 相连的边
                    Vertex nextVertex = vertexes[e.tid]; // minVertex-->nextVertex
                    if (minVertex.dist + e.w < nextVertex.dist) { // 更新 next 的 dist
                        nextVertex.dist = minVertex.dist + e.w;
                        predecessor[nextVertex.id] = minVertex.id;
                        if (inqueue[nextVertex.id] == true) {
                            queue.update(nextVertex); // 更新队列中的 dist 值
                        } else {
                            queue.add(nextVertex);
                            inqueue[nextVertex.id] = true;
                        }
                    }
                }
            }
            // 输出最短路径
            System.out.print(s);
            print(s, t, predecessor);
        }

        private void print(int s, int t, int[] predecessor) {
            if (s == t) {
                return;
            }
            print(s, predecessor[t], predecessor);
            System.out.print("->" + t);
        }
    }


}