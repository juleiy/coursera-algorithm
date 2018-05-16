import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args)  
    {  
        int k = Integer.parseInt(args[0]);
        if (k<=0) return;
        
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();        
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }
        
        while (k > 0) {
            StdOut.println(randomizedQueue.dequeue());
            k--;
        }    
    }  
}