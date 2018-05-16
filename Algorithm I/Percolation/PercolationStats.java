import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int t;
    private final double mean;
    private final double stddev;
        
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        t = trials;      
        double[] pThreshold = new double[trials];
        
        for (int i = 0; i < trials; i++)
        {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(n)+1;
                int col = StdRandom.uniform(n)+1;
                if (!p.isOpen(row, col)) 
                {
                    p.open(row, col);
                }
            }            
            double threshold = p.numberOfOpenSites()*1.0/(n*n);
            pThreshold[i] = threshold;
        }
        mean = StdStats.mean(pThreshold);
        stddev = StdStats.stddev(pThreshold);
    }
    
    public double mean()                          // sample mean of percolation threshold
    {
        return mean;
    }
    
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return stddev;
    }
    
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean - (CONFIDENCE_95*stddev/Math.sqrt(t));
    } 
    
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean + (CONFIDENCE_95*stddev/Math.sqrt(t));
    }
        
    public static void main(String[] args)   // test client (optional)
    {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() +", " + ps.confidenceHi() + "]"); 
    }
}