import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class PointSET {
    private SET<Point2D> points;
    
    public PointSET()                               // construct an empty set of points 
    {
        points = new SET<Point2D>();
    }
    
    public boolean isEmpty()                      // is the set empty? 
    {
        return points.isEmpty();
    }
    
    public int size()                         // number of points in the set 
    {
        return points.size();
    }
    
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if(p == null) throw new java.lang.IllegalArgumentException();
        points.add(p);
    }
    
    public boolean contains(Point2D p)            // does the set contain point p? 
    {
        if(p == null) throw new java.lang.IllegalArgumentException();
        return points.contains(p);
    }
    
    public void draw()                         // draw all points to standard draw 
    {
        for(Point2D p : points) {
            p.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
    {
        SET<Point2D> pointsInsideRect = new SET<Point2D>();
        for(Point2D p : points) {
            if(rect.contains(p)) {
               pointsInsideRect.add(p);
            }
        }
        return pointsInsideRect;
    }
    
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        Point2D nearest = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for(Point2D x : points) {   
            double distance = p.distanceSquaredTo(x);
            if(distance < minDistance) {
                minDistance = distance;
                nearest = x;
            }
        }
        return nearest;
    }
    
    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {     
    }
}