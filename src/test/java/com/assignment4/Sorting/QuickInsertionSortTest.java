package com.assignment4.Sorting;
import org.junit.jupiter.api.Test;

import com.assignment4.Sorting.QuickInsertionSort;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;



public class QuickInsertionSortTest {

    @Test
    public void testQuickInsertionSortSmallArray() {
        QuickInsertionSort<Float> sorter = new QuickInsertionSort<>();
        Float[] arr = {5.4f, 3.3f, 2.2f, 1.1f, 4.4f};
        Float[] expected = {1.1f, 2.2f, 3.3f, 4.4f, 5.4f};
        sorter.quickInsertionSort(arr);
        assertArrayEquals(expected, arr);
        assertEquals(1, QuickInsertionSort.insertCount);
        assertEquals(0, QuickInsertionSort.pivotCount);
    }

    @Test
    public void testQuickInsertionSortLargeArray() {
        QuickInsertionSort<Float> sorter = new QuickInsertionSort<>();

        Random r = new Random(); 
        r.setSeed(0);
        Float[] randomInput = new Float[10000];
        for(int i = 0; i < randomInput.length; i++){
            randomInput[i] = r.nextFloat();
        }        
        Float[] duplicate = Arrays.copyOf(randomInput, randomInput.length);
        Arrays.sort(duplicate);
        sorter.quickInsertionSort(randomInput);
        
        assertArrayEquals(duplicate, randomInput);
        assertEquals(908, QuickInsertionSort.insertCount);
        assertEquals(907, QuickInsertionSort.pivotCount);
    }

}