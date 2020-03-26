package com.teach.tree;

public class BinarySearchTree {
  private Node tree;

  public Node find(int data) {
    Node p = tree;
    while (p != null) {
      if (data < p.data) p = p.left;
      else if (data > p.data) p = p.right;
      else return p;
    }
    return null;
  }
  
  public void insert(int data) {
    if (tree == null) {
      tree = new Node(data);
      return;
    }

    Node p = tree;
    while (p != null) {
      if (data > p.data) {
        if (p.right == null) {
          p.right = new Node(data);
          return;
        }
        p = p.right;
      } else { // data < p.data
        if (p.left == null) {
          p.left = new Node(data);
          return;
        }
        p = p.left;
      }
    }
  }

  public void delete(int data) {
    Node p = tree; // p指向要删除的节点，初始化指向根节点
    Node pp = null; // pp记录的是p的父节点
    while (p != null && p.data != data) {
      pp = p;
      if (data > p.data) p = p.right;
      else p = p.left;
    }
    if (p == null) return; // 没有找到

    // 要删除的节点有两个子节点
    if (p.left != null && p.right != null) { // 查找右子树中最小节点
      Node minP = p.right;
      Node minPP = p; // minPP表示minP的父节点
      while (minP.left != null) {
        minPP = minP;
        minP = minP.left;
      }
      p.data = minP.data; // 将minP的数据替换到p中
      p = minP; // 下面就变成了删除minP了
      pp = minPP;
    }

    // 删除节点是叶子节点或者仅有一个子节点
    Node child; // p的子节点
    if (p.left != null) child = p.left;
    else if (p.right != null) child = p.right;
    else child = null;

    if (pp == null) tree = child; // 删除的是根节点
    else if (pp.left == p) pp.left = child;
    else pp.right = child;
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
    tree.print1(tree.tree);
    System.out.println();
    tree.print2(tree.tree);
    System.out.println();
    tree.print3(tree.tree);
    /**
     * 前序：33，16，13，15，18，17，25，19，27，50，34，58，51，55，66
     * 中序：13，15，16，17，18，19，25，27，33，34，50，51，55，58，66
     * 后序：15，13，17，19，27，25，18，16，34，55，51，66，58，50，33
     * 33 16 15 19 17 25 27 50 34 58 51 66
     * 15 16 17 19 25 27 33 34 50 51 58 66
     * 15 17 27 25 19 16 34 51 66 58 50 33
     *
     * 33 16 15 19 17 25 27 50 34 58 51 66
     * 15 16 17 19 25 27 33 34 50 51 58 66
     * 15 17 27 25 19 16 34 51 66 58 50 33
     */
  }

  /**
   * 前序遍历
   * @param tree
   */
  public  void print1(Node tree){
    if (tree!=null){
      System.out.print(tree.data+" ");
      print1(tree.left);
      print1(tree.right);
    }

  }

  /**
   * 中序遍历
   * @param tree
   */
  public  void print2(Node tree){

    if (tree!=null){
      print2(tree.left);
      System.out.print(tree.data+" ");
      print2(tree.right);
    }

  }

  /**
   * 后序遍历
   * @param tree
   */
  public  void print3(Node tree){
    if (tree!=null){
      print3(tree.left);
      print3(tree.right);
      System.out.print(tree.data+" ");
    }
  }
}
