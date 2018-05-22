/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pkg8.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;



/**
 *
 * @author xiepeijie
 */
public class Solver {
    
    private final MinPQ<SearchNode> pq;
    private final MinPQ<SearchNode> pqTwin;
    private final int n;
    private final SearchNode initialNode;
    private final SearchNode initialNodeTwin;
   
    private class SearchNode implements Comparable<SearchNode> {
        
        private final Board board;
        private final int moves;
        private final SearchNode preNode;
        private final int priority;
        
        public SearchNode(Board board, int moves, SearchNode preNode) {
            this.board = board;
            this.moves = moves;
            this.preNode = preNode;
            this.priority = this.moves + this.board.manhattan();
        }
        
        public int priority() {
            return priority;
        }
        
        @Override
        public int compareTo(SearchNode that) {
            return this.priority() - that.priority();
        }
        
    }
    // find a solution to the initial board
    public Solver(Board initial) {
        
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        
        n = initial.dimension();
        pq = new MinPQ();
        pqTwin = new MinPQ();
        initialNode = new SearchNode(initial, 0, null);
        initialNodeTwin = new SearchNode(initial.twin(), 0, null);
        pq.insert(initialNode);
        pqTwin.insert(initialNodeTwin);
        
        SearchNode del;
        SearchNode delTwin;      
        
        while (!pq.min().board.isGoal() && !pqTwin.min().board.isGoal()) { 
                        
            del = pq.delMin();
            delTwin = pqTwin.delMin();   
                        
            for (Board neighbors : del.board.neighbors()) {
                
                if (del.moves == 0) {
                    pq.insert(new SearchNode(neighbors, del.moves + 1, del));
                }               
                else if (!neighbors.equals(del.preNode.board)) {
                    pq.insert(new SearchNode(neighbors, del.moves + 1, del));
                }
            }
            
            for (Board neighbors : delTwin.board.neighbors()) {
                
                if (delTwin.moves == 0) {
                    pqTwin.insert(new SearchNode(neighbors, del.moves + 1, del));
                }
                else if (!neighbors.equals(delTwin.preNode.board)) {
                    pqTwin.insert(new SearchNode(neighbors, delTwin.moves + 1, delTwin));
                }                
            }
            
        }

    } 
           
    // is the initial board solvable?        
    public boolean isSolvable() {
        
        return pq.min().board.isGoal();
        
    } 
            
   
    // return min number of moves to solve initial board; -1 if no solution
    public int moves() {
        
        if (!isSolvable()) {
            return -1;
        }
        else {
            return pq.min().moves;
        }
        
    }        
           
    // return an Iterable of board positions in solution        
    public Iterable<Board> solution() {
        
        Stack<Board> solution = new Stack<Board>();
        
        if (!isSolvable()) {            
            return null;
        }
        
        else {            
            SearchNode current;
            current = pq.min();
            while (current != null){              
                solution.push(current.board);
                current = current.preNode;          
            }           
        }
        
        return solution;
        
    }
    
    public static void main(String[] args) {

        // create initial board from file
        //In in = new In("puzzle3x3-01.txt");
        In in = new In("puzzle30.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        StdOut.println(solver.isSolvable());
        StdOut.println(solver.moves());
        StdOut.println(solver.isSolvable());
        StdOut.println(solver.solution());
        StdOut.println(solver.solution());

       //print solution to standard output
   //    if (!solver.isSolvable())
   //        StdOut.println("No solution possible");
   //    else {
   //        StdOut.println("Minimum number of moves = " + solver.moves());
   //        for (Board board : solver.solution())
   //            StdOut.println(board);
   //    }
   }
    
}
