package com.caojm.lessons.jvm.clazzload.singleton;

import java.util.UUID;



public class HungerInstance {
    public static String StaticHello=HungerInstance.ConstHello;

    public static final String ConstHello="Hello!";

    public static final String UId= UUID.randomUUID().toString();

    //只要类初始化了就会生成对象
    private static HungerInstance instance = new HungerInstance();

    private HungerInstance(){
        System.out.println("Aha~ New instance have been ready for you!");
    }

    public static HungerInstance getInstance(){
        return instance;
    }
}
