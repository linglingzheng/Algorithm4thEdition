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
*/
import java.io.IOException;
import java.util.Iterator;

public class Subset {

	public static void main(String[] args) throws IOException
	{

		RandomizedQueue<String> q = new RandomizedQueue<String>();   
		
        while (!StdIn.isEmpty())
        {
        	String item = StdIn.readString();
            q.enqueue(item);
            
        }        

        int k = Integer.parseInt(args[0]);
        
        Iterator<String> it = q.iterator();
        for (int i = 0; i < k; i++)
        {
        	String s = it.next();
        	StdOut.println(s);
        }
		
		

	}

}
