package travel_package;
import exceptions.*;
// Assignment 1
// Question: Smart Travel Agency
// Written by: Harsh Patel (40341498) and Pratik Patel (40330468)

import client_package.Client;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

public class Trip {

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

    // Methods

    public double calculateTotalCost() {
        double totalCost = this.basePrice + transportation.calculateCost(this.duration) + accommodation.calculateCost(this.duration);
        return totalCost;
    }

    // Getters and Setters

    public String getTripID() {
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

    public double getBasePrice() {
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

    // toString method
    @Override
    public String toString() {
        return "\nTrip ID: " + tripID + "\nDestination: " + destination + "\nDuration: " + duration + " days, \nBase Price: $" + basePrice + "\nClient ID: " + client.getClientID() + "\nTransportID: " + transportation.getTransportID() + "\nAccommodation ID: " + accommodation.getAccommodationID();
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
}