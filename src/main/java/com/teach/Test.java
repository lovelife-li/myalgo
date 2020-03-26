package com.teach;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author ldb
 * @date 2019-09-30 9:04
 */
public class Test {



    public static void main(String[] args) {
        Test test = new Test();


        System.out.println(UUID.randomUUID().toString().replace("-","").substring(0,10));

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss-")));

        String a = "abde";
        System.out.println(a.substring("ab".length()));

    }

}