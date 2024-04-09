package com.caojm.lessons.algorithm;

/**
 * 有序数组合并
 */
public class SortedArrayMerge {
    public static void main(String[] args) {
        int[] nums1=new int[]{1,2,3,0,0,0};
        int[] nums2=new int[]{2,5,6};
        SortedArrayMerge obj=new SortedArrayMerge();
       obj.merge(nums1,3,nums2,3);
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] nums1Copy=new int[m+n];
        int pm=0,pn=0,p=0;
        while (pm<m || pn<n){
            if(pm==m && pn<n){
                nums1Copy[p++]=nums2[pn++];
            }else if(pn==n && pm<m) {
                nums1Copy[p++] = nums1[pm++];
            } else if (pm==m && pn==n) {
                break;
            } else {
                nums1Copy[p++] = nums1[pm] < nums2[pn] ? nums1[pm++] : nums2[pn++];
            }

        }
        for (int i : nums1Copy) {
            System.out.print(i+" ");
        }
    }
}
