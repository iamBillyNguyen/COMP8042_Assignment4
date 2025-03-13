package com.assignment4.EightPuzzle;
import java.util.LinkedList;
import java.util.List;

public class SeparateChainingHashTable<AnyType>
{
    private static final int DEFAULT_TABLE_SIZE = 101;

    /** The array of Lists. */
    private List<AnyType> [ ] lists; 
    private int currentSize;

    public SeparateChainingHashTable( )
    {
        this( DEFAULT_TABLE_SIZE );
    }


    public SeparateChainingHashTable( int size )
    {
        lists = new LinkedList[size];
        for( int i = 0; i < lists.length; i++ ){
            lists[ i ] = new LinkedList<>( );
        }
    }

    public void insert( AnyType x )
    {
        List<AnyType> whichList = lists[ myhash( x ) ];
        if( !whichList.contains( x ) )
        {
            whichList.add( x );

            // Rehash
            if( ++currentSize > lists.length )
                rehash( );
        }
    }

    public void remove( AnyType x )
    {
        List<AnyType> whichList = lists[ myhash( x ) ];
        if( whichList.contains( x ) )
    {
        whichList.remove( x );
            currentSize--;
    }
    }

    public boolean contains( AnyType x )
    {
        List<AnyType> whichList = lists[ myhash( x ) ];
        return whichList.contains( x );
    }


    public void makeEmpty( )
    {
        for( int i = 0; i < lists.length; i++ )
            lists[ i ].clear( );
        currentSize = 0;    
    }

    /**
     * A hash routine for String objects.
     */
    public static int hash( String key, int tableSize )
    {
        int hashVal = 0;

        for( int i = 0; i < key.length( ); i++ )
            hashVal = 37 * hashVal + key.charAt( i );

        hashVal %= tableSize;
        if( hashVal < 0 )
            hashVal += tableSize;

        return hashVal;
    }

    private void rehash( )
    {
        List<AnyType> [ ]  oldLists = lists;

            // Create new double-sized, empty table
        lists = new List[2 * lists.length];
        for( int j = 0; j < lists.length; j++ )
            lists[ j ] = new LinkedList<>( );

            // Copy table over
        currentSize = 0;
        for( List<AnyType> list : oldLists )
            for( AnyType item : list )
                insert( item );
    }

    private int myhash( AnyType x )
    {
        int hashVal = x.hashCode( );

        hashVal %= lists.length;
        if( hashVal < 0 )
            hashVal += lists.length;

        return hashVal;
    }
    
}

