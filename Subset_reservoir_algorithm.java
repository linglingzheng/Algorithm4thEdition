/*
 * @lingling zheng
 * 
 * Subset client. Write a client program Subset.java that takes a command-line integer k;
 *  reads in a sequence of N strings from standard input using StdIn.readString(); 
 *  and prints out exactly k of them, uniformly at random. 
 *  Each item from the sequence can be printed out at most once. 
 *  You may assume that k ¡Ý 0 and no greater than the number of string N on standard 
 *  input.
 *  
 *  extra:
 *  use only one RandomizedQueue object of maximum size at most k.
 *  
 *  test :
 *  % echo A B C D E F G H I | java Subset 3       % echo AA BB BB BB BB BB CC CC | java Subset 8
C                                              BB
G                                              AA
A                                              BB
                                               CC
% echo A B C D E F G H I | java Subset 3       BB
E                                              BB
F                                              CC
G                                              BB

Check that maximum size of any or Deque or RandomizedQueue object
                created is <= k
  * filename = tale.txt, N = 138653, k = 5
  * filename = tale.txt, N = 138653, k = 50
  * filename = tale.txt, N = 138653, k = 500
  * filename = tale.txt, N = 138653, k = 5000
  * filename = tale.txt, N = 138653, k = 50000
*/

import java.util.Iterator;

public class Subset {

	public static void main(String[] args) 
	{
		int k = Integer.parseInt(args[0]);
		int count = 0;
		
		String[] r = new String[k];
		
        while (!StdIn.isEmpty())
        {
        	String item = StdIn.readString();
        	
            if (count <= k-1)
            	r[count] = item;
            
            else 
            {
            	int rand;
        		rand = StdRandom.uniform(k);
        		r[rand] = item;
            }
            
            count++;
        }             
        
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        String it = null;
		for (int i = 0; i < k; i++)
		{
			it = r[i];
        	q.enqueue(it);
		}
        	
        
        Iterator<String> iter = q.iterator();
        for (int i = 0; i < k; i++)
        {
        	String s = iter.next();
        	StdOut.println(s);
        }
		

	}

}
