package com.assignment4.EightPuzzle;
import java.util.*;

public class AStar8PuzzleSolver implements EightPuzzleSolver{

    enum solvedStatus{
        SOLVED, NOT_POSSIBLE, NOT_EXECUTED
    }

    private GameBoard initialBoardState;
    private GameBoard goalBoardState;
    BinaryHeap<GameBoardPQEntry> minPQ;
    private Map<GameBoard, GameBoard> predecessors;
    private GameBoardPQEntry current;
    private solvedStatus solved;

    // You need to decide what data structure to use to store the visited nodes, either a 
    // Separate chaining hash table or a quadratic probing hash table.
     private SeparateChainingHashTable visited;
    
    public AStar8PuzzleSolver(GameBoard initial, GameBoard goal){
        this.initialBoardState = initial;
        this.goalBoardState = goal;
        minPQ = new BinaryHeap<>();
        predecessors = new HashMap<>();
        solved = solvedStatus.NOT_EXECUTED;
        // visited = new YourChoiceOfHashTable<>();
    }

    public void printSolution(){
        if(solved == solvedStatus.SOLVED){
            for(GameBoard board : reconstructPath(current.board)){
                System.out.println(board);
            }
        }
    }

    public Iterable<GameBoard> solution(){
        if(solved == solvedStatus.SOLVED){
            return reconstructPath(current.board);
        }
        return new ArrayList<GameBoard>();
    }

    public long numberMoves(){
        if(solved == solvedStatus.SOLVED){
            return reconstructPath(current.board).spliterator().getExactSizeIfKnown() - 1;
        }
        return -1;
    }

    public void solve(){
       /*
       * Your code here 
       * Use the exploreNext method to explore the next node in the frontier until the queue is empty
       */

    }

    //Explore the next node in the frontier according to the priority queue
    private void exploreNext(){
        /*
         * Your code here
         */
    }

    private boolean solutionReached(){
        return current.board.equals(goalBoardState);
    }

    public solvedStatus status(){
        return solved;
    }

    private Iterable<GameBoard> reconstructPath(GameBoard current){
        /*
         * You shouldn't have to modify this method.
         */
        List<GameBoard> path = new ArrayList<>();
        while(current != null){
            path.add(current);
            current = predecessors.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private class GameBoardPQEntry implements Comparable<GameBoardPQEntry>{
        public GameBoard board;
        public int priority;
        public int gScore;
        public int hScore;
        
        public GameBoardPQEntry(GameBoard board, int gScore){
            this.board = board;
            this.gScore = gScore;
            this.hScore = board.hamming();
            this.priority = gScore + hScore;
        }

        @Override
        public int compareTo(GameBoardPQEntry o) {
            return this.priority - o.priority;
        }
    }
}
