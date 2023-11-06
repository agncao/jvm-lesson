package com.caojm.lessons.algorithm;

/**
 * 求平方根
 */
public class SquareRoot {
    public static void main(String[] args) {
        System.out.println(square(99));
        System.out.println((int)sq(99));
        System.out.println(square(199));
        System.out.println((int)sq(199));
        System.out.println(square(1199));
        System.out.println((int)sq(1199));
    }

    private static int square(int x){
        int left=0,right=x,mid;
        while (left<=right){
            mid=left+(right-left)/2;
            if (mid*mid==x) return mid;
            else if (mid*mid<x) left=mid+1;
            else right=mid-1;
        }
        return right;
    }

    private static double sq(int x){
        double left=0,right=x,mid;
        while (true){
            mid = (left + right) / 2;
            if(mid * mid == x) return mid;

            if(mid * mid <x) left = mid;
            else right = mid;
        }
    }
}
