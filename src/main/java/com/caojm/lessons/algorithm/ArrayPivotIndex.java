package com.caojm.lessons.algorithm;

/**
 * 寻找数组的中心索引
 */
public class ArrayPivotIndex {
    public static void main(String[] args) {
        System.out.println(pivotIndex(new int[]{1,7,3,6,5,6}));
        System.out.println(pivotIndex(new int[]{1,2,3}));
        System.out.println(pivotIndex(new int[]{2,1,-1}));
        System.out.println(pivotIndex(new int[]{2,1,-1,1}));
        System.out.println(pivotIndex(new int[]{2,1,-1,1,1}));
    }

    private static int pivotIndex(int[] nums){
        int sum=0;
        for (int num : nums) {
            sum+=num;
        }

        int leftSum=0;
        for (int i = 0; i < nums.length; i++) {
            leftSum += nums[i];
            if(leftSum == sum - leftSum + nums[i]) return i;
        }
        return -1;
    }
}
