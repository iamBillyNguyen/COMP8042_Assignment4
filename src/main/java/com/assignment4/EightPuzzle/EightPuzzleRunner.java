package com.assignment4.EightPuzzle;

public class EightPuzzleRunner {
    public static void main(String[] args) {
        
        // GameBoard initialState = new GameBoard(new int[][] {
        //     {8, 4, 5},
        //     {2, 0, 3},
        //     {7, 1, 6}
        // });

        GameBoard initialState = new GameBoard(new int[][] {
            {2, 3, 0},
            {1, 4, 6},
            {7, 5, 8}
        });


        GameBoard goalState = new GameBoard(new int[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        });

        AStar8PuzzleSolver solver = new AStar8PuzzleSolver(initialState, goalState);
        solver.solve();

        if(solver.status() == AStar8PuzzleSolver.solvedStatus.NOT_POSSIBLE) {
            System.out.println("No solution found.");
        } 
        else {
            System.out.println("Solution found:");
            //This should print out the set of board states that lead to the solution
            solver.printSolution();
        }
    }
}
