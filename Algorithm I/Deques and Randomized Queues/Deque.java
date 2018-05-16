/*
 * use linked list implementation
 */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int count;
    private Node first;
    private Node last;
    
    // doubly linked list node
    private class Node {
        Item item;
        Node next;
        Node pre;
        public Node(Item item) {
            this.item = item;
        }
    }
    
    public Deque()                           // construct an empty deque
    {        
    }
    
    public boolean isEmpty()                 // is the deque empty?
    {
        return count == 0;
    }
    
    public int size()                        // return the number of items on the deque
    {
        return count;
    }
    
    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (count == 0) {
            first = new Node(item);
            last = first;
        }
        else {
            Node oldFirst = first;
            first = new Node(item);
            first.next = oldFirst;
            oldFirst.pre = first;
        }
        count++;
    }
    
    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (count == 0) {
            first = new Node(item);
            last = first;
        } 
        else {
            Node oldLast = last;
            last = new Node(item);
            oldLast.next = last;
            last.pre = oldLast;
        }           
        count++;
    }
    
    public Item removeFirst()                // remove and return the item from the front
    {
        if (count == 0) {
            throw new java.util.NoSuchElementException();
        }
        
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.pre = null;
        }
        else {
            last = null;
        }
        count--;
        return item;
    }
    
    public Item removeLast()                 // remove and return the item from the end
    {
        if (count == 0) {
            throw new java.util.NoSuchElementException();
        }
        
        Item item = last.item;
        last = last.pre;
        if (last != null) {
            last.next = null;
        }
        else {
            first = null;
        }
        count--;
        return item;        
    }
    
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new ListIterator();      
    }

    private class ListIterator implements Iterator<Item>
    {
        Node current = first;
        
        public ListIterator() {
        }
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }  
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
    
    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<Integer> deque = new Deque<Integer>();
        
        System.out.print("add first: ");
        for (int i = 0; i < args.length; i++) {
            
            deque.addFirst(Integer.valueOf(args[i]));
        } 
        for (int j : deque) {
            System.out.print(j + " ");
        }
        for (int i = deque.size(); i > 0; i--) {
            deque.removeFirst();
        }             
        
        System.out.print("\n" + "add last: ");
        for (int i = 0; i < args.length; i++) {
            deque.addLast(Integer.valueOf(args[i]));
        } 
        for (int j : deque) {
            System.out.print(j + " ");
        }
        
        System.out.print("\n" + "remove first and last: ");    
        if (!deque.isEmpty()) {
            deque.removeFirst();
        }
        if (!deque.isEmpty()) {
            deque.removeLast(); 
        }
        for (int j : deque)  {
            System.out.print(j + " ");
        }
        System.out.println();
    }
}