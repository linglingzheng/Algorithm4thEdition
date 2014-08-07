/*solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.
*/

import java.util.Stack;

public class Board {
	
	private final int N;
	private final int[][] init;
	
	
	public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
                                          // (where blocks[i][j] = block in row i, column j)
	{
		N = blocks.length;
		init = new int[N][N];
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++)
			{
				init[i][j] = blocks[i][j];
			}
		}
					
	}

	public int dimension()                 // board dimension N
	{
		return N;
	}	
	
	private int[][] goal()
	{
		int[][] goal;
		goal = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				goal[i][j] = goalValueAt(i, j);
		return goal;
	}
	
	private int goalValueAt(int i, int j) 
	{
		
		if (isEnd(i, j))
			return 0;
		else return i * N + j + 1;
		
	}
	
	private boolean isEnd(int i, int j)
	{
		return  ((i == N - 1) && (j == N - 1));
	}
	
	public int hamming()                   // number of blocks out of place
	{
		int count = 0;
		for (int i = 0; i < N; i++)
			for (int j =0; j < N; j++)
				if ((init[i][j] != goalValueAt(i, j)) && !isEnd(i, j))
					count++;
		return count;
	}
	
   public int manhattan()                 // sum of Manhattan distances between blocks and goal
   {
	 int count = 0;
	 int val;
	 for (int i = 0; i < N; i++)
	 {
		 for (int j = 0; j < N; j++)
		 {
			 val = init[i][j];
			 if ((val != goalValueAt(i, j)) && val != 0)
			 {
				 int X = (val - 1) / N;
				 int Y = (val - 1) % N;
				 int distance = Math.abs(X - i) + Math.abs(Y - j);
				 count = count + distance;
			 }
		 } 
	 }		 			
	 return count;
	 
   }   

   public boolean isGoal()                // is this board the goal board?
   {
	
	   return initEquals(this.init, goal());
	  
   }
   
   private boolean initEquals(int[][] initial, int[][] theGoal)
   {
	   for (int i = 0; i < N; i++)
		   for (int j = 0; j < N; j++)
			   if (initial[i][j] != theGoal[i][j])
				   return false;
	   return true;
   }				    
   

   public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
   {
	   Board blocks = new Board(init);
	   
	   for (int i = 0; i < N; i++)
	   {
		   for (int j =0; j < N - 1; j++)
		   {
			   if ((init[i][j] != 0) && init[i][j + 1] != 0)
			   {
				   blocks.swap(i, j, i, j + 1);
				   return blocks;
			   }
			   
		
		   }
	   }
	   return blocks;
		  
   }
   
   private void swap(int i, int j, int is, int js)
   {
	   int swap;
	 
	   swap = init[i][j];
	   init[i][j] = init[is][js];
	   init[is][js] = swap;
	   
	   assert isRange(is, js);
	   
   }
   
   private boolean isRange(int is, int js)
   {
	   if (is < 0 || is > N || js < 0 || js > N)
		   return false;
	   else
		   return true;
   }   

   public boolean equals(Object y)        // does this board equal y?
   {
	   if (y == this) return true;
	   if (y == null) return false;
	   if (this.getClass() != y.getClass()) return false;
	   
	   Board that = (Board) y;
	   return ((this.N == that.N) && initEquals(this.init, that.init));
   }
   
   public Iterable<Board> neighbors()     // all neighboring boards
   {	   
	   Stack<Board> all = new Stack<Board>();
	   
	   for (int i = 0; i < N - 1; i++)
	   {
		   for (int j = 0; j < N - 1; j++)
		   {
			   if (init[i][j] != 0 && init[i][j + 1] == 0)
			   {
				   Board neighbor;
				   neighbor = new Board(init); 
				   
				   neighbor.swap(i, j, i, j + 1);
				   all.push(neighbor);
				  
			   }
			   else if (init[i][j] != 0 && (j != 0 && init[i][j - 1] == 0))
			   {
				   Board neighbor;
				   neighbor = new Board(init); 
				   
				   neighbor.swap(i, j, i, j - 1);
				   all.push(neighbor);
				  

			   }
			   else if (init[i][j] != 0 && init[i + 1][j] == 0)
			   {
				   Board neighbor;
				   neighbor = new Board(init); 
				   
				   neighbor.swap(i, j, i + 1, j);
				   all.push(neighbor);
				 
			   }
			   else if (init[i][j] != 0 && (i != 0 && init[i - 1][j] == 0))
			   {
				   Board neighbor;
				   neighbor = new Board(init); 
				   
				   neighbor.swap(i, j, i - 1, j);
				   all.push(neighbor);
				  

			   }
		   }			   
	   }
	   
	   return all;
   }
   
   

   public String toString()               // string representation of the board (in the output format specified below)
   {
	   StringBuilder str = new StringBuilder();
	   str.append(N + "\n");
	   
	   for (int i = 0; i < N; i++)
	   {
		   for (int j = 0; j < N; j++)
		   {
			   str.append(String.format("%2d", init[i][j]));
			   if (j == N)
				   str.append("\n");
		   }
	   }
		
	   return str.toString();
		   
   }
	
   public static void main(String[] args) {
	    // create initial board from file	   
	   
	    In in = new In("puzzle04.txt");
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);	
	    
	    StdOut.println(initial.toString());
	    		
	}

}
