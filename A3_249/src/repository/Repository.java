package repository;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
//are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
//can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
//feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

//This class is a storage just like the arrayList that we have in service but instead is used to specifically view different properties. If we create a client for instance, this class will also saved it. The information about
//the client is the same which means when we edit one both gets edited. When the user wishes to filter specific aspects about the information such as checking for prices, this is where it happens

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import exceptions.EntityNotFoundException;
import interfaces.Identifiable;

public class Repository<T extends Identifiable & Comparable<? super T>>{
	
	private List<T> list = new ArrayList<>();
	
	public void add(T item) {
		list.add(item);
	}
	
	public void remove(T item) {
		list.remove(item);
	}
	
	public T findByID(String id) throws EntityNotFoundException{
		for(T lookup : list) {
			if(lookup.getID().equals(id)) {
				return lookup;
			}
		}
		throw new EntityNotFoundException("EntityNotFoundException: ID Not Found!");
	}
	
	public List<T> filter(Predicate<T> predicate){
		
		List<T> filteredList = new ArrayList<>();
		
		for(T item : list) {
			if(predicate.test(item)) {
				filteredList.add(item);
			}
		}
		
		return filteredList;
		
	}
	
	public List<T> getSorted(){
		Collections.sort(list); // Collections utilizes comparable method compareTo to sort the list in a business-oriented fashion
		return list;
	}

}
