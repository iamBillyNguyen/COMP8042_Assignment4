package com.assignment4.EightPuzzle;

import java.util.*;

public class GameBoard implements Comparable<GameBoard>{ 

    private static class Coordinate{
        private final int row;
        private final int col;

        public Coordinate(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
    
    private final int[][] tiles;
    private final int dimension;
    //Useful to access the empty square in constant time
    private Coordinate emptySquare;

    public GameBoard(int[][] tiles){
        //Assume 0 denotes the empty square
        this.tiles = tiles;
        this.dimension = tiles.length;
    
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(tiles[i][j] == 0){
                    emptySquare = new Coordinate(i, j);
                }
            }
        }

        if (!isValid()){
            tiles = null;
            throw new IllegalArgumentException("Invalid board! It must include consecutive numbers and be square");
        }
    }

    public boolean isValid(){
        if (tiles == null) return false;
        if (tiles.length != tiles[0].length) return false;

        Set<Integer> unseen = new HashSet<>();
        for(int counter = 0; counter < dimension * dimension; counter++){
            unseen.add(counter);
        }
        
        for (int[] row : tiles) {
            for (int tile : row) {
                if (tile < 0){
                    return false;
                }
                unseen.remove(tile);
            }
        }
        return unseen.isEmpty();
    }

    public int[][] getTiles(){
        return tiles;
    }
                                           
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append(tiles[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int dimension(){
        return dimension;
    }

    // number of tiles out of place relative to goal
    public int hamming(){
        /*
         * Your code here
         * The Hamming distance betweeen a board and the goal board is the number of tiles in the wrong position. 
         */
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        /*
         * Your code here
         * The Manhattan distance between a board and the goal board is the sum of the Manhattan distances (sum of the vertical and horizontal distance) from the tiles to their goal positions.
         */

        }
    public int manhattanPlusHamming(){
        /*
         * Your code here
         */
    }

    public boolean isGoal(){
        /*
         * Your code here
         */
    }

    @Override
    public int hashCode(){
        /*
         * Your code here
         */
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        GameBoard other = (GameBoard) obj;
        if (dimension != other.dimension) return false;
        
        /*
         * Your code here
         */
        return true;
    }

    // all neighboring board states
    public Iterable<GameBoard> neighbors(){
        ArrayList<GameBoard> neighbors = new ArrayList<>();
        
        //Swap the empty square with all of its neighbors
        if(emptySquare.row > 0){
            neighbors.add(swap(tiles[emptySquare.row - 1][emptySquare.col], 0));
        }
        if(emptySquare.row < dimension - 1){
            neighbors.add(swap(tiles[emptySquare.row + 1][emptySquare.col], 0));
        }
        if(emptySquare.col > 0){
            neighbors.add(swap(tiles[emptySquare.row][emptySquare.col - 1], 0));
        }
        if(emptySquare.col < dimension - 1){
            neighbors.add(swap(tiles[emptySquare.row][emptySquare.col + 1], 0));
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public GameBoard swap(int tile1Value, int tile2Value){
        //Swap the tiles at the given indices
        if(tile1Value != 0 & tile2Value != 0){
            throw new IllegalArgumentException("One of the tiles must be the empty square!");
        }
        else{
            int[][] newTiles = new int[dimension][dimension];

            for(int i = 0; i < dimension; i++){
                for(int j = 0; j < dimension; j++){
                    if(tiles[i][j] == tile1Value){
                        newTiles[i][j] = tile2Value;
                    }
                    else if(tiles[i][j] == tile2Value){
                        newTiles[i][j] = tile1Value;
                    }
                    else{
                        newTiles[i][j] = tiles[i][j];
                    }
                }
            }
            return new GameBoard(newTiles);
        }
    }
}
