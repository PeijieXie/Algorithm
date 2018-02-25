/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author xiepeijie
 */
public class Permutation {
    public static void main(String[] args){  
        int k = Integer.valueOf(args[0]);  
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();  
            q.enqueue(item);  
        }  
        while (k > 0){  
            StdOut.println(q.dequeue());  
            k--;
        }
    }
}
