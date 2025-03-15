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
     private SeparateChainingHashTable<GameBoard> visited;
    
    public AStar8PuzzleSolver(GameBoard initial, GameBoard goal){
        this.initialBoardState = initial;
        this.goalBoardState = goal;
        minPQ = new BinaryHeap<>();
        predecessors = new HashMap<>();
        solved = solvedStatus.NOT_EXECUTED;
        visited = new SeparateChainingHashTable<>();
    }

    public void printSolution(){
        if(solved == solvedStatus.SOLVED){
            for(GameBoard board : reconstructPath(current.board)){
                //System.out.println(board);
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

    public void solve() {
       /*
       * Your code here 
       * Use the exploreNext method to explore the next node in the frontier until the queue is empty
       */
        GameBoardPQEntry start = new GameBoardPQEntry(initialBoardState, 0);
        minPQ.insert(start);

        visited.insert(initialBoardState);

        while(!minPQ.isEmpty()) {
            exploreNext();
            GameBoard currentBroadState = current.board;
            if (currentBroadState.isGoal()) {
                solved = solvedStatus.SOLVED;
//                Iterable<GameBoard> iterable = reconstructPath(currentBroadState);
                return;
            }
            //System.out.println("Current board:\n"+currentBroadState.toString());
            //System.out.println("Hash code: "+currentBroadState.hashCode());

            Iterable<GameBoardPQEntry> iterable = getNeighbours(current);
            for (GameBoardPQEntry neighbour : iterable) {
                //System.out.println("Not Visited: " + !visited.contains(neighbour.board));
                if (!visited.contains(neighbour.board) || current.gScore < neighbour.gScore) {
                    //System.out.println("Found neighbour to keep:\n"+neighbour.board.toString());
                    //System.out.println("Hash code: "+neighbour.board.hashCode());
                    visited.insert(neighbour.board);
                    minPQ.insert(neighbour);
                }
            }
        }
        solved = solvedStatus.NOT_POSSIBLE;
    }

    //Explore the next node in the frontier according to the priority queue
    private void exploreNext() {
        try {
            current = minPQ.deleteMin();
        } catch (BinaryHeap.UnderflowException e) {
            //System.out.println(e);
        }
    }

    private Iterable<GameBoardPQEntry> getNeighbours(GameBoardPQEntry current) {
        Iterable<GameBoard> neighbourBoards = current.board.neighbors();
        ArrayList<GameBoardPQEntry> neighbors = new ArrayList<>();

        for (GameBoard board : neighbourBoards) {
            neighbors.add(new GameBoardPQEntry(board, Integer.MAX_VALUE));
        }
        return neighbors;
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
            // TODO Billy: Can change heuristic function here
            // hamming vs. manhattan vs. both
            this.hScore = board.manhattan();
            this.priority = gScore + hScore;
        }

        @Override
        public int compareTo(GameBoardPQEntry o) {
            return this.priority - o.priority;
        }
    }
}
