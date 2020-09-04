package com.example.demo;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * todo
 *
 * @author ldb
 * @date 2020/08/12
 */
public class Test2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        write();
        read();

    }

    private static void read() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("d:/test2.xx"));
        Object readObject = objectInputStream.readObject();
        Node node = (Node) readObject;
        System.out.println(node.val);
        System.out.println(node.next.val);
        System.out.println(node.next.next.val);
    }

    static class Node implements Serializable {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    public static void write() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("d:/test2.xx"));
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        oos.writeObject(head);
        oos.flush();
    }
}
