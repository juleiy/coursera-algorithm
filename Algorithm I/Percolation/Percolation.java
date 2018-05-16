import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation { 
    private boolean[][] grid; 
    private int countOfOpenSites;
    private final WeightedQuickUnionUF unionfound1;
    private final WeightedQuickUnionUF unionfound2;
    private final int virtualTop, virtualBottom;   
    private boolean isPercolate;

    public Percolation(int n)      // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        grid = new boolean[n][n];
        unionfound1 = new WeightedQuickUnionUF(n*n+2);
        virtualTop = n*n;
        virtualBottom = n*n + 1;
        
        unionfound2 = new WeightedQuickUnionUF(n*n+1);
    }
        
    private int getIndex(int row, int col)
    {
        return (row-1)*grid.length + (col-1);
    }
    
    public void open(int row, int col)    // open site (row, col) if it is not open already
    {  
        if (row < 1 || row > grid.length || col < 1 || col > grid.length) {
            throw new java.lang.IllegalArgumentException();
        }
        if (!isOpen(row, col)) {
            // open site
            grid[row-1][col-1] = true;
            countOfOpenSites++;
            int siteIndex = getIndex(row, col);
            
            // connect to virtualTop or virtualBottom
            if (row == 1) {
                unionfound1.union(virtualTop, siteIndex);
                unionfound2.union(virtualTop, siteIndex);
            }
            if (row == grid.length) {
                unionfound1.union(virtualBottom, siteIndex);
            }
            
            // connec to surrounding open sites
            if (col > 1 && isOpen(row, col-1)) {
                unionfound1.union(siteIndex, getIndex(row, col-1));
                unionfound2.union(siteIndex, getIndex(row, col-1));
            }
            if (col < grid.length && isOpen(row, col+1)) {
                unionfound1.union(siteIndex, getIndex(row, col+1));
                unionfound2.union(siteIndex, getIndex(row, col+1));
            }
            if (row > 1 && isOpen(row-1, col)) {
                unionfound1.union(siteIndex, getIndex(row-1, col));
                unionfound2.union(siteIndex, getIndex(row-1, col));
            }        
            if (row < grid.length && isOpen(row+1, col)) {
                unionfound1.union(siteIndex, getIndex(row+1, col));
                unionfound2.union(siteIndex, getIndex(row+1, col));
            }           
        }
    }
    
    public boolean isOpen(int row, int col)  // is site (row, col) open? 
    {
        if (row < 1 || row > grid.length || col < 1 || col > grid.length) {
            throw new java.lang.IllegalArgumentException();
        }        
        return grid[row-1][col-1];          
    }
    
    public boolean isFull(int row, int col)  // is site (row, col) full? an opened site connected to topcd
    {
        if (isOpen(row, col)) {
            return unionfound2.connected(virtualTop, getIndex(row, col));
        }
        else {
            return false;
        }
    }
    
    public int numberOfOpenSites()       // number of open sites
    {
        return countOfOpenSites;
    }
    
    public boolean percolates()              // does the system percolate?
    {
        if (!isPercolate) {
            isPercolate = unionfound1.connected(virtualTop, virtualBottom);
        }
        return isPercolate;
    }    
    
    public static void main(String[] args)   // test client (optional)
    {
        int n = Integer.parseInt(args[0]);
        Percolation p = new Percolation(n);            
        while (!p.percolates()) {
            int row = StdRandom.uniform(n)+1;
            int col = StdRandom.uniform(n)+1;

            if (!p.isOpen(row, col)) 
            {
                p.open(row, col);
            }
        }
        StdOut.println("number of open sites: " + p.numberOfOpenSites());
        StdOut.println("percolates: " + p.percolates());   
        StdOut.println("percolation threshold: " + p.numberOfOpenSites()*1.0/(n*n)); 
    }
}