package text_files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import client_package.Client;
import exceptions.DuplicateEmailException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidAccommodationDataException;
import exceptions.InvalidClientDataException;
import exceptions.InvalidTransportDataException;
import exceptions.InvalidTripDataException;
import interfaces.CsvPersistable;
import travel_package.Accommodation;
import travel_package.Bus;
import travel_package.Flight;
import travel_package.Hostel;
import travel_package.Hotel;
import travel_package.Train;
import travel_package.Transportation;
import travel_package.Trip;

public class GenericFileManager <T extends CsvPersistable>{
	
	public static <T extends CsvPersistable> void save(List<T> items, String filePath) throws IOException {
		
		PrintWriter writer = new PrintWriter(new FileWriter(filePath));
		
		for(T item : items) {
			writer.println(item.toCsvRow());
		}
		
		writer.close();
		
	}
	
	public static <T extends CsvPersistable> List<T> load(String filePath, Class<T> clazz) throws IOException {
		
		List<T> list = new ArrayList<>();
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));		
		String line = "";
		
		while((line = reader.readLine()) != null) {
			
			String[] content = line.split(";");
			
			if(clazz == Client.class) {
				try {					
					Client client = Client.fromCsvRow(line);
					
					boolean emailRepeat = false;
		            for (T c : list) {
		            	Client clientToCheck = (Client) c;
		                if (clientToCheck != null && clientToCheck.getEmail().equalsIgnoreCase(client.getEmail())) {
		                    emailRepeat = true;
		                    break;
		                }
		            }

		            if (emailRepeat) { // If the email has been seen already duplicateEmailException is called
		                throw new DuplicateEmailException("DuplicateEmailException: Duplicate email: " + client.getEmail());
		            }
					
		            list.add((T) client);
		            
				} catch (InvalidClientDataException e) {
		        	ErrorLogger.logError("Client", line, e.getMessage());
		        } catch (DuplicateEmailException e) {
		        	ErrorLogger.logError("Client", line, e.getMessage());
		        }
			}
			else if(clazz == Accommodation.class) {
				Accommodation accommodation = null;
				
				try {
					if(content[0].equals("HOTEL")) {
						accommodation = Hotel.fromCsvRow(line);
					}
					else if(content[0].equals("HOSTEL")){
						accommodation = Hostel.fromCsvRow(line);
					}
					else { // If the type is none of the two, produce an exception
						throw new InvalidAccommodationDataException("InvalidAccommodationDataException: Unknown Accommodation Type: " + content[0]);
					}
					
					list.add((T) accommodation);
					
				} catch (InvalidAccommodationDataException e) {
					ErrorLogger.logError("Accommodation", line, e.getMessage());
				}
			}
			else if(clazz == Transportation.class) {
				Transportation transportation = null;
				
				try {
					if(content[0].equals("FLIGHT")) {
						transportation = Flight.fromCsvRow(line);
					}
					else if(content[0].equals("TRAIN")){
						transportation = Train.fromCsvRow(line);
					}
					else if(content[0].equals("BUS")){
						transportation = Bus.fromCsvRow(line);
					}
					else {
						throw new InvalidTransportDataException("InvalidTransportDataException: Unknown Transportation Type: " + content[0]);
					}
					
					list.add((T) transportation);
					
				} catch (InvalidTransportDataException e) {
					ErrorLogger.logError("Transportation", line, e.getMessage());
				}
			}
		}
		
		return list;
		
	}
	
	// Overloaded method to load the trips after checking if the client, accommodation and transportation exist
	public static <T extends CsvPersistable> List<T> load(String filePath, Class<T> clazz, List<Client> clients, List<Accommodation> accommodationOptions, List<Transportation> transportOptions) throws IOException {
		
		List<T> list = new ArrayList<>();
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));		
		String line = "";
		
		while((line = reader.readLine()) != null) {
			
			Trip trip = null;
			
			String[] content = line.split(";");
		
			try {
				
				trip = Trip.fromCsvRow(line);
				
				// Exceptions are handled if the trips are not able to match a client, transportation or accommodation with it
				if(!clientExists(content[1], clients, clients.size())) {
					throw new EntityNotFoundException("EntityNotFoundException : Client ID not found: " + content[1]);
				}
				if(!accommodationExists(content[2], accommodationOptions, accommodationOptions.size())) {
					throw new EntityNotFoundException("EntityNotFoundException : Accommodation ID not found: " + content[2]);
				}
				if(!transportationExists(content[3], transportOptions, transportOptions.size())) {
					throw new EntityNotFoundException("EntityNotFoundException : Transportation ID not found: " + content[3]);
				}
				
				Client client = findClientByID(content[1], clients, clients.size());
				Accommodation accommodation = findAccommodationByID(content[2], accommodationOptions, accommodationOptions.size());
				Transportation transportation = findTransportationByID(content[3], transportOptions, transportOptions.size());
				
				trip.setClient(client);
				trip.setAccommodation(accommodation);
				trip.setTransportation(transportation);
				
				list.add((T) trip);
			} catch (InvalidTripDataException | EntityNotFoundException e) { 
				ErrorLogger.logError("Trip", line, e.getMessage());
			}
			
		}
		
		return list;
		
	}
	
	// Helper Methods:
	
		private static Client findClientByID(String clientID, List<Client> clients, int clientCount) { // Finds the client based on it's ID
			
			for(Client c : clients) {
				if(clientID.equals(c.getID())) {
					return c;
				}
			}
			
			return null;
			
		}
		
		private static boolean clientExists(String clientID, List<Client> clients, int clientCount) { // Finds the client based on it's ID but uses to see if it exist
			
			for(Client c : clients) {
				if(clientID.equals(c.getID())) {
					return true;
				}
			}
			return false;
		}
		
		private static Accommodation findAccommodationByID(String accommodationID, List<Accommodation> accomodationOptions, int accommodationCount) { // Finds the accommodation based on it's ID
			
			for(Accommodation a : accomodationOptions) {
				if(accommodationID.equals(a.getID())) {
					return a;
				}
			}
			
			return null;
			
		}
		
		private static boolean accommodationExists(String accommodationID, List<Accommodation> accomodationOptions, int accommodationCount) { // Finds the accommodation based on it's ID but uses to see if it exist
			
			for(Accommodation a : accomodationOptions) {
				if(accommodationID.equals(a.getID())) {
					return true;
				}
			}
			
			return false;
		}
		
		private static Transportation findTransportationByID(String transportationID, List<Transportation> transportOptions, int transportCount) { // Finds the transportation based on it's ID
			
			for(Transportation t : transportOptions) {
				if(transportationID.equals(t.getID())) {
					return t;
				}
			}
			
			return null;
			
		}
		
		private static boolean transportationExists(String transportationID, List<Transportation> transportOptions, int transportCount) { // Finds the transportation based on it's ID but uses to see if it exist
			
			for(Transportation t : transportOptions) {
				if(transportationID.equals(t.getID())) {
					return true;
				}
			}
			
			return false;
		}
	
}
