import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.*;

public class Board {
    private final int N;    
    private final int[][] board;
    private int hammingDistance;
    private int manhattanDistance;
    private int zeroX;
    private int zeroY;
        
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        N = blocks.length;
        board = new int[N][N];
        int[][]goal = new int[N][N];
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++) {                
                board[i][j] = blocks[i][j];
                if(board[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }                
                goal[i][j] = i*N + (j+1);
            }
        }
        goal[N-1][N-1] = 0;
        
        hammingDistance = 0;
        manhattanDistance = 0;        
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++) {
                if(board[i][j] !=0 && board[i][j] != goal[i][j]) {
                    hammingDistance++;
                    manhattanDistance += getDistance(i, j);    
                }
            }
        }                   
    } 
                                          // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension n
    {
        return N;
    }
    
    public int hamming()                   // number of blocks out of place
    { 
        return hammingDistance;
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    { 
        return manhattanDistance;        
    }
    
    private int getDistance(int i, int j) {
        int value = this.board[i][j];
        int goalX = (value - 1) / N;
        int goalY = value - 1 - goalX * N;     
        int distance = Math.abs(goalX - i) + Math.abs(goalY - j);
        return distance;
    }
    
    public boolean isGoal()                // is this board the goal board?
    {
        return hammingDistance == 0;
    }
    
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {   
        Board twin = new Board(this.board);
        if(twin.board[0][0] == 0 || twin.board[0][1] == 0)
        {
            int temp = twin.board[1][0];
            twin.board[1][0] = twin.board[1][1];
            twin.board[1][1] = temp;
        }
        else
        {
            int temp = twin.board[0][0];
            twin.board[0][0] = twin.board[0][1];
            twin.board[0][1] = temp;            
        }
        return twin;
    }
    
    public boolean equals(Object y)        // does this board equal y?
    {
        if(this.board == null || y == null || !(y instanceof Board)) {
            return false;
        }
        
        Board that = (Board)y;
        if (that.dimension() != N) return false;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++) {
                if(this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        List<Board> neighborBoards = new ArrayList<Board>();           
        if(zeroX - 1 >= 0) addNeighbors(neighborBoards, zeroX - 1, zeroY);
        if(zeroX + 1 < N) addNeighbors(neighborBoards, zeroX + 1, zeroY);
        if(zeroY - 1 >= 0) addNeighbors(neighborBoards, zeroX, zeroY - 1);
        if(zeroY + 1 < N) addNeighbors(neighborBoards, zeroX, zeroY + 1);  
        return neighborBoards;
    }
    
    private void addNeighbors(List<Board> neighbors, int neighborX, int neighborY)
    {
        if(neighborX < 0 || neighborX >= N || neighborY < 0 || neighborY >= N) {
            return;        
        }
        
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++) {
                copy[i][j] = this.board[i][j];
            }
        }                
        copy[zeroX][zeroY] = copy[neighborX][neighborY];
        copy[neighborX][neighborY] = 0;
        Board neighbor = new Board(copy);
        neighbors.add(neighbor);
    }
    
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++)
        { 
            for (int j = 0; j < N; j++)
            {
                s.append(" " + board[i][j] + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args)  // unit tests (not graded)
    {   
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        StdOut.println(initial);   
        for (Board board : initial.neighbors()) {
            StdOut.println(board);   
        }
        
        StdOut.println(initial.twin());   
    }
}