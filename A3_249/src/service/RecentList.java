package service;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
//are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
//can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
//feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

//This class is used as another storage to save which information has been viewed. It is a generic and allows any classes but since we have 4 it will view between the 4 different classes. This class saves the viewed information
//in order, where the most recently viewed item is first and the oldest viewed item gets pushed back. This is similar to most softwares as they have the same features. We leave a limit of 10 so once it goes over 10 we simply remove
//the last item and push the one we have first into the list

//printRecent allows the user to view the most recently viewed objects. We create an iterator so that we can run through the list and check which ones we have and print them out. This one prints it out in the regular order which
//means whatever came in recently is the first one to get printed

import java.util.Iterator;
import java.util.LinkedList;

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
