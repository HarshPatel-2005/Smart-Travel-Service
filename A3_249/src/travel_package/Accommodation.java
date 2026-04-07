package travel_package;
import exceptions.*;
import interfaces.CsvPersistable;
import interfaces.Identifiable;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
//are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
//can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
//feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

//We do not save have the csvPersistable interface here as it needs information from the child classes to print it to a csv format

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