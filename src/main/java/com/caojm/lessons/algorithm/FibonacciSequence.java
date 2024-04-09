package com.caojm.lessons.algorithm;

/**
 * 斐波那契数列
 * 0,1,1,2,3,5,8,13,21,34,55,89,144,233,377
 * f(n)=f(n-1)+f(n-2)
 * f(0)=0,f(1)=1
 * f(2)=f(1)+f(0)=1
 * f(3)=f(2)+f(1)=2
 * f(4)=f(3)+f(2)=3
 * f(5)=f(4)+f(3)=5
 * f(6)=f(5)+f(4)=8
 * f(7)=f(6)+f(5)=13
 * f(8)=f(7)+f(6)=21
 * f(9)=f(8)+f(7)=34
 * f(10)=f(9)+f(8)=55
 * f(11)=f(10)+f(9)=89
 * f(12)=f(11)+f(10)=144
 * f(13)=f(12)+f(11)=233
 * f(14)=f(13)+f(12)=377
 * f(15)=f(14)+f(13)=610
 * f(16)=f(15)+f(14)=987
 * f(17)=f(16)+f(15)=1597
 * f(18)=f(17)+f(16)=2584
 * f(19)=f(18)+f(17)=4181
 * f(20)=f(19)+f(18)=6765
 * f(21)=f(20)+f(19)=10946
 * f(22)=f(21)+f(20)=17711
 * f(23)=f(22)+f(21)=28657
 * f(24)=f(23)+f(22)=46368
 * f(25)=f(24)+f(23)=75025
 * f(26)=f(25)+f(24)=121393
 * f(27)=f(26)+f(25)=196418
 * f(28)=f(27)+f(26)=317811
 * f(29)=f(28)+f(27)=514229
 * f(30)=f(29)+f(28)=832040
 * f(31)=f(30)+f(29)=1346269
 * f(32)=f(31)+f(30)=2178309
 * f(33)=f(32)+f(31)=3524578
 * f(34)=f(33)+f(32)=5702887
 * f(35)=f(34)+f(33)=9227465
 * f(36)=f(35)+f(34)=14930352
 * f(37)=f(36)+f(35)=24157817
 * f(38)=f(37)+f(36)=39088169
 * f(39)=f(38)+f(37)=63245986
 * f(40)=f(39)+f(38)=102334155
 * f(41)=f(40)+f(39)=165580141
 * f(42)=f(41)+f(40)=267914296
 * f(43)=f(42)+f(41)=433494437
 * f(44)=f(43)+f(42)=701408733
 * f(45)=f(44)+f(43)=1134903170
 * f(46)=f(45)+f(44)=1836311903
 */
public class FibonacciSequence {
    public static void main(String[] args) {
        System.out.println(recursionFib2(45));
        System.out.println(fib(45));
    }
    public static int fib(int n) {
        if(n<2) return n;
        int sum0=0;
        int sum1=1;
        for(int i=2;i<n;i++){
            int temp = sum0+sum1;
            System.out.println("n="+i+", fib(n)="+temp);
            sum0=sum1;
            sum1=temp;
        }
        return sum0+sum1;
    }
    public static int recursionFib(int n) {
        if(n<2) return n;
        return recursionFib(n-1)+recursionFib(n-2);
    }

    public static int doRecursionFib2(int n, int[] result) {  // 递归
        if (n <= 2) {
        return 1;
        }
        if (result[n - 1] == 0) {
            result[n - 1] = doRecursionFib2(n - 1, result) + doRecursionFib2(n - 2, result);
        }
        return result[n - 1];
    }

    public static int recursionFib2(int n) {
        if(n<2) return n;
        int[] result = new int[n];
        return doRecursionFib2(n, result);
    }

}
