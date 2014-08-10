/*A* search algorithm to solve 8 puzzle problem
 * 
 * % more puzzle04.txt
3
 0  1  3
 4  2  5
 7  8  6

% java Solver puzzle04.txt
Minimum number of moves = 4

3
 0  1  3 
 4  2  5 
 7  8  6 

3
 1  0  3 
 4  2  5 
 7  8  6 

3
 1  2  3 
 4  0  5 
 7  8  6 

3
 1  2  3 
 4  5  0   
 7  8  6 

3
 1  2  3 
 4  5  6 
 7  8  0
% more puzzle-unsolvable3x3.txt
3
 1  2  3
 4  5  6
 8  7  0

% java Solver puzzle3x3-unsolvable.txt
No solution possible

*/
import java.util.LinkedList;

public class Solver {

	private boolean isSolvable;
	private final LinkedList<Board> boards;

	private class SearchNode implements Comparable<SearchNode>
	{
		private Board board;
		private SearchNode prev;
		private int prior;
		private int moves;

		private SearchNode(Board board, int moves, SearchNode prev)
		{
			this.board = board;
			this.moves = moves;
			this.prev = prev;
		}	   

		private int priority()
		{
			prior = board.manhattan() + moves;
			return prior;
		}

		public int compareTo(SearchNode that) {

			if (this.priority() < that.priority())
				return -1;
			if (this.priority() > that.priority())
				return +1;
			if (this.priority() == that.priority())
			{
				if (this.board.manhattan() < that.board.manhattan())
					return -1;
				if (this.board.manhattan() > that.board.manhattan())
					return +1;
				return 0;
			}
			return 0;

		}

	}	

	public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
	{    	
		boards  = new LinkedList<Board>();
		boolean isFound = false;

		if (initial.isGoal())
		{
			isSolvable = true;
			this.boards.addFirst(initial);
			return;
		}

		if (initial.twin().isGoal())
		{
			isSolvable = false;
			return;
		}

		MinPQ<SearchNode> queue = new MinPQ<SearchNode>();
		SearchNode node = new SearchNode(initial, 0, null);
		MinPQ<SearchNode> queueTwin = new MinPQ<SearchNode>();
		SearchNode nodeTwin = new SearchNode(initial.twin(), 0, null);

		queue.insert(node);
		queueTwin.insert(nodeTwin);
		
		while (isFound == false)
		{
			
			if (!queue.isEmpty() && !queueTwin.isEmpty())
			{
				node = queue.delMin(); 
				nodeTwin = queueTwin.delMin(); 

				if (node.board.isGoal() && !nodeTwin.board.isGoal())
				{
					this.boards.addFirst(node.board);
					isSolvable = true;
					isFound = true;
					
					while (node.prev != null)
					{
						node = node.prev;
						this.boards.addFirst(node.board);
					}
					return;
				}			
				else if (!node.board.isGoal() && nodeTwin.board.isGoal())
				{
					isSolvable = false;
					isFound = true;
					return;
				}
				else if (!node.board.isGoal() && !nodeTwin.board.isGoal())
				{
					isSolvable = false;
					isFound = false;
					return;
				}
			}
		
			node.moves++;
			nodeTwin.moves++;

			for (Board s: node.board.neighbors())
			{
				if (node.prev != null && s.equals(node.prev.board))
				{
					continue;
				}
					SearchNode newNode = new SearchNode(s, node.moves, node);
					queue.insert(newNode);
				
			
			} 

			for (Board t: nodeTwin.board.neighbors())
			{
				if (nodeTwin.prev != null && t.equals(nodeTwin.prev))
				{
					continue;
				}
					SearchNode newNodeTwin = new SearchNode(t, nodeTwin.moves, nodeTwin);
					queueTwin.insert(newNodeTwin);
				
			} 
		}

	}

	public boolean isSolvable()             // is the initial board solvable?
	{
		return isSolvable;
	}

	public int moves()                      // min number of moves to solve initial board; -1 if no solution
	{
		if (!isSolvable)
			return -1;

		return boards.size() - 1;

	}

	public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
	{
		if (!isSolvable)
			return null;

		return boards;

	}

	public static void main(String[] args)  // solve a slider puzzle (given below)
	{
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}