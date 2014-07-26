/*
 * @lingling zheng
 * 
 * Randomized queue. A randomized queue is similar to a stack or queue, 
 * except that the item removed is chosen uniformly at random from items 
 * in the data structure.
*/
import java.util.*;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private int N = 0;
	private int last = 0;
	private int capacity = 1;	
	@SuppressWarnings("unchecked")
	private Item[] q = (Item[]) new Object[capacity];

	public RandomizedQueue()                 // construct an empty randomized queue
	{
	}

	public boolean isEmpty()                 // is the queue empty?
	{
		return N == 0;
	}

	public int size()                        // return the number of items on the queue
	{
		return N;
	}

	@SuppressWarnings("unchecked")
	private void resize(int max)
	{
		assert max >= N;
		Item[] temp = (Item[]) new Object[max];
		for (int i = 0; i < N; i++){
			temp[i] = q[i];
		}

		q = temp;
		last = N;
	}

	public void enqueue(Item item)           // add the item
	{
		if (item == null)
			throw new NullPointerException();

		if (N == q.length) 
			resize(2*q.length);
		q[last++] = item;
		N++;

	}

	public Item dequeue()                    // delete and return a random item
	{
		if (isEmpty())
			throw new NoSuchElementException();

		int rand;
		do{
			rand = StdRandom.uniform(N);

		}while(q[rand] == null);

		exch(q, rand, --last);

		Item item = q[last];
		q[last] = null;
		N--;
		if ( N>0 && N == q.length/4)
			resize(q.length/2);
		return item;		

	}

	private void exch(Item[] a, int i, int j)
	{
		if (i == j) return;

		Item swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	public Item sample()                     // return (but do not delete) a random item
	{
		if (isEmpty())
			throw new NoSuchElementException();

		int rand;
		rand = StdRandom.uniform(N);
		return q[rand];

	}

	public Iterator<Item> iterator()         // return an independent iterator over items in random order
	{
		return new RandomizedQueueIterator();
	}


	private class RandomizedQueueIterator implements Iterator<Item>
	{
		private int[] ind;
		private int count = N;

		public RandomizedQueueIterator()
		{
			ind = new int[count];
			for (int i = 0; i < count; i++)
				ind[i] = i;
			StdRandom.shuffle(ind);
		}

		public boolean hasNext()
		{
			return count > 0;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next()
		{
			if (!hasNext()) throw new NoSuchElementException();

			Item item = q[ind[--count]];

			return item;
		}
	}

	public static void main(String[] args)   // unit testing
	{
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		while (!StdIn.isEmpty())
		{
			String item =StdIn.readString();
			if (item.equals("-")) 
				StdOut.print(q.dequeue() + " ");
			else q.enqueue(item); 
			if (item.equals("+"))
				StdOut.print(q.sample() + " ");					
		}

		System.out.println("(" + q.size() + " left on randomized queue)");

	}


}
