import java.util.TreeSet;

public class PointSET {
	
	private TreeSet<Point2D> pointSet;
	
	public PointSET()                               // construct an empty set of points
	{
		this.pointSet = new TreeSet<Point2D>();
	}
	
	public boolean isEmpty()                        // is the set empty?
	{
		return pointSet.size() == 0;
	}
	
	public int size()                               // number of points in the set
	{
		return pointSet.size();
	}
	
    public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)	
    {
    	if (!pointSet.contains(p))
    		pointSet.add(p);
    }
    
    public boolean contains(Point2D p)              // does the set contain the point p?	 
    {
    	return pointSet.contains(p);    	
    }
    
    public void draw()                              // draw all of the points to standard draw
    {
    	for (Point2D p : pointSet)
    		StdDraw.point(p.x(), p.y());
    }
    
    public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
    {
    	TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();
    	
    	for (Point2D p : pointSet)
    	{
    		if (rect.contains(p))
    		{
    			rangeSet.add(p);
    		}
    	}
    	
    	return rangeSet;
    }
    
	public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
	{
		Point2D nearestPoint = null;
		double distance = Double.MAX_VALUE;
		
		if (this.pointSet.size() == 0)
			return nearestPoint;
		
		for (Point2D iter : pointSet)
		{
			if (iter.distanceTo(p) < distance)
			{
				nearestPoint = iter;
				distance = iter.distanceTo(p);
			}			
		}
		
		return nearestPoint;
		
	}
	
}
