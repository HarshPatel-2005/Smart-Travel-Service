package travel_package;
import exceptions.*;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

public class Hotel extends Accommodation {

    private int starRating;

    // Default constructor
    public Hotel() throws InvalidAccommodationDataException {
        super();
        this.starRating = 0;
    }

    // Parameterized constructor
    public Hotel(String name, String location, double pricePerNight, int starRating) throws InvalidAccommodationDataException {
        super(name, location, pricePerNight);
        setStarRating(starRating);
    }
    
    public Hotel(String accommodationID, String name, String location, double pricePerNight, int starRating) throws InvalidAccommodationDataException {
		super(accommodationID, name,  location,  pricePerNight);
		this.starRating = starRating;
	}

    // Copy constructor
    public Hotel(Hotel hotel) throws InvalidAccommodationDataException {
        super(hotel);
        setStarRating(hotel.starRating);
    }

    // METHODS //
    @Override
    public double calculateCost(int numOfDays) {
        double regularPrice = getPricePerNight() * numOfDays;
        double surchargePrice = regularPrice * 0.1;
        return (regularPrice + surchargePrice);
    }

    @Override
    public String toString() {
        return super.toString() + "\nStar Rating: " + starRating;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Hotel hotel = (Hotel) obj;
        return (this.starRating == hotel.starRating);
    }

    @Override
    public Accommodation copy() {
        try {
            return new Hotel(this);
        } catch (InvalidAccommodationDataException e) {
            System.out.println("Error copying Hotel: " + e.getMessage());
            return null;
        }
    }
    
    @Override
	public String toCsvRow() { // CsvPersistable Interface method inside of this class because accommodation does not have access to subclass variables
		return this.getClass().getSimpleName().toUpperCase() + ";" + super.getID() + ";" + super.getName() + ";" + super.getLocation() + ";" + super.getPricePerNight() + ";" + this.getStarRating();
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
		int starRating = Integer.valueOf(content[5]); 

        a = new Hotel(content[1], content[2], content[3], pricePerNight, starRating);

		return a;
		
	}

    // GETTERS AND SETTERS //
    public int getStarRating() {  
        return starRating;
    }

    public void setStarRating(int starRating) throws InvalidAccommodationDataException {
        if (starRating < 1 || starRating > 5) {
            throw new InvalidAccommodationDataException("Star rating must be between 1 and 5!");
        }
        this.starRating = starRating;
    }
}