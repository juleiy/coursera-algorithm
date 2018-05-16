import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.*;

public class FastCollinearPoints2 {
    private final ArrayList<LineSegment> segments;
    
    public FastCollinearPoints2(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }      
        
        for(int i = 0; i < points.length; i++)
        {
            if (points[i] == null) throw new java.lang.IllegalArgumentException();
        }

        int n = points.length;
        Point[] sorted = new Point[n]; 
        Point[] copy = new Point[n]; 
        for(int i = 0; i<n; i++)
        {
            sorted[i] = points[i];
            copy[i] = points[i];
        }
        Arrays.sort(sorted);  
        
        for (int i = 0; i<n; i++)
        {
            if( i+1 < n && sorted[i].compareTo(sorted[i+1]) == 0) {                
                throw new java.lang.IllegalArgumentException();
            }
        }         
        
        segments = new ArrayList<LineSegment>();   
        for(int i=0; i<n; i++)
        {
            Point p = sorted[i];
            Arrays.sort(copy, p.slopeOrder());            
            ArrayList<Point> collinear = new ArrayList<Point>();
            double preSlope = Double.NEGATIVE_INFINITY;
            for(int j = 1; j<n; j++)
            {
                Point q = copy[j];
                double curSlope = p.slopeTo(q);
                if(preSlope == Double.NEGATIVE_INFINITY ||
                   curSlope == preSlope) {   
                    collinear.add(q);
                    if(j == n-1 && collinear.size() >= 3) 
                    {
                        collinear.add(p);
                        Collections.sort(collinear);
                        if(collinear[0].compareTo(p)>=0 {
                            addSegment(collinear);
                        }
                    }
                    preSlope = curSlope; 
                }
                else {
                    if(collinear.size() >= 3) 
                    {
                        collinear.add(p);
                        Collections.sort(collinear);
                        addSegment(collinear, sorted, i);
                    }   
                    preSlope = curSlope;  
                    collinear.clear();
                }
            } 
        }
    }

    private addSegment(ArrayList<Point> collinear)
    {
        
    }

    public int numberOfSegments()        // the number of line segments
    {
        return segments.size();
    }
    
    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] s = new LineSegment[segments.size()];
        return segments.toArray(s);
    }   
         
    public static void main(String[] args) {
        
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }   
}