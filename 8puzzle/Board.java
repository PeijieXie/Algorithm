/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pkg8.puzzle;

import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


/**
 *
 * @author xiepeijie
 */
public class Board {
    
    private int[][] blocks;
    private int n;
    
    private void swap(int[][] blocks, int i0, int j0, int i1, int j1){
        int temp = blocks[i0][j0];
        blocks[i0][j0] = blocks[i1][j1];
        blocks[i1][j1] = temp;
    }
    
    // construct a board from an n-by-n array of blocks
       // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {  

        n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n ; i++){
            for (int j = 0; j < n; j++){
                this.blocks[i][j] = blocks[i][j];
            }
        }
        
    }
 
                                           
    // board dimension n        
    public int dimension() {
        
        return n;
        
    }
            
    // number of blocks out of place        
    public int hamming() {
        
        int hamDist = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (blocks[i][j] != i * n + j + 1 && blocks[i][j] != 0){
                    hamDist++;
                }
            }
        }
        return hamDist;
        
    }                 
    
    // sum of Manhattan distances between blocks and goal        
    public int manhattan() {
        
        int mamDist = 0;
        int rowDiff;
        int colDiff;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (blocks[i][j] != i * n + j + 1 && blocks[i][j] != 0){
                    rowDiff = Math.abs((blocks[i][j] - 1) / n - i);
                    colDiff = Math.abs((blocks[i][j] - 1)% n - j);
                    mamDist = mamDist + rowDiff + colDiff;
                }
            }
        }
        return mamDist;
        
    }                   
           
    // is this board the goal board?        
    public boolean isGoal() {        
        
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (i == n - 1 && j == n - 1) {
                    break;
                }
                if (this.blocks[i][j] != i * n + j + 1) {
                    return false;
                }
                
            }
        }
        return true;   
        
    }             
            
    // a board that is obtained by exchanging any pair of blocks        
    public Board twin() {
        
        int[][] twinBlocks = new int[n][n];
        int temp;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                twinBlocks[i][j] = blocks[i][j];
            }
        }
        if (twinBlocks[0][0] == 0 || twinBlocks[0][1] == 0) {
             swap(twinBlocks, 1, 0, 1, 1);
        }
        else {
            swap(twinBlocks, 0, 0, 0, 1);
        }
        return new Board(twinBlocks);
        
    }
            
    // does this board equal y?        
    public boolean equals(Object y) {
        
        if (y == this){
            return true;
        }
        
        if (y == null) {
            return false;
        }
                
        if (y.getClass() != this.getClass()) {
            return false;
        }
        
        if (this.dimension() != ((Board) y).dimension()) {
            return false;
        }

        
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (((Board) y).blocks[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }
        
        return true;
       
    }
            
    // all neighboring boards        
    public Iterable<Board> neighbors() {
        
        ArrayList<Board> boardsNeighbor = new ArrayList<>();
        int[][] swapBlocks = new int[n][n];
        
        int i0 = 0;
        int j0 = 0;
        boolean found0 = false;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++){
                if (blocks[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                    found0 = true;
                    break;
                }
            }
            if (found0) {
                break;
            }
        }
        
        if (i0 != 0) {
            for (int i = 0; i < n ; i++){
                for (int j = 0; j < n; j++){
                    swapBlocks[i][j] = blocks[i][j];
                }
            }
            swap(swapBlocks, i0, j0, i0 - 1, j0);
            boardsNeighbor.add(new Board(swapBlocks));
        }
        
        if (i0 != n - 1) {
            for (int i = 0; i < n ; i++){
                for (int j = 0; j < n; j++){
                    swapBlocks[i][j] = blocks[i][j];
                }
            }
            swap(swapBlocks, i0, j0, i0 + 1, j0);
            boardsNeighbor.add(new Board(swapBlocks));
        }
        
        if (j0 != 0) {
            for (int i = 0; i < n ; i++){
                for (int j = 0; j < n; j++){
                    swapBlocks[i][j] = blocks[i][j];
                }
            }
            swap(swapBlocks, i0, j0, i0, j0 - 1);
            boardsNeighbor.add(new Board(swapBlocks));
        }
        
        if (j0 != n - 1) {
            for (int i = 0; i < n ; i++){
                for (int j = 0; j < n; j++){
                    swapBlocks[i][j] = blocks[i][j];
                }
            }
            swap(swapBlocks, i0, j0, i0, j0 + 1);
            boardsNeighbor.add(new Board(swapBlocks));
        }
        
        return boardsNeighbor;
        
    }
            
     // string representation of this board (in the output format specified below)        
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        
        return s.toString();
        
    }              

            
    // unit tests (not graded)        
    public static void main(String[] args) {
        
        In in = new In("puzzle3x3-01.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();        
        Board initial = new Board(blocks);
        StdOut.println(initial.isGoal());
        StdOut.println(initial);
        StdOut.println(initial.twin());
        
        for (Board neighbors : initial.neighbors()) {
                               
            StdOut.println(neighbors);
            StdOut.println(neighbors.manhattan());
            StdOut.println(neighbors.hamming());
            
        }
    } 
    
}
