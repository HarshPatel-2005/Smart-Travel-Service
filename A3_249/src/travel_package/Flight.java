package travel_package;
import exceptions.*;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

public class Flight extends Transportation {

    private String airlineName;
    private double luggageAllowance;
    private double ticketPrice = 0;

    // Default constructor
    public Flight() throws InvalidTransportDataException {
        super();
        this.airlineName = "";
        this.luggageAllowance = 0;
        this.ticketPrice = 1500;
    }

    // Parameterized constructor
    public Flight(String companyName, String departureCity, String arrivalCity, String airlineName, double luggageAllowance) throws InvalidTransportDataException {
        super(companyName, departureCity, arrivalCity);
        setAirlineName(airlineName);
        setLuggageAllowance(luggageAllowance);
        this.ticketPrice = 1500;
    }
    
    public Flight(String transportID, String companyName, String departureCity, String arrivalCity, String airlineName, double luggageAllowance, double ticketPrice) throws InvalidTransportDataException { 
		// For CVS file
	  super(transportID, companyName, departureCity, arrivalCity);
	  this.airlineName = airlineName;
	  this.luggageAllowance = luggageAllowance;
	  this.ticketPrice = ticketPrice;
	}

    // Copy constructor
    public Flight(Flight flight) throws InvalidTransportDataException {
        super(flight);
        setAirlineName(flight.airlineName);
        setLuggageAllowance(flight.luggageAllowance);
        this.ticketPrice = flight.ticketPrice;
    }

    // METHODS //
    
    @Override
    public double calculateCost(int numOfDays) {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return super.toString() + "\nAirline: " + airlineName + "\nLuggage Allowance: " + luggageAllowance + "kg";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Flight flight = (Flight) obj;
        return (this.airlineName.equals(flight.airlineName)) && (this.luggageAllowance == flight.luggageAllowance);
    }

    @Override
    public Transportation copy() {
        try {
            return new Flight(this);
        } catch (InvalidTransportDataException e) {
            System.out.println("Error copying Flight: " + e.getMessage());
            return null;
        }
    }
    
    @Override
	public String toCsvRow() { // CsvPersistable Interface method inside of this class because accommodation does not have access to subclass variables
		return this.getClass().getSimpleName().toUpperCase() + ";" + super.getID() + ";" + super.getCompanyName() + ";" + super.getDepartureCity() + ";" + super.getArrivalCity()
		+ ";" + calculateCost(0) + ";" + this.getLuggageAllowance();
	}
    
    public static Transportation fromCsvRow(String csvLine) throws InvalidTransportDataException {
		
    	Transportation t = null;

        String[] content = csvLine.split(";");	

        if(content.length != 7) {
			throw new InvalidTransportDataException("InvalidTransportDataException: Invalid number of columns");
		}
		
		if(content[1] == null || content[1].isEmpty() || !content[1].substring(0, 2).equals("TR")) {
			throw new InvalidTransportDataException("InvalidTransportDataException: Incorrect Transportation ID: " + content[1]);
		}
		
		double luggage = Double.parseDouble(content[6]);
		double price = Double.parseDouble(content[5]);
		t = new Flight(content[1], "Expedia", content[3], content[4], content[2], luggage, price);

		return t;
		
	}

    // GETTERS AND SETTERS //
    
    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) throws InvalidTransportDataException {
        if (airlineName == null || airlineName.trim().isEmpty()) {
            throw new InvalidTransportDataException("Airline name cannot be empty!");
        }
        this.airlineName = airlineName;
    }

    public double getLuggageAllowance() {
        return luggageAllowance;
    }

    public void setLuggageAllowance(double luggageAllowance) throws InvalidTransportDataException {
        if (luggageAllowance < 0) {
            throw new InvalidTransportDataException("Luggage allowance cannot be negative!");
        }
        this.luggageAllowance = luggageAllowance;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
}