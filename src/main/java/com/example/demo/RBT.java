package com.example.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @desc 红黑树主要难点在于平衡，平衡都是从局部平衡开始，<br/>
 *       局部平衡主要是通过旋转和重新着色来完成，如果局部<br/>
 *       平衡后，此局部平衡的红黑树，根部仍然是黑色，<br/>
 *       黑色节点高度没有变化，那整棵树也是平衡的，如果发<br/>
 *       生黑色节点高度变化，或者这个局部树根为红色，都需<br/>
 *       要递归对整棵树进行平衡，对于插入只有一种情况导致<br/>
 *       局部树根为红色，需要递归对整棵树进行平衡。对于删除<br/>
 *       有部分情况导致局部树黑色节点高度变小，需要递归调整<br/>
 *       整棵树的平衡。
 * @author: TGJ
 * @date: 2020年8月10日
 * @since: 1.0.0
 */
public class RBT<V> implements Serializable {

	private static final long serialVersionUID = 1L;
	static RBT<Integer> rbt;

	public static void main(String[] args) {
		rbt = new RBT<>();
		// 19, 18, 60, 35, 12, 24, 17, 26, 65
//		int[] base = { 15, 16, 17, 19, 25, 27, 33, 34, 50, 51, 58, 66, 68, 52, 37, 12, 2, 90, 30, 22, 11, 44, 55, 77,
//				88, 99 };
		List<Integer> records = new ArrayList<>();
//		Arrays.stream(base).forEach(t -> {
//			rbt.insert(t);
//			records.add(t);
//		});
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
//		int baseLen = base.length + 1;
		int baseLen = 1000;
		int times = 1_000;
		int v = 0;
		for (int i = 0; i < times; i++) {
			for (int j = 0; j < baseLen;) {
				v = random.nextInt(times);
				if (!records.contains(v) && rbt.insert(v)) {
					records.add(v);
					j++;
				}
			}
			for (int j = 0; j < baseLen;) {
				v = records.remove(0);
				rbt.delete(v);
				rbt.lrr(sb, rbt.root, new LinkedList<>(), 0);
				if (Stream.of(sb.toString().split(",")).distinct().count() != 1) {
					System.out.println("delete err------------------------------");
					return;
				}
				j++;
				sb.delete(0, sb.length());
			}
			System.out.println(i);
		}
	}

	public RBT() {
		root = new Node<>();
		root.left = new Node<>();
		root.right = new Node<>();
	}

	private Node<V> root;

	public Node<V> getRoot() {
		return root;
	}

	public void setRoot(Node<V> root) {
		this.root = root;
	}

	/**
	 * 
	 * @desc 删除，主要是用一个元素替换删除元素的值，然后删除替换元<br/>
	 *       素，（这里采用后继的方式找到替换元素），因为删除元素只是更改<br/>
	 *       值，没有更改颜色，故不会产生平衡问题，所以平衡问题就只用解决<br/>
	 *       替换元素的就可以了。<br/>
	 *       <br/>
	 * 
	 *       查找替换元素（替换元素总是在叶子上，非常重要，如果不是就递归的找并替换直到叶子为止），如下：<br/>
	 *       1. 若删除节点无子节点，直接删除，不用找替换元素。<br/>
	 *       2. 若删除节点只有一个子节点，子节点就是替换元素。<br/>
	 *       3. 若删除节点两个子节点，用后继节点作为替换元素。<br/>
	 * @param t
	 * @return Node<V>
	 */
	public boolean delete(V t) {
		Node<V> cn = search(t);
		// 不存在
		if (null == cn.value) {
			return false;
		}
		Node<V> sn = cn, tn;
		do {
			tn = sn;
			sn = findSuccessor(tn);
			tn.value = sn.value;
		} while (sn != tn);
		deleteCheckAndBalance(sn);
		Node<V> node = new Node<>();
		if (sn != root) {
			node.parent = sn.parent;
			if (sn.parent.left == sn) {
				sn.parent.left = node;
			} else {
				sn.parent.right = node;
			}
		} else {
			root = node;
			root.left = new Node<>();
			root.right = new Node<>();
		}
		return true;
	}

	private Node<V> findSuccessor(Node<V> cn) {
		Node<V> tn = cn;
		if (null == cn.left.value && null == cn.right.value) {
			return tn;
		}
		if (null != tn.right.value && null != tn.left.value) {
			tn = tn.right;
			for (; null != tn.left.value; tn = tn.left) {
			}
			return tn;
		}
		return null != tn.right.value ? tn.right : tn.left;
	}

	/**
	 * 
	 * @desc 所有调整，都是为了局部平衡，删除情况如下 (以下所有坐标都是以替换节点原始位置计算的) ：
	 * 
	 *       <pre>
	 *  1. 替换结节点是红色节点，由于是红色节点，直接删除，不影响平衡。
	 *  2. 替换节点是黑色节点（所有的操作都是为了最终变成替换元素的兄弟的子节点都是黑色，
	 *     然后将替换节点的兄弟节点标记为红色来减少一个黑色节点，达到删除后局部平衡），
	 *     具体分如下情况：
	 *   2.1. 替换节点是其父节点的左子节点。
	 *    2.1.1. 替换节点的兄弟节点是红色节点，操作如下：
	 *     1. 对 parent 节点进行左旋。
	 *     2. 将 brother 节点标记为黑色。
	 *     3. 将 parent 节点标记为红色。
	 *     4. 进行后面 2.1.2 相关的情景处理。
	 *    2.1.2. 替换节点的兄弟节点是黑色节点，具体分如下情况：
	 *     2.1.2.1. 替换节点的兄弟节点的右子节点是红色节点，左子节点任意颜色
	 *              （此种情况，已经完成局部平衡，并且没有黑色节点损失，整棵
	 *              树达到平衡），操作如下：
	 *      1. 对 parent 节点进行左旋。
	 *      2. 将 brother 节点标记为替换节点的 parent 节点的颜色。
	 *      3. 将 parent 节点标记为黑色。
	 *      4. 将 brother 节点的右子节点标记为黑色。
	 *     2.1.2.2. 替换节点的兄弟节点的右子节点为黑色节点，左子节点为红色节点，操作如下：
	 *      1. 对 brother 节点进行右旋。
	 *      2. 将 brother 节点标记为红色。
	 *      3. 将 brother 节点的左子节点标记为黑色。
	 *      4. 进行情景 2.1.2.1 的处理。
	 *     2.1.2.3. 替换节点的兄弟节点的子节点都为黑色节点（此步便能使树局部平衡，
	 *              并且减少一个黑色节点的高度），操作如下：
	 *      1. 将 brother 节点标记为红色。
	 *      2. 将 parent 节点作为新的替换元素，重新处理。
	 *   2.2. 替换节点是其父节点的右子节点。
	 *    2.2.1. 替换节点的兄弟节点是红色节点，操作如下：
	 *     1. 对 parent 节点进行右旋。
	 *     2. 将 brother 节点标记为黑色。
	 *     3. 将 parent 节点标记为红色。
	 *     4. 进行 2.2.2 相关的情景处理。
	 *    2.2.2. 替换节点的兄弟节点是黑色节点，具体分如下情况：
	 *     2.2.2.1. 替换节点的兄弟节点的左子节点是红色节点，右子节点任意颜色
	 *             （此种情况，已经完成局部平衡，并且没有黑色节点损失，整棵树
	 *              达到平衡），操作如下：
	 *      1. 对 parent 节点进行右旋。
	 *      2. 将 brother 节点标记为替换节点的 parent 节点的颜色。
	 *      3. 将 parent 节点标记为黑色。
	 *      4. 将 brother 节点的左子节点标记为黑色。
	 *     2.2.2.2. 替换节点的兄弟节点的左子节点为黑色节点，右子节点为红色节点，操作如下：
	 *      1. 对 brother 节点进行左旋。
	 *      2. 将 brother 节点标记为红色。
	 *      3. 将 brother 节点的右子节点标记为黑色。
	 *      4. 进行情景 2.2.2.1 的处理。
	 *     2.2.2.3. 替换节点的兄弟节点的子节点都为黑色节点（此步便能使树局部平衡，
	 *              并且减少一个黑色节点的高度），操作如下：
	 *      1. 将 brother 节点标记为红色。
	 *      2. 将 parent 节点作为新的替换元素，重新处理。
	 *       </pre>
	 * 
	 * @param cn 调整结节
	 * @return boolean
	 */
	private void deleteCheckAndBalance(Node<V> cn) {
		boolean isLeft;
		for (Node<V> rp = cn, p = rp.parent, b, bcr, bcl; null != p; p = rp.parent) {
			if (rp.color == Color.RED) {
				rp.color = Color.BLACK;
				break;
			}
			for (;;) {
				p = rp.parent;
				isLeft = rp == p.left;
				b = isLeft ? p.right : p.left;
				bcr = b.right;
				bcl = b.left;
				// 替换节点是其父节点的左子节点
				if (isLeft) {
					// 替换节点的兄弟节点是红色节点
					if (b.color == Color.RED) {
						leftRotation(p);
						rebuildColor(p, b);
						continue;
					}
					// 替换节点的兄弟节点的右子节点是红色节点，左子节点任意颜色
					if (bcr.color == Color.RED) {
						leftRotation(p);
						deleteRebuildBrotherChildColor(p, b, bcr);
						return;
					} // 替换节点的兄弟节点的右子节点是黑色节点，左子节点是红色节点
					if (bcl.color == Color.RED) {
						rightRotation(b);
						rebuildColor(b, bcl);
						continue;
					}
					// 替换节点的兄弟节点的子节点都为黑色节点
					b.color = Color.RED;
					rp = p;
					break;
				}
				// 替换节点的兄弟节点是黑色节点
				else {
					// 替换节点的兄弟节点是红色节点
					if (b.color == Color.RED) {
						rightRotation(p);
						rebuildColor(p, b);
						continue;
					}
					// 替换节点的兄弟节点的左子节点是红色节点，右子节点任意颜色
					if (bcl.color == Color.RED) {
						rightRotation(p);
						deleteRebuildBrotherChildColor(p, b, bcl);
						return;
					}
					// 替换结点的兄弟结点的左子结点是黑色节点，右子结点是红色节点
					if (bcr.color == Color.RED) {
						leftRotation(b);
						rebuildColor(b, bcr);
						continue;
					}
					// 替换节点的兄弟节点的子节点都为黑色节点
					b.color = Color.RED;
					rp = p;
					break;
				}
			}
		}
	}

	private void rebuildColor(Node<V> p, Node<V> b) {
		p.color = Color.RED;
		b.color = Color.BLACK;
	}

	private void deleteRebuildBrotherChildColor(Node<V> p, Node<V> b, Node<V> bc) {
		b.color = p.color;
		p.color = Color.BLACK;
		bc.color = p.color;
	}

	public boolean insert(V t) {
		if (null == root.value) {
			setValue(t, root);
			return true;
		}
		Node<V> cn = search(t);
		// 存在
		if (null != cn.value) {
			return false;
		}
		cn.color = Color.RED;
		setValue(t, cn);
		insertCheckAndBalance(cn);
		return true;
	}

	private void setValue(V t, Node<V> cn) {
		cn.value = t;
		Node<V> left = new Node<>();
		cn.left = left;
		left.parent = cn;
		Node<V> right = new Node<>();
		cn.right = right;
		right.parent = cn;
	}

	/**
	 * 
	 * @desc 对于树，只有旋转才能让其高度降低，所有调整，都是为了局部平衡 (以下所有坐标都是以插入节点原始位置计算的)。<br/>
	 *       <br/>
	 *       1. 插入节点为红色<br/>
	 *       2. 如果插入节点的 parent 点为黑色，插入完成<br/>
	 *       3. 如果插入节点的 parent 点为红色，分以下情况<br/>
	 * 
	 *       <pre>
	 * 3.1. 如果插入节点的 uncle（叔叔，此节点一定存在，
	 *      这点很重要，需要好好理解）节点是红色，操作步骤如下：
	 *  1. 将 parent 和 uncle 标记为黑色（此方式也是 BRT 增加
	 *     黑色节点高度的唯一方式）
	 *  2. 将 grand parent (祖父) 标记为红色（因为祖父变为红色，可能
	 *     导致出现两个连续的红色节点，所以需要步骤 3）
	 *  3. 然后以 grand parent 节点为准，向上递归检察并调整，直到 
	 *     root 节点为止（root永远都是黑色哦）。
	 * 3.2. 如果插入节点的 uncle（叔叔）节点是黑色（只需要进行一定的旋转
	 *      即可达到局部平衡，因为旋转后的 grand parent 总是黑色，并且黑
	 *      色节点高度也没有变化，也即达到了整棵树的平衡，插入完成），需
	 *      要继续分以下情况：
	 *  3.2.1. 插入节点是 parent 节点的左节点，插手节点的 parent 节点是
	 *         grand parent 节点的左节点（简称，左左情况），操作如下：
	 *    1. 以 grand parent 节点进行右旋。
	 *    2. 将 grand parent 节点标记为红色。
	 *    3. 以 parent 节点标记为黑色。
	 *  3.2.2. 插入节点是 parent 节点的右节点，插手节点的 parent 节点是
	 *         grand parent 节点的左节点（简称，左右情况），操作如下：
	 *    1. 以 parent 进行左旋。
	 *    2. 再以 grand parent 节点进行右旋。
	 *    3. 将 grand parent 节点标记为红色。
	 *    4. 将插入节点标记为黑色。
	 *  3.2.3. 插入节点是 parent 节点的右节点，插手节点的 parent 节点是
	 *         grand parent 节点的右节点（简称，右右情况，实际此情况为
	 *         左左的对称情况），操作如下：
	 *    1. 以 grand parent 节点进行左旋。
	 *    2. 将 grand parent 节点标记为红色。
	 *    3. 以 parent 节点标记为黑色。
	 *  3.2.4. 插入节点是 parent 节点的右节点，插手节点的 parent 节点是
	 *         grand parent 节点的右节点（简称，右左情况，实际此情况为
	 *         左右的对称情况），操作如下：
	 *    1. 以 parent 进行右旋。
	 *    2. 再以 grand parent 节点进行左旋。
	 *    3. 将 grand parent 节点标记为红色。
	 *    4. 将插入节点标记为黑色。
	 *       </pre>
	 * 
	 * @param cn 插入节点
	 * @return void
	 */
	private void insertCheckAndBalance(Node<V> cn) {
		// 当 parent 节点为黑色时已经达到局部平衡
		for (Node<V> p = cn.parent; null != p && p.color == Color.RED; p = cn.parent) {
			Node<V> gpn = p.parent;
			Node<V> un = gpn.left == p ? gpn.right : gpn.left;
			// uncle 为红色节点情况
			if (un.color == Color.RED) {
				p.color = Color.BLACK;
				un.color = Color.BLACK;
				if (null != gpn.parent) {
					gpn.color = Color.RED;
				}
				cn = gpn;
			}
			// uncle 为黑色节点情况
			else {
				if (gpn.left == p) {
					// 左左情况
					if (p.left == cn) {
						rightRotation(gpn);
						rebuildColor(gpn, p);
					} // 左右情况
					else {
						leftRotation(p);
						rightRotation(gpn);
						rebuildColor(gpn, cn);
					}
				} else {
					// 右右
					if (p.right == cn) {
						leftRotation(gpn);
						rebuildColor(gpn, p);
					}
					// 右左
					else {
						rightRotation(p);
						leftRotation(gpn);
						rebuildColor(gpn, cn);
					}
				}
				// 凡是走到此处一定经过了旋转，并达到了局部平衡
				break;
			}
		}
	}

	/**
	 * 
	 * @desc: 左旋
	 * @param rn 待旋转节点 void
	 */
	private void leftRotation(Node<V> rn) {
		Node<V> rcn = rn.right;
		Node<V> llcn = rcn.left;
		Node<V> p = rn.parent;
		rcn.parent = p;
		rcn.left = rn;
		rn.right = llcn;
		rn.parent = rcn;
		llcn.parent = rn;
		if (null != p) {
			if (p.left == rn) {
				p.left = rcn;
			} else {
				p.right = rcn;
			}
		} else {
			root = rcn;
		}
	}

	/**
	 * 
	 * @desc: 右旋
	 * @param rn 待旋转节点 void
	 */
	private void rightRotation(Node<V> rn) {
		Node<V> lcn = rn.left;
		Node<V> rlcn = lcn.right;
		Node<V> p = rn.parent;
		lcn.parent = p;
		lcn.right = rn;
		rn.parent = lcn;
		rn.left = rlcn;
		rlcn.parent = rn;
		if (null != p) {
			if (p.right == rn) {
				p.right = lcn;
			} else {
				p.left = lcn;
			}
		} else {
			root = lcn;
		}
	}

	/**
	 * 
	 * @desc 查找，如果未找到，则返回最后的空节点
	 * @param t
	 * @return Node<V>
	 */
	public Node<V> search(V t) {
		Node<V> tn = root;
		int ch = t.hashCode();
		int nh;
		for (;;) {
			if (null == tn.value) {
				return tn;
			}
			nh = tn.value.hashCode();
			if (ch == nh) {
				return tn;
			}
			if (ch > nh) {
				tn = tn.right;
			} else {
				tn = tn.left;
			}
		}
	}

	public static class Node<V> implements Serializable {
		private static final long serialVersionUID = 1L;
		Node<V> parent;
		Node<V> right;
		Node<V> left;
		Color color;
		V value;

		public Node() {
			super();
			this.color = Color.BLACK;
		}

		@Override
		public String toString() {
//			return "Node: [parent:" + (null != parent ? parent.value : null) + ", value: " + value + ", color: " + color
//					+ "]";
			return "[" + (null != parent ? parent.value : null) + ", " + value + ", " + color + "]";
		}

	}

	private static enum Color {
		BLACK, RED;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		lrr(sb, root, new LinkedList<>(), 0);
		return sb.toString();
	}

	private void print(StringBuilder sb, Node<V> root, List<Node<V>> record, int num) {
		if (null == root.value) {
			return;
		}
		record.add(root);
		num = root.color == Color.BLACK ? ++num : num;
		print(sb, root.left, record, num);
		if (root.left.value == null || root.right.value == null) {
			sb.append(record.stream().map(Node::toString).collect(Collectors.joining(" ---> "))).append("\tblackNum: ")
					.append(num).append("\r\n");
		}
		print(sb, root.right, record, num);
		record.remove(root);
	}

	private void lrr(StringBuilder sb, Node<V> root, List<Node<V>> record, int num) {
		if (null == root.value) {
			return;
		}
		record.add(root);
		num = root.color == Color.BLACK ? ++num : num;
		if (null == root.left.value || null == root.right.value) {
			sb.append(num).append(",");
		}
		lrr(sb, root.left, record, num);
		lrr(sb, root.right, record, num);
		record.remove(root);
	}
}
