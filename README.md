# 字节码执行引擎
## 举例 MethodStackTestUnit
![MethodStackTestUnit](https://i.niupic.com/images/2019/09/15/_310.png)

+ 栈内存随着线程的创建而创建
+ 栈帧的大小在编译阶段就能确定下来，不会随着线程的运行而变化。在code属性中描述：
    + 局部变量表大小
    + 操作数栈的深度
+ 栈桢的创建随着方法的调用而创建并入栈，随着方法的调用结束而出栈
+ 归属特定的一个一个的线程，因此不存在并发、同步，垃圾回收的概念
+ 栈帧与栈帧可以有重叠区域：下面栈桢的操作数栈与栈顶栈桢的局部变量可以共享，无需额外的参数复制传递
+ 程序计数器记录的是每个线程当前执行jvm指令码的内存地址

## 方法调用