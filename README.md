# 字节码执行引擎
## 举例 MethodStackTestUnit
![MethodStackTestUnit](https://i.niupic.com/images/2019/09/15/_310.png)

+ 当创建一个线程时，同时会创建一个栈
+ 栈帧的大小在编译阶段就能确定下来，不会随着线程的运行而变化。在code属性中描述：
    + 局部变量表大小
    + 操作数栈的深度
+ 栈帧与栈帧可以有重叠区域