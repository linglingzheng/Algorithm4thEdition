/*************************************************************************
 * Name:lingling zheng
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE
    
    private class SlopeOrder implements Comparator<Point>
    {
    
    	public int compare(Point p1, Point p2)
    	{
    		double a = slopeTo(p1);
    		double b = slopeTo(p2);
    		return Double.compare(a, b);
    	}
    }
    
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
    	double slope;    	
    	if (that.y == this.y && that.x != this.x)
    		return 0;
    	if (that.x == this.x && that.y != this.y)
    		return Double.POSITIVE_INFINITY;
    	if (that.x == this.x && that.y == this.y)
    		return Double.NEGATIVE_INFINITY;
    	slope = (double) (that.y - this.y)/(that.x - this.x);
    	return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
    	if (this.y != that.y) 
    		return Double.compare(this.y, that.y);
    	else
    		return Double.compare(this.x, that.x);
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    	
    	Point p0 = new Point(8, 60);
        Point p1 = new Point(1, 2);
        Point p2 = new Point(10, 12);
    	
    	StdOut.println(p0.slopeTo(p1)); 
    	
    	StdOut.println(p0.compareTo(p1));  
    	
    	StdOut.println(p0.toString()); 
    	
    	StdOut.println(p1.SLOPE_ORDER.compare(p0, p2));    
    	
    	StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
    	p0.draw();
    	p1.draw();
    	p0.drawTo(p1);
    	p2.draw();
    	p1.drawTo(p2);
    	StdDraw.show(0);

    	
    }
}
