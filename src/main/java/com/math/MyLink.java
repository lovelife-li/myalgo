package com.math;

import java.util.LinkedList;
import java.util.List;

public  class MyLink extends LinkedList {

    public boolean add(String s) {
        return true;
    }


    public static void main(String[] args) {
        List a = new MyLink();
        a.add("");
        a.add(13);
    }
}
