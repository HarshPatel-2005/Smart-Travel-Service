package travel_package;
import exceptions.*;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

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
    
    @Override
	public String toCsvRow() { // CsvPersistable Interface method inside of this class because accommodation does not have access to subclass variables
    	return this.getClass().getSimpleName().toUpperCase() + ";" + super.getID() + ";" + super.getName() + ";" + super.getLocation() + ";" + super.getPricePerNight() + ";" +  this.getNumOfSharedBeds();
	}
    
    public static Accommodation fromCsvRow(String csvLine) throws InvalidAccommodationDataException {
		
		Accommodation a = null;

        String[] content = csvLine.split(";");	

        if(content.length != 6) { // The array must contain 6 columns of data otherwise it's invalid
			throw new InvalidAccommodationDataException("InvalidAccommodationDataException: Invalid number of columns.");
		}
		
		if(content[1] == null || content[1].isEmpty() || content[1].charAt(0) != 'A') { // Accommodation ID always starts with an A hence it should be otherwise it won't be loaded
			throw new InvalidAccommodationDataException("InvalidAccommodationDataException: Incorrect Accommodation ID");
		}
		
		double pricePerNight = Double.valueOf(content[4]);
        int numSharedBeds = Integer.valueOf(content[5]);

        a = new Hostel(content[1], content[2], content[3], pricePerNight, numSharedBeds);

		return a;
		
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