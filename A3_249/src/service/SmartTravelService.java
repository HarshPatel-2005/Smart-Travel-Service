package service;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

// The SmartTravelService file now contains the logic of the program. This file allows the connection to happen between every file

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import client_package.Client;
import exceptions.DuplicateEmailException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidAccommodationDataException;
import exceptions.InvalidClientDataException;
import exceptions.InvalidTransportDataException;
import exceptions.InvalidTripDataException;
import repository.Repository;
import text_files.AccommodationFileManager;
import text_files.ClientFileManager;
import text_files.GenericFileManager;
import text_files.TransportationFileManager;
import text_files.TripFileManager;
import travel_package.Accommodation;
import travel_package.Bus;
import travel_package.Flight;
import travel_package.Hostel;
import travel_package.Hotel;
import travel_package.Train;
import travel_package.Transportation;
import travel_package.Trip;

	public class SmartTravelService {
		
		// Initializing the arrays and the counts for the different instances
		
		// Each time an instance gets created it gets added to it's ArrayList and also it's Repository, where the repository is the list that gets searched through when looking for specific variations
		// The RecentList variables are list where when they get viewed they get added to the list in order to show the user what they have recently viewed
		
		private static Scanner input;
		
		private static List<Client> clients;
		private static Repository<Client> clientRepo;
		private static RecentList<Client> recentClients;
		
		private static List<Trip> trips;	
		private static Repository<Trip> tripsRepo;
		private static RecentList<Trip> recentTrips;
		
		private static List<Transportation> transportOptions;
		private static Repository<Transportation> transportRepo;
		private static RecentList<Transportation> recentTransportations;
		
		private static List<Accommodation> accomodationOptions;
		private static Repository<Accommodation> accomodationRepo;
		private static RecentList<Accommodation> recentAccommodations;
		
		public SmartTravelService(Scanner input) {
			clients = new ArrayList<>();
			accomodationOptions = new ArrayList<>();
	        transportOptions = new ArrayList<>();
	        trips = new ArrayList<>();
	        
	        clientRepo = new Repository<>();
	        tripsRepo = new Repository<>();
	        transportRepo = new Repository<>();
	        accomodationRepo = new Repository<>();
	        
	        recentClients = new RecentList<>();
	        recentTrips = new RecentList<>();
	        recentTransportations = new RecentList<>();
	        recentAccommodations = new RecentList<>();
	        
	        SmartTravelService.input = input;
		}
	
		// -------------------------
	    // Methods for client
	    // -------------------------

	    public void addClient() {
	        System.out.print("Enter client's first name: ");
	        String firstName = input.nextLine();
	        System.out.print("Enter client's last name: ");
	        String lastName = input.nextLine();
	        System.out.print("Enter client's email: ");
	        String email = input.nextLine();

	        // This section runs through every client until the amount of clients exist has reached and compares the different emails to the one that is currently being created
	        // If the email exist, the user shall not proceed through due to the fact that the email already exist for a different client
	        
	        try {
	        	
	            for (Client c : clients) {
	                if (c.getEmail().equalsIgnoreCase(email)) {
	                    throw new DuplicateEmailException("Email already exists!");
	                }
	            }
	            
	            Client newClient = new Client(firstName, lastName, email);
	            clients.add(newClient);
	            clientRepo.add(newClient);
	            
	            System.out.println("Client added successfully! \nClient ID: " + newClient.getID());
	            
	        } catch (InvalidClientDataException e) {
	            System.out.println("Error: Invalid client data. " + e.getMessage()); // If the data is incorrect, it catches InvalidClientDataException 
	        } catch (DuplicateEmailException e) {
	            System.out.println("Duplicate Email: " + e.getMessage()); // If the email exist for a different user, it catches DuplicateEmailException
	        }
	    }

	    public void editClient() {
	    	
	        if (clients.size() == 0) { 
	        	System.out.println("There are no clients! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("What client would you like to edit? (Enter Client ID): ");
				for (Client c : clients) 
					System.out.print("\nClient ID: " + c.getID());
				
				System.out.print("\nClient ID choice: ");
				String clientID = input.nextLine();

				Client clientToEdit = null;
				for (Client c : clients) {
				    if (c.getID().equalsIgnoreCase(clientID)) { 
				    	clientToEdit = c; 
				    	break; 
				    }
				}
				
				if (clientToEdit == null) { 
					throw new EntityNotFoundException("Client Not Found!");
				}

				System.out.println("What would you like to edit?");
				System.out.println("1. First Name\n2. Last Name\n3. Email");
				System.out.print("Enter your choice: ");
				int editChoice = input.nextInt();
				input.nextLine();

				// Since we are updating the user's clients in this method, we must also verify what they have updated to is valid. This is why it will run through the switch cases depending on what they pick,
				// then it will let them put an option and this option is sent to the Client class where in those methods that we are updating the information we can throw InvalidClientException if their input is invalid.
				// This will then be caught here
				switch (editChoice) {
				    case 1:
				        System.out.print("Enter new first name: ");
				        try { 
				        	clientToEdit.setFirstName(input.nextLine()); 
				        	System.out.println("First name updated successfully!"); 
				        }
				        catch (InvalidClientDataException e) { 
				        	System.out.println("Error: " + e.getMessage()); 
				        }
				        break;
				    case 2:
				        System.out.print("Enter new last name: ");
				        try { 
				        	clientToEdit.setLastName(input.nextLine()); 
				        	System.out.println("Last name updated successfully!"); 
				        }
				        catch (InvalidClientDataException e) { 
				        	System.out.println("Error: " + e.getMessage()); 
				        }
				        break;
				    case 3:
				        System.out.print("Enter new email: ");
				        try { 
				        	clientToEdit.setEmail(input.nextLine()); 
				        	System.out.println("Email updated successfully!"); 
				        }
				        catch (InvalidClientDataException e) { 
				        	System.out.println("Error: " + e.getMessage()); 
				        }
				        break;
				    default: 
				    	System.out.println("Invalid choice!"); 
				    	break;
				}
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    public void deleteClient() {
	    	
	        if (clients.size() == 0) { 
	        	System.out.println("There are no clients! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("What client would you like to delete? (Enter Client ID) ");
				for (Client c : clients) 
					System.out.print("\nClient ID: " + c.getID());
				
				System.out.print("\nClient ID choice: ");
				String clientID = input.nextLine();

				Client clientToDelete = null;
				for (Client c : clients) {
				    if (c.getID().equalsIgnoreCase(clientID)) { 
				    	clientToDelete = c; 
				    	break; 
				    }
				}
				
				if (clientToDelete == null) { 
					throw new EntityNotFoundException("Client Not Found!");
				}
				
				clients.remove(clientToDelete);
				clientRepo.remove(clientToDelete);
				
				System.out.println("Client deleted successfully!");
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    public void listAllClients() {
	        if (clients.size() == 0) 
	        	System.out.println("No clients found!");
	        else 
	        	for (Client c : clients) 
	        		System.out.println(c.toString());
	    }
	    
	    public Client getClient(int clientIndex) {
	    	if (clientIndex < 0 || clientIndex >= clients.size()) {
	            return null;
	        }
	        return clients.get(clientIndex);
	    }
	    
	    public int getClientCount() {
	    	return clients.size();
	    }

	    // -------------------------
	    // Methods for trip
	    // -------------------------

	    public void createTrip() {
	    	
	        if (clients.size() == 0) { 
	        	System.out.println("There are no clients! Returning to Menu!"); 
	        	return; 
	        }

	        try {
	            System.out.print("\nAvailable Client Options:");
	            
	            for (Client c : clients) 
	            	System.out.print("\nClient ID: " + c.getID());
	            
	            System.out.print("\nClient ID choice: ");
	            String clientID = input.nextLine();

	            Client clientToCompare = null;
	            for (Client c : clients) {
	                if (c.getID().equalsIgnoreCase(clientID)) { 
	                	clientToCompare = c; 
	                	break; 
	                }
	            }
	            
	            if (clientToCompare == null) // If the client does not exist in the system, EntityNotFoundException exception will handle this issue
	            	throw new EntityNotFoundException("Client not found!");

	            if (transportOptions.size() == 0) { 
	            	System.out.println("There are no transportation options available! Returning to Menu!"); 
	            	return; 
	            }
	            
	            System.out.println("\nAvailable transportation options:");
	            for (Transportation t : transportOptions) 
	            	System.out.println("Transportation ID: " + t.getID());
	            
	            System.out.print("Choice: ");
	            String transportID = input.nextLine();

	            Transportation selectedTransport = null;
	            for (Transportation t : transportOptions) {
	                if (t.getID().equalsIgnoreCase(transportID)) { 
	                	selectedTransport = t; 
	                	break; 
	                }
	            }
	            
	            if (selectedTransport == null) // If the transportation does not exist in the system, EntityNotFoundException will handle this issue
	            	throw new EntityNotFoundException("Transportation not found!");

	            if (accomodationOptions.size() == 0) { 
	            	System.out.println("There are no accommodation options available! Returning to Menu!"); 
	            	return; 
	            }
	            
	            System.out.print("\nAvailable accommodation options:");
	            for (Accommodation a : accomodationOptions) 
	            	System.out.print("\nAccommodation ID: " + a.getID());
	            
	            System.out.print("\nChoice: ");
	            String accomodationID = input.nextLine();

	            Accommodation selectedAccomodation = null;
	            for (Accommodation a : accomodationOptions) {
	                if (a.getID().equalsIgnoreCase(accomodationID)) { 
	                	selectedAccomodation = a; 
	                	break; 
	                }
	            }
	            
	            if (selectedAccomodation == null) // If the accommodation does not exist in the system, EntityNotFoundException will handle this issue
	            	throw new EntityNotFoundException("Accommodation not found!");
	            
	            // If everything exist nothing will need to be caught and will continue

	            System.out.print("\nEnter trip destination: ");
	            	String destination = input.nextLine();
	            System.out.print("Enter trip duration (in days): ");
	            	int duration = input.nextInt();
	            System.out.print("Enter trip base price: ");
	            	double basePrice = input.nextDouble();
	            	
	            input.nextLine();

	            Trip newTrip = new Trip(destination, duration, basePrice, clientToCompare, selectedTransport, selectedAccomodation);
	            
	            trips.add(newTrip);
	            tripsRepo.add(newTrip);
	            
	            clientToCompare.addAmountSpent(newTrip.getTotalCost());
	            System.out.println("Trip created successfully! \nTrip ID: " + newTrip.getID());

	        } catch (EntityNotFoundException e) {
	            System.out.println("Error: " + e.getMessage());
	        } catch (InvalidTripDataException e) {
	            System.out.println("Error: Invalid trip data. " + e.getMessage());
	        }
	    }

	    public void editTrip() {
	    	
	        if (trips.size() == 0) { 
	        	System.out.println("There are no Trips! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("What trip would you like to edit? (Enter Trip ID)");
				for (Trip tr : trips) 
					System.out.print("\nTrip ID: " + tr.getID());
				
				System.out.print("\nChoice: ");
				String tripID = input.nextLine();

				Trip tripToEdit = null;
				for (Trip tr : trips) {
				    if (tr.getID().equalsIgnoreCase(tripID)) { 
				    	tripToEdit = tr;
				    	break; 
				    }
				}
				
				if (tripToEdit == null) { // If the trip the user input does not exist the program will simply catch an EntityNotFoundException 
					throw new EntityNotFoundException("Trip not found!");
				}

				System.out.println("What would you like to edit?\n1. Destination\n2. Duration\n3. Base Price");
				System.out.print("Enter your choice: ");
				int editChoice = input.nextInt();
				input.nextLine();

				switch (editChoice) {
				    case 1:
				        System.out.print("Enter new destination: ");
				        try {
				            tripToEdit.setDestination(input.nextLine());
				            System.out.println("Destination updated successfully!");
				        } catch (InvalidTripDataException e) { 
				        	System.out.println("Error: " + e.getMessage()); 
				        }
				        break;
				    case 2:
				        System.out.print("Enter new duration (in days): ");
				        try {
				            tripToEdit.setDuration(input.nextInt());
				            System.out.println("Duration updated successfully!");
				        } catch (InvalidTripDataException e) { 
				        	System.out.println("Error: " + e.getMessage()); 
				        }
				        break;
				    case 3:
				        System.out.print("Enter new base price: ");
				        try {
				            tripToEdit.setBasePrice(input.nextDouble());
				            System.out.println("Base price updated successfully!");
				        } catch (InvalidTripDataException e) { 
				        	System.out.println("Error: " + e.getMessage()); 
				        }
				        break;
				    default: System.out.println("Invalid choice!"); break;
				}
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    public void cancelTrip() {
	    	
	        if (trips.size() == 0) { 
	        	System.out.println("There are no trips! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Which trip would you like to cancel? (Enter Trip Id) ");
				for (Trip tr : trips) 
					System.out.print("\nTrip ID: " + tr.getID());
				
				System.out.print("\nChoice: ");
				String tripId = input.nextLine();

				Trip tripToCancel = null;
				for (Trip tr : trips) {
				    if (tr.getID().equalsIgnoreCase(tripId)) { 
				    	tripToCancel = tr; 
				    }
				}
				if (tripToCancel == null) { 
					throw new EntityNotFoundException("Trip not found!");
				}
				
				trips.remove(tripToCancel);
				tripsRepo.remove(tripToCancel);
				
				System.out.println("Trip cancelled successfully!");
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    public static void listAllTrips() {
	        if (trips.size() == 0) {
	        	System.out.println("No trips found!");
	        }
	        else {
	        	for (Trip tr : trips) {
	        		System.out.println("-------------------");
	        		System.out.println(tr.toString());
	        		System.out.println("\n" + tr.getClient().toString());
	        		System.out.println(tr.getTransportation().toString());
	        		System.out.println(tr.getAccommodation().toString());
	        		System.out.println("-------------------");

	        		recentClients.addRecent(tr.getClient());
	        		recentTrips.addRecent(tr);
	        		recentTransportations.addRecent(tr.getTransportation());
	        		recentAccommodations.addRecent(tr.getAccommodation());
	        	}
	        }
	    }

	    public void listTripsByClient() {
	    	
	        if (clients.size() == 0) { 
	        	System.out.println("There are no clients! Returning to Menu!"); 
	        	return; 
	        }
	        if (trips.size() == 0) { 
	        	System.out.println("There are no Trips! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Enter Client ID to list their trips ");
				for (Client c : clients) 
					System.out.print("\nClient ID: " + c.getID());
				
				System.out.print("\nChoice: ");
				String clientID = input.nextLine();

				boolean found = false;
				for (Trip tr : trips) {
				    if (tr.getClient().getID().equalsIgnoreCase(clientID)) {
				        System.out.println(tr.toString());
				        recentTrips.addRecent(tr);
				        found = true;
				    }
				}
				
				if (!found) 
					throw new EntityNotFoundException("Client not found!");
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }
	    
	    public double calculateTripTotal(Trip trip) { // Method to calculate the total value of the trip
	    	return trip.getTotalCost();
	    }
	    
	    public Trip getTrip(int tripIndex) { // Method to receive an index and match it with the index of the array in this file
	    	if (tripIndex < 0 || tripIndex >= trips.size()) { // If the index is invalid return nothing
	            return null;
	        }
	        return trips.get(tripIndex);
	    }
	    
	    public int getTripCount() { // Method to receive the number of trips
	    	return trips.size();
	    }
	    
	    public List<Trip> getAllTrips() { // Method to receive the entire array to use outside of this class
	    	return trips;
	    }

	    // -------------------------
	    // Methods for transportation
	    // -------------------------

	    public void addTransportation() {
	    	
	        System.out.print("Enter company name: ");
	        	String companyName = input.nextLine();
	        System.out.print("Enter departure city: ");
	        	String departureCity = input.nextLine();
	        System.out.print("Enter arrival city: ");
	        	String arrivalCity = input.nextLine();
	        System.out.print("Enter transportation type (Flight, Train, Bus): ");
	        	String type = input.nextLine();

	        try {
	            Transportation newTransport;
	            switch (type.toLowerCase()) {
	                case "flight":
	                    System.out.print("Enter airline name: ");
	                    	String airlineName = input.nextLine();
	                    System.out.print("Enter luggage allowance (in kg): ");
	                    	double luggageAllowance = input.nextDouble();
	                    newTransport = new Flight(companyName, departureCity, arrivalCity, airlineName, luggageAllowance);
	                    break;
	                case "train":
	                    System.out.print("Enter train type: ");
	                    	String trainType = input.nextLine();
	                    System.out.print("Enter seat class: ");
	                    	String seatClass = input.next();
	                    newTransport = new Train(companyName, departureCity, arrivalCity, trainType, seatClass);
	                    break;
	                case "bus":
	                    System.out.print("Enter bus company: ");
	                    	String busCompany = input.nextLine();
	                    System.out.print("Enter number of stops: ");
	                    	int numOfStops = input.nextInt();	                    
	                    newTransport = new Bus(companyName, departureCity, arrivalCity, busCompany, numOfStops);
	                    break;
	                default:
	                    System.out.println("Invalid transportation type!");
	                    return;
	            }
	            
	            input.nextLine();
	            transportOptions.add(newTransport);
	            transportRepo.add(newTransport);
	            
	            System.out.println("Transportation option added successfully! \nTransport ID: " + newTransport.getID());
	        } catch (InvalidTransportDataException e) {
	            System.out.println("Error: Invalid transportation data. " + e.getMessage());
	        }
	    }

	    public void removeTransportation() {
	    	
	        if (transportOptions.size() == 0) { 
	        	System.out.println("There are no transportations! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Enter Transport ID to remove ");
				for (Transportation t : transportOptions) 
					System.out.print("\nTransport ID: " + t.getID());
				
				System.out.print("\nChoice: ");
				String transportID = input.nextLine();

				Transportation transportToRemove = null;
				for (Transportation t : transportOptions) {
				    if (t.getID().equalsIgnoreCase(transportID)) { 
				    	transportToRemove = t; 
				    	break; 
				    }
				}
				
				if (transportToRemove == null) { // If the transportation is not found, allow EntityNotFoundException to handle this 
					throw new EntityNotFoundException("Transportation Not Found!");
				}
				
				transportOptions.remove(transportToRemove);
				transportRepo.remove(transportToRemove);
				
				System.out.println("Transportation option removed successfully!");				
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    public void listTransportationByType() {
	        int counter = 0;
	        if (transportOptions.size() == 0) { 
	        	System.out.println("There are no Transportations! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Which Transportation type would you like to view (Flight, Train, Bus): ");
				String userTransportationInput = input.nextLine();

				for (Transportation t : transportOptions) {
				    if (t.getClass().getSimpleName().equalsIgnoreCase(userTransportationInput)) {
				        System.out.println("\n" + t.toString());
				        counter++;
				        recentTransportations.addRecent(t);
				    }
				}
				
				if (counter == 0)  // If the transportation type the user input does not exist it will throw an EntityNotFoundException
					throw new EntityNotFoundException("There are no transportations of type: " + userTransportationInput);
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    // -------------------------
	    // Methods for accommodation
	    // -------------------------

	    public void addAccomodation() {
	        System.out.print("Enter accommodation name: ");
	        	String name = input.nextLine();
	        System.out.print("Enter accommodation location: ");
	        	String location = input.nextLine();
	        System.out.print("Enter price per night: ");
	        double pricePerNight = input.nextDouble();
	        	input.nextLine();

	        System.out.print("Enter accommodation type (Hotel, Hostel): ");
	        String type = input.nextLine();

	        Accommodation newAccommodation = null;
	        try {
	            switch (type.toLowerCase()) {
	                case "hotel":
	                    System.out.print("Enter star rating (1-5): ");
	                    	int starRating = input.nextInt();
	                    newAccommodation = new Hotel(name, location, pricePerNight, starRating);
	                    break;
	                case "hostel":
	                    System.out.print("Enter number of shared beds: ");
	                    	int numOfSharedBeds = input.nextInt();
	                    newAccommodation = new Hostel(name, location, pricePerNight, numOfSharedBeds);
	                    break;
	                default:
	                    System.out.println("Invalid accommodation type!");
	                    return;
	            }
	            
	            accomodationOptions.add(newAccommodation);
	            accomodationRepo.add(newAccommodation);
	            
	            System.out.println("Accommodation option added successfully! \nAccommodation ID: " + newAccommodation.getID());
	        } catch (InvalidAccommodationDataException e) {
	            System.out.println("Error: Invalid accommodation data. " + e.getMessage());
	        }
	    }

	    public void removeAccomodation() {
	    	
	        if (accomodationOptions.size() == 0) { 
	        	System.out.println("There are no accommodations! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Enter Accommodation ID to remove ");
				for (Accommodation a : accomodationOptions) 
					System.out.print("\nAccommodation ID: " + a.getID());
				
				System.out.print("\nChoice: ");
				String accommodationID = input.nextLine();

				Accommodation accommodationToRemove = null;
				for (Accommodation a : accomodationOptions) {
				    if (a.getID().equalsIgnoreCase(accommodationID)) { 
				    	accommodationToRemove = a; 
				    	break; 
				    }
				}
				
				if (accommodationToRemove == null) { 
					// If the accommodation is not found, allow EntityNotFoundException to handle this 
					throw new EntityNotFoundException("Transportation Not Found!");
				}
				
				accomodationOptions.remove(accommodationToRemove);
				accomodationRepo.remove(accommodationToRemove);
				
				System.out.println("Accommodation option removed successfully!");
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    public void listAccomodationByType() {
	        int counter = 0;
	        if (accomodationOptions.size() == 0) { 
	        	System.out.println("There are no accommodations! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Which Accommodation type would you like to view (Hotel, Hostel): ");
				String userAccommodationInput = input.nextLine();

				for (Accommodation a : accomodationOptions) {
				    if (a.getClass().getSimpleName().equalsIgnoreCase(userAccommodationInput)) {
				        System.out.println(a.toString());
				        counter++;
				        recentAccommodations.addRecent(a);
				    }
				}
				if (counter == 0) 
					throw new EntityNotFoundException("There are no transportations of type: " + userAccommodationInput);
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    // -------------------------
	    // Additional Operations
	    // -------------------------

	    // For General Usage
	    public void displayMostExpensiveTrip() {
	        if (trips.size() == 0) { 
	        	System.out.println("No trips found!"); 
	        	return; 
	        }

	        double highestCost = 0.0;
	        Trip mostExpensiveTrip = trips.get(0);

	        for (Trip tr : trips) {
	            if (tr.getTotalCost() > highestCost) {
	                highestCost = tr.getTotalCost();
	                mostExpensiveTrip = tr;
	            }
	        }
	        System.out.println("Most Expensive trip ID: " + mostExpensiveTrip.getID() + ", Cost: $" + highestCost);
	        recentTrips.addRecent(mostExpensiveTrip);
	    }
	    
	    // For Predefined Scenario
	    public void displayMostExpensiveTrip(Trip[] tripArr) {
	        if (tripArr.length == 0) { 
	        	System.out.println("No trips found!"); 
	        	return; 
	        }

	        double highestCost = 0.0;
	        Trip mostExpensiveTrip = tripArr[0];

	        for (int i = 0; i < tripArr.length; i++) {
	            if (tripArr[i].getTotalCost() > highestCost) {
	                highestCost = tripArr[i].getTotalCost();
	                mostExpensiveTrip = tripArr[i];
	            }
	        }
	        System.out.println("Most Expensive trip ID: " + mostExpensiveTrip.getID() + ", Cost: $" + highestCost);
	    }

	    public void displayCostOfTrip() {
	        int counter = 0;
	        
	        if (trips.size() == 0) { 
	        	System.out.println("No trips found!"); 
	        	return; 
	        }

	        try {
				Trip displayTrip = trips.get(0);
				System.out.println("\nAvailable trips:");
				for (Trip tr : trips) 
					System.out.println(tr.getID());

				System.out.print("Which trip's cost would you like to display? ");
					String chosenTrip = input.nextLine();

				for (Trip tr : trips) {
				    if (tr.getID().equalsIgnoreCase(chosenTrip)) { 
				    	displayTrip = tr; 
				    	counter++; 
				    	recentTrips.addRecent(displayTrip);
				    }
				}

				if (counter == 0) 
					throw new EntityNotFoundException("No Trips Found!");
				else 
					System.out.println("Total cost of Trip ID: " + displayTrip.getID() + " is $" + displayTrip.getTotalCost());
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    
	    // Copy Arrays (FOR PREDEFINED)
	    
	    public Transportation[] copyTransportArray(Transportation[] original) {
	        Transportation[] copyArray = new Transportation[original.length];
	        if (original.length == 0) { 
	        	System.out.println("Nothing to copy! Returning to menu!"); }
	        else {
	            for (int i = 0; i < original.length; i++) {
	                if (original[i] != null) copyArray[i] = original[i].copy();
	            }
	        }
	        return copyArray;
	    }

	    public Accommodation[] copyAccommodationArray(Accommodation[] original) {
	        Accommodation[] copyArray = new Accommodation[original.length];
	        if (original.length == 0) { 
	        	System.out.println("Nothing to copy! Returning to menu!"); }
	        else {
	            for (int i = 0; i < original.length; i++) {
	                if (original[i] != null) copyArray[i] = original[i].copy();
	            }
	        }
	        return copyArray;
	    }
	    
	    // Copy Arrays (FOR USAGE) 
	    // These two are overwriting the other two methods since this one is not taking any parameters so that in the driver we can use this without concern
	    
	    public List<Transportation> copyTransportArray() {
	        List<Transportation> copyArray = new ArrayList<>(transportOptions.size());
	        if (transportOptions.size() == 0) { 
	        	System.out.println("Nothing to copy! Returning to menu!"); }
	        else {
	            for (Transportation t : transportOptions) {
	                if (t != null) 
	                	copyArray.add(t.copy());
	            }
	        }
	        
	        System.out.println("Transportation Array Copied!");
	        
	        return copyArray;
	    }

	    public List<Accommodation> copyAccommodationArray() {
	    	List<Accommodation> copyArray = new ArrayList<>(accomodationOptions.size());
	        if (accomodationOptions.size() == 0) { 
	        	System.out.println("Nothing to copy! Returning to menu!"); }
	        else {
	            for (Accommodation a : accomodationOptions) {
	                if (a != null) 
	                	copyArray.add(a.copy());
	            }
	        }
	        
	        System.out.println("Accommodation Array Copied!");
	        
	        return copyArray;
	    }
	    
	    // This method loads the saveClients depending on the folder path the programmer uses
	    // It has the possibility to produce a IOException on every file which is why we separate them in case everything is fine
		public static void SaveAllData(String folderPath){
			try {
				GenericFileManager.save(clients, folderPath + "clients.csv");
				if(clients.size() == 0) { System.out.println("There are no clients to save!"); }
			} catch (IOException e) {
				System.out.println("Client File does not exist!");
			}
			
			try {
				GenericFileManager.save(transportOptions, folderPath + "transports.csv");
				if(transportOptions.size() == 0) { System.out.println("There are no transportations to save!"); }
			} catch (IOException e) {
				System.out.println("Transportation File does not exist!");
			}
			
			try {
				GenericFileManager.save(accomodationOptions, folderPath + "accommodations.csv");
				if(accomodationOptions.size() == 0) { System.out.println("There are no accommodations to save!"); }
			} catch (IOException e) {
				System.out.println("Accommodation File does not exist!");
			}
			
			try {
				GenericFileManager.save(trips, folderPath + "trips.csv");
				if(trips.size() == 0) { System.out.println("There are no trips to save!"); }
			} catch (IOException e) {
				System.out.println("Trip File does not exist!");
			}
			
		}

		// This method loads the file based on the folderPath
		// It can not only throw an IOException which is why we must catch it in case the file does not exist, but also incorrect data. If the files have wrong inputs then they should not be loaded into the system
		public static void LoadAllData(String folderPath) throws InvalidClientDataException, InvalidTransportDataException, InvalidAccommodationDataException, InvalidTripDataException {
			
			boolean useGenericPersistence = true;
			
			if(useGenericPersistence) {
				try {
					clients = GenericFileManager.load(folderPath + "clients.csv", Client.class);
					
					for(Client c: clients) {
						clientRepo.add(c);
					}
					
					System.out.println("Loaded clients into the system!");
				} catch (IOException e) {
					System.out.println("Cannot find the file location for clients"); // Error-Logger
				}
				
				try {
					accomodationOptions = GenericFileManager.load(folderPath + "accommodations.csv", Accommodation.class);
					
					for(Accommodation a : accomodationOptions) {
						accomodationRepo.add(a);
					}

					System.out.println("Loaded accommodations into the system!");
				} catch (IOException e) {
					System.out.println("Cannot find the file location for accommodations"); // Error-Logger
				}
				
				try {
					transportOptions = GenericFileManager.load(folderPath + "transports.csv", Transportation.class);
					
					for(Transportation tr : transportOptions) {
						transportRepo.add(tr);
					}
					
					System.out.println("Loaded Transportations into the system!");
				} catch (IOException e) {
					System.out.println("Cannot find the file location for transportations"); // Error-Logger
				}
				try {
					trips = GenericFileManager.load(folderPath + "trips.csv", Trip.class, clients, accomodationOptions, transportOptions);
					
					// Loads in the total spent by each client when loaded in the system
					for (int i = 0; i < clients.size(); i++) {
			        	
			            Client client = clients.get(i);
			            double spent = 0;
			            
			            for(Trip tr : trips) {
			            	if(tr.getClient().getID().equalsIgnoreCase(client.getID())) {
			            		spent += tr.getTotalCost();
			            	}
			            }
			            
			            client.addAmountSpent(spent);
					}
					
					for(Trip t : trips) {
						tripsRepo.add(t);
					}
					System.out.println("Loaded Trips into the system!");
				} catch (IOException e) {
					System.out.println("Cannot find the file location for trips"); // Error-Logger
				}
			}
			else { // Backup
				try {
					ClientFileManager.loadClients(clients, clients.size(), folderPath + "clients.csv");
					System.out.println("Loaded clients into the system!");
				} catch (IOException e) {
					System.out.println("Cannot find the file location for clients"); // Error-Logger
				}
				
				try {
					AccommodationFileManager.loadAccommodation(accomodationOptions, accomodationOptions.size(), folderPath + "accommodations.csv");
					System.out.println("Loaded accommodations into the system!");
				} catch (IOException e) {
					System.out.println("Cannot find the file location for accommodations"); // Error-Logger
				}
				
				try {
					TransportationFileManager.loadTransportation(transportOptions, transportOptions.size(), folderPath + "transports.csv");
					System.out.println("Loaded Transportations into the system!");
				} catch (IOException e) {
					System.out.println("Cannot find the file location for transportations"); // Error-Logger
				}
				try {
					TripFileManager.loadTrips(clients, clients.size(), accomodationOptions, accomodationOptions.size(), transportOptions, transportOptions.size(), trips, trips.size(), folderPath + "trips.csv");
					
					// Loads in the total spent by each client when loaded in the system
					for (int i = 0; i < clients.size(); i++) {
			        	
			            Client client = clients.get(i);
			            double spent = 0;
			            
			            for(Trip tr : trips) {
			            	if(tr.getClient().getID().equalsIgnoreCase(client.getID())) {
			            		spent += tr.getTotalCost();
			            	}
			            }
			            
			            client.addAmountSpent(spent);
					}
					
					System.out.println("Loaded Trips into the system!");
				} catch (IOException e) {
					System.out.println("Cannot find the file location for trips"); // Error-Logger
				}
			}
			
		}

		// Lists all the trips with the client and such
		public static void listAllData() {
			listAllTrips();
		}
		
		// -------------------------
	    // Advanced Analytics
	    // -------------------------
		
		public void tripsByDestination() {
			
			List<Trip> tripFiltered = null;
			
			if(trips.size() == 0) {
				System.out.println("There are no trips to filter through!");
				return;
			}
			
			System.out.print("What destination would you like to filter your trips for: ");
				String userInputDestination = input.next();
				
			Predicate<Trip> destinationString = t -> t.getDestination().equalsIgnoreCase(userInputDestination);
			tripFiltered = tripsRepo.filter(destinationString);

			boolean found = false;
			for(Trip t : tripFiltered) {
				found = true;
				System.out.print("\nFiltered Trip for destination: " + userInputDestination);
				System.out.println("\n-----------------" + t.toString() + "\n-----------------");
				recentTrips.addRecent(t);
			}
			
			if(!found) {
				System.out.println("There are no destinations for the trip: " + userInputDestination);
			}
				
		}
		
		public void tripsByCostRange() {
			
			List<Trip> tripFiltered = null;
			
			double tripCostMin;
			double tripCostMax;
			
			double tempMin;
			double tempMax;
			
			if(trips.size() == 0) {
				System.out.println("There are no trips to filter through!");
				return;
			}
			
			System.out.print("\nWhat minimum trip price would you like to filter your trips for: ");
				tempMin = input.nextDouble();
				
			while(tempMin < 0) {
				System.out.print("\nMinimum price cannot be less than 0. Try again: ");
					tempMin = input.nextDouble();
			}
				
			System.out.print("\nWhat maximum trip price would you like to filter your trips for: ");
				tempMax = input.nextDouble();
				
			while(tempMax < tempMin) {
				System.out.print("\nMaximum price cannot be less than the minimum price. Try again: ");
					tempMax = input.nextDouble();
			}
				
			tripCostMin = tempMin;
			tripCostMax = tempMax;
				
			Predicate<Trip> minCostTrip = t -> t.getTotalCost() > tripCostMin && t.getTotalCost() < tripCostMax;
			tripFiltered = tripsRepo.filter(minCostTrip);
			
			if(tripFiltered.size() == 0) {
				System.out.println("\nThere are no trips within the range of $" + tripCostMin + " and $" + tripCostMax);
				return;
			}
			
			System.out.print("\nFiltered Trip for within the range of $" + tripCostMin + " and $" + tripCostMax + ":");
			
			for(Trip t : tripFiltered) {
				System.out.println("\n--------------------------");
				System.out.println(t.toString());
				System.out.println("\n" + t.getClient().toString());
				System.out.println(t.getTransportation().toString());
				System.out.println(t.getAccommodation().toString());
				System.out.println("\n--------------------------");
				recentTrips.addRecent(t);
			}
			
		}
		
		public void topClientsBySpending() {
			
			if(recentClients.isEmpty()) {
				System.out.println("There has not been any clients that you have viewed!");
				return;
			}
			
			List<Client> sortedClients = new ArrayList<>(recentClients.getList());
			
			Collections.sort(sortedClients);
			
			System.out.println("From the recently viewed clients, these are top clients by spending: ");
			
			int counter = 1;
			for(Client c : sortedClients) {
				System.out.println(counter + ". " + c.getID() + ": $" + c.getTotalSpent());
				counter++;
			}
			
		}
		
		public void viewRecentTrips() {
			
			if(recentTrips.isEmpty()) {
				System.out.println("There has not been any trips that you have viewed!");
				return;
			}
			
			System.out.println("\nRecently viewed trips: ");
			
			System.out.print("-------------------");
			recentTrips.printRecent();
		}
		
		public void SmartSortCollections() {
			
			System.out.println("Which Class would you like to see from the following options: "
					+ "\n1. Client \n2. Trip \n3. Accommodation \n4. Transportation");
			System.out.print("Choice: ");
				int userChoice = input.nextInt();
				
			while(userChoice > 4 || userChoice < 1) {
				System.out.print("Invalid Input. Try again: ");
					userChoice = input.nextInt();
			}
			
			switch(userChoice) {
				case 1: {
					// Sorting Clients: 
					
					if(recentClients.isEmpty()) {
						System.out.println("There has not been any clients that you have viewed!");
						return;
					}
					
					List<Client> sortedClients = new ArrayList<>(recentClients.getList());
					
					Collections.sort(sortedClients);
					
					System.out.println("\nFrom the recently viewed clients, these are top clients by spending: ");
					
					System.out.println("---------------------------------------------");
					
					int counterClient = 1;
					for(Client c : sortedClients) {
						System.out.println(counterClient + ". " + c.getID() + ": $" + c.getTotalSpent());
						counterClient++;
					}
					
					System.out.println("---------------------------------------------");
					
					break;
				}
				case 2: {
					// Sorting Trips:
					
					if(recentTrips.isEmpty()) {
						System.out.println("There has not been any trips that you have viewed!");
						return;
					}
					
					List<Trip> sortedTrips = new ArrayList<>(recentTrips.getList());
					
					Collections.sort(sortedTrips);
					
					System.out.println("\nFrom the recently viewed trips, these are top trips by their cost: ");
					
					System.out.println("---------------------------------------------");
					
					int counterTrip = 1;
					for(Trip t : sortedTrips) {
						System.out.println(counterTrip + ". " + t.getID() + ": $" + t.getTotalCost());
						counterTrip++;
					}
					
					System.out.println("---------------------------------------------");
					
					break;
				}
				case 3: {
					// Sorting Accommodations:
					
					if(recentAccommodations.isEmpty()) {
						System.out.println("There has not been any accommodations that you have viewed!");
						return;
					}
					
					List<Accommodation> sortedAccommodation = new ArrayList<>(recentAccommodations.getList());
					
					Collections.sort(sortedAccommodation);
					
					System.out.println("\nFrom the recently viewed accommodations, these are top trips by their cost: ");
					
					System.out.println("---------------------------------------------");
					
					int counterAccommodations = 1;
					for(Accommodation a : sortedAccommodation) {
						System.out.println(counterAccommodations + ". " + a.getID() + ": $" + a.getPricePerNight());
						counterAccommodations++;
					}
					
					System.out.println("---------------------------------------------");
					
					break;
				}
				case 4: {
					// Sorting Transportations:
					
					if(recentTransportations.isEmpty()) {
						System.out.println("There has not been any transportation that you have viewed!");
						return;
					}
					
					List<Transportation> sortedTransportation = new ArrayList<>(recentTransportations.getList());
					
					Collections.sort(sortedTransportation);
					
					System.out.println("\nFrom the recently viewed transportations, these are top trips by their cost: ");
					
					System.out.println("---------------------------------------------");
					
					int counterTransportation = 1;
					for(Transportation tr : sortedTransportation) {
						System.out.println(counterTransportation + ". " + tr.getID() + ": $" + tr.calculateCost(0));
						counterTransportation++;
					}
					
					System.out.println("---------------------------------------------");
					
					break;
				}
					
			}
			
		}

	    // -------------------------
	    // Predefined Scenario
	    // -------------------------
	    public void predefinedScenario() {
	        try {
	            // Clients
	            Client client1 = new Client("Pratik", "Patel", "xxx@gmail.com");
	            Client client2 = new Client("Harsh", "Patel", "yyy@gmail.com");
	            Client client3 = new Client("David", "Durocher", "zzz@gmail.com");
	            // Changed duplicate email to unique email zzz2@gmail.com
	            Client client4 = new Client("David", "Durocher", "zzz2@gmail.com");

	            // Transportation
	            Transportation transport1 = new Flight("Elite Voyage", "Montreal", "Tokyo", "Air-Canada", 200);
	            Transportation transport2 = new Flight("Elite Voyage", "Toronto", "Paris", "Air-Canada", 250);
	            Transportation transport3 = new Train("Union Pacific", "Vancouver", "BC", "High-Speed", "T405");
	            Transportation transport4 = new Train("Union Pacific", "BC", "Nunavut", "Railway", "C507");
	            Transportation transport5 = new Bus("Apex Expeditions", "Montreal", "Toronto", "Megabus", 3);
	            Transportation transport6 = new Bus("Apex Expeditions", "Montreal", "Toronto", "Megabus", 3);

	            // Accommodation
	            Accommodation accommodation1 = new Hotel("The Peninsula", "Tokyo", 35, 4);
	            Accommodation accommodation2 = new Hotel("Azure Oasis", "Paris", 40, 5);
	            Accommodation accommodation3 = new Hostel("Dreamcatcher Hostel", "Montreal", 25, 3);
	            Accommodation accommodation4 = new Hostel("Sun Beach Inn", "Miami", 30, 2);
	            Accommodation accommodation5 = new Hostel("Sun Beach Inn", "Miami", 30, 2);

	            // Trip2 duration changed from 21 to 18 (must be 1-20 days)
	            Trip trip1 = new Trip("Japan", 14, 1500, client1, transport1, accommodation1);
	            Trip trip2 = new Trip("France", 18, 1350, client2, transport2, accommodation2);
	            Trip trip3 = new Trip("Canada", 7, 800, client3, transport6, accommodation3);
	            Trip trip4 = new Trip("Canada", 7, 800, client3, transport6, accommodation3);

	            // Update client spending
	            client1.addAmountSpent(trip1.getTotalCost());
	            client2.addAmountSpent(trip2.getTotalCost());
	            client3.addAmountSpent(trip3.getTotalCost());
	            client3.addAmountSpent(trip4.getTotalCost());

	            // Display all
	            System.out.println("\n" + client1); System.out.println(client2); System.out.println(client3);
	            System.out.println("--------------------------------");
	            System.out.println(transport1); System.out.println(transport2); System.out.println(transport3);
	            System.out.println(transport4); System.out.println(transport5); System.out.println(transport6);
	            System.out.println("--------------------------------");
	            System.out.println(accommodation1); System.out.println(accommodation2); System.out.println(accommodation3);
	            System.out.println(accommodation4); System.out.println(accommodation5);
	            System.out.println("--------------------------------");
	            System.out.println(trip1); System.out.println(trip2); System.out.println(trip3);
	            System.out.println("--------------------------------");

	            // equals() comparisons
	            System.out.println("\nCompare Client 1 and Client 2: " + client1.equals(client2));
	            System.out.println("Compare Client 1 and Trip 1: " + client1.equals(trip1));
	            System.out.println("Compare Client 3 and Client 4: " + client3.equals(client4));
	            System.out.println("\nCompare Transport 1 and Transport 2: " + transport1.equals(transport2));
	            System.out.println("Compare Transport 2 and Transport 3: " + transport2.equals(transport3));
	            System.out.println("Compare Transport 5 and Transport 6: " + transport5.equals(transport6));
	            System.out.println("\nCompare Accommodation 1 and Accommodation 2: " + accommodation1.equals(accommodation2));
	            System.out.println("Compare Accommodation 2 and Accommodation 3: " + accommodation2.equals(accommodation3));
	            System.out.println("Compare Accommodation 4 and Accommodation 5: " + accommodation4.equals(accommodation5));
	            System.out.println("\nCompare Trip 1 and Trip 2: " + trip1.equals(trip2));
	            System.out.println("Compare Trip 1 and Client 1: " + trip1.equals(client1));
	            System.out.println("Compare Trip 3 and Trip 4: " + trip3.equals(trip4));

	            // --- Exception Demonstrations ---
	            System.out.println("\n--- Exception Demonstrations ---");

	            // DuplicateEmailException
	            try {
	                throw new DuplicateEmailException("Email xxx@gmail.com already exists!");
	            } catch (DuplicateEmailException e) {
	                System.out.println("DuplicateEmailException caught: " + e.getMessage());
	            }

	            // InvalidClientDataException - empty name
	            try { 
	            	new Client("", "Test", "valid@email.com"); 
	            }
	            catch (InvalidClientDataException e) { 
	            	System.out.println("InvalidClientDataException caught: " + e.getMessage()); 
	            }

	            // InvalidClientDataException - bad email format
	            try { 
	            	new Client("Test", "User", "notanemail"); 
	            }
	            catch (InvalidClientDataException e) { 
	            	System.out.println("InvalidClientDataException caught: " + e.getMessage()); 
	            }

	            // InvalidTripDataException - duration out of range
	            try { 
	            	new Trip("Nowhere", 25, 500, client1, transport1, accommodation1); 
	            }
	            catch (InvalidTripDataException e) { 
	            	System.out.println("InvalidTripDataException caught: " + e.getMessage()); 
	            }

	            // InvalidTripDataException - base price too low
	            try { 
	            	new Trip("Nowhere", 5, 50, client1, transport1, accommodation1); 
	            }
	            catch (InvalidTripDataException e) { 
	            	System.out.println("InvalidTripDataException caught: " + e.getMessage()); 
	            }

	            // InvalidTransportDataException - Luggage with less than 0
	            try {
					new Flight("Expedia", "Somewhere", "Somewhere", "AIR CANADA", -1);
				} catch (InvalidTransportDataException e) {
					System.out.println("InvalidTransportDataException caught: " + e.getMessage()); 
				}
	            
	            // InvalidTransportDataException - bus with 0 stops
	            try { 
	            	new Bus("BadBus", "A", "B", "BadCo", 0); 
	            }
	            catch (InvalidTransportDataException e) { 
	            	System.out.println("InvalidTransportDataException caught: " + e.getMessage()); 
	            }

	            // InvalidAccommodationDataException - hostel over $150
	            try { 
	            	new Hostel("Expensive", "Paris", 200, 4); 
	            }
	            catch (InvalidAccommodationDataException e) { 
	            	System.out.println("InvalidAccommodationDataException caught: " + e.getMessage()); 
	            }

	            // InvalidAccommodationDataException - invalid star rating
	            try { 
	            	new Hotel("Bad Hotel", "Rome", 100, 6); 
	            }
	            catch (InvalidAccommodationDataException e) { 
	            	System.out.println("InvalidAccommodationDataException caught: " + e.getMessage()); 
	            }

	            // EntityNotFoundException - simulated
	            try { 
	            	throw new EntityNotFoundException("Client with ID C9999 not found!"); 
	            }
	            catch (EntityNotFoundException e) { 
	            	System.out.println("EntityNotFoundException caught: " + e.getMessage()); 
	            }
	            
	            System.out.println("-----------------");
	            
	            LoadAllData("output/data/"); // Working version
	            LoadAllData("outputs/data/"); // Non-Working version (outputs instead output)
	            
	            System.out.println("-----------------");

	            System.out.println("--------------------------------");

	            // Trip costs
	            Trip[] tripArr = {trip1, trip2, trip3, trip4};
	            Transportation[] transportationArr = {transport1, transport2, transport3, transport4, transport5, transport6};
	            Accommodation[] accommodationArr = {accommodation1, accommodation2, accommodation3, accommodation4, accommodation5};

	            double totalCost = 0;
	            for (int i = 0; i < tripArr.length; i++) {
	                totalCost += tripArr[i].getTotalCost();
	                System.out.println("Cost of trip #" + (i + 1) + ": $" + String.format("%.2f", tripArr[i].getTotalCost()));
	            }
	            System.out.println("Total Cost of every trip: $" + String.format("%.2f", totalCost));
	            System.out.println("--------------------------------");

	            displayMostExpensiveTrip(tripArr);
	            System.out.println("--------------------------------");

	            // Deep copy
	            Transportation[] newArrTransportation = copyTransportArray(transportationArr);
	            Accommodation[] newArrAccommodation = copyAccommodationArray(accommodationArr);

	            for (int i = 0; i < newArrTransportation.length; i++) {
	                if (newArrTransportation[i] == null) 
	                	continue;
	                System.out.println(transportationArr[i].toString());
	                try { 
	                	newArrTransportation[i].setCompanyName("No-Name"); 
	                } catch (InvalidTransportDataException e) { 
	                	System.out.println("Error: " + e.getMessage());
	                }
	                
	                System.out.println(newArrTransportation[i].toString());
	                System.out.println("--------------------------------");
	            }

	            for (int j = 0; j < newArrAccommodation.length; j++) {
	                if (newArrAccommodation[j] == null) 
	                	continue;
	                System.out.println(accommodationArr[j].toString());
	                try { 
	                	newArrAccommodation[j].setLocation("Unknown"); 
	                } catch (InvalidAccommodationDataException e) { 
	                	System.out.println("Error: " + e.getMessage()); 
	                }
	                System.out.println(newArrAccommodation[j].toString());
	                System.out.println("--------------------------------");
	            }

	            System.out.print("\nThank you for using our predefined software implements!");

	        } catch (InvalidClientDataException e) {
	            System.out.println("Error: Invalid client data. " + e.getMessage());
	        } catch (InvalidTripDataException e) {
	            System.out.println("Error: Invalid trip data. " + e.getMessage());
	        } catch (InvalidTransportDataException e) {
	            System.out.println("Error: Invalid transport data. " + e.getMessage());
	        } catch (InvalidAccommodationDataException e) {
	            System.out.println("Error: Invalid accommodation data. " + e.getMessage());
	        }
	    }
}
