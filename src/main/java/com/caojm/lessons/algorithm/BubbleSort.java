package com.caojm.lessons.algorithm;

public class BubbleSort {
    public static void main(String[] args) {
        int arr[] = { 3, 2, 5, 4,1};
        sort(arr);
        arr = new int[]{}; // empty array
        sort(arr);
        arr = null; // null array
        sort(arr);
        arr = new int[]{1}; // single element array
        sort(arr);
    }
    private static void sort(int[] arr){
        if (arr == null || arr.length < 1) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            for(int j=0;j<arr.length-i-1;j++){
                if(arr[j]>arr[j+1]){
                    int temp=arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=temp;
                }
            }
        }
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
}
