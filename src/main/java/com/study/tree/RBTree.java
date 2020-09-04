package com.study.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * 红黑树
 *
 * @author ldb
 * @date 2020/08/13
 */
public class RBTree<T> {

    public Node root;
    public Node nul = new Node(null);
    public int size;

    /**
     * 递归查找
     * @param t
     * @param cur
     * @return
     */
    public Node find(T t, Node cur) {
        if (cur == null) {
            return null;
        } else {
            // 节点hash
            int hash = cur.value.hashCode();
            if (hash == t.hashCode()) {
                return cur;
            } else if (hash < t.hashCode()) {
                cur = cur.right;
                return find(t, cur);
            } else {
                cur = cur.left;
                return find(t, cur);
            }
        }
    }

    /**
     * 循环查找
     * @param t
     * @return
     */
    public Node find2(T t) {
        Node cur = root;
        int hash = t.hashCode();
        while (cur != nul) {
            if (cur.value.hashCode() == hash) {
                return cur;
            } else if (cur.value.hashCode() < hash) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }

        return null;
    }

    /**
     * 规则：红黑树规定，插入的节点必须是红色的。而且，二叉查找树中新插入的节点都是放在叶子节点
     * 简化描述:最开始的关注节点就是新插入的节点,父节点的兄弟节点叫作叔叔节点，父节点的父节点叫作祖父节点。
     * 插入情况分类
     * 1）如果插入节点的父节点是黑色的，那我们什么都不用做，它仍然满足红黑树的定义。
     * 2）如果插入的节点是根节点，那我们直接改变它的颜色，把它变成黑色就可以了。
     * 3）插入结点的父结点为红结点，那么该父结点不可能为根结点，所以插入结点总是存在祖父结点,做平衡调整
     */
    public boolean insert(T t) {
        if (t == null) {
            return false;
        }
        Node cur = root;
        int hash = t.hashCode();
        if (cur == null) {
            // 插入的节点是根节点
            root = new Node(Color.BLACK, t);
            ++size;
            setNul(root);
            return true;
        }
        // 33, 16, 50, 15, 19, 17, 25, 27, 34, 58, 51, 66
        while (cur != null) {
            if (cur.value.hashCode() < hash) {
                if (cur.right == nul) {
                    Node node = new Node(Color.RED, t);
                    cur.right = node;
                    node.parent = cur;
                    setNul(node);
                    ++size;
                    balanceInsertion2(cur, node);
                    return true;
                }
                cur = cur.right;
            } else if (cur.value.hashCode() > hash) {
                if (cur.left == nul) {
                    Node node = new Node(Color.RED, t);
                    cur.left = node;
                    node.parent = cur;
                    setNul(node);
                    ++size;
                    balanceInsertion2(cur, node);
                    return true;
                }
                cur = cur.left;
            } else {
                // 已存在,不做处理
                return false;
            }

        }
        return false;
    }

    // 设置节点子节点为空节点
    private void setNul(Node node) {
        node.left = nul;
        node.right = nul;
    }

    /**
     * 循环版本
     * 平衡插入操作，使其满足红黑树定义
     * 一，父节点是左子节点
     * CASE 1：如果关注节点是 a，它的叔叔节点 d 是红色，我们就依次执行下面的操作：
     * ------将关注节点 a 的父节点 b、叔叔节点 d 的颜色都设置成黑色；
     * ------将关注节点 a 的祖父节点 c 的颜色设置成红色；
     * ------关注节点变成 a 的祖父节点 c；
     * ------跳到 CASE 2 或者 CASE 3。
     * <p>
     * CASE 2：如果关注节点是 a，它的叔叔节点 d 是黑色，关注节点 a 是其父节点 b 的右子节点，我们就依次执行下面的操作：
     * ------关注节点变成节点 a 的父节点 b；
     * ------围绕新的关注节点b 左旋；
     * ------跳到 CASE 3。
     * <p>
     * CASE 3：如果关注节点是 a，它的叔叔节点 d 是黑色，关注节点 a 是其父节点 b 的左子节点，我们就依次执行下面的操作：
     * ------围绕关注节点 a 的祖父节点 c 右旋；
     * ------将关注节点 a 的父节点 b、兄弟节点 c 的颜色互换。
     * ------调整结束
     * <p>
     * 二，父节点是右子节点，跟前面相识
     * CASE 1：如果关注节点是 a，它的叔叔节点 d 是红色，我们就依次执行下面的操作：
     * ------将关注节点 a 的父节点 b、叔叔节点 d 的颜色都设置成黑色；
     * ------将关注节点 a 的祖父节点 c 的颜色设置成红色；
     * ------关注节点变成 a 的祖父节点 c；
     * <p>
     * CASE 2：如果关注节点是 a，它的叔叔节点 d 是黑色，关注节点 a 是其父节点 b 的右子节点，我们就依次执行下面的操作：
     * ------围绕关注节点 a 的祖父节点 c 左旋；
     * ------将关注节点 a 的父节点 b、兄弟节点 c 的颜色互换。
     * <p>
     * CASE 3：如果关注节点是 a，它的叔叔节点 d 是黑色，关注节点 a 是其父节点 b 的左子节点，我们就依次执行下面的操作：
     * ------关注节点变成节点 a 的父节点 b；
     * ------围绕新的关注节点 b  右旋；
     * 跳到 CASE 2
     *
     * @param p 插入节点父节点
     * @param c 插入节点
     */
    private void balanceInsertion2(Node p, Node c) {
        Node tmp;
        // 插入节点父节点是红色才需要平衡
        while (c != root && p.color == Color.RED) {
            if (p.parent.left == p) {
                // 父节点是左子节点处理
                Node uncle = p.parent.right;
                if (uncle.color == Color.RED) {
                    // 叔叔节点 d 是红色
                    p.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    p.parent.color = Color.RED;
                    tmp = p;
                    p = p.parent.parent;
                    c = tmp.parent;
                    continue;
                } else if (uncle.color == Color.BLACK && p.right == c) {
                    // 如果关注节点是 cur，它的叔叔节点是黑色，关注节点 cur是其父节点 b 的右子节点
                    rotateLeft(p, c);
                    tmp = p;
                    p = c;
                    c = tmp;
                    continue;
                } else if (uncle.color == Color.BLACK && p.left == c) {
                    // 如果关注节点是 cur，它的叔叔节点是黑色，关注节点cur是其父节点的左子节点
                    rotateRight(p.parent, p);
                    p.color = Color.BLACK;
                    p.right.color = Color.RED;
                    break;
                }
            } else {
                // 父节点是右子节点处理
                Node uncle = p.parent.left;
                if (uncle.color == Color.RED) {
                    // 叔叔节点 d 是红色
                    p.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    p.parent.color = Color.RED;
                    tmp = p;
                    p = p.parent.parent;
                    c = tmp.parent;
                    continue;
                } else if (uncle.color == Color.BLACK && p.right == c) {
                    // 如果关注节点是 cur，它的叔叔节点是黑色，关注节点 cur是其父节点 b 的右子节点
                    rotateLeft(p.parent, p);
                    p.color = Color.BLACK;
                    p.left.color = Color.RED;
                    break;
                } else if (uncle.color == Color.BLACK && p.left == c) {
                    // 如果关注节点是 cur，它的叔叔节点是黑色，关注节点cur是其父节点的左子节点
                    rotateRight(p, c);
                    p = c;
                    c = c.right;
                    continue;
                }
            }
        }
        if (c == root && c.color == Color.RED) {
            c.color = Color.BLACK;
        }
    }

    /**
     * 递归版本
     * 平衡插入操作，使其满足红黑树定义
     * 一，父节点是左子节点
     * CASE 1：如果关注节点是 a，它的叔叔节点 d 是红色，我们就依次执行下面的操作：
     * ------将关注节点 a 的父节点 b、叔叔节点 d 的颜色都设置成黑色；
     * ------将关注节点 a 的祖父节点 c 的颜色设置成红色；
     * ------关注节点变成 a 的祖父节点 c；
     * ------跳到 CASE 2 或者 CASE 3。
     * <p>
     * CASE 2：如果关注节点是 a，它的叔叔节点 d 是黑色，关注节点 a 是其父节点 b 的右子节点，我们就依次执行下面的操作：
     * ------关注节点变成节点 a 的父节点 b；
     * ------围绕新的关注节点b 左旋；
     * ------跳到 CASE 3。
     * <p>
     * CASE 3：如果关注节点是 a，它的叔叔节点 d 是黑色，关注节点 a 是其父节点 b 的左子节点，我们就依次执行下面的操作：
     * ------围绕关注节点 a 的祖父节点 c 右旋；
     * ------将关注节点 a 的父节点 b、兄弟节点 c 的颜色互换。
     * ------调整结束
     * <p>
     * 二，父节点是右子节点，跟前面相识
     * CASE 1：如果关注节点是 a，它的叔叔节点 d 是红色，我们就依次执行下面的操作：
     * ------将关注节点 a 的父节点 b、叔叔节点 d 的颜色都设置成黑色；
     * ------将关注节点 a 的祖父节点 c 的颜色设置成红色；
     * ------关注节点变成 a 的祖父节点 c；
     * <p>
     * CASE 2：如果关注节点是 a，它的叔叔节点 d 是黑色，关注节点 a 是其父节点 b 的右子节点，我们就依次执行下面的操作：
     * ------围绕关注节点 a 的祖父节点 c 左旋；
     * ------将关注节点 a 的父节点 b、兄弟节点 c 的颜色互换。
     * <p>
     * CASE 3：如果关注节点是 a，它的叔叔节点 d 是黑色，关注节点 a 是其父节点 b 的左子节点，我们就依次执行下面的操作：
     * ------关注节点变成节点 a 的父节点 b；
     * ------围绕新的关注节点 b  右旋；
     * 跳到 CASE 2
     *
     * @param parent 插入节点父节点
     * @param cur    插入节点
     */
    private void balanceInsertion(Node parent, Node cur) {
        if (cur == root && cur.color == Color.RED) {
            cur.color = Color.BLACK;
            return;
        }
        // 插入节点父节点是红色才需要平衡
        if (parent.color == Color.RED) {
            if (parent.parent.left == parent) {
                // 父节点是左子节点处理
                Node uncle = parent.parent.right;
                if (uncle.color == Color.RED) {
                    // 叔叔节点 d 是红色
                    parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    parent.parent.color = Color.RED;
                    balanceInsertion(parent.parent.parent, parent.parent);
                } else if (uncle.color == Color.BLACK && parent.right == cur) {
                    // 如果关注节点是 cur，它的叔叔节点是黑色，关注节点 cur是其父节点 b 的右子节点
                    rotateLeft(parent, cur);
                    balanceInsertion(cur, parent);
                } else if (uncle.color == Color.BLACK && parent.left == cur) {
                    // 如果关注节点是 cur，它的叔叔节点是黑色，关注节点cur是其父节点的左子节点
                    rotateRight(parent.parent, parent);
                    parent.color = Color.BLACK;
                    parent.right.color = Color.RED;
                }
            } else {
                // 父节点是右子节点处理
                Node uncle = parent.parent.left;
                if (uncle.color == Color.RED) {
                    // 叔叔节点 d 是红色
                    parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    parent.parent.color = Color.RED;
                    balanceInsertion(parent.parent.parent, parent.parent);
                } else if (uncle.color == Color.BLACK && parent.right == cur) {
                    // 如果关注节点是 cur，它的叔叔节点是黑色，关注节点 cur是其父节点 b 的右子节点
                    rotateLeft(parent.parent, parent);
                    parent.color = Color.BLACK;
                    parent.left.color = Color.RED;
                } else if (uncle.color == Color.BLACK && parent.left == cur) {
                    // 如果关注节点是 cur，它的叔叔节点是黑色，关注节点cur是其父节点的左子节点
                    rotateRight(parent, cur);
                    balanceInsertion(cur, cur.right);
                }
            }

        }

    }


    /**
     * 删除情况跟二叉搜索树类似，只是要做平衡
     * 1，删除节点有两个非null子节点，找到后继节点。把删除节点的值替换成后继节点的值，删除节点指针指向后继节点。
     * ----找后继节点的替代节点，有可能为其子节点，那么把后继节点的值改为其子节点的值。删除节点指针指向其子节点。
     * ----如果后继节点子节点为null ,后继节点就是删除节点。
     * ----以删除节点为关注节点，进行平衡操作。
     * 2，删除节点有一个非null子节点，那个删除节点必定是黑色节点，子节点是红色节点。很好操作。
     * 3，删除节点有两个null子节点，判断是不是根节点。不是根节点，是黑色节点，需要做平衡操作。
     * @param t
     */
    private void delete(T t) {
        Node del = find2(t);
        if (del == null) {
            // 没找到
            return;
        }
        size--;
        if (del.left != nul && del.right != nul) {
            // 没有空子节点
            // 找后继节点
            Node successor = findSuccessor(del.right);
            del.value = successor.value;
            // 删除节点变为后继节点
            del = successor;
            // 找替换节点
            Node replaceNode = findReplaceNode(del.right);
            if (replaceNode == nul) {
                if (del.color == Color.BLACK) {
                    balanceDelete(del.parent, del);
                }
            } else {
                del.value = replaceNode.value;
                del = replaceNode;
            }
            deleteNode(del);
        } else if (del.left != nul) {
            // 有右空子节点，黑色,del 只能黑色，del.left 只能红色
            del.value = del.left.value;
            del.left = nul;
        } else if (del.right != nul) {
            // 有左空子节点，黑色,del 只能红色，del.right 只能红色
            del.value = del.right.value;
            del.right = nul;
        } else {
            // 有两个空子节点
            // 平衡
            if (del == root) {
                root = null;
                return;
            } else {
                if (del.color == Color.BLACK) {
                    balanceDelete(del.parent, del);
                }
                deleteNode(del);
            }
        }

    }

    private void deleteNode(Node del) {
        if (del.parent.left == del) {
            del.parent.left = del.left;
        } else {
            del.parent.right = del.right;
        }
    }


    /**
     * 4种情况，主要看兄弟节点颜色，进行处理
     * 判断关注节点不是根节点，为黑色。
     * @param p 父节点
     * @param c 关注节点
     */
    private void balanceDelete(Node p, Node c) {

        while (c != root && c.color == Color.BLACK) {
            if (p.left == c) {
                // 兄弟节点
                Node b = p.right;
                // 替换结点是其父结点的左子结点
                if (b.color == Color.RED) {
                    // 替换结点的兄弟结点是红结点
                    rotateLeft(p, b);
                    swapColor(p, b);
                    continue;
                } else {
                    // 替换结点的兄弟结点是黑结点,并且它的左右子节点都是黑色的
                    if (b.left.color == Color.BLACK && b.right.color == Color.BLACK) {
                        b.color = Color.RED;
                        c = p;
                        p = p.parent;
                    } else if (b.left.color == Color.RED && b.right.color == Color.BLACK) {
                        Node tmp = b.left;
                        rotateRight(b, tmp);
                        swapColor(b, tmp);
                        continue;
                    } else if (b.right.color == Color.RED) {
                        rotateLeft(p, b);
                        b.color = p.color;
                        p.color = Color.BLACK;
                        b.right.color = Color.BLACK;
                        break;
                    }
                }
            } else {
                // 兄弟节点
                Node b = p.left;
                // 替换结点是其父结点的右子结点
                if (b.color == Color.RED) {
                    // 替换结点的兄弟结点是红结点
                    rotateRight(p, b);
                    b.color = Color.BLACK;
                    p.color = Color.RED;
                    continue;
                } else {
                    // 替换结点的兄弟结点是黑结点,并且它的左右子节点都是黑色的
                    if (b.left.color == Color.BLACK && b.right.color == Color.BLACK) {
                        b.color = Color.RED;
                        c = p;
                        p = p.parent;
                        continue;
                    } else if (b.left.color == Color.BLACK && b.right.color == Color.RED) {
                        b.color = Color.RED;
                        b.right.color = Color.BLACK;
                        Node tmp = b.right;
                        rotateLeft(b, tmp);
                        continue;
                    } else if (b.left.color == Color.RED) {
                        b.color = p.color;
                        p.color = Color.BLACK;
                        b.left.color = Color.BLACK;
                        rotateRight(p, b);
                        break;
                    }
                }

            }
        }
        c.color = Color.BLACK;
    }

    // 交换颜色
    private void swapColor(Node n1, Node n2) {
        Color c = n1.color;
        n1.color = n2.color;
        n2.color = c;
    }

    // 找后继节点
    private Node findSuccessor(Node node) {
        while (node.left != nul) {
            node = node.left;
        }
        return node;
    }

    // 找替换节点
    private Node findReplaceNode(Node node) {
        while (node != nul && node.right != nul) {
            node = node.right;
        }
        return node;
    }


    /**
     * 左旋
     *
     * @param parent 旋转节点
     * @param cur    旋转节点右子节点
     */
    private void rotateLeft(Node parent, Node cur) {
        if (parent == root) {
            root = cur;
            cur.parent = null;
        } else {
            Node grandfather = parent.parent;
            relatedParent(parent, cur, grandfather);
            cur.parent = grandfather;
        }
        // 临时记录关注节点左子树
        Node tmp = cur.left;
        cur.left = parent;
        parent.parent = cur;
        parent.right = tmp;
        tmp.parent = parent;
    }

    // 关联父节点
    private void relatedParent(Node parent, Node cur, Node grandfather) {
        if (grandfather.left == parent) {
            // 旋转节点是左子节点
            grandfather.left = cur;
        } else {
            // 旋转节点是右子节点
            grandfather.right = cur;
        }
    }

    /**
     * 右旋
     *
     * @param parent 旋转节点
     * @param cur    旋转节点左子节点
     */
    private void rotateRight(Node parent, Node cur) {
        if (parent == root) {
            root = cur;
            cur.parent = null;
        } else {
            Node grandfather = parent.parent;
            relatedParent(parent, cur, grandfather);
            cur.parent = grandfather;
        }
        // 临时记录关注节点右子树
        Node tmp = cur.right;
        cur.right = parent;
        parent.parent = cur;
        parent.left = tmp;
        tmp.parent = parent;
    }

    /**
     * 二叉树中打印从根到叶的所有路径
     *
     * @param node
     * @param list
     */
    private void printTree(Node node, ArrayList<Node> list, HashSet set) {
        if (node != nul && node != null) {
            list.add(node);
            if (node.left == nul && node.right == nul) {
                long num = list.stream().filter(x -> x.color == Color.BLACK).count();
//                System.out.println(list + ", " + num);
                set.add(num);
//                System.out.println(set.size());
                return;
            }
            printTree(node.left, (ArrayList<Node>) list.clone(), set);
            printTree(node.right, (ArrayList<Node>) list.clone(), set);
        }

    }


    static final class Node<T> {
        Node left;
        Node right;
        Node parent;
        Color color;
        T value;

        public Node(Color color, T value) {
            this.color = color;
            this.value = value;
        }

        public Node(T value) {
            this.color = Color.BLACK;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{value:" + value + ",color:" + color + "}";
        }
    }

    private enum Color {
        BLACK, RED;

        @Override
        public String toString() {
            return this.name();
        }
    }

    /**
     * 中序遍历
     *
     * @param tree
     */
    public void print1(Node tree) {
        if (tree != nul && tree != null) {
            print1(tree.left);
            System.out.print(tree.value + " ");
            print1(tree.right);
        }

    }

    public static void main(String[] args) {
        RBTree<Integer> tree = new RBTree<>();
        for (int i = 1; i < 10; i++) {
            test1(tree,1000);
        }

        test1(tree,1000);


    }

    /**
     * 少量数据测试
     * @param tree
     */
    public static void test2(RBTree<Integer> tree) {
        System.out.println("33, 16, 50, 15, 19, 17, 25, 27, 34, 58, 51, 66");
        int[] arr = {33, 16, 50, 15, 19, 17, 25, 27, 34, 58, 51, 66};

        for (int i = 0; i < arr.length; i++) {
            tree.insert(arr[i]);
        }
        tree.print1(tree.root);
        System.out.println();
        int[] arr2 = {19, 15, 25, 27, 34, 58, 66, 16, 17, 33, 50, 51};
        for (int i = 0; i < arr2.length; i++) {
//            System.out.println("删除：" + arr2[i]);
            tree.delete(arr2[i]);
//            tree.print1(tree.root);
//            System.out.println();
            HashSet sets = new HashSet<>();
            tree.printTree(tree.root, new ArrayList<>(), sets);
            System.out.println(sets.size());
        }
    }

    /**
     * 随机数据测试
     * @param tree
     * @param size 树有值节点个数
     */
    public static void test1(RBTree<Integer> tree, int size) {
        HashSet<Integer> items = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int item = random.nextInt(size);
            items.add(item);
//            System.out.println(item);
            tree.insert(item);
        }
//        System.out.println("---------" + items.size() + "----" + tree.size + "------------");
//        AtomicInteger i = new AtomicInteger();
        items.forEach(x -> {
//            System.out.println("删除：" + x);
            tree.delete(x);
//            i.getAndIncrement();
//            System.out.println(i.get());
            HashSet sets = new HashSet<>();
            tree.printTree(tree.root, new ArrayList<>(), sets);
//            System.out.println(sets.size());
            if (sets.size() > 1 || sets.size() == 0) {
                System.out.println(sets.size());
            }
        });
//        System.out.println("---------" + items.size() + "----------------");
    }


    /**
     * 依次插入1000个数据，依次删除1000个数据测试
     * @param tree
     */
    public static void test3(RBTree<Integer> tree) {

        int size = 1000;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
            System.out.println(i);
            tree.insert(i);
        }

        for (int i = 0; i < size; i++) {
            tree.delete(i);
            HashSet sets = new HashSet<>();
            tree.printTree(tree.root, new ArrayList<>(), sets);
            if (sets.size() > 1 || sets.size() == 0) {
                System.out.println(sets.size());
            }

        }
    }

}
