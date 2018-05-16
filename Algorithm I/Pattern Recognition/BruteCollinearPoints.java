import java.util.*;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }      
        for(int i = 0; i < points.length; i++)
        {
            if (points[i] == null) throw new java.lang.IllegalArgumentException();
        }

        int n = points.length;
        Point[] copy = new Point[n]; 
        for(int i = 0; i<n; i++)
        {
            copy[i] = points[i];
        }
        Arrays.sort(copy);         
        for (int i = 0; i<n; i++)
        {
            if( i+1 < n && copy[i].compareTo(copy[i+1]) == 0) {                
                throw new java.lang.IllegalArgumentException();
            }
        }        
        
        segments = new ArrayList<LineSegment>();
        for (int i = 0; i<n; i++) {
            for(int j = i+1; j<n; j++) {
                double s1 = copy[i].slopeTo(copy[j]);
                for(int k = j+1; k<n; k++) {
                    double s2 = copy[i].slopeTo(copy[k]);
                    for(int x = k+1; x<n; x++) {
                        double s3 = copy[i].slopeTo(copy[x]);
                        if(s1 == s2 && s2 == s3) {
                            LineSegment segment = new LineSegment(copy[i], copy[x]);
                            segments.add(segment);
                        }
                    }
                }
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
    
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point[] points = {new Point(1,1), new Point(2,1),  new Point(3,1),
         new Point(4,1)};
        
        BruteCollinearPoints b = new BruteCollinearPoints(points); 
        LineSegment[] s = b.segments();
        for(int i=0; i<s.length; i++) {
            System.out.println(s[i].toString());
        }
    }   
}