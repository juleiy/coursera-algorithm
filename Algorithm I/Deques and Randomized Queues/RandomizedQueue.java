/*
 * use dynamic array implementation
 */

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count = 0;
    private Item[] items;
      
    public RandomizedQueue()                 // construct an empty randomized queue
    {
        items = (Item[]) new Object[1];
    }
    
    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return count == 0;
    }
    
    public int size()                        // return the number of items on the randomized queue
    {
        return count;
    }
    
    public void enqueue(Item item)           // add the item
    {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        if (count == items.length) {
            resize(2*items.length);     
        }
        items[count] = item;
        count++;
    }    
    
    public Item dequeue()                    // remove and return a random item
    {
        if (count == 0) {
            throw new java.util.NoSuchElementException();
        }
        
        int random = StdRandom.uniform(count); // returns a random integer uniformly in [0, n).
        Item item = items[random];   
        items[random] = items[--count];
        items[count] = null;
        if (count > 0 && count == items.length/4) {
            resize(items.length/2);
        }        
        return item;
    }
    
    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++)
        {
            copy[i] = items[i];
        }     
        items = copy;
    }
    
    public Item sample()                     // return a random item (but do not remove it)
    {
        if (count == 0) {
            throw new java.util.NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(count); // returns a random integer uniformly in [0, n).
        return items[randomIndex];        
    }
    
    // shuffle a deck of cards or randomize a given array
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomIterator();
    }
    
    private class RandomIterator implements Iterator<Item>
    {     
        private int sequence = 0;
        private int[] shuffledIndex = new int[count];        
        public RandomIterator() {  
            for (int i = 0; i < count; i++) {
                shuffledIndex[i] = i;
            }
            StdRandom.shuffle(shuffledIndex);
        }
               
        public boolean hasNext() {
            return sequence < count;
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return items[shuffledIndex[sequence++]];
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
    
    public static void main(String[] args)   // unit testing (optional)
    {       
        int[] data = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            data[i] = Integer.parseInt(args[i]);
        }
        
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        
        System.out.print("enqueue: ");
        for (int i = 0; i < data.length; i++) {
            randomizedQueue.enqueue(data[i]);
            System.out.print(data[i] + " ");
        }
        
        System.out.print("\n" + "iterate: ");
        for (int j : randomizedQueue) {
            System.out.print(j + " ");
        }     
        
        System.out.print("\n" +"dequeue: ");
        for (int i = 0; i < data.length; i++) {
            int r = randomizedQueue.dequeue();
            System.out.print(r + " ");
        }     
        
        System.out.print("\n" + "iterate: ");
        for (int j : randomizedQueue) {
            System.out.print(j + " ");
        }           
    }
}