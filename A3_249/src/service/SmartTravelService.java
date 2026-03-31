package service;

//Assignment 2
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498) and Pratik Patel (40330468)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

// The SmartTravelService file now contains the logic of the program. This file allows the connection to happen between every file

import java.io.IOException;
import java.util.Scanner;

import client_package.Client;
import exceptions.DuplicateEmailException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidAccommodationDataException;
import exceptions.InvalidClientDataException;
import exceptions.InvalidTransportDataException;
import exceptions.InvalidTripDataException;
import text_files.AccommodationFileManager;
import text_files.ClientFileManager;
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
		
		private static Scanner input;
		
		private static Client[] clients;
		private static int clientCount;
		
		private static Trip[] trips;
		private static int tripCount;
		
		private static Transportation[] transportOptions;
		private static int transportCount;
		
		private static Accommodation[] accomodationOptions;
		private static int accomodationCount;
		
		public SmartTravelService(Scanner input) {
			clients = new Client[100];
			accomodationOptions = new Accommodation[50];
	        transportOptions = new Transportation[50];
	        trips = new Trip[200];
	
	        clientCount = 0;
	        accomodationCount = 0;
	        transportCount = 0;
	        tripCount = 0;
	        
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
	            for (int i = 0; i < clientCount; i++) {
	                if (clients[i].getEmail().equalsIgnoreCase(email)) {
	                    throw new DuplicateEmailException("Email already exists!");
	                }
	            }
	            Client newClient = new Client(firstName, lastName, email);
	            clients[clientCount++] = newClient;
	            System.out.println("Client added successfully! \nClient ID: " + newClient.getClientID());
	        } catch (InvalidClientDataException e) {
	            System.out.println("Error: Invalid client data. " + e.getMessage()); // If the data is incorrect, it catches InvalidClientDataException 
	        } catch (DuplicateEmailException e) {
	            System.out.println("Duplicate Email: " + e.getMessage()); // If the email exist for a different user, it catches DuplicateEmailException
	        }
	    }

	    public void editClient() {
	    	
	        if (clientCount == 0) { 
	        	System.out.println("There are no clients! Returning to Menu!"); 
	        	return; 
	        }

	        System.out.print("What client would you like to edit? (Enter Client ID): ");
	        for (int i = 0; i < clientCount; i++) 
	        	System.out.print("\nClient ID: " + clients[i].getClientID());
	        
	        System.out.print("\nClient ID choice: ");
	        String clientID = input.nextLine();

	        Client clientToEdit = null;
	        for (int i = 0; i < clientCount; i++) {
	            if (clients[i].getClientID().equalsIgnoreCase(clientID)) { 
	            	clientToEdit = clients[i]; 
	            	break; 
	            }
	        }
	        
	        if (clientToEdit == null) { 
	        	System.out.println("Client not found!"); 
	        	return; 
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
	    }

	    public void deleteClient() {
	    	
	        if (clientCount == 0) { 
	        	System.out.println("There are no clients! Returning to Menu!"); 
	        	return; 
	        }

	        System.out.print("What client would you like to delete? (Enter Client ID) ");
	        for (int i = 0; i < clientCount; i++) 
	        	System.out.print("\nClient ID: " + clients[i].getClientID());
	        
	        System.out.print("\nClient ID choice: ");
	        String clientID = input.nextLine();

	        int clientToDelete = -1;
	        for (int i = 0; i < clientCount; i++) {
	            if (clients[i].getClientID().equalsIgnoreCase(clientID)) { 
	            	clientToDelete = i; 
	            	break; 
	            }
	        }
	        
	        if (clientToDelete == -1) { 
	        	System.out.println("Client not found!"); 
	        	return; 
	        }

	        for (int i = clientToDelete; i < clientCount - 1; i++) 
	        	clients[i] = clients[i + 1];
	        
	        clients[--clientCount] = null;
	        System.out.println("Client deleted successfully!");
	    }

	    public void listAllClients() {
	        if (clientCount == 0) 
	        	System.out.println("No clients found!");
	        else 
	        	for (int i = 0; i < clientCount; i++) 
	        		System.out.println(clients[i].toString());
	    }
	    
	    public Client getClient(int clientIndex) {
	    	if (clientIndex < 0 || clientIndex >= clientCount) {
	            return null;
	        }
	        return clients[clientIndex];
	    }
	    
	    public int getClientCount() {
	    	return clientCount;
	    }

	    // -------------------------
	    // Methods for trip
	    // -------------------------

	    public void createTrip() {
	    	
	        if (clientCount == 0) { 
	        	System.out.println("There are no clients! Returning to Menu!"); 
	        	return; 
	        }

	        try {
	            System.out.print("\nAvailable Client Options:");
	            
	            for (int i = 0; i < clientCount; i++) 
	            	System.out.print("\nClient ID: " + clients[i].getClientID());
	            
	            System.out.print("\nClient ID choice: ");
	            String clientID = input.nextLine();

	            Client clientToCompare = null;
	            for (int i = 0; i < clientCount; i++) {
	                if (clients[i].getClientID().equalsIgnoreCase(clientID)) { 
	                	clientToCompare = clients[i]; 
	                	break; 
	                }
	            }
	            
	            if (clientToCompare == null) // If the client does not exist in the system, EntityNotFoundException exception will handle this issue
	            	throw new EntityNotFoundException("Client not found!");

	            if (transportCount == 0) { 
	            	System.out.println("There are no transportation options available! Returning to Menu!"); 
	            	return; 
	            }
	            
	            System.out.println("\nAvailable transportation options:");
	            for (int i = 0; i < transportCount; i++) 
	            	System.out.println("Transportation ID: " + transportOptions[i].getTransportID());
	            
	            System.out.print("Choice: ");
	            String transportID = input.nextLine();

	            Transportation selectedTransport = null;
	            for (int i = 0; i < transportCount; i++) {
	                if (transportOptions[i].getTransportID().equalsIgnoreCase(transportID)) { 
	                	selectedTransport = transportOptions[i]; break; }
	            }
	            
	            if (selectedTransport == null) // If the transportation does not exist in the system, EntityNotFoundException will handle this issue
	            	throw new EntityNotFoundException("Transportation not found!");

	            if (accomodationCount == 0) { 
	            	System.out.println("There are no accommodation options available! Returning to Menu!"); 
	            	return; 
	            }
	            
	            System.out.print("\nAvailable accommodation options:");
	            for (int i = 0; i < accomodationCount; i++) 
	            	System.out.print("\nAccommodation ID: " + accomodationOptions[i].getAccommodationID());
	            
	            System.out.print("\nChoice: ");
	            String accomodationID = input.nextLine();

	            Accommodation selectedAccomodation = null;
	            for (int i = 0; i < accomodationCount; i++) {
	                if (accomodationOptions[i].getAccommodationID().equalsIgnoreCase(accomodationID)) { 
	                	selectedAccomodation = accomodationOptions[i]; 
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
	            trips[tripCount++] = newTrip;
	            clientToCompare.addAmountSpent(newTrip.calculateTotalCost());
	            System.out.println("Trip created successfully! \nTrip ID: " + newTrip.getTripID());

	        } catch (EntityNotFoundException e) {
	            System.out.println("Error: " + e.getMessage());
	        } catch (InvalidTripDataException e) {
	            System.out.println("Error: Invalid trip data. " + e.getMessage());
	        }
	    }

	    public void editTrip() {
	    	
	        if (tripCount == 0) { 
	        	System.out.println("There are no Trips! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("What trip would you like to edit? (Enter Trip ID)");
				for (int i = 0; i < tripCount; i++) 
					System.out.print("\nTrip ID: " + trips[i].getTripID());
				
				System.out.print("\nChoice: ");
				String tripID = input.nextLine();

				Trip tripToEdit = null;
				for (int i = 0; i < tripCount; i++) {
				    if (trips[i].getTripID().equalsIgnoreCase(tripID)) { 
				    	tripToEdit = trips[i];
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
	    	
	        if (tripCount == 0) { 
	        	System.out.println("There are no trips! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Which trip would you like to cancel? (Enter Trip Id) ");
				for (int i = 0; i < tripCount; i++) 
					System.out.print("\nTrip ID: " + trips[i].getTripID());
				
				System.out.print("\nChoice: ");
				String tripId = input.nextLine();

				int tripToCancel = -1;
				for (int i = 0; i < tripCount; i++) {
				    if (trips[i].getTripID().equalsIgnoreCase(tripId)) { 
				    	tripToCancel = i; 
				    }
				}
				if (tripToCancel == -1) { 
					throw new EntityNotFoundException("Trip not found!");
				}

				for (int i = tripToCancel; i < tripCount - 1; i++) 
					trips[i] = trips[i + 1];
				
				trips[--tripCount] = null;
				System.out.println("Trip cancelled successfully!");
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    public static void listAllTrips() {
	        if (tripCount == 0) System.out.println("No trips found!");
	        else for (int i = 0; i < tripCount; i++) {
	        	System.out.println("-------------------");
				System.out.println(trips[i].toString());
				System.out.println("\n" + trips[i].getClient().toString());
				System.out.println(trips[i].getTransportation().toString());
				System.out.println(trips[i].getAccommodation().toString());
				System.out.println("-------------------");
	        }
	    }

	    public void listTripsByClient() {
	    	
	        if (clientCount == 0) { 
	        	System.out.println("There are no clients! Returning to Menu!"); 
	        	return; 
	        }
	        if (tripCount == 0) { 
	        	System.out.println("There are no Trips! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Enter Client ID to list their trips ");
				for (int i = 0; i < clientCount; i++) 
					System.out.print("\nClient ID: " + clients[i].getClientID());
				
				System.out.print("\nChoice: ");
				String clientID = input.nextLine();

				boolean found = false;
				for (int i = 0; i < tripCount; i++) {
				    if (trips[i].getClient().getClientID().equalsIgnoreCase(clientID)) {
				        System.out.println(trips[i].toString());
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
	    	return trip.calculateTotalCost();
	    }
	    
	    public Trip getTrip(int tripIndex) { // Method to receive an index and match it with the index of the array in this file
	    	if (tripIndex < 0 || tripIndex >= tripCount) { // If the index is invalid return nothing
	            return null;
	        }
	        return trips[tripIndex];
	    }
	    
	    public int getTripCount() { // Method to receive the number of trips
	    	return tripCount;
	    }
	    
	    public Trip[] getAllTrips() { // Method to receive the entire array to use outside of this class
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
	            transportOptions[transportCount++] = newTransport;
	            System.out.println("Transportation option added successfully! \nTransport ID: " + newTransport.getTransportID());
	        } catch (InvalidTransportDataException e) {
	            System.out.println("Error: Invalid transportation data. " + e.getMessage());
	        }
	    }

	    public void removeTransportation() {
	    	
	        if (transportCount == 0) { 
	        	System.out.println("There are no transportations! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Enter Transport ID to remove ");
				for (int i = 0; i < transportCount; i++) 
					System.out.print("\nTransport ID: " + transportOptions[i].getTransportID());
				
				System.out.print("\nChoice: ");
				String transportID = input.nextLine();

				int transportToRemove = -1;
				for (int i = 0; i < transportCount; i++) {
				    if (transportOptions[i].getTransportID().equalsIgnoreCase(transportID)) { 
				    	transportToRemove = i; 
				    	break; 
				    }
				}
				
				if (transportToRemove == -1) { // If the transportation is not found, allow EntityNotFoundException to handle this 
					throw new EntityNotFoundException("Transportation Not Found!");
				}

				for (int i = transportToRemove; i < transportCount - 1; i++) transportOptions[i] = transportOptions[i + 1];
				transportOptions[--transportCount] = null;
				System.out.println("Transportation option removed successfully!");
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    public void listTransportationByType() {
	        int counter = 0;
	        if (transportCount == 0) { 
	        	System.out.println("There are no Transportations! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Which Transportation type would you like to view (Flight, Train, Bus): ");
				String userTransportationInput = input.nextLine();

				for (int i = 0; i < transportCount; i++) {
				    if (transportOptions[i].getClass().getSimpleName().equalsIgnoreCase(userTransportationInput)) {
				        System.out.println("\n" + transportOptions[i].toString());
				        counter++;
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
	            accomodationOptions[accomodationCount++] = newAccommodation;
	            System.out.println("Accommodation option added successfully! \nAccommodation ID: " + newAccommodation.getAccommodationID());
	        } catch (InvalidAccommodationDataException e) {
	            System.out.println("Error: Invalid accommodation data. " + e.getMessage());
	        }
	    }

	    public void removeAccomodation() {
	    	
	        if (accomodationCount == 0) { 
	        	System.out.println("There are no accommodations! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Enter Accommodation ID to remove ");
				for (int i = 0; i < accomodationCount; i++) 
					System.out.print("\nAccommodation ID: " + accomodationOptions[i].getAccommodationID());
				
				System.out.print("\nChoice: ");
				String accommodationID = input.nextLine();

				int accommodationToRemove = -1;
				for (int i = 0; i < accomodationCount; i++) {
				    if (accomodationOptions[i].getAccommodationID().equalsIgnoreCase(accommodationID)) { 
				    	accommodationToRemove = i; 
				    	break; 
				    }
				}
				
				if (accommodationToRemove == -1) { 
					// If the accommodation is not found, allow EntityNotFoundException to handle this 
					throw new EntityNotFoundException("Transportation Not Found!");
				}

				for (int i = accommodationToRemove; i < accomodationCount - 1; i++) 
					accomodationOptions[i] = accomodationOptions[i + 1];
				
				accomodationOptions[--accomodationCount] = null;
				System.out.println("Accommodation option removed successfully!");
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
	    }

	    public void listAccomodationByType() {
	        int counter = 0;
	        if (accomodationCount == 0) { 
	        	System.out.println("There are no accommodations! Returning to Menu!"); 
	        	return; 
	        }

	        try {
				System.out.print("Which Accommodation type would you like to view (Hotel, Hostel): ");
				String userAccommodationInput = input.nextLine();

				for (int i = 0; i < accomodationCount; i++) {
				    if (accomodationOptions[i].getClass().getSimpleName().equalsIgnoreCase(userAccommodationInput)) {
				        System.out.println(accomodationOptions[i].toString());
				        counter++;
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
	        if (tripCount == 0) { 
	        	System.out.println("No trips found!"); 
	        	return; 
	        }

	        double highestCost = 0.0;
	        Trip mostExpensiveTrip = trips[0];

	        for (int i = 0; i < trips.length; i++) {
	            if (trips[i].calculateTotalCost() > highestCost) {
	                highestCost = trips[i].calculateTotalCost();
	                mostExpensiveTrip = trips[i];
	            }
	        }
	        System.out.println("Most Expensive trip ID: " + mostExpensiveTrip.getTripID() + ", Cost: $" + highestCost);
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
	            if (tripArr[i].calculateTotalCost() > highestCost) {
	                highestCost = tripArr[i].calculateTotalCost();
	                mostExpensiveTrip = tripArr[i];
	            }
	        }
	        System.out.println("Most Expensive trip ID: " + mostExpensiveTrip.getTripID() + ", Cost: $" + highestCost);
	    }

	    public void displayCostOfTrip() {
	        int counter = 0;
	        
	        if (tripCount == 0) { 
	        	System.out.println("No trips found!"); 
	        	return; 
	        }

	        try {
				Trip displayTrip = trips[0];
				System.out.println("\nAvailable trips:");
				for (int i = 0; i < tripCount; i++) 
					System.out.println(trips[i].getTripID());

				System.out.print("Which trip's cost would you like to display? ");
					String chosenTrip = input.nextLine();

				for (int i = 0; i < tripCount; i++) {
				    if (trips[i].getTripID().equalsIgnoreCase(chosenTrip)) { 
				    	displayTrip = trips[i]; counter++; 
				    }
				}

				if (counter == 0) 
					throw new EntityNotFoundException("No Trips Found!");
				else 
					System.out.println("Total cost of Trip ID: " + displayTrip.getTripID() + " is $" + displayTrip.calculateTotalCost());
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
	    
	    public Transportation[] copyTransportArray() {
	        Transportation[] copyArray = new Transportation[transportCount];
	        if (transportOptions.length == 0) { 
	        	System.out.println("Nothing to copy! Returning to menu!"); }
	        else {
	            for (int i = 0; i < transportOptions.length; i++) {
	                if (transportOptions[i] != null) copyArray[i] = transportOptions[i].copy();
	            }
	        }
	        return copyArray;
	    }

	    public Accommodation[] copyAccommodationArray() {
	        Accommodation[] copyArray = new Accommodation[accomodationCount];
	        if (accomodationOptions.length == 0) { 
	        	System.out.println("Nothing to copy! Returning to menu!"); }
	        else {
	            for (int i = 0; i < accomodationOptions.length; i++) {
	                if (accomodationOptions[i] != null) copyArray[i] = accomodationOptions[i].copy();
	            }
	        }
	        return copyArray;
	    }
	    
	    // This method loads the saveClients depending on the folder path the programmer uses
	    // It has the possibility to produce a IOException on every file which is why we separate them in case everything is fine
		public static void SaveAllData(String folderPath){
			try {
				ClientFileManager.saveClients(clients, clientCount, folderPath + "clients.csv");
			} catch (IOException e) {
				System.out.println("Client File does not exist!");
			}
			
			try {
				TransportationFileManager.saveTransportation(transportOptions, transportCount, folderPath + "transports.csv");
			} catch (IOException e) {
				System.out.println("Transportation File does not exist!");
			}
			
			try {
				AccommodationFileManager.saveAccommodation(accomodationOptions, accomodationCount, folderPath + "accommodations.csv");
			} catch (IOException e) {
				System.out.println("Accommodation File does not exist!");
			}
			
			try {
				TripFileManager.saveTrips(trips, tripCount, folderPath + "trips.csv");
			} catch (IOException e) {
				System.out.println("Trip File does not exist!");
			}
			
		}

		// This method loads the file based on the folderPath
		// It can not only throw an IOException which is why we must catch it in case the file does not exist, but also incorrect data. If the files have wrong inputs then they should not be loaded into the system
		public static void LoadAllData(String folderPath) throws InvalidClientDataException, InvalidTransportDataException, InvalidAccommodationDataException, InvalidTripDataException {
			try {
				clientCount = ClientFileManager.loadClients(clients, clientCount, folderPath + "clients.csv");
				System.out.println("Loaded clients into the system!");
			} catch (IOException e) {
				System.out.println("Cannot find the file location for clients"); // Error-Logger
			}
			
			try {
				accomodationCount = AccommodationFileManager.loadAccommodation(accomodationOptions, accomodationCount, folderPath + "accommodations.csv");
				System.out.println("Loaded accommodations into the system!");
			} catch (IOException e) {
				System.out.println("Cannot find the file location for accommodations"); // Error-Logger
			}
			
			try {
				transportCount = TransportationFileManager.loadTransportation(transportOptions, transportCount, folderPath + "transports.csv");
				System.out.println("Loaded Transportations into the system!");
			} catch (IOException e) {
				System.out.println("Cannot find the file location for transportations"); // Error-Logger
			}
			try {
				tripCount = TripFileManager.loadTrips(clients, clientCount, accomodationOptions, accomodationCount, transportOptions, transportCount, trips, tripCount, folderPath + "trips.csv");
				System.out.println("Loaded Trips into the system!");
			} catch (IOException e) {
				System.out.println("Cannot find the file location for trips"); // Error-Logger
			}
			
		}

		// Lists all the trips with the client and such
		public static void listAllData() {
			listAllTrips();
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
	            client1.addAmountSpent(trip1.calculateTotalCost());
	            client2.addAmountSpent(trip2.calculateTotalCost());
	            client3.addAmountSpent(trip3.calculateTotalCost());
	            client3.addAmountSpent(trip4.calculateTotalCost());

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
	                totalCost += tripArr[i].calculateTotalCost();
	                System.out.println("Cost of trip #" + (i + 1) + ": $" + String.format("%.2f", tripArr[i].calculateTotalCost()));
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
