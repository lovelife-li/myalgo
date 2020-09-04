package com.example.demo;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * 
 * @desc: B+Tree 基于B+Tree 实现的 Map，暂时没有解决冲突问题
 * @author: TGJ
 * @date: 2020年8月12日
 * @since: 1.0.0
 */
public class BTree<K, V> implements Serializable {

	private static final long serialVersionUID = 1L;

	Node<K, V> root;

	public Node<K, V> getRoot() {
		return root;
	}

	public void setRoot(Node<K, V> root) {
		this.root = root;
	}

	public BTree(int m) {
		root = new Node<>(m);
	}

	public static void main(String[] args) {
		BTree<Integer, Integer> bt = new BTree<>(5);
		// 5, 8, 10, 15, 16, 17, 18, 19, 20, 21, 22, 9, 6, 7
		Stream.of(5, 8, 10, 15, 16, 17, 18, 19, 20, 21, 22, 9, 6, 7).forEach(t -> bt.insert(t, t));
		System.out.println(bt);
		bt.delete(22);
		System.out.println(22);
		System.out.println(bt);
		bt.delete(15);
		System.out.println(15);
		System.out.println(bt);
		bt.delete(8);
		System.out.println(8);
		System.out.println(bt);
	}

	public boolean delete(K k) {
		Node<K, V> node = searchOrInsert(root, k, null);
		if (null == node.sdata) {
			return false;
		}
		int mid = searchIndex(node, k);
		node.data[mid] = null;
		node.index--;
		IntWrapper intWrapper = new IntWrapper(mid);
		deleteCheckAndAdjust(node, k, intWrapper);
		reset(node);
		return true;
	}

	private void deleteCheckAndAdjust(Node<K, V> delNode, K k, IntWrapper intWrapper) {
		Node<K, V> node = delNode;
		for (; node.index != 0;) {
			Data<K, V>[] data = node.data;
			int size = node.index, m = node.data.length;
			// 将元素移动到一起
			System.arraycopy(data, intWrapper.mid + 1, data, intWrapper.mid, size - intWrapper.mid);
			// 上步合并后，后面不可再使用 sindex 取值
			int base = (int) (Math.ceil(m / 2.0) - 1);
			if (size >= base) {
				// 如果是首节点，并且是叶子节点，处理父节点，解决B+Tree删除后索引，还存在相关 Key 的问题
				if (intWrapper.mid == 0 && !node.key) {
					Node<K, V> parent = node.parent;
					intWrapper.mid = searchIndex(parent, k);
					parent.data[intWrapper.mid].k = node.data[0].k;
					parent.index--;
					node = parent;
					continue;
				}
				return;
			}
			// 处理借用问题
			Node<K, V> b = node.pre;
			if (null != b && b.index > base || null != (b = node.next) && b.index > base) {
				borrowData(b, node);
				return;
			}
			node = mergeData(node, k, intWrapper);
		}
	}

	private Node<K, V> mergeData(Node<K, V> node, K k, IntWrapper intWrapper) {
		boolean next = null == node.pre;
		Node<K, V> b = next ? node.next : node.pre;
		mergeNode(node, b, next);
		Node<K, V> parent = mergeParent(node, b, k, intWrapper, next);
		if (parent.index == 0) {
			root = node;
		}
		return parent;
	}

	/**
	 * 
	 * @desc: 处理父节点，借用问题
	 * @param node
	 * @param b
	 * @param k
	 * @param intWrapper
	 * @param next
	 * @return Node<K,V>
	 */
	private Node<K, V> mergeParent(Node<K, V> node, Node<K, V> b, K k, IntWrapper intWrapper, boolean next) {
		Node<K, V> parent = node.parent;
		Data<K, V>[] data = parent.data;
		Data<K, V> tn = next ? b.pdata : node.pdata;
		int mid = searchIndex(parent, tn.k);
		// 处理索引节点下降问题
		if (node.key) {
			Data<K, V> td = data[mid];
			saveToMid(node, searchIndex(parent, tn.k), td.k, null);
			node.index++;
		}
		// 更新相关索引
		if (mid < parent.index - 1) {
			data[mid + 1].pre = node;
		}
		if (mid > 0) {
			data[mid - 1].next = node;
			if (!next) {
				data[mid - 1].k = node.data[0].k;
				node.pdata = data[mid - 1];
			}
		}
		data[mid] = null;
		intWrapper.mid = mid;
		parent.index--;
		return parent;
	}

	/**
	 * 
	 * @desc: 合并相邻节点
	 * @param node
	 * @param b
	 * @param next void
	 */
	private void mergeNode(Node<K, V> node, Node<K, V> b, boolean next) {
		if (next) {
			System.arraycopy(b.data, 0, node.data, node.index, b.index);
			node.next = b.next;
			if (null != b.next) {
				b.next.pre = node;
			}
		} else {
			System.arraycopy(node.data, 0, node.data, b.index, node.index);
			System.arraycopy(b.data, 0, node.data, 0, b.index);
			node.pre = b.pre;
			if (null != b.pre) {
				b.pre.next = node;
			}
		}
		node.index += b.index;
	}

	private void borrowData(Node<K, V> b, Node<K, V> node) {
		Data<K, V> borrow;
		b.index--;
		if (b == node.pre) {
			borrow = b.data[b.index];
			b.data[b.index] = null;
			System.arraycopy(node.data, 0, node.data, 1, node.index);
			node.data[0] = borrow;
			node.pdata.k = node.data[0].k;
		} else {
			borrow = b.data[0];
			System.arraycopy(b.data, 1, b.data, 0, b.index);
			node.data[node.index] = borrow;
			b.pdata.k = b.data[0].k;
		}
		node.index++;
	}

	/**
	 * 
	 * @desc: 需要插入的 k 从根节点开始找最接近 k 的节点，<br/>
	 *        一层一层找，直到在叶子节点
	 * @param k
	 * @param v
	 * @return boolean
	 */
	public boolean insert(K k, V v) {
		Node<K, V> node = searchOrInsert(root, k, v);
		if (null != node.sdata) {
			reset(node);
			return false;
		}
		insertCheckAndAdjust(node);
		return true;
	}

	private void reset(Node<K, V> node) {
		node.sdata = null;
	}

	private Node<K, V> searchOrInsert(Node<K, V> root, K k, V v) {
		Node<K, V> node = null;
		Data<K, V>[] data;
		Data<K, V> td = null;
		int mid = -1;
		// 从 root 往下查找
		for (node = root;; // 根据最近的值的大小决定下次查找 node
				node = null != td && k.hashCode() < td.k.hashCode() ? td.pre : td.next) {
			data = node.data;
			// 获取到最近一个值
			mid = searchIndex(node, k);
			td = data[mid];
			// 如果已经存在，则返回
			if (!node.key && null != td && td.k.hashCode() == k.hashCode()) {
				node.sdata = td;
				return node;
			}
			// 如果是叶子节点，则插入
			if (!node.key) {
				if (null != v) {
					saveToMid(node, mid, k, v);
				}
				break;
			}
		}
		return node;
	}

	private int saveToMid(Node<K, V> node, int mid, K k, V v) {
		Data<K, V>[] data = node.data;
		Data<K, V> td = data[mid];
		// 返回的下标只是根据二分查找，找到最后一次配置的位置，所以需要再次调整位置
		mid = null != td && td.k.hashCode() < k.hashCode() ? ++mid : mid;
		System.arraycopy(data, mid, data, mid + 1, node.index - mid);
		node.data[mid] = new Data<>(k, v);
		node.index++;
		return mid;
	}

	/**
	 * 
	 * @desc: 调整，主要是根据当天节点，是否超过 m 阶来判断，如果超过，反向向上一层一层调整
	 * @param node void
	 */
	private void insertCheckAndAdjust(Node<K, V> node) {
		int m = node.data.length;
		for (Data<K, V>[] data = node.data; node.index == m; data = node.data) {
			int half = m >>> 1;
			Node<K, V> other = new Node<>(node.key, m);
			if (node.key) {
				System.arraycopy(data, half + 1, other.data, 0, m - half - 1);
			} else {
				System.arraycopy(data, half, other.data, 0, m - half);
			}
			other.index = m - half - (node.key ? 1 : 0);
			node.index = half;
			Node<K, V> parent = adjustParent(node, other, data[half].k);
			// 删除已经复制到 other 节点的数据
			for (int i = node.index; i < m; data[i] = null, i++) {
			}
			node = parent;
		}
		// 重新设置 root 节点
		for (root = node; null != root.parent; root = root.parent) {
		}
	}

	/**
	 * 
	 * @desc: 处理上提 k 并调整相关连接关系
	 * @param node
	 * @param other
	 * @param upK
	 * @return Node<K,V>
	 */
	private Node<K, V> adjustParent(Node<K, V> node, Node<K, V> other, K upK) {
		Node<K, V> parent = null == node.parent ? new Node<>(true, node.data.length) : node.parent;
		int mid = searchIndex(parent, upK);
		mid = saveToMid(parent, mid, upK, null);
		Data<K, V> parentData = parent.data[mid];
		other.pdata = parentData;
		parentData.pre = node;
		parentData.next = other;
		node.parent = parent;
		other.parent = parent;
		if (null != node.next) {
			other.next = node.next;
			node.next.pre = other;
		}
		if (mid == 0 && parent.index > 1) {
			parent.data[1].pre = other;
		}
		node.next = other;
		other.pre = node;
		return parent;
	}

	public V search(K k) {
		Node<K, V> node = searchOrInsert(root, k, null);
		V v = node.sdata.v;
		reset(node);
		return v;
	}

	/**
	 * 
	 * @desc: 二分查找，返回等于或最后一个与之相近的元素下标
	 * @param node
	 * @param k
	 * @return int
	 */
	private int searchIndex(Node<K, V> node, K k) {
		Data<K, V>[] data = node.data;
		Data<K, V> td = null;
		int end, start, mid, pre = 0;
		for (end = node.index - 1, start = 0, mid = start + ((end - start) >>> 1); start <= end; mid = start
				+ (end - start) / 2) {
			td = data[mid];
			pre = mid;
			if (null == td || td.k.hashCode() == k.hashCode()) {
				break;
			} else if (k.hashCode() > td.k.hashCode()) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return pre;
	}

	@SuppressWarnings("unchecked")
	public static class Node<K, V> implements Serializable {
		private static final long serialVersionUID = 1L;
		Data<K, V>[] data;
		boolean key;
		int index;
		Data<K, V> pdata;
		Data<K, V> sdata;
		Node<K, V> next;
		Node<K, V> pre;
		Node<K, V> parent;

		public Node() {
		}

		public Node(int size) {
			data = new Data[size];
		}

		public Node(boolean key, int size) {
			super();
			data = new Data[size];
			this.key = key;
		}

	}

	public static class Data<K, V> implements Serializable {
		private static final long serialVersionUID = 1L;
		V v;
		K k;
		Node<K, V> pre;
		Node<K, V> next;

		public Data(K k, V v) {
			super();
			this.v = v;
			this.k = k;
		}

		public Data(K k) {
			super();
			this.k = k;
		}

		@Override
		public String toString() {
			return "[k:" + (null == k ? "" : k.toString()) + ", v:" + (null == v ? "" : v.toString()) + "]";
		}

	}

	public static class IntWrapper implements Serializable {
		private static final long serialVersionUID = 1L;
		int mid;

		public IntWrapper(int mid) {
			super();
			this.mid = mid;
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		rlr(sb, root, 1);
		return sb.toString();
	}

	/**
	 * 
	 * @desc: 平级递归
	 * @param sb
	 * @param root
	 * @param layer void
	 */
	private void rlr(StringBuilder sb, Node<K, V> root, int layer) {
		if (null == root) {
			return;
		}
		Node<K, V> tn = root;
		for (Data<K, V>[] data = root.data; tn.index > 0; tn = tn.next, data = tn.data) {
			sb.append(layer).append(": ");
			for (Data<K, V> d : data) {
				if (null != d) {
					sb.append(d.toString()).append(" ");
				}
			}
			sb.append("\r\n");
			if (null == tn.next) {
				break;
			}
		}
		if (root.index > 0) {
			rlr(sb, root.data[0].pre, ++layer);
		}
	}

}
