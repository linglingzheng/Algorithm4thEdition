/**
 * @author linglingzheng
 * 
*Dequeue. A double-ended queue or deque (pronounced "deck") is a generalization 
*of a stack and a queue that supports inserting and removing items from either 
*the front or the back of the data structure.
*/

import java.util.*;

public class Deque<Item> implements Iterable<Item> {

	private Node<Item> first;    // beginning of deque
	private Node<Item> last;     //  end of deque
	private int N;
  
    
	// helper linked list class
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
		private Node<Item> prev;
		
		public Node(Node<Item> previous, Node<Item> next, Item item) {
            this.prev = previous;
            this.next = next;
            this.item = item;
        }
		
	}

	// construct an empty deque
	public Deque() {
		first = null;
		last  = null;
		N = 0;        
	}

	public boolean isEmpty()                 // is the deque empty?
	{
		return first == null;
	}

	public int size()                        // return the number of items on the deque
	{
		return N;	
	}

	public void addFirst(Item item)          // insert the item at the front
	{
		if (item == null)
			throw new NullPointerException();
		
		Node<Item> node = new Node<Item>(null, first, item);
		if (!isEmpty())
			first.prev = node;
		first = node;
		if (last == null) last = first;				
		N++;
		
	}

	public void addLast(Item item)           // insert the item at the end
	{
		if (item == null)
			throw new NullPointerException();
		
		Node<Item> node = new Node<Item>(last, null, item);
		if (last != null)
			last.next = node;
		last = node;
		if (isEmpty()) first = last;
		N++;
		
	}

	public Item removeFirst()                // delete and return the item at the front
	{
		if (isEmpty()) throw new NoSuchElementException();
		
		Item item = first.item;
		if (first.next != null) first.next.prev = null;
		if (last == first) last = null;
		first = first.next;
		N--;
		return item;
	}

	public Item removeLast()                 // delete and return the item at the end
	{
		if (isEmpty()) throw new NoSuchElementException();

		Item item = last.item;
		if (last.prev != null) last.prev.next = null;
		if (last == first) first = null;
		if (last != null) last = last.prev;
		N--;
		return item;
		
	}

	public Iterator<Item> iterator()         // return an iterator over items in order from front to end
	{
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item>
	{
		private Node<Item> current = first;

		public boolean hasNext()
		{
			return current != null;
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		public Item next()
		{
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;

		}

	}

		public static void main(String[] args)   // unit testing
		{
			Deque<String> d = new Deque<String>();
			Scanner sr = new Scanner("123-456-789");
			while(!sr.hasNext())
			{
				
				String item = sr.next();
	            if (!item.equals("-")) {
	            	d.addFirst(item);
	            	d.addLast(item);
	            }
				if (!d.isEmpty()) 
					  System.out.print(d.removeFirst() + " " + d.removeLast() + " ");
					  
			}
		}

}