package com.assignment4.EightPuzzle;

public class BinaryHeap<T extends Comparable<? super T>>
{
    static class UnderflowException extends Exception{
        public UnderflowException(){
            super("Underflow Exception");
        }
    }

    private static final int DEFAULT_CAPACITY = 1000;
    private int currentSize;      
    private T [ ] array; 

    /**
     * Construct the binary heap.
     */
    public BinaryHeap( )
    {
        this( DEFAULT_CAPACITY );
    }

    public BinaryHeap( int capacity )
    {
        currentSize = 0;
        array = (T[]) new Comparable[ capacity + 1 ];
    }
    
    public BinaryHeap( T [ ] items )
    {
            currentSize = items.length;
            array = (T[]) new Comparable[ ( currentSize + 2 ) * 11 / 10 ];

            int i = 1;
            for( T item : items )
                array[ i++ ] = item;
            buildHeap( );
    }

    public void insert( T x )
    {
        if( currentSize == array.length - 1 ){
            enlargeArray( array.length * 2 + 1 );
        }

        array[++currentSize] = x;
        percolateUp(currentSize);
    }

    private void percolateUp( int holeIndex )
    {
        int parentIndex = getParentIndex(holeIndex);

        while(holeIndex > 1 && array[ holeIndex ].compareTo(array[ parentIndex ]) < 0){
            swap(parentIndex, holeIndex);
            holeIndex = parentIndex;
            parentIndex = getParentIndex(holeIndex);
        }
    }

    private void swap(int index1, int index2){
        T temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    
    private void enlargeArray( int newSize )
    {
            T [] old = array;
            array = (T []) new Comparable[ newSize ];
            for( int i = 0; i < old.length; i++ ){
                array[ i ] = old[ i ];        
            }
    }


    
    public T getMin( ) throws UnderflowException
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return array[ 1 ];
    }

    public T deleteMin( ) throws UnderflowException
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        T minItem = getMin( );
        array[ 1 ] = array[ currentSize-- ];
        percolateDown( 1 );
        return minItem;
    }

    private int getParentIndex(int index){
        return index / 2;
    }

    private int getLeftChildIndex(int index){
        return index * 2;
    }

    private int getRightChildIndex(int index){
        return index * 2 + 1;
    }


    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap( )
    {
        for( int i = currentSize / 2; i > 0; i-- ){
            percolateDown( i );
        }
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
    }



    /**
     * Internal method to percolate down in the heap.
     * param hole the index at which to percolate begins.
     */
    private void percolateDown( int holeIndex )
    {
        int leftChildIndex;
        int rightChildIndex;
        T temp = array[ holeIndex ];

        for( ; holeIndex * 2 <= currentSize; holeIndex = leftChildIndex )
        {
            leftChildIndex = getLeftChildIndex(holeIndex);
            rightChildIndex = getRightChildIndex(holeIndex); 

            if( leftChildIndex != currentSize && array[ rightChildIndex ].compareTo( array[ leftChildIndex ] ) < 0 ){
                leftChildIndex++;
            }
            if( array[ leftChildIndex ].compareTo( temp ) < 0 ){
                array[ holeIndex ] = array[ leftChildIndex ];
            }
            else{
                break;
            }
        }
        array[ holeIndex ] = temp;
    }
}
