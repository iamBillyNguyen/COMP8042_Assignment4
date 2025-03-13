package com.assignment4.Sorting;

import java.util.*;

public class MergeInsertionSort<T extends Comparable<T>> {
    static int insertCount = 0;
    static int mergeCount = 0;
    InsertionSort<T> insertionSorter = new InsertionSort<T>();
    
    public void insertMergeSort(T[] arr){
        InsertionSort.insertCount = 0;
        mergeCount = 0;
        /*
         * Your code here
         */
        
        // update this classes insertCount when the array has been sorted
        insertCount = InsertionSort.insertCount;
    }

    // Using this to call recursively makes things easier
    private void insertMergeSort(T[] arr, int left, int right) {
        /*
         * Your code here
         */
    }

    //merge two sub-arrays which are beside each other
    private void merge(T[] arr, int leftStart, int center, int rightEnd) {
        mergeCount++;
        
        /*
         * Your code here
         */
    }

    
}
