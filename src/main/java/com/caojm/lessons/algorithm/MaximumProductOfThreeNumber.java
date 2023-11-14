package com.caojm.lessons.algorithm;

import java.util.Arrays;

/**
 * 三个数的最大乘积
 */
public class MaximumProductOfThreeNumber {
    public static void main(String[] args) {
        System.out.println(maximumProduct(new int[]{-3, -2, 3, 4, 5, 6, 7, 8, 9, 10}));
        System.out.println(maximumProduct(new int[]{-11, -1, 3, 4, 5, 6, 7, 8, 9, 10}));

    }
    private static int maximumProduct(int[] nums){
        Arrays.sort(nums);
        int n=nums.length;
        int max = Math.max(nums[0]*nums[1]*nums[n-1],nums[n-1]*nums[n-2]*nums[n-3]);
        return Math.max(max,nums[0]*nums[1]*nums[2]);
    }

    //-3, -2, 3, 4, 5, 6, 7, 8, 9, 10
    //-11, -1, 3, 4, 5, 6, 7, 8, 9, 10
}
