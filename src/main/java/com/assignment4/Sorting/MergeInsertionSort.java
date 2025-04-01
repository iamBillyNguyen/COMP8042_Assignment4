package com.assignment4.Sorting;

import java.util.*;

public class MergeInsertionSort<T extends Comparable<T>> {
    static int insertCount = 0;
    static int mergeCount = 0;
    InsertionSort<T> insertionSorter = new InsertionSort<T>();
    
    public void insertMergeSort(T[] arr){
        InsertionSort.insertCount = 0;
        mergeCount = 0;
        insertMergeSort(arr, 0, arr.length - 1);
        // update this classes insertCount when the array has been sorted
        insertCount = InsertionSort.insertCount;
    }

    // Using this to call recursively makes things easier
    private void insertMergeSort(T[] arr, int left, int right) {
        // If array's length is less than 10, use insertion sort to sort the sub-list
        if ((right - left) < 10) {
            insertionSorter.insertionSort(arr, left, right);
            return;
        }

        // Recursively split the list into two halves until the length of the sub-list is less than 10
        if (left < right) {
            int mid = left + (right - left )  / 2;
            insertMergeSort(arr, left, mid);
            insertMergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    //merge two sub-arrays which are beside each other
    private void merge(T[] arr, int leftStart, int center, int rightEnd) {
        mergeCount++;

        T[] subLeft, subRight;
        subLeft = Arrays.copyOfRange(arr, leftStart, center + 1);
        subRight = Arrays.copyOfRange(arr, center + 1, rightEnd + 1);

        int i = 0, j = 0, k = leftStart;
        while (i < subLeft.length && j < subRight.length) {
            if (subLeft[i].compareTo(subRight[j]) <= 0) {
                arr[k] = subLeft[i];
                k++;
                i++;
            } else {
                arr[k] = subRight[j];
                k++;
                j++;
            }
        }

        while (j < subRight.length) {
            arr[k] = subRight[j];
            k++;
            j++;
        }


        while (i < subLeft.length) {
            arr[k] = subLeft[i];
            k++;
            i++;
        }
    }
}
