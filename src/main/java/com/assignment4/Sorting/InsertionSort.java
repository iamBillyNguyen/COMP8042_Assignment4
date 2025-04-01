package com.assignment4.Sorting;

import java.util.Arrays;

public class InsertionSort<T extends Comparable<T>> {
    static int insertCount = 0;

    // Given a start and end index, sort the array from the start index to the end index
    public void insertionSort(T[] arr, int startIndex, int endIndex) {
        insertCount++;
        for (int i = startIndex; i <= endIndex; ++i) {
            T key = arr[i];
            int j = binarySearch(arr, key, 0, i - 1);

            // Shift elements to make space for key
            for (int k = i - 1; k >= j; k--) {
                arr[k + 1] = arr[k];
            }
            arr[j] = key;
        }
        System.out.println("insertCount: "+insertCount);
    }


    //Binary search already provided
    public int binarySearch(T[] arr, T target, int left, int right) {
        if (right >= left) {
            int mid = left + (right - left) / 2;
            if (arr[mid].compareTo(target) == 0) {
                return mid;
            }
            if (arr[mid].compareTo(target) > 0) {
                return binarySearch(arr, target, left, mid - 1);
            }
            return binarySearch(arr, target, mid + 1, right);
        }
        return left;
    }
}
