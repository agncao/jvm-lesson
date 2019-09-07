package com.caojm.lessons.jvm.clazzload.singleton;

/**
 * StaticInt什么时候会给默认值
 */
public class HungerInstance {
    public static int StaticInt;
    public static final int ConstantInt=0;

    private static HungerInstance instance = new HungerInstance();

    private HungerInstance(){
        System.out.println("Aha~ New instance have been ready for you!");
    }

    public static HungerInstance getInstance(){
        return instance;
    }
}
