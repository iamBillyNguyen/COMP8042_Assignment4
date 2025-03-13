package com.assignment4.Sorting;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import com.assignment4.Sorting.MergeInsertionSort;

public class MergeInsertionSortTest {

    public int getExpectedNumberInsertions(int inputSize){
        double numberOfTimesHalved = Math.ceil(Math.log(inputSize/10.0f)/Math.log(2));
        double partitionSizes = inputSize * 1/Math.pow(2, numberOfTimesHalved);
        int numberPartitions = (int) Math.ceil(inputSize/partitionSizes);
        return numberPartitions;
    }

    public int getExpectedNumberMerges(int inputSize){
        // for each level n of the tree, merge 2^n elements
        double numberOfTimesHalved = Math.ceil(Math.log(inputSize/10)/Math.log(2));
        int mergesCount = 0;
        for(int i = 0; i < numberOfTimesHalved; i++){
            mergesCount += Math.pow(2, i);
        }

        return mergesCount;
    }

    @Test
    public void testInsertMergeSortOne() {
        //Test case 1 - 2 insertionSorts and 1 merge
        Float[] input = {5.5f, 2.2f, 9.9f, 1.1f, 6.6f, 3.3f, 8.8f, 7.7f, 4.4f, 0.0f, 3.2f, 5.6f};
        Float[] expected = {0.0f, 1.1f, 2.2f, 3.2f, 3.3f, 4.4f, 5.5f, 5.6f, 6.6f, 7.7f, 8.8f, 9.9f};
        MergeInsertionSort<Float> sorter = new MergeInsertionSort<>();
        sorter.insertMergeSort(input);
        assertArrayEquals(expected, input);

        assertEquals(2, MergeInsertionSort.insertCount);
        assertEquals(1, MergeInsertionSort.mergeCount);
    }

    @Test
    public void testInsertMergeSortTwo() {

        //Test case 2 - array length 25
        //This will be broken down into:
        // merge(merge([6 things], [6 things]), merge([6 things], [7 things]))
        // so 4 insertion sorts for the 4 arrys of size less than 10 and 3 merges.
        Float[] input = new Float[]{5.5f, 2.2f, 9.9f, 1.1f, 6.6f, 3.3f, 8.8f, 7.7f, 4.4f, 0.0f, 3.2f, 5.6f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 100.0f, 99.0f, 200.0f, 220.0f};
        Float[] expected = new Float[]{0.0f, 1.0f, 1.1f, 2.0f, 2.2f, 3.0f, 3.2f, 3.3f, 4.0f, 4.4f, 5.0f, 5.5f, 5.6f, 6.0f, 6.6f, 7.0f, 7.7f, 8.0f, 8.8f, 9.0f, 9.9f,  99.0f, 100.0f, 200.0f, 220.0f};
        MergeInsertionSort<Float> sorter = new MergeInsertionSort<>();
        sorter.insertMergeSort(input);
        assertArrayEquals(expected, input);

        assertEquals(4, MergeInsertionSort.insertCount);
        assertEquals(3, MergeInsertionSort.mergeCount);
    }
    @Test
    public void testInsertMergeSortThree() {
        //Test case 3 - just one insertionSort
        Float[] input = new Float[] {5.5f, 2.2f, 9.9f, 1.1f, 6.6f, 3.3f, 8.8f, 7.7f};
        Float[] expected = new Float[] {1.1f, 2.2f, 3.3f, 5.5f, 6.6f, 7.7f, 8.8f, 9.9f};
        MergeInsertionSort<Float> sorter = new MergeInsertionSort<>();
        sorter.insertMergeSort(input);
        assertArrayEquals(expected, input);

    }

    @Test
    public void testInsertMergeSortBigArray() {    
        //Test case 3
        Float[] randomInput = new Float[100];
        for(int i = 0; i < randomInput.length; i++){
            randomInput[i] = (float) Math.random();
        }        
        Float[] expectedArray = randomInput.clone();
        
        Arrays.sort(expectedArray);
        MergeInsertionSort<Float> sorter = new MergeInsertionSort<>();
        sorter.insertMergeSort(randomInput);

        assertArrayEquals(expectedArray, randomInput);
        assertEquals(getExpectedNumberInsertions(randomInput.length), MergeInsertionSort.insertCount);
        assertEquals(getExpectedNumberMerges(randomInput.length), MergeInsertionSort.mergeCount);
    }
}