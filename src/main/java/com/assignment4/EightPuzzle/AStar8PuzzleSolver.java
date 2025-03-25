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
    // TODO - Billy: Explain why?
     private SeparateChainingHashTable<GameBoard> visited;

    // Bonus 2
    private List<Integer> moves;
    
    public AStar8PuzzleSolver(GameBoard initial, GameBoard goal){
        this.initialBoardState = initial;
        this.goalBoardState = goal;
        minPQ = new BinaryHeap<>();
        predecessors = new HashMap<>();
        solved = solvedStatus.NOT_EXECUTED;
        visited = new SeparateChainingHashTable<>();

        // Bonus 2
        moves = new LinkedList<>();
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

    public void solve() {
       /*
       * Your code here 
       * Use the exploreNext method to explore the next node in the frontier until the queue is empty
       */
        // Starts at initial board state
        GameBoardPQEntry start = new GameBoardPQEntry(initialBoardState, 0);
        minPQ.insert(start);

        // Visits the initial board state
        visited.insert(initialBoardState);

        while(!minPQ.isEmpty()) {
            exploreNext();
            if (current.board.isGoal() && solutionReached()) {
                solved = solvedStatus.SOLVED;
//                Iterable<GameBoard> iterable = reconstructPath(current.board);
                reconstructPath(current.board);
//                System.out.println("visited: "+visited);
                // TODO - Billy: Place where?
                movesToString();
                return;
            }
//            System.out.println("Current board:\n"+current.board.toString());
//            System.out.println("current.gScore "+current.gScore);

            //System.out.println("Hash code: "+current.board.hashCode());

            // Gets all neighbour board states with pre-set INFINITY gScore value
            Iterable<GameBoardPQEntry> iterable = getNeighbours(current);
            // Iterate through all the neighbour board states of the current board state
            for (GameBoardPQEntry neighbour : iterable) {
                //System.out.println("Not Visited: " + !visited.contains(neighbour.board));
                // TODO - Billy: Verify current.gScore < neighbour.gScore . Is it correct?
                // If neighbour board state has not been visited and the its gScore is larger than current's
                if (!visited.contains(neighbour.board) && current.gScore < neighbour.gScore) {
//                    System.out.println("Found neighbour to keep:\n"+neighbour.board.toString());
//                    System.out.println("Visited: "+visited.contains(neighbour.board));
//                    System.out.println("neighbour.gScore "+neighbour.gScore);

                    // Visits neighbour board state to prevent looping
                    visited.insert(neighbour.board);
                    predecessors.put(neighbour.board, current.board);
//                    minPQ.insert(neighbour);
                    // Insert neighbour board state with current board's gScore incremented by 1 to min heap
                    minPQ.insert(new GameBoardPQEntry(neighbour.board, current.gScore + 1));
                }
            }
        }
        // If all board states are explored, but found no solution, it is impossible to solve
        solved = solvedStatus.NOT_POSSIBLE;
    }

    /* TODO - Billy (Ask David): Bonus 1
     * !!! Ask David if "randomly moves a square" has to be
     *          (1) between 0 and one of its neighbours, or
     *          (2) between 0 and any random tiles, or
     *          (3) between any tiles.
     */
    public void solveWithRandom() {
        /*
         * Your code here
         * Use the exploreNext method to explore the next node in the frontier until the queue is empty
         */
        // Starts at initial board state
        Random rand = new Random();
        Boolean hasRandomized = false;
        GameBoardPQEntry start = new GameBoardPQEntry(initialBoardState, 0);
        minPQ.insert(start);

        // Visits the initial board state
        visited.insert(initialBoardState);

        while(!minPQ.isEmpty()) {
            exploreNext();

            // Decide to move tiles randomly if has never randomized
            if (rand.nextBoolean() && !hasRandomized)
            {
                // Ensure feature can only be done once
                hasRandomized = true;

                int tile1 = rand.nextInt(0, initialBoardState.dimension())*initialBoardState.dimension();
                int tile2 = rand.nextInt(1, initialBoardState.dimension())*initialBoardState.dimension();

                // Re-randomize if second tile is the same as first tile
                while (tile1 == tile2) {
                    tile2 = rand.nextInt(0, initialBoardState.dimension())*initialBoardState.dimension();
                }

                System.out.println("Randomly moves tile "+tile1+" to tile "+tile2);
                current.board.swap(tile1, tile2);
                // TODO - Billy: Redo heuristic but preserve predecessor
            }

            if (current.board.isGoal() && solutionReached()) {
                solved = solvedStatus.SOLVED;
//                Iterable<GameBoard> iterable = reconstructPath(current.board);
                reconstructPath(current.board);
//                System.out.println("visited: "+visited);
                // TODO - Billy: Place where?
                movesToString();
                return;
            }
//            System.out.println("Current board:\n"+current.board.toString());
//            System.out.println("current.gScore "+current.gScore);

            //System.out.println("Hash code: "+current.board.hashCode());

            // Gets all neighbour board states with pre-set INFINITY gScore value
            Iterable<GameBoardPQEntry> iterable = getNeighbours(current);
            // Iterate through all the neighbour board states of the current board state
            for (GameBoardPQEntry neighbour : iterable) {
                //System.out.println("Not Visited: " + !visited.contains(neighbour.board));
                // TODO - Billy: Verify current.gScore < neighbour.gScore . Is it correct?
                // If neighbour board state has not been visited and the its gScore is larger than current's
                if (!visited.contains(neighbour.board) && current.gScore < neighbour.gScore) {
//                    System.out.println("Found neighbour to keep:\n"+neighbour.board.toString());
//                    System.out.println("Visited: "+visited.contains(neighbour.board));
//                    System.out.println("neighbour.gScore "+neighbour.gScore);

                    // Visits neighbour board state to prevent looping
                    visited.insert(neighbour.board);
                    predecessors.put(neighbour.board, current.board);
//                    minPQ.insert(neighbour);
                    // Insert neighbour board state with current board's gScore incremented by 1 to min heap
                    minPQ.insert(new GameBoardPQEntry(neighbour.board, current.gScore + 1));
                }
            }
        }
        // If all board states are explored, but found no solution, it is impossible to solve
        solved = solvedStatus.NOT_POSSIBLE;
    }

    // TODO - Billy (Ask David): Bonus 2 - Is this what he wants per the description?
    // "Use a suitable data structure to record the order of the moves. Once the algorithm has run you should
    // be able to call a methods which says ‘move tile 1 to the empty tile, move tile 4 to the empty tile, etc."
    public void movesToString() {
        Iterable<GameBoard> iterable = reconstructPath(current.board);
        GameBoard currentBoard = null;
        for (GameBoard board : iterable) {
            // Save and skip first board since it is the initiate board stage
            if (currentBoard == null) {
                currentBoard = board;
                continue;
            }
            // Save the tile at the coordinate of the previously empty tile
            moves.add(board.getTiles()[currentBoard.getEmptyTileRow()][currentBoard.getEmptyTileColumn()]);
            // Update the current board
            currentBoard = board;
        }

        for (int i = 0; i < moves.size() - 1; i++) {
            System.out.print("move tile " + moves.get(i) + " to the empty tile, ");
        }
        System.out.print("move tile " + moves.getLast() + " to the empty tile.\n");
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
            // TODO Billy: Can change heuristic function here. Explain why?
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
