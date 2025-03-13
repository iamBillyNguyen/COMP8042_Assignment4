package com.assignment4.EightPuzzle;

import org.junit.jupiter.api.Test;

import com.assignment4.EightPuzzle.GameBoard;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {
    
    @Test 
    public void boardIsValid(){
        int[][] badTiles = new int[][] {
            {8, 4, 5},
            {2, 0, 6},
            {7, 1, 6}
        };

        try{
            new GameBoard(badTiles);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid board! It must include consecutive numbers and be square", e.getMessage());
        }
    }

    @Test
    public void testToString() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        GameBoard board = new GameBoard(tiles);
        String expected = "\n1 2 3 \n4 5 6 \n7 8 0 \n";
        assertEquals(expected, board.toString());
    }

    @Test
    public void testDimension() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        GameBoard board = new GameBoard(tiles);
        assertEquals(3, board.dimension());
    }

    @Test
    public void testHamming() {
        
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
        };
        GameBoard board = new GameBoard(tiles);
        assertEquals(2, board.hamming());
    }

    @Test
    public void testManhattan() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
        };
        GameBoard board = new GameBoard(tiles);
        assertEquals(2, board.manhattan());
    }

    @Test
    public void testIsGoal() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        GameBoard board = new GameBoard(tiles);
        assertTrue(board.isGoal());
    }

    @Test
    public void testEquals() {
        int[][] tiles1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        int[][] tiles2 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        int[][] tiles3 = {
            {3, 2, 1},
            {4, 8, 6},
            {7, 5, 0}
        };
        GameBoard board1 = new GameBoard(tiles1);
        GameBoard board2 = new GameBoard(tiles2);
        GameBoard board3 = new GameBoard(tiles3);
        assertEquals(board1, board2);
        assertNotEquals(board1, board3);
    }

    @Test
    public void testNeighbors() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 0, 8}
        };
        GameBoard board = new GameBoard(tiles);
        Iterable<GameBoard> neighbors = board.neighbors();
        int count = 0;
        for (GameBoard neighbor : neighbors) {
            count++;
        }
        assertEquals(3, count);

        int[][] otherTiles = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };
        board = new GameBoard(otherTiles);
        neighbors = board.neighbors();
        count = 0;
        for (GameBoard neighbor : neighbors) {
            count++;
        }
        assertEquals(4, count);

        int[][] cornerTiles = {
            {0, 2, 3},
            {4, 1, 6},
            {7, 5, 8}
        };
        board = new GameBoard(cornerTiles);
        neighbors = board.neighbors();
        count = 0;
        for (GameBoard neighbor : neighbors) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    public void testSwap() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 0, 8}
        };
        GameBoard board = new GameBoard(tiles);
        GameBoard swappedBoard = board.swap(8, 0);
        int[][] expectedTiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        assertArrayEquals(expectedTiles, swappedBoard.getTiles());

        try{
            board.swap(1, 0);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid tile value", e.getMessage());
        }
    }

    @Test
    public void testHashCode(){
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 0, 8}
        };

        int[][] tiles2 = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };
        GameBoard board = new GameBoard(tiles);
        GameBoard board1 = new GameBoard(tiles);
        GameBoard board2 = new GameBoard(tiles2);
        assertEquals(board.hashCode(), board1.hashCode());
        assertNotEquals(board.hashCode(), board2.hashCode());
    }
}