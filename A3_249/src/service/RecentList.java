package service;

import java.util.Iterator;
import java.util.LinkedList;;

public class RecentList <T>{
	
	private LinkedList<T> list = new LinkedList<>();
	private final int MAX_SIZE = 10;
	
	public void addRecent(T item) {
		
		if(list.size() == MAX_SIZE) {
			list.removeLast();
			list.addFirst(item);
		}
		else
			list.addFirst(item);
		
	}
	
	public void printRecent(int maxToShow) {
		
		Iterator<T> iterator = list.descendingIterator(); // Creates an iterator interface in order to traverse a collection one by one. This one will specifically get the list in it's reverse order to print from the last
															// to the first
		
		while(iterator.hasNext()) { // Check if there's another value after the one it's currently on
			System.out.print(iterator.next() + " | "); // Print the next value
		}
		
	}
	
	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		
		if(list.size() == 0) {
			return true;
		}
		else
			return false;
		
	}
	
}
