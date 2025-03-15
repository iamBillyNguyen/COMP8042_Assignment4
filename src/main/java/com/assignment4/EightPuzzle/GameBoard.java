package com.assignment4.EightPuzzle;

import java.util.*;

public class GameBoard implements Comparable<GameBoard>{

    @Override
    public int compareTo(GameBoard other) {
        if (dimension() != other.dimension()) {
            return dimension() - other.dimension(); // Not equal in terms of dimensions
        }

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                // If the current coordinate is different from goal's, tile is in the wrong position
                if (other.tiles[i][j] != tiles[i][j]) {
                    return -1; // Not equal in terms of tile values
                }
            }
        }

        return 0; // Boards are equal
    }

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
    private HashMap<Integer, Coordinate> goalState;
    private GameBoard goalBoardState;

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
        sb.append("\n");
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
         * The Hamming distance between a board and the goal board is the number of tiles in the wrong position.
         */
        int[][] board = getTiles();
        int hamming = 0;
        createGoalState();

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int tile = board[i][j];
                Coordinate currentCoordinate = goalState.get(tile);
                // If the current coordinate is different from goal's, tile is in the wrong position
                if (currentCoordinate.row != i || currentCoordinate.col != j) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    /**
     * Creates a goal state Hash Map and GameBoard object containing correct coordinate for each tile.
     * This function also adopts singleton behaviour, which avoids re-creating objects if object already exists
     */
    public void createGoalState() {
        // If goalState has already been created,
        // return which avoids re-creating the goal state again
        if (goalState != null) {
            return;
        }

        goalState = new HashMap<>();
        int[][] board = new int[dimension][dimension];
        int tileNum = 1;

        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++) {
                if (tileNum >= (dimension * dimension)) {
                    tileNum = 0;
                }

                goalState.put(tileNum, new Coordinate(i, j));
                board[i][j] = tileNum;
                tileNum++;
            }
        }
        goalBoardState = new GameBoard(board);
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int[][] board = getTiles();
        int manhattan = 0;

        // Creates a goal state based on the dimension of the current state
        // to retrieve the difference in x and y coordinate
        createGoalState();

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int tile = board[i][j];
                Coordinate currentCoordinate = goalState.get(tile);
                // If the current coordinate is different from goal's, tile is in the wrong position
                if (currentCoordinate.row != i || currentCoordinate.col != j) {
                    manhattan += Math.abs(i - goalState.get(tile).row) + Math.abs(j - goalState.get(tile).col);
                }
            }
        }
        return manhattan;
    }

    public int manhattanPlusHamming(){
        return hamming() + manhattan();
    }

    public boolean isGoal(){
        createGoalState();
        return equals(goalBoardState);
    }

    @Override
    public int hashCode(){
        return Arrays.deepHashCode(tiles);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        GameBoard other = (GameBoard) obj;
        if (dimension != other.dimension) return false;

        int boardComparison = this.compareTo(other);
        if (boardComparison != 0) return false;
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
