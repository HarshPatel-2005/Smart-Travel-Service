package text_files;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import client_package.Client;
import exceptions.EntityNotFoundException;
import exceptions.InvalidTripDataException;
import travel_package.Accommodation;
import travel_package.Transportation;
import travel_package.Trip;

public class TripFileManager {

	public static void saveTrips(List<Trip> trips, int tripCount, String filePath) throws IOException {
		
		File csvOutputFile = new File(filePath);
		csvOutputFile.getParentFile().mkdirs();
		
		if(tripCount == 0) {
			System.out.println("There are no trips to save!");
			return;
		}
		else {
			PrintWriter writer = new PrintWriter(new FileWriter(csvOutputFile));
			
			for(Trip tr : trips) {
				writer.println(tr.getTripID() + ";" + tr.getClient().getClientID() + ";" + tr.getAccommodation().getAccommodationID() + ";" 
						+ tr.getTransportation().getTransportID() + ";" + tr.getDestination() + ";" + tr.getDurationInDays() + ";" + 
						tr.getBasePrice());
			}
			
			writer.close();
			
			System.out.println("Trips Successfully saved!");
		}
		
	}
	
	public static void loadTrips(List<Client> clients, int clientCount, List<Accommodation> accomodationOptions, int accomodationCount, List<Transportation> transportOptions, int transportCount, List<Trip> trips, int tripCount, String filePath) throws IOException, InvalidTripDataException {
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		String line;
		
		while((line = reader.readLine()) != null) {
			
			try {
				String[] content = line.split(";");
				
				if(content.length != 7) {
					throw new InvalidTripDataException("InvalidTripDataException: Invalid number of columns");
				}
				
				if(content[0] == null || content[0].isEmpty() || content[0].charAt(0) != 'T') {
					throw new InvalidTripDataException("InvalidTripDataException: Incorrect Trip ID: " + content[0]);
				}
				
				// Exceptions are handled if the trips are not able to match a client, transportation or accommodation with it
				if(!clientExists(content[1], clients, clientCount)) {
					throw new EntityNotFoundException("EntityNotFoundException : Client ID not found: " + content[1]);
				}
				if(!accommodationExists(content[2], accomodationOptions, accomodationCount)) {
					throw new EntityNotFoundException("EntityNotFoundException : Accommodation ID not found: " + content[2]);
				}
				if(!transportationExists(content[3], transportOptions, transportCount)) {
					throw new EntityNotFoundException("EntityNotFoundException : Transportation ID not found: " + content[3]);
				}
				
				Client client = findClientByID(content[1], clients, clientCount);
				Accommodation accommodation = findAccommodationByID(content[2], accomodationOptions, accomodationCount);
				Transportation transportation = findTransportationByID(content[3], transportOptions, transportCount);
				
				int duration = Integer.valueOf(content[5]);
				double basePrice = Double.valueOf(content[6]);
				
				Trip trip = new Trip(content[0], content[4], duration, basePrice, client, transportation, accommodation);

				trips.add(trip);
			} catch (InvalidTripDataException | EntityNotFoundException e) {
				ErrorLogger.logError("Trip", line, e.getMessage());
			}
		}

		reader.close();
	}
	
	// Helper Methods:
	
	private static Client findClientByID(String clientID, List<Client> clients, int clientCount) { // Finds the client based on it's ID
		
		for(Client c : clients) {
			if(clientID.equals(c.getClientID())) {
				return c;
			}
		}
		
		return null;
		
	}
	
	private static boolean clientExists(String clientID, List<Client> clients, int clientCount) { // Finds the client based on it's ID but uses to see if it exist
		
		for(Client c : clients) {
			if(clientID.equals(c.getClientID())) {
				return true;
			}
		}
		return false;
	}
	
	private static Accommodation findAccommodationByID(String accommodationID, List<Accommodation> accomodationOptions, int accommodationCount) { // Finds the accommodation based on it's ID
		
		for(Accommodation a : accomodationOptions) {
			if(accommodationID.equals(a.getAccommodationID())) {
				return a;
			}
		}
		
		return null;
		
	}
	
	private static boolean accommodationExists(String accommodationID, List<Accommodation> accomodationOptions, int accommodationCount) { // Finds the accommodation based on it's ID but uses to see if it exist
		
		for(Accommodation a : accomodationOptions) {
			if(accommodationID.equals(a.getAccommodationID())) {
				return true;
			}
		}
		
		return false;
	}
	
	private static Transportation findTransportationByID(String transportationID, List<Transportation> transportOptions, int transportCount) { // Finds the transportation based on it's ID
		
		for(Transportation t : transportOptions) {
			if(transportationID.equals(t.getTransportID())) {
				return t;
			}
		}
		
		return null;
		
	}
	
	private static boolean transportationExists(String transportationID, List<Transportation> transportOptions, int transportCount) { // Finds the transportation based on it's ID but uses to see if it exist
		
		for(Transportation t : transportOptions) {
			if(transportationID.equals(t.getTransportID())) {
				return true;
			}
		}
		
		return false;
	}
	
}
