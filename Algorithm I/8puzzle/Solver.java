import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.*;

public class Solver {
    private SearchNode searchNode;
    private boolean solvable;

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {  
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();                
        searchNode = new SearchNode(initial, 0, null);
        minPQ.insert(searchNode);
        
        MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>();
        minPQTwin.insert(new SearchNode(initial.twin(), 0, null));        
               
        while(!minPQ.isEmpty() || !minPQTwin.isEmpty())
        {            
            SearchNode curSearchTwin = minPQTwin.delMin();
            if (curSearchTwin.board.isGoal()) {
                solvable = false;
                break;
            }
            else {
                for(Board neighbor : curSearchTwin.board.neighbors())
                {
                    if(curSearchTwin.predecessor == null || !neighbor.equals(curSearchTwin.predecessor.board)) {
                        SearchNode newSearchNode = new SearchNode(neighbor, curSearchTwin.getMoves() + 1, curSearchTwin);
                        minPQTwin.insert(newSearchNode);                        
                    }                                                
                }
            }    
            
            SearchNode curSearch = minPQ.delMin();
            if(curSearch.board.isGoal()) {
                solvable = true;
                searchNode = curSearch;
                break;
            }
            else {
                for(Board neighbor : curSearch.board.neighbors())
                {
                    if(curSearch.predecessor == null || !neighbor.equals(curSearch.predecessor.board)) {
                        SearchNode newSearchNode = new SearchNode(neighbor, curSearch.getMoves() + 1, curSearch);
                        minPQ.insert(newSearchNode);                        
                    }                                                
                }
            }
        }
    }
                      
    public boolean isSolvable()            // is the initial board solvable?
    {
        return solvable;
    }
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (isSolvable())
        {
            return searchNode.getMoves();
        }
        else {
            return -1;
        }
    }
    
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (isSolvable())
        {
            List<Board> sequence = new ArrayList<Board>();
            SearchNode cur = searchNode;
            while(cur != null)
            {
                sequence.add(cur.board);
                cur = cur.predecessor;                
            }
            
            Collections.reverse(sequence);
            return sequence;
        }
        else {
            return null;
        }
    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }        
    }    
    
    private class SearchNode implements Comparable<SearchNode>
    {
        private final Board board;
        private final int moves;
        private final SearchNode predecessor;   
        private final int manhattanPriority;
        
        public SearchNode(Board board, int moves, SearchNode predecessor) {
            this.board = board;
            this.moves = moves;
            this.predecessor = predecessor;
            manhattanPriority = board.manhattan() + moves;
        }
        
        public int getMoves() {
            return moves;
        }
        
        public int compareTo(SearchNode that) {
            int thisManhattanPriority = this.manhattanPriority;
            int thatManhattanPriority = that.manhattanPriority;
            
            int diff = thisManhattanPriority - thatManhattanPriority;
            if(diff < 0) return -1;
            else if (diff > 0) return 1;
            else return 0;         
        }
    }     
}