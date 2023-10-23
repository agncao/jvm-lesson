package com.caojm.lessons.algorithm;

public class SelectedSort {
    public static void main(String[] args) {
        int arr[] = {1, 3, 2, 5, 4};
        sort(arr);
        arr = new int[]{}; // empty array
        sort(arr);
        arr = null; // null array
        sort(arr);
        arr = new int[]{1}; // single element array
        sort(arr);
    }

    private static void sort(int[] arr) {
        if (null == arr || arr.length<1) return;
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp=arr[i];
            arr[i]=arr[minIndex];
            arr[minIndex]=temp;
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
