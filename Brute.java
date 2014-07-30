/*Brute force program Brute.java that examines 4 points at a time and checks whether 
 * they all lie on the same line segment, printing out any such line segments to 
 * standard output and drawing them using standard drawing. To check whether the 
 * 4 points p, q, r, and s are collinear, check whether the slopes between p and q, 
 * between p and r, and between p and s are all equal.

The order of growth of the running time of your program should be N4 in the worst 
case and it should use space proportional to N.

Input format. Read the points from an input file in the following format: An integer N, followed by N pairs of integers (x, y), each between 0 and 32,767. Below are two examples.

% more input6.txt       % more input8.txt
6                       8
19000  10000             10000      0
18000  10000                 0  10000
32000  10000              3000   7000
21000  10000              7000   3000
 1234   5678             20000  21000
14000  10000              3000   4000
                         14000  15000
                          6000   7000
                          
Output format. Print to standard output the line segments that your program discovers, one per line. Print each line segment as an ordered sequence of its constituent points, separated by " -> ".

% java Brute input6.txt
(14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (21000, 10000)
(14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (32000, 10000)
(14000, 10000) -> (18000, 10000) -> (21000, 10000) -> (32000, 10000)
(14000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000)
(18000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000)

% java Brute input8.txt
(10000, 0) -> (7000, 3000) -> (3000, 7000) -> (0, 10000) 
(3000, 4000) -> (6000, 7000) -> (14000, 15000) -> (20000, 21000) 
*/



import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Brute {

	public static void main(String[] args) throws FileNotFoundException 
	{
		
		StdDraw.setXscale(0, 32767);
        StdDraw.setYscale(0, 32767);		
		
		File file = new File(args[0]);
		Scanner sc = new Scanner(new FileInputStream(file));
		try
		{
			int len = sc.nextInt();
			Point[] arr = new Point[len];

			int count = 0;
			
			while (sc.hasNextInt())
			{
				int x = sc.nextInt();
				int y = sc.nextInt();

				arr[count] = new Point(x, y);

				count++;

			}
				
			Arrays.sort(arr);
			
			
			for (int p = 0; p < len; p++)
			{
				
				for (int q = p + 1; q < len; q++)
				{
					
					for (int r = q + 1; r < len; r++)
					{						

						for (int s = r + 1; s < len; s++)
						{
							double PQ = arr[p].slopeTo(arr[q]);
							double PR = arr[p].slopeTo(arr[r]);
							double PS = arr[p].slopeTo(arr[s]);

							if ((PQ == PR) && (PQ == PS))
							{

								StdOut.println(arr[p].toString() + " -> " + arr[q].toString() + " -> " +
										arr[r].toString() + " -> " + arr[s].toString());
					
						    	arr[p].drawTo(arr[s]);
							}
							
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
