package travel_package;
import exceptions.*;

//Assignment 2
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498) and Pratik Patel (40330468)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

public class Hostel extends Accommodation {

    private int numOfSharedBeds;

    // Default constructor
    public Hostel() throws InvalidAccommodationDataException {
        super();
        this.numOfSharedBeds = 0;
    }

    // Parameterized constructor
    public Hostel(String name, String location, double pricePerNight, int numOfSharedBeds) throws InvalidAccommodationDataException {
        super(name, location, pricePerNight);
        if (pricePerNight > 150) {
            throw new InvalidAccommodationDataException("Hostel price per night cannot exceed $150!");
        }
        setNumOfSharedBeds(numOfSharedBeds);
    }
    
    public Hostel(String accommodationID, String name, String location, double pricePerNight, int numOfSharedBeds) throws InvalidAccommodationDataException { // For CVS file
		super(accommodationID, name,  location,  pricePerNight);
		if (pricePerNight > 150) {
            throw new InvalidAccommodationDataException("Hostel price per night cannot exceed $150!");
        }
        setNumOfSharedBeds(numOfSharedBeds);
	}

    // Copy constructor
    public Hostel(Hostel hostel) throws InvalidAccommodationDataException {
        super(hostel);
        setNumOfSharedBeds(hostel.numOfSharedBeds);
    }

    // Methods
    @Override
    public double calculateCost(int numOfDays) {
        double regularPrice = getPricePerNight() * numOfDays;
        double surchargePrice = regularPrice * 0.15;
        return (regularPrice - surchargePrice);
    }

    @Override
    public String toString() {
        return super.toString() + "\nNumber Of Shared Beds: " + numOfSharedBeds;
    }

    @Override
    public Accommodation copy() {
        try {
            return new Hostel(this);
        } catch (InvalidAccommodationDataException e) {
            System.out.println("Error copying Hostel: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Hostel hostel = (Hostel) obj;
        return (this.numOfSharedBeds == hostel.numOfSharedBeds);
    }

    // Getters and Setters
    public int getNumOfSharedBeds() {
        return numOfSharedBeds;
    }

    public void setNumOfSharedBeds(int numOfSharedBeds) throws InvalidAccommodationDataException {
        if (numOfSharedBeds < 0) {
            throw new InvalidAccommodationDataException("Number of shared beds cannot be negative!");
        }
        this.numOfSharedBeds = numOfSharedBeds;
    }
}