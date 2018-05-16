//import edu.princeton.cs.algs4.LineSegment;
import java.util.*;
public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;
    
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null || points.length < 4) {
            throw new java.lang.IllegalArgumentException();
        }                
        Arrays.sort(points);
        int n = points.length;
        for (int i = 0; i<n; i++)
        {
            if(points[i] == null || (i+1 < n && points[i] == points[i+1])) {                
                throw new java.lang.IllegalArgumentException();
            }
        }         
        
        // keep copy of original points
        Point[] origin = new Point[n]; 
        for(int i = 0; i<n; i++)
        {
            origin[i] = points[i];
        }
        
        for(int i=0; i<n; i++)
        {
            Point p = origin[i];
            Arrays.sort(points, 0, n, p.slopeOrder());
            
            double preSlope = Double.NEGATIVE_INFINITY;
            Point maxPoint = p;
            int count = 0;
            for(int j = 0; j<n; j++)
            {   
                maxPoint = points[j];
                double curSlope = points[j].slopeTo(p);                
                if(curSlope == preSlope) {
                    count++;                                   
                }
                else {
                    if(count >= 4) {                     
                        segments.add(new LineSegment(p, maxPoint));
                    } 
                    count = 2;
                }
                preSlope = curSlope;     
            }
        }
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
}