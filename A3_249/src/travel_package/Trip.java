package travel_package;
import exceptions.*;
import interfaces.Billable;
import interfaces.CsvPersistable;
// Assignment 1
// Question: Smart Travel Agency
// Written by: Harsh Patel (40341498) and Pratik Patel (40330468)
import interfaces.Identifiable;
import client_package.Client;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
//are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
//can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
//feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

//This class also has billable, which is used for the getBasePrice and the getTotalCost methods

public class Trip implements Identifiable, Billable, CsvPersistable, Comparable<Trip>{

    private String tripID;
    private String destination;
    private int duration;
    private double basePrice;
    private static int counter = 2001;

    private Client client;
    private Transportation transportation;
    private Accommodation accommodation;

    // Default constructor
    public Trip() throws InvalidTripDataException {
        tripID = "T" + (counter);
        this.destination = "";
        this.duration = 0;
        this.basePrice = 0;
        counter++;

        this.client = null;
        this.transportation = null;
        this.accommodation = null;
    }

    // Parameterized constructor
    public Trip(String destination, int duration, double basePrice, Client client, Transportation transportation, Accommodation accommodation) throws InvalidTripDataException {
        tripID = "T" + (counter);
        counter++;

        setDestination(destination);
        setDuration(duration);
        setBasePrice(basePrice);

        this.client = client;
        this.transportation = transportation;
        this.accommodation = accommodation;
    }
    
    public Trip(String tripID, String destination, int duration, double basePrice, Client client, Transportation transportation, Accommodation accommodation) throws InvalidTripDataException {
		// For CVS file
		this.tripID = tripID;
		setDestination(destination);
        setDuration(duration);
        setBasePrice(basePrice);
		counter++;
		
		this.client = client;
		this.transportation = transportation;
		this.accommodation = accommodation;
	}

    // Copy constructor
    public Trip(Trip trip) throws InvalidTripDataException {
        tripID = "T" + (counter);
        counter++;

        setDestination(trip.destination);
        setDuration(trip.duration);
        setBasePrice(trip.basePrice);

        this.client = trip.client;
        this.transportation = trip.transportation;
        this.accommodation = trip.accommodation;
    }

    // METHODS //

    @Override
    public double getTotalCost() { // Interface method to get total cost of everything
        double totalCost = this.basePrice + transportation.calculateCost(this.duration) + accommodation.calculateCost(this.duration);
        return totalCost;
    }
    
 // toString method
    @Override
    public String toString() {
        return "\nTrip ID: " + tripID + "\nDestination: " + destination + "\nDuration: " + duration + " days, \nBase Price: $" + basePrice + "\nClient ID: " + client.getID() + "\nTransportID: " + transportation.getID() + "\nAccommodation ID: " + accommodation.getID();
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Trip trip = (Trip) obj;
        return (this.destination.equals(trip.destination))
                && (this.duration == trip.duration)
                && (this.basePrice == trip.basePrice)
                && (this.client == trip.client)
                && (this.transportation == trip.transportation)
                && (this.accommodation == trip.accommodation);
    }

    @Override
	public int compareTo(Trip o) { // Comparable Interface Method
		if(o.getTotalCost() > this.getTotalCost())
			return 1;
		else if(o.getTotalCost() == this.getTotalCost())
			return 0;
		else
			return -1;
	} 

	@Override
	public String toCsvRow() { // CsvPersistable Interface method
		return this.getID() + ";" + this.getClient().getID() + ";" + this.getAccommodation().getID() + ";" 
				+ this.getTransportation().getID() + ";" + this.getDestination() + ";" + this.getDurationInDays() + ";" + 
				this.getBasePrice();
	}
	
	public static Trip fromCsvRow(String csvLine) throws InvalidTripDataException {
		
		String[] content = csvLine.split(";");
		
		if(content.length != 7) {
			throw new InvalidTripDataException("InvalidTripDataException: Invalid number of columns");
		}
		
		if(content[0] == null || content[0].isEmpty() || content[0].charAt(0) != 'T') {
			throw new InvalidTripDataException("InvalidTripDataException: Incorrect Trip ID: " + content[0]);
		}
		
		int duration = Integer.valueOf(content[5]);
		double basePrice = Double.valueOf(content[6]);
		
		Trip trip = new Trip(content[0], content[4], duration, basePrice, null, null, null); // The trip currently does not pay attention to the client, accommodation nor the transportation as we're simply converting the

		return trip;
		
	}

    // GETTERS AND SETTERS //

    @Override
    public String getID() { // Interface method to get the ID
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) throws InvalidTripDataException {
        if (destination == null || destination.trim().isEmpty()) {
            throw new InvalidTripDataException("Destination cannot be empty!");
        }
        this.destination = destination;
    }

    public int getDurationInDays() {
        return duration;
    }

    public void setDuration(int duration) throws InvalidTripDataException {
        if (duration < 1 || duration > 20) {
            throw new InvalidTripDataException("Duration must be between 1 and 20 days!");
        }
        this.duration = duration;
    }

    @Override
    public double getBasePrice() { // Interface Method to get the base price of the trip
        return basePrice;
    }

    public void setBasePrice(double basePrice) throws InvalidTripDataException {
        if (basePrice < 100) {
            throw new InvalidTripDataException("Base price must be at least $100.00!");
        }
        this.basePrice = basePrice;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

}