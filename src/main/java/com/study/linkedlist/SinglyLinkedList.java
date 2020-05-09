package com.study.linkedlist;

/**
 * 1）单链表的插入、删除、查找操作；
 * 2）链表中存储的是int类型的数据；
 * <p>
 * Author：ldb
 */
public class SinglyLinkedList {

    private Node head = null;

    public Node findByValue(int value) {
        Node p = head;
        while (p != null) {
            if (p.data == value) {
                return p;
            }
            p = p.next;
        }
        return null;
    }

    public Node findByIndex(int index) {
        Node p = head;
        for (int i = 0; i < index; i++) {
            if (p != null) {
                p = p.next;
            } else {
                return null;
            }
        }
        return p;

    }

    //表头部插入
    public void insertToHead(int value) {
        Node node = new Node(value, head);
        head = node;
    }

    public void insertToHead(Node newNode) {
        newNode.next = head;
        head = newNode;
    }

    //顺序插入
    //链表尾部插入
    public void insertTail(int value) {
        if (head == null) {
            head = new Node(value, null);
        } else {
            Node p = head;
            while (p.next != null) {
                p = p.next;
            }
            p.next = new Node(value, null);
        }


    }

    public void insertAfter(Node p, int value) {
        Node newNode = new Node(value, null);
        insertAfter(p, newNode);
    }

    public void insertAfter(Node p, Node newNode) {
        Node current = head;
        if (current == null) {
            System.out.println("链表为null,找不到p节点");
        } else {
            while (current != null && current != p) {
                current = current.next;
            }
            if (current == null) {
                System.out.println("链表中找不到p节点");
            } else {
                // 找到p
                Node n = current.next;
                current.next = newNode;
                newNode.next = n;
            }

        }
    }

    public void insertBefore(Node p, int value) {
        Node newNode = new Node(value, null);
        insertBefore(p, newNode);
    }

    public void insertBefore(Node p, Node newNode) {
        Node current = head;
        if (current == null) {
            System.out.println("链表为null,找不到p节点");
        } else {
            Node parent = null;
            while (current != null && current != p) {
                parent = current;
                current = current.next;
            }
            if (current == null) {
                System.out.println("链表中找不到p节点");
            } else {
                // 找到p
                parent.next = newNode;
                newNode.next = current;
            }

        }

    }

    public void deleteByNode(Node p) {
        Node current = head;
        if (current == null) {
            System.out.println("链表为null,找不到p节点,不能删除");
        } else {
            Node parent = null;
            while (current != null && current != p) {
                parent = current;
                current = current.next;
            }
            if (current == null) {
                System.out.println("链表中找不到p节点");
            } else {
                // 找到p
                Node n = current.next;
                parent.next = n;
            }

        }
    }

    public void deleteByValue(int value) {
        Node current = head;
        if (current == null) {
            System.out.println("链表为null,不能删除");
        } else {
            Node parent = null;
            while (current != null && current.data != value) {
                parent = current;
                current = current.next;
            }
            if (current == null) {
                System.out.println("链表中找不到p节点");
            } else {
                // 找到p
                Node n = current.next;
                parent.next = n;
            }

        }
    }

    public void printAll(Node node) {
        Node p = node;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    //判断true or false
    public boolean TFResult(Node left, Node right) {
        while (left != null && right != null) {
            if (left.data == right.data) {
                left = left.next;
                right = right.next;
                continue;
            } else {
                return false;
            }
        }
        if (left == null && right == null) {
            return true;
        } else {
            return false;
        }
    }

    //　判断是否为回文 
    public boolean palindrome() {
        if (head == null) {
            return false;
        } else {
            if (head.next == null) {
                return true;
            } else {
                Node p = head;
                Node q = head;
                while (q.next != null && q.next.next != null) {
                    p = p.next;
                    q = q.next.next;
                }
                Node lnode = null;
                Node rnode = null;
                // 找到中间节点
                if (q.next == null) {
                    // p 是最中间结点
                    rnode = p.next;
                    lnode = inverseLinkList(p).next;
                } else {
                    rnode = p.next;
                    lnode = inverseLinkList(p);

                }
                return TFResult(lnode, rnode);

            }
        }
    }


    //链表中指定节点翻转
    public Node inverseLinkList(Node p) {
        Node pre = null;
        Node r = head;
        Node next;
        while (r != p) {
            next = r.next;
            r.next = pre;
            pre = r;
            r = next;
        }
        r.next = pre;
        return r;

    }



    public static Node createNode(int value) {
        return new Node(value, null);
    }

    public static class Node {
        private int data;
        private Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }
    }

    public static void main(String[] args) {

        SinglyLinkedList link = new SinglyLinkedList();
//        int data[] = {1};
        //int data[] = {1,2};
        //int data[] = {1,2,3,1};
        //int data[] = {1,2,5};
        //int data[] = {1, 2, 2, 1};
        //int data[] = {1,2,5,2,1};
        int data[] = {1, 2, 3, 4, 5, 6};

        for (int i = 0; i < data.length; i++) {
            //link.insertToHead(data[i]);
            link.insertTail(data[i]);
        }
        link.insertBefore(link.head,new Node(7,null));
        System.out.println("打印原始:");
        link.printAll(link.head);

//        Node node = link.inverseLinkList(link.head.next.next.next);
//        link.printAll(node);
//        link.printAll(link.head);


        System.out.println();
        if (link.palindrome()) {
            System.out.println("回文");
        } else {
            System.out.println("不是回文");
        }
    }

}
