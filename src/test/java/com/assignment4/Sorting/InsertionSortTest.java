package com.assignment4.Sorting;
import org.junit.jupiter.api.Test;

import com.assignment4.Sorting.InsertionSort;

import static org.junit.jupiter.api.Assertions.*;

public class InsertionSortTest {

    @Test
    public void testInsertionSort() {
        Float[] arr = {5.4f, 2.3f, 3.1f, 1.2f, 4.8f};
        Float[] expected = {1.2f, 2.3f, 3.1f, 4.8f, 5.4f};
        InsertionSort<Float> sorter = new InsertionSort<>();
        sorter.insertionSort(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);

        //test a partial insertion sort
        Float[] secondArr = {5.4f, 2.3f, 3.1f, 1.2f, 4.8f};
        Float[] secondExpected = {2.3f, 3.1f, 5.4f, 1.2f, 4.8f};
        sorter.insertionSort(secondArr, 0, 2);
        assertArrayEquals(secondExpected, secondArr);        
    }

    @Test
    public void testInsertionSortEmptyArray() {
        Float[] arr = {};
        Float[] expected = {};
        InsertionSort<Float> sorter = new InsertionSort<>();
        sorter.insertionSort(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testInsertionSortSingleElement() {
        Float[] arr = {1.0f};
        Float[] expected = {1.0f};
        InsertionSort<Float> sorter = new InsertionSort<>();
        sorter.insertionSort(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testInsertionSortAlreadySorted() {
        Float[] arr = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        Float[] expected = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        InsertionSort<Float> sorter = new InsertionSort<>();

        sorter.insertionSort(arr, 0, arr.length - 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testBinarySearch() {
        Float[] arr = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        InsertionSort<Float> sorter = new InsertionSort<>();
        int index = sorter.binarySearch(arr, 3.0f, 0, arr.length - 1);
        assertEquals(2, index);
    }

    @Test
    public void testBinarySearchNotFound() {
        Float[] arr = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        InsertionSort<Float> sorter = new InsertionSort<>();
        int index = sorter.binarySearch(arr, 6.0f, 0, arr.length - 1);
        assertEquals(5, index);
    }
}