package com.assignment4.EightPuzzle;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.assignment4.EightPuzzle.AStar8PuzzleSolver;
import com.assignment4.EightPuzzle.GameBoard;

import java.util.*;



public class AStar8PuzzleSolverTest {

    private GameBoard initialBoard;
    private GameBoard goalBoard;
    private AStar8PuzzleSolver solver;

    @BeforeEach
    public void setUp() {
        initialBoard = new GameBoard(new int[][] {
            {1, 2, 0},
            {4, 5, 3},
            {7, 8, 6}
        });

        goalBoard = new GameBoard(new int[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        });

        solver = new AStar8PuzzleSolver(initialBoard, goalBoard);
    }

    @Test
    public void testSolutionFound() {
        solver.solve();
        Iterable<GameBoard> solution = solver.solution();
        assertEquals(AStar8PuzzleSolver.solvedStatus.SOLVED, solver.status());
        assertNotNull(solution);
        List<GameBoard> solutionList = new ArrayList<>();
        solution.forEach(solutionList::add);
        assertFalse(solutionList.isEmpty());
        assertEquals(goalBoard, solutionList.get(solutionList.size() - 1));
    }

    @Test
    public void testNoSolution() {
        initialBoard = new GameBoard(new int[][] {
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
        });

        solver = new AStar8PuzzleSolver(initialBoard, goalBoard);
        assertEquals(solver.status(), AStar8PuzzleSolver.solvedStatus.NOT_EXECUTED);
        solver.solve();
        assertEquals(solver.status(), AStar8PuzzleSolver.solvedStatus.NOT_POSSIBLE);
    }
}