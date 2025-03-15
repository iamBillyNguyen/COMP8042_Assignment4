package com.assignment4.EightPuzzle;

public interface EightPuzzleSolver {
    
        public void printSolution();
    
        public Iterable<GameBoard> solution();
    
        public long numberMoves();
    
        public void solve();    
}
