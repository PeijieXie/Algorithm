/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xiepeijie
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item>{
    
    private Item[] q;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue(){
        size = 0;
        q = (Item[])new Object[2];
    }
    
    // is the randomized queue empty?
    public boolean isEmpty(){
        return size == 0;
    }
    
    // return the number of items on the randomized queue
    public int size(){
        return this.size;
    }
    
    //add the item;
    public void enqueue(Item item){
        if (item == null) {
            throw new IllegalArgumentException("Element cannot be null.");
        }
        //array is full
        if (size == q.length){
            resize(q.length * 2);
        }
        q[size] = item;
        size++;
    }
    
    // remove and return a random item
    public Item dequeue(){
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty.");
        }
        int random = StdRandom.uniform(0, size);
        Item selected = q[random];
        q[random] = q[size - 1];
        q[size - 1] = null;
        size--;
        return selected;
    }
    
    // return a random item (but do not remove it)
    public Item sample(){
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty.");
        }
        Item selected = q[StdRandom.uniform(0, size)];
        return selected;
    }
    
    private void resize(int newSize){
        Item[] newq;
        newq = (Item[])new Object[newSize];
        for (int i = 0; i < size; i++){
            newq[i] = q[i];
        }
        q = newq;
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedIterator();
    }
    
    private class RandomizedIterator implements Iterator<Item>{
        private RandomizedQueue copyRandQueue;
        RandomizedIterator(){
            copyRandQueue = new RandomizedQueue();
            copyRandQueue.size = size;
            copyRandQueue.q = q;
            /*Item[] copyq;
            copyq = (Item[])new Object[size];
            for (int i = 0; i < size; i++){
                copyq[i] = q[i];
            }
            copyRandQueue.q = copyq;
            */
        }
        
        @Override
        public boolean hasNext(){
            return copyRandQueue.size > 0;
        }
        
        @Override
        public Item next(){
            if (!hasNext()){
                throw new UnsupportedOperationException();
            }
            Item next = (Item)copyRandQueue.dequeue();
            return(next);
        }
        
        @Override
        public void remove(){
            throw new UnsupportedOperationException("Remove unsupported.");
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomQueue = new RandomizedQueue<Integer>();
        randomQueue.enqueue(0);
        randomQueue.enqueue(1);
        randomQueue.enqueue(2);
        randomQueue.enqueue(3);
        randomQueue.enqueue(4);
        randomQueue.enqueue(5);
        randomQueue.enqueue(6);

        System.out.println("items: " + randomQueue.size);
        System.out.println(randomQueue.dequeue());
        System.out.println(randomQueue.dequeue());
        System.out.println(randomQueue.dequeue());
        System.out.println("items: " + randomQueue.size);
        
        //System.out.println(rq.to`String());

        Iterator it1 = randomQueue.iterator();
        Iterator it2 = randomQueue.iterator();

        while (it1.hasNext()) {
            System.out.print(it1.next());
        }
        System.out.println("\n");
        while (it2.hasNext()) {
            System.out.print(it2.next());
        }
        System.out.println("\n");

    }
    
}
