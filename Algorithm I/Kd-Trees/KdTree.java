import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import java.util.*;

public class KdTree {
    private Node root;
    private int count;    

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        
        public Node(Point2D p, RectHV rect) {
            if(rect == null) {
                this.rect = new RectHV(0,0,1,1);
            }
            else {
                this.rect = rect;
            }
            this.p = p;
        }
    }    
    
    public KdTree()                               // construct an empty set of points 
    {
    }
    
    public boolean isEmpty()                      // is the set empty? 
    {
        return root == null;
    }
    
    public int size()                         // number of points in the set 
    {
        return count;
    }
    
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if(p == null) throw new java.lang.IllegalArgumentException();
        root = insert(root, p, null, true);
    }
    
    private Node insert(Node x, Point2D p, RectHV r, boolean isVertical) {
        if(x == null) {
            count++;
            return new Node(p, r);            
        }
        
        if(x.p.equals(p)) return x;
        
        int cmp = 0;
        if(isVertical) {
            cmp = Point2D.X_ORDER.compare(x.p, p);  
            if(cmp > 0) {
                if(x.lb == null) {
                    r = new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(),  x.rect.ymax());
                }
                else {
                    r = x.lb.rect;
                }
                x.lb = insert(x.lb, p, r, !isVertical);
            }
            else {
                if(x.rt == null) {
                    r = new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(),  x.rect.ymax());
                }
                else {
                    r = x.rt.rect;
                }
                x.rt = insert(x.rt, p, r, !isVertical);
            }
        }
        else {
            cmp = Point2D.Y_ORDER.compare(x.p, p);
            if(cmp > 0) {
                if(x.lb == null) {
                    r = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(),  x.p.y());
                }
                else {
                    r = x.lb.rect;
                }
                x.lb = insert(x.lb, p, r, !isVertical);
            }
            else {
                if(x.rt == null) {
                    r = new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(),  x.rect.ymax());
                }
                else {
                    r = x.rt.rect;
                }
                x.rt = insert(x.rt, p, r, !isVertical);
            }
        }           
        
        return x;
    }       
    
    public boolean contains(Point2D p)            // does the set contain point p? 
    {
        if(p == null) throw new java.lang.IllegalArgumentException();
        return contains(root, p, true);
    }
    
    private boolean contains(Node x, Point2D p, boolean isVertical) {
        if(x == null) return false;
        if(x.p.equals(p)) return true;
        
        int cmp = 0;
        if(isVertical) {
            cmp = Point2D.X_ORDER.compare(x.p, p);
        }
        else {
            cmp = Point2D.Y_ORDER.compare(x.p, p);
        }
        
        if(cmp > 0) {
            return contains(x.lb, p, !isVertical);            
        }
        else {
            return contains(x.rt, p, !isVertical);
        }
    }    
    
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
    {
        if(rect == null) throw new java.lang.IllegalArgumentException();
        Stack<Point2D> s = new Stack<Point2D>();
        range(root, rect, s);
        return s;
    }
    
    private void range(Node x, RectHV rect, Stack<Point2D> s) {
        if(x == null || !x.rect.intersects(rect)) return;
        if(rect.contains(x.p)) {
            s.push(x.p);
        }
        if(x.lb != null) {
            range(x.lb, rect, s);
        }  
        if(x.rt != null) {
            range(x.rt, rect, s);
        }        
    } 
    
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        if(p == null) throw new java.lang.IllegalArgumentException();
        if (root == null || p == null) return null;
        return nearest(root, p, root.p, true);
    }
    
    // need to think more - most difficult logic in this file
    private Point2D nearest(Node x, Point2D p, Point2D nearestP, boolean isVertical) {
        if(x == null) return nearestP;        
        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(nearestP)) {
            nearestP = x.p;
        }     
        
        if(isVertical) {
            if(x.p.x() < p.x()) {
                nearestP = nearest(x.rt, p, nearestP, !isVertical);
                if(x.lb != null && nearestP.distanceSquaredTo(p) > x.lb.rect.distanceSquaredTo(p)) {
                    nearestP = nearest(x.lb, p, nearestP, !isVertical);
                }
            }
            else {
                nearestP = nearest(x.lb, p, nearestP, !isVertical);
                if(x.rt != null && nearestP.distanceSquaredTo(p) > x.rt.rect.distanceSquaredTo(p)) {
                    nearestP = nearest(x.rt, p, nearestP, !isVertical);
                }                
            }
        }
        else {
            if (x.p.y() < p.y()) {
                nearestP = nearest(x.rt, p, nearestP, !isVertical);
                if (x.lb != null && (nearestP.distanceSquaredTo(p)> x.lb.rect.distanceSquaredTo(p)))
                    nearestP = nearest(x.lb, p,nearestP, !isVertical);
            } else {
                nearestP = nearest(x.lb, p, nearestP, !isVertical);
                if (x.rt != null && (nearestP.distanceSquaredTo(p) > x.rt.rect.distanceSquaredTo(p)))
                    nearestP = nearest(x.rt, p, nearestP, !isVertical);
            }            
        }   
        
        return nearestP;
    }
    
    public void draw()                         // draw all points to standard draw 
    {
        // draw the rectangle
        draw(root, true);
    }

    private void draw(Node x, boolean isVertical) {
        if(x == null) return;
        
        // draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(x.p.x(), x.p.y());
        
        // draw line
        RectHV r = x.rect;
        double xmin = r.xmin(), ymin = r.ymin(), xmax = r.xmax(), ymax = r.ymax();  
        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            xmin = x.p.x();
            xmax = x.p.x();
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            ymin = x.p.y();
            ymax = x.p.y();
        }
        StdDraw.setPenRadius();
        StdDraw.line(xmin, ymin, xmax, ymax);
            
        if (x.lb != null) {
            draw(x.lb, !isVertical);
        }
        if (x.rt != null) {
            draw(x.rt, !isVertical);
        }            
    }            
    
    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {     
    }
}