package repository;

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
