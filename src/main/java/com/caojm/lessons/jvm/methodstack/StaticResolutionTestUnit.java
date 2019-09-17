package com.caojm.lessons.jvm.methodstack;

import com.caojm.lessons.jvm.methodstack.dynamiclink.Dog;

/**
 * 方法静态解析演示
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-17 上午11:29
 **/
public class StaticResolutionTestUnit {
    public static void main(String[] args) {
        Dog.wang();
    }
}
