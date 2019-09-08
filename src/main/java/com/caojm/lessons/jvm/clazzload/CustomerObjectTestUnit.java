package com.caojm.lessons.jvm.clazzload;

public class CustomerObjectTestUnit {

    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(">>>>>>>>>>>");
        System.out.println(o.toString());
        System.out.println(o.getClass().getClassLoader());
    }

}
