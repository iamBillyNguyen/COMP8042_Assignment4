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

    // Reason: Use SeparateChainingHashTable because it handles collision well
    // and is simple to use for this problem. (More details can be found in pdf)
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
        // Starts at initial board state
        GameBoardPQEntry start = new GameBoardPQEntry(initialBoardState, 0);
        minPQ.insert(start);

        // Visits the initial board state
        visited.insert(initialBoardState);

        while(!minPQ.isEmpty()) {
            // Explore next board state in minPQ
            exploreNext();

            // If current board state is goal state
            if (current.board.isGoal() && solutionReached()) {
                solved = solvedStatus.SOLVED;
                reconstructPath(current.board);
                return;
            }

            // Gets all neighbour board states with pre-set INFINITY gScore value
            Iterable<GameBoardPQEntry> iterable = getNeighbours(current);
            // Iterate through all the neighbour board states of the current board state
            for (GameBoardPQEntry neighbour : iterable) {
                // If neighbour board state has not been visited and its gScore is larger than current's
                if (!visited.contains(neighbour.board) && current.gScore < neighbour.gScore) {
                    // Visits neighbour board state to prevent looping
                    visited.insert(neighbour.board);
                    predecessors.put(neighbour.board, current.board);
                    // Insert neighbour board state with current board's gScore incremented by 1 to min heap
                    minPQ.insert(new GameBoardPQEntry(neighbour.board, current.gScore + 1));
                }
            }
        }
        // If all board states are explored, but found no solution, it is impossible to solve
        solved = solvedStatus.NOT_POSSIBLE;
    }

    // Bonus 1
    public void solveWithRandom() {
        // Starts at initial board state
        Random rand = new Random();
        boolean hasRandomized = false;
        GameBoardPQEntry start = new GameBoardPQEntry(initialBoardState, 0);
        minPQ.insert(start);

        // Visits the initial board state
        visited.insert(initialBoardState);

        while(!minPQ.isEmpty()) {
            exploreNext();
            if (current.board.isGoal() && solutionReached()) {
                solved = solvedStatus.SOLVED;
                reconstructPath(current.board);
                return;
            }
            // Gets all neighbour board states with pre-set INFINITY gScore value
            Iterable<GameBoardPQEntry> iterable = getNeighbours(current);

            // Iterate through all the neighbour board states of the current board state
            for (GameBoardPQEntry neighbour : iterable) {
                // If neighbour board state has not been visited and its gScore is larger than current's
                if (!visited.contains(neighbour.board) && current.gScore < neighbour.gScore) {
                    // Visits neighbour board state to prevent looping
                    visited.insert(neighbour.board);
                    predecessors.put(neighbour.board, current.board);
                    // Insert neighbour board state with current board's gScore incremented by 1 to min heap
                    minPQ.insert(new GameBoardPQEntry(neighbour.board, current.gScore + 1));
                }
            }

            // Decide to move tiles randomly if solution has never been randomized
            if ((rand.nextInt(0 , 5) % 2 == 0) && !hasRandomized)
            {
                // Ensure feature can only be done once
                hasRandomized = true;

                List<GameBoardPQEntry> neighbours = new ArrayList<>();
                iterable.forEach(neighbours::add);
                GameBoardPQEntry neighbourToSwap = neighbours.get(rand.nextInt(0, neighbours.size()));

                System.out.println("Randomly moves tile 0 to neighbour "+neighbourToSwap.board.getTiles()[current.board.getEmptyTileRow()][current.board.getEmptyTileColumn()]+" from board "+current.board.toString());

                // Reset minPQ to add a new path for prioritization, but preserve predecessor and visited
                minPQ = new BinaryHeap<>();

                // Swap tile 0 with one of its neighbour randomly in the current board
                // Add new initial board state starting at the randomized move
                minPQ.insert(new GameBoardPQEntry(neighbourToSwap.board, 0));
                visited.insert(neighbourToSwap.board);
            }
        }
        // If all board states are explored, but found no solution, it is impossible to solve
        solved = solvedStatus.NOT_POSSIBLE;
    }

    // Bonus 2
    public void movesToString() {
        if (status() != solvedStatus.SOLVED) {
            return;
        }

        List<Integer> moves = new LinkedList<>();
        GameBoard currentBoard = null;

        // Iterate through all boards in the shortest path found from current board
        for (GameBoard board : reconstructPath(current.board)) {

            // Assign and skip the first board since it is the initial board stage
            if (currentBoard == null) {
                currentBoard = board;
                continue;
            }

            // Save the tile number at the coordinate of the previously empty tile
            moves.add(board.getTiles()[currentBoard.getEmptyTileRow()][currentBoard.getEmptyTileColumn()]);
            // Move to the next board state
            currentBoard = board;
        }

        // Print each tile movement in order
        for (int i = 0; i < moves.size() - 1; i++) {
            System.out.print("move tile " + moves.get(i) + " to the empty tile, ");
        }
        System.out.print("move tile " + moves.getLast() + " to the empty tile.\n");
    }

    // Explore the next node in the frontier according to the priority queue
    private void exploreNext() {
        try {
            current = minPQ.deleteMin();
        } catch (BinaryHeap.UnderflowException e) {
            System.out.println(e);
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
            // Reason: Use manhattan heuristic function since it is consistent and accurate, more likely to
            // provide an optimal path to the goal state. (More details can be found in pdf)
            this.hScore = board.manhattan();
            this.priority = gScore + hScore;
        }

        @Override
        public int compareTo(GameBoardPQEntry o) {
            return this.priority - o.priority;
        }
    }
}
