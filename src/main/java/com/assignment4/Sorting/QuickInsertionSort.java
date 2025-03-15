package com.assignment4.Sorting;
import java.util.Random;

public class QuickInsertionSort<T extends Comparable<T>> {
    static int insertCount = 0;
    static int pivotCount = 0;
    // If you want to randomly choose pivtots, you can use this (not necessary)
    static Random random = new Random();
    InsertionSort<T> sorter = new InsertionSort<>();


    public void quickInsertionSort(T[] arr){
        InsertionSort.insertCount = 0;
        pivotCount = 0;
        /*
         * Your code here
         */
        insertCount = InsertionSort.insertCount;
    }

    //Using this to call recursively makes things easier
    public void quickInsertionSort(T[] arr, int low, int high){
        /*
         * Your code here
         */
    }

    //returns the index of the pivot element
    //everything to the left of the pivot is less than the pivot and everything to the right is greater than the pivot
    private int partition(T[] arr, int left, int right){
        //choose the pivot element deterministically
        /*
         * Your code here
         */
        return 0;
    }

    //helper method
    private void swap(T[] arr, int i, int j){
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
