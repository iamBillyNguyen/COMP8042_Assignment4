package com.assignment4.EightPuzzle;
import java.util.*;

public class AStar8PuzzleSolver {

    enum solvedStatus{
        SOLVED, NOT_POSSIBLE, NOT_EXECUTED
    }

    private GameBoard initialBoardState;
    private GameBoard goalBoardState;
    BinaryHeap<GameBoard> minPQ;
    private Map<GameBoard, GameBoard> predecessors;
    private GameBoard current;
    private solvedStatus solved;

    // You need to decide what data structure to use to store the visited nodes, either a 
    // Separate chaining hash table or a quadratic probing hash table.
    // private YourChoiceOfHashTable visited;
    
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
            for(GameBoard board : reconstructPath(current)){
                System.out.println(board);
            }
        }
    }

    public Iterable<GameBoard> solution(){
        if(solved == solvedStatus.SOLVED){
            return reconstructPath(current);
        }
        return new ArrayList<GameBoard>();
    }

    public long numberMoves(){
        if(solved == solvedStatus.SOLVED){
            return reconstructPath(current).spliterator().getExactSizeIfKnown() - 1;
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
        return current.equals(goalBoardState);
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
}
