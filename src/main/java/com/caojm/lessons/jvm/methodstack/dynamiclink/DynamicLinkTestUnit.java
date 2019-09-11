package com.caojm.lessons.jvm.methodstack.dynamiclink;

/**
 * ${DESCRIPTION}
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-09 上午11:44
 **/
public class DynamicLinkTestUnit {

    public static void main(String[] args) {
        Dog.getViceProfSalary();

        Animal cat = new Cat();
        cat.run();

        Animal dog = new Dog();
        dog.run();

    }
}
