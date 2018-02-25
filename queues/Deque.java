
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

public class Deque<Item> implements Iterable<Item>{
    private int size = 0;
    private Node head;
    private Node tail;
    
    private class Node{
        Item item;
        Node next;
        Node prev;
        
        Node(Item item){
            this.item = item;
        }
    }
    
    // construct an empty deque
    public Deque(){
        head = new Node(null);  // dummy head
        tail = new Node(null);  // dummy tail
        head.next = tail;
        tail.prev = head;
        size = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty(){
        return size == 0;
    }
    
    // return the number of items on the deque
    public int size(){
        return this.size;
    }
    
    // add the item to the front
    public void addFirst(Item item){
        if (item == null){
            throw new IllegalArgumentException("cannot add a null argument");
        }
        Node node = new Node(item);
        node.next = head.next;
        head.next = node;
        node.next.prev = node;
        node.prev = head;
        size++;
    }
    
    // add the item to the end
    public void addLast(Item item){
        if (item == null){
            throw new IllegalArgumentException("cannot add a null argument");
        }
        Node node = new Node(item);
        node.next = tail;
        node.prev = tail.prev;
        node.prev.next = node;
        tail.prev = node;
        size++;
    }
    
    // remove and return the item from the front
    public Item removeFirst(){
        if(size == 0){
            throw new NoSuchElementException("empty deque");
        }
        Node node = head.next;
        head.next = head.next.next;
        head.next.prev = head;
        size--;
        return node.item;
    }
    
    // remove and return the item from the end
    public Item removeLast(){
        if(size == 0){
            throw new NoSuchElementException("empty deque");
        }
        Node node = tail.prev;
        tail.prev = tail.prev.prev;
        tail.prev.next = tail;
        size--;
        return node.item;
    }
    
    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator(){
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item>{
    
        private Node current = head.next;
        
         @Override
        public boolean hasNext(){
            return (current != tail);
        }
        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }
        @Override
        public Item next(){
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args) {
        Deque<String> q = new Deque<String>();
        String item = "hello";
        q.addFirst(item);
        System.out.println(q.removeFirst());
    }
    
}
