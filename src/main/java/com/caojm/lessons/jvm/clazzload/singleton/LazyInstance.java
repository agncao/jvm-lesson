package com.caojm.lessons.jvm.clazzload.singleton;

public class LazyInstance extends Object {
    private static class LazyHolder{
    private static final LazyInstance instance=new LazyInstance();
}

    private LazyInstance(){
        System.out.println("Aha~ New instance have been ready for you!");
    }

    public static Object getInstance(boolean listFlag){
        if(listFlag){
            return new LazyInstance[2];
        }
        return LazyHolder.instance;
    }

    public static void main(String[] args) {
        getInstance(true);  //1、是否会生成singleton instance?
        getInstance(false); //2、是否会生成singleton instance?
    }
}
