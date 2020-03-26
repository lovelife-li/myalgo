package com.study.suanfa;

import lombok.Data;

/**
 * @author ldb
 * @date 2019-11-15 14:03
 */
@Data
public class Cat{

    protected int id;
    protected String name;



    public static void main(String[] args) {
        Animal cat = new Animal(){};
        cat.say();
    }
}
