package com.assignment4.Sorting;
import java.util.Random;

public class QuickInsertionSort<T extends Comparable<T>> {
    static int insertCount = 0;
    static int pivotCount = 0;
    // If you want to randomly choose pivots, you can use this (not necessary)
    static Random random = new Random();
    InsertionSort<T> sorter = new InsertionSort<>();


    public void quickInsertionSort(T[] arr){
        InsertionSort.insertCount = 0;
        pivotCount = 0;
        quickInsertionSort(arr, 0, arr.length - 1);
        insertCount = InsertionSort.insertCount;
    }

    //Using this to call recursively makes things easier
    public void quickInsertionSort(T[] arr, int low, int high){
        if (low < high) {
            // If array's length is less than 20, use insertion sort to sort the sub-list
            if ((high - low + 1) < 20) {
                sorter.insertionSort(arr, low, high);
            } else {
                int pivot = partition(arr, low, high);
                quickInsertionSort(arr, low, pivot - 1);
                quickInsertionSort(arr, pivot + 1, high);
            }
        }
    }

    //returns the index of the pivot element
    //everything to the left of the pivot is less than the pivot and everything to the right is greater than the pivot
    private int partition(T[] arr, int left, int right){
        pivotCount++;
        //choose the pivot element deterministically
        // Reason: Random pivot selection reduces the probability of encountering the worst-case scenarios O(n^2)
        // that first and last pivot selections are susceptible to. It consistently takes O(nlog(n)) time complexity
        int pivotIndex = random.nextInt(right - left + 1) + left; // Randomize pivot index

        // Swap the pivot with the last element
        swap(arr, pivotIndex, right);
        T pivot = arr[right]; // Choose a random-indexed element as the pivot
        int i = left - 1; // Index of smaller element

        for (int j = left; j < right; j++) {
            // If the current element is smaller than or equal to the pivot
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                // Swap current element that is smaller than pivot with the element at the unsorted furthest left index
                swap(arr, i, j);
            }
        }
        // Swap pivot back to its correct position
        swap(arr, i+1, right);
        return i+1;
    }

    //helper method
    private void swap(T[] arr, int i, int j){
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
