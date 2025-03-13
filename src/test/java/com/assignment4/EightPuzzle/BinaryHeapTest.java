package com.assignment4.EightPuzzle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BinaryHeapTest {

    @Test
    public void testInsertAndGetMin() throws Exception {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.insert(10);
        heap.insert(5);
        heap.insert(20);
        heap.insert(30);
        heap.insert(35);
        heap.insert(7);
        heap.insert(6);
        assertEquals(5, heap.deleteMin());
        assertEquals(6, heap.deleteMin());
        assertEquals(7, heap.deleteMin());
        heap.insert(4);
        assertEquals(4, heap.deleteMin());
        assertEquals(10, heap.deleteMin());
        assertEquals(20, heap.deleteMin());
        assertEquals(30, heap.deleteMin());
        assertEquals(35, heap.deleteMin());
    }

    @Test
    public void testDeleteMin() throws Exception {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.insert(10);
        heap.insert(5);
        heap.insert(20);
        assertEquals(5, heap.deleteMin());
        assertEquals(10, heap.getMin());
    }

    @Test
    public void testIsEmpty() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        assertTrue(heap.isEmpty());
        heap.insert(10);
        assertFalse(heap.isEmpty());
    }

    @Test
    public void testMakeEmpty() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.insert(10);
        heap.insert(5);
        heap.makeEmpty();
        assertTrue(heap.isEmpty());
    }


    @Test
    public void testBuildHeap() throws Exception {
        Integer[] items = {10, 5, 20, 2, 8};
        BinaryHeap<Integer> heap = new BinaryHeap<>(items);
        assertEquals(2, heap.getMin());
    }
}