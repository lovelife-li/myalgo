package com.study.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * @author wayne
 */
public class ACAutoMata {
    private ACNode root;

    public ACAutoMata() {
        this.root = new ACNode("/");
    }

    private void insert(String pattern) {
        ACNode node = this.root;
        int len = pattern.length();
        for (int i = 0; i < len; i++) {
            String c = pattern.charAt(i) + "";
            if (Objects.isNull(node.children.get(c))) {
                node.children.put(c, new ACNode(c));
            }
            node = node.children.get(c);
        }

        node.isEndingChar = true;
        node.length = pattern.length();
    }

    private void buildFailurePointer() {
        ACNode root = this.root;
        LinkedList<ACNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            ACNode p = queue.pop();

            for (ACNode pc : p.children.values()) {
                if (Objects.isNull(pc)) {
                    continue;
                }

                if (p == root) {
                    pc.fail = root;
                } else {
                    ACNode q = p.fail;
                    while (Objects.nonNull(q)) {
                        ACNode qc = q.children.get(pc.data);
                        if (Objects.nonNull(qc)) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (Objects.isNull(q)) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }
    }

    private Map<Integer, Integer> match(String text) {
        ACNode root = this.root;
        ACNode p = root;
        HashMap<Integer, Integer> res = new HashMap<>();
        int n = text.length();
        for (int i = 0; i < n; i++) {
            String c = text.charAt(i) + "";
            while (Objects.isNull(p.children.get(c)) && p != root) {
                p = p.fail;
            }

            p = p.children.get(c);
            if (Objects.isNull(p)) {
                p = root;
            }

            ACNode tmp = p;
            while (tmp != root) {
                if (tmp.isEndingChar == true) {
                    int key = i - p.length + 1;
                    System.out.println("index: " + key + " , length:" + p.length);
                    res.put(key, p.length);
                }
                tmp = tmp.fail;
            }
        }

        return res;
    }

    public static Map<Integer, Integer> match(String text, String[] patterns) {
        ACAutoMata automata = new ACAutoMata();
        for (String pattern : patterns) {
            automata.insert(pattern);
        }

        automata.buildFailurePointer();
        return automata.match(text);
    }

    public class ACNode {
        private String data;
        private Map<String, ACNode> children;
        private Boolean isEndingChar;
        private Integer length;
        private ACNode fail;

        public ACNode(String data) {
            this.data = data;
            this.children = new HashMap<>();
            this.isEndingChar = false;
            this.length = 0;
            this.fail = null;
        }
    }

    public static void main(String[] args) {
        String[] patterns = {"at", "art", "oars", "soar"};
        String text = "soarsoars";
        match(text, patterns);
        String[] patterns2 = {"fuck", "傻逼", "one"};

        System.out.println();
        String text2 = "我是中国人,我爱中国。one world,one dream! hello,world.fuck you.fuck. 傻逼 .二货";
//        text2 = "fuck";
        Map<Integer, Integer> map = match(text2, patterns2);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int val = entry.getValue();
            StringBuilder star = new StringBuilder();
            for (int i = 0; i < val; i++) {
                star.append("*");
            }
            text2 = text2.replace(text2.substring(key, key + val ), star.toString());
        }
        System.out.println(text2);
    }
}
