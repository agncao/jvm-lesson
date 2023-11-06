package com.caojm.lessons.algorithm;

public class PrimeNumSearch {
    public static void main(String[] args) {
        System.out.println(countPrime(17));
    }

    private static int countPrime(int n){
        int count=n;
        boolean[] isPrime=new boolean[n+1];
        for(int i=2;(i*i)<=n;i++){
            for (int j = i; j * i <= n; j++) {
                if (!isPrime[i * j]) count--;
                isPrime[i * j] = true;
            }
        }
        return count;
    }

}
// [1 - 17]
// 1 2 3 5 7 11 13 17
// 4 6 8 9 10 12 14 15 16

// 2*2 2*3,2*4,2*5,2*6,2*7,2*8
// 3*3,3*4,3*5
// 4*4
