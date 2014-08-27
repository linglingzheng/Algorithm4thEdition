import java.util.TreeSet;


public class KdTree {
	
	private class KdNode
	{
		private KdNode leftNode;
		private KdNode rightNode;
		private boolean isVert;
		private double x;
		private double y;
		
		private KdNode(double x, double y, KdNode leftNode, KdNode rightNode, boolean isVert)
		{
			this.leftNode = leftNode;
			this.rightNode = rightNode;
			this.isVert = isVert;
			this.x = x;
			this.y = y;
		}
	}
	
	private KdNode rootNode;
	private int size;
	private RectHV BOX = new RectHV(0, 0, 1, 1);
	
	// construct an empty set of points
	public KdTree()
	{
	    this.size = 0;
		this.rootNode = null;
	}
	
	// is the set empty?
	public boolean isEmpty()
	{
		return this.size == 0;
	}
	
	// number of points in the set
	public int size()
	{
		return this.size;
	}
	
	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p)
	{
		this.rootNode = insert(this.rootNode, p, true);
	}
	
	private KdNode insert(KdNode node, Point2D p, boolean isVert)
	{
		if (node == null)
		{
			size ++;
			return new KdNode(p.x(), p.y(), null, null, isVert);
		}
		
		if ((p.x() < node.x && node.isVert) || (p.y() < node.y && !node.isVert))
		{
			node.leftNode = insert(node.leftNode, p, !isVert);
		}
		else
			node.rightNode = insert(node.rightNode, p , !isVert);
		
		if (p.x() == node.x && p.y() == node.y)
			return node;
		
		return node;
	}
	
	// does the set contain the point p?
	public boolean contains(Point2D p)
	{
		return contains(rootNode, p.x(), p.y());
	}
	
	private boolean contains(KdNode node, double x, double y)
	{
		if (node == null)
			return false;
		
		if (node.x == x && node.y == y)
			return true;
		
		if ((x < node.x && node.isVert) || (y < node.y && !node.isVert))
		{
			return contains(node.leftNode, x, y);
		}
		else
			return contains(node.rightNode, x, y);
			
	}
	
	// draw all of the points to standard draw
	public void draw()
	{
		StdDraw.setScale(0, 1);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius();
		BOX.draw();
		draw(rootNode, BOX);
	}
	
	private void draw(KdNode node, RectHV rect)
	{
		if (node == null)
			return;
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		new Point2D(node.x, node.y).draw();
		
		Point2D min, max;
		if (node.isVert)
		{
			StdDraw.setPenColor(StdDraw.RED);
			min = new Point2D(node.x, rect.ymin());
			max = new Point2D(node.x, rect.ymax());
		}
		else 
		{
			StdDraw.setPenColor(StdDraw.BLUE);
			min = new Point2D(rect.xmin(), node.y);
			max = new Point2D(rect.xmax(), node.y);
		}
		
		StdDraw.setPenRadius();
		min.drawTo(max);
		
		draw(node.leftNode, leftRect(rect, node));
		draw(node.rightNode, rightRect(rect, node));

	}
	
	private RectHV leftRect(RectHV rect, KdNode node)
	{
		if (node.isVert)
		{
			return new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
		}
		else
			return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
	}
	
	private RectHV rightRect(RectHV rect, KdNode node)
	{
		if (node.isVert)
		{
			return new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());
		}
		else
			return new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());
	}
	
	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect)
	{
		
		TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();
		range(rootNode, rect, BOX, rangeSet);
		
		return rangeSet;
	}
	
	private void range(KdNode node, RectHV rect, RectHV rectq, TreeSet<Point2D> rangeSet)
	{
		if (node == null)
			return;
		
		if (rect.intersects(rectq))
		{
			Point2D p = new Point2D(node.x, node.y);
			if (rect.contains(p))
				rangeSet.add(p);
			
			range(node.leftNode, rect, leftRect(rectq, node), rangeSet);
			range(node.rightNode, rect, rightRect(rectq, node), rangeSet);
		}
	
		
	}
	
	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p)
	{
		return nearest(p, rootNode, BOX, null);
	}
	
	private Point2D nearest(Point2D p, KdNode node, RectHV rect, Point2D candidNear)
	{
		Point2D nearest = candidNear;
		double dpn = 0.0;
		double drn = 0.0;
		RectHV left = null;
		RectHV right = null;
		
		if (node == null)
			return candidNear;
		
		if (nearest != null)
		{
			dpn = p.distanceSquaredTo(nearest);
			drn = rect.distanceSquaredTo(nearest);
		}
		
		if (nearest == null || drn < dpn)
		{
			Point2D point = new Point2D(node.x, node.y);
			
			if (nearest == null || dpn > p.distanceSquaredTo(point));
			
			if (node.isVert)
			{
				left = new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
				right = new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());
				
				if (p.x() < node.x)
				{
					nearest = nearest(p, node.leftNode, left, nearest);
					nearest = nearest(p, node.rightNode, right, nearest);
				}
				else 
				{
					nearest = nearest(p, node.rightNode, right, nearest);
					nearest = nearest(p, node.leftNode, left, nearest);
				}
			}
			
			else 
			{
				left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
				right = new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());
				
				if (p.y() < node.y)
				{
					nearest = nearest(p, node.leftNode, left, nearest);
					nearest = nearest(p, node.rightNode, right, nearest);
				}
				else 
				{
					nearest = nearest(p, node.rightNode, right, nearest);
					nearest = nearest(p, node.leftNode, left, nearest);
				}
			}
		}
	
		return nearest;
	}
}
