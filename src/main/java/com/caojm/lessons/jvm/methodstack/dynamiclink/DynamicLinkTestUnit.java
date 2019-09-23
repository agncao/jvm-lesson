package com.caojm.lessons.jvm.methodstack.dynamiclink;

/**
 * invokevirtual:会调用栈顶真实对象类型的方法，如果此方法不匹配，则向上继续查找
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-09 上午11:44
 **/
public class DynamicLinkTestUnit {

    public static void main(String[] args) {
        Animal cat = new Cat();
        cat.run();

        Animal dog = new Dog();
        dog.run();

        cat.eat();  //调用父类的eat
        dog.eat();

        Dog dog1 =new Dog();
        dog1.run();
    }
}
