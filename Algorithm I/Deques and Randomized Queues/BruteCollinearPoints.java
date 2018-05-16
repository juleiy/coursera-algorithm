import java.util.*;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
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
        
        segments = new ArrayList<LineSegment>();
        for (int i = 0; i<n; i++) {
            for(int j = i+1; j<n; j++) {
                double s1 = points[i].slopeTo(points[j]);
                for(int k = j+1; k<n; k++) {
                    double s2 = points[i].slopeTo(points[k]);
                    for(int x = k+1; x<n; x++) {
                        double s3 = points[i].slopeTo(points[x]);
                        if(s1 == s2 && s2 == s3) {
                            LineSegment segment = new LineSegment(points[i], points[x]);
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
}