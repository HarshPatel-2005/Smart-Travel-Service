package service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;;

public class RecentList <T>{
	
	private LinkedList<T> list = new LinkedList<>();
	private final int MAX_SIZE = 10;
	
	public void addRecent(T item) {
		
		if(list.size() == MAX_SIZE) {
			list.removeLast(); // If the list is full remove the last one
		}
		
		list.remove(item); // Removes any previous item viewed
		list.addFirst(item); // Adds the removed item back at the start
		
	}
	
	public void printRecent() {
		
		Iterator<T> iterator = list.iterator(); // Creates an iterator interface in order to traverse a collection one by one. This list will print from the most recent item added to the oldest item added
		
		while(iterator.hasNext()) { // Check if there's another value after the one it's currently on
			System.out.println(iterator.next()); // Print the next value
			System.out.print("-------------------");
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
	
	public LinkedList<T> getList() {
		return list;
	}
	
}
