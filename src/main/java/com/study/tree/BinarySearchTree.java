package com.study.tree;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 二叉搜索树
 */
public class BinarySearchTree {
    private Node tree;

    public Node find(int data) {
        Node p = tree;
        while (p != null) {
            if (p.data > data) {
                p = p.left;
            } else if (p.data < data) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    /**
     * 找到叶子节点，插入
     *
     * @param data
     */
    public void insert(int data) {
        Node p = tree;
        if (p == null) {
            tree = new Node(data);
            return;
        }
        while (p != null) {
            if (p.data < data) {
                if (p.right == null) {
                    p.right = new Node(data);
                    return;
                }
                p = p.right;

            } else {
                if (p.left == null) {
                    p.left = new Node(data);
                    return;
                }
                p = p.left;

            }
        }

    }

    /**
     * 三种情况
     * （1）如果要删除的节点没有子节点，我们只需要直接将父节点中，指向要删除节点的指
     * 针置为 null 。
     * （2）如果要删除的节点只有一个子节点（只有左子节点或者右子节点），我们只需要更
     * 新父节点中，指向要删除节点的指针，让它指向要删除节点的子节点就可以了。
     * （3）如果要删除的节点有两个子节点，我们需要找到这个节点的右子
     * 树中的最小节点，把它替换到要删除的节点上。然后再删除掉这个最小节点，因为最小节点肯定没
     * 有左子节点（如果有左子结点，那就不是最小节点了）
     *
     * @param data
     */
    public void delete(int data) {
        // 找到要删除节点
        Node p = tree;
        Node pp = null;
        while (p != null && p.data != data) {
            pp = p;
            if (p.data > data) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        if (p == null) {
            return;
        }
        Node child = null;
        //  要删除的节点有两个子节点,查找右子树中最小节点
        if (p.left != null && p.right != null) {
            Node minp = p.right;
            Node minpp = p;
            // 找后继节点minp
            while (minp.left != null) {
                minpp = minp;
                minp = minp.left;
            }
            //  将 minP  的数据替换到 p  中
            p.data = minp.data;
            pp = minpp;
            p = minp;

        }

        if (p.left != null) {
            //  删除节点是叶子节点或者仅有一个子节点
            child = p.left;
        } else if (p.right != null) {
            child = p.right;
        }
        if (pp == null) {
            tree = child;
        } else {
            if (pp.left == p) {
                pp.left = child;
            } else {
                pp.right = child;
            }
        }


    }

    public Node findMin() {
        if (tree == null) return null;
        Node p = tree;
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }

    public Node findMax() {
        if (tree == null) return null;
        Node p = tree;
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }

    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(33);
        tree.insert(16);
        tree.insert(50);
        tree.delete(33);

        tree.print1(tree.tree);
        //test1(tree);
    }

    private static void test1(BinarySearchTree tree) {
        tree.insert(33);
        tree.insert(16);
        tree.insert(50);
        tree.insert(13);
        tree.insert(18);
        tree.insert(34);
        tree.insert(58);
        tree.insert(15);
        tree.insert(17);
        tree.insert(25);
        tree.insert(51);
        tree.insert(66);
        tree.insert(19);
        tree.insert(27);
        tree.insert(55);
        tree.insert(20);
        tree.delete(13);
        tree.delete(18);
        tree.delete(55);
//        tree.print1(tree.tree);
//        System.out.println();
//        tree.print2(tree.tree);
//        System.out.println();
//        tree.print3(tree.tree);

        tree.printTree(tree.tree, new ArrayList<>());
        /**
         * 前序：33，16，13，15，18，17，25，19，27，50，34，58，51，55，66
         * 中序：13，15，16，17，18，19，25，27，33，34，50，51，55，58，66
         * 后序：15，13，17，19，27，25，18，16，34，55，51，66，58，50，33
         */}

    /**
     * 前序遍历
     *
     * @param tree
     */
    public void print1(Node tree) {
        if (tree != null) {
            System.out.print(tree.data + " ");
            print1(tree.left);
            print1(tree.right);
        }
    }

    /**
     * 二叉树中打印从根到叶的所有路径
     *
     * @param node
     * @param list
     */
    private void printTree(Node node, ArrayList<Integer> list) {
        if (node != null) {
            list.add((node.data));
            if (node.left == null && node.right == null) {
                System.out.println(list);
                return;
            }
            printTree(node.left, (ArrayList<Integer>) list.clone());
            printTree(node.right, (ArrayList<Integer>) list.clone());
        }

    }

    /**
     * 中序遍历
     *
     * @param tree
     */
    public void print2(BinarySearchTree.Node tree) {

        if (tree != null) {
            print2(tree.left);
            System.out.print(tree.data + " ");
            print2(tree.right);
        }

    }

    /**
     * 后序遍历
     *
     * @param tree
     */
    public void print3(BinarySearchTree.Node tree) {
        if (tree != null) {
            print3(tree.left);
            print3(tree.right);
            System.out.print(tree.data + " ");
        }
    }
}
