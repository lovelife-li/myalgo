package com.study.suanfa;

import lombok.Data;

@Data
public abstract class Animal {

    protected String name;

    public static void say(){
        System.out.println("hello");
    }
}
