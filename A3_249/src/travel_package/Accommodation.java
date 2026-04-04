package travel_package;
import exceptions.*;
import interfaces.CsvPersistable;
import interfaces.Identifiable;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

public abstract class Accommodation implements Identifiable, CsvPersistable, Comparable<Accommodation>{

    private String accommodationID;
    private String name;
    private String location;
    private double pricePerNight;
    private static int counter = 4001;

    // Default constructor
    public Accommodation() throws InvalidAccommodationDataException {
        accommodationID = "A" + (counter);
        this.name = "";
        this.location = "";
        this.pricePerNight = 0;
        counter++;
    }

    // Parameterized constructor
    public Accommodation(String name, String location, double pricePerNight) throws InvalidAccommodationDataException {
        accommodationID = "A" + (counter);
        counter++;
        setName(name);
        setLocation(location);
        setPricePerNight(pricePerNight);
    }
    
    public Accommodation(String accommodationID, String name, String location, double pricePerNight) throws InvalidAccommodationDataException { // For CVS file
		this.accommodationID = accommodationID;
		setName(name);
        setLocation(location);
        setPricePerNight(pricePerNight);
		counter++;
	}

    // Copy constructor
    public Accommodation(Accommodation accommodation) throws InvalidAccommodationDataException {
        accommodationID = "A" + (counter);
        counter++;
        setName(accommodation.name);
        setLocation(accommodation.location);
        setPricePerNight(accommodation.pricePerNight);
    }

    // Abstract methods
    public abstract double calculateCost(int numOfDays);
    
    public abstract Accommodation copy();

    // METHODS // 
    
    // toString method
    @Override
    public String toString() {
        return "\nAccommodation ID: " + accommodationID + "\nName: " + name + "\nLocation: " + location + "\nPrice Per Night: $" + pricePerNight;
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
        Accommodation accommodation = (Accommodation) obj;
        return (this.name.equals(accommodation.name))
                && (this.location.equals(accommodation.location))
                && (this.pricePerNight == accommodation.pricePerNight);
    }
    
    @Override
	public int compareTo(Accommodation o) { // Comparable Interface Method
		
		if(o.getPricePerNight() > this.getPricePerNight())
			return 1;
		else if(o.getPricePerNight() == this.getPricePerNight())
			return 0;
		else
			return -1;
		
	}

    // GETTERS AND SETTERS //
    
    @Override
    public String getID() {
        return accommodationID;
    }

    public void setAccommodationID(String accommodationID) {
        this.accommodationID = accommodationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidAccommodationDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidAccommodationDataException("Accommodation name cannot be empty!");
        }
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) throws InvalidAccommodationDataException {
        if (location == null || location.trim().isEmpty()) {
            throw new InvalidAccommodationDataException("Location cannot be empty!");
        }
        this.location = location;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) throws InvalidAccommodationDataException {
        if (pricePerNight <= 0) {
            throw new InvalidAccommodationDataException("Price per night must be greater than $0!");
        }
        this.pricePerNight = pricePerNight;
    }
    
}