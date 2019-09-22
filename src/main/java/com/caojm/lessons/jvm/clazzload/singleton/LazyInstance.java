package com.caojm.lessons.jvm.clazzload.singleton;

public class LazyInstance{

    /**
     * 只有初始化了此内部类才会触发LazyInstance 单例对象的生成
     */
    private static class LazyHolder{
        static {
            System.out.println("LazyHolder Class will be initial, and a singleton LazyInstance object will be constructed!");
        }
        private static final LazyInstance instance=new LazyInstance();
    }

    private LazyInstance(){
        System.out.println("Aha~ New instance have been ready for you!");
    }

    public static Object getInstance(boolean listFlag){
        if(listFlag){
            LazyHolder[] lazyHolders = new LazyHolder[2];
            System.out.println(lazyHolders.getClass()); //查看加载的是个什么类对象
            return lazyHolders;
        }
        return LazyHolder.instance;
    }

    public static void main(String[] args) {
        getInstance(true);  //1、是否会生成singleton instance?
//        getInstance(false); //2、是否会生成singleton instance?
    }
}
