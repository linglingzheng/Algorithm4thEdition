import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Hashtable;

public class Fast {

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
			
			double currentKey = 0;
			Point origin, begin, end, last;
			boolean isFound = false;

			Hashtable<Double, ArrayList<Point>> hash = new Hashtable<Double, ArrayList<Point>>();

			for ( int p = 0; p < len; p++)
			{				
				origin = arr[p];
				//				Sort the points according to the slopes they makes with p
				Arrays.sort(arr, p + 1, len, arr[p].SLOPE_ORDER);
				last = arr[len - 1];


				//				Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p
				for ( int b = p + 1, e = p + 2; e < len; e++)
				{
					begin = arr[b];
					end = arr[e];

					if (origin.slopeTo(begin) != origin.slopeTo(end) || e == len -1)
					{
						currentKey = arr[p].slopeTo(origin);

						if ((e == len - 1) && currentKey == origin.slopeTo(last))
							e = len;

						isFound = false;
						if (hash.containsKey(currentKey))
						{
							ArrayList<Point> select = hash.get(currentKey);
							for (int j = 0; j < select.size(); j++)
							{
								if (select.get(j).slopeTo(origin) == currentKey)
								{
									isFound = true;
									break;
								}
							}

						}



						if (e - b >=3 && !isFound)
						{
							Arrays.sort(arr, b, e);

							StdOut.print(origin.toString());
							for (int j = b; j < e; j++)
							{
								StdOut.print(" -> ");
								StdOut.print(arr[j].toString());
							}

							StdOut.println();
							origin.drawTo(arr[e - 1]);


							if (hash.containsKey(currentKey))
							{
								hash.get(currentKey).add(origin);
							} else
							{
								ArrayList<Point> select = new ArrayList<Point>();
								select.add(origin);
								hash.put(currentKey, select);
							}
						}
						b = e;

					}
				}
				Arrays.sort(arr, p + 1, len);

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
