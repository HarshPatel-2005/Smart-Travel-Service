package travel_package;
import exceptions.*;

//Assignment 2
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498) and Pratik Patel (40330468)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

public class Bus extends Transportation {

    private String busCompany;
    private int numOfStops;
    private double ticketPrice;

    // Default constructor
    public Bus() throws InvalidTransportDataException {
        super();
        this.busCompany = "";
        this.numOfStops = 0;
        this.ticketPrice = 120;
    }

    // Parameterized constructor
    public Bus(String companyName, String departureCity, String arrivalCity, String busCompany, int numOfStops) throws InvalidTransportDataException {
        super(companyName, departureCity, arrivalCity);
        setBusCompany(busCompany);
        setNumOfStops(numOfStops);
        this.ticketPrice = 120;
    }
    
    public Bus(String transportID, String companyName, String departureCity, String arrivalCity, String busCompany, int numOfStops, double ticketPrice) throws InvalidTransportDataException { 
		// For the CVS file
		super(transportID, companyName, departureCity, arrivalCity);
		setBusCompany(busCompany);
        setNumOfStops(numOfStops);
		this.ticketPrice = ticketPrice;
	}

    // Copy constructor
    public Bus(Bus bus) throws InvalidTransportDataException {
        super(bus);
        setBusCompany(bus.busCompany);
        setNumOfStops(bus.numOfStops);
        this.ticketPrice = bus.ticketPrice;
    }

    // Methods
    @Override
    public double calculateCost(int numOfDays) {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return super.toString() + "\nBus Company: " + busCompany + "\nNumber Of Stops: " + numOfStops;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Bus bus = (Bus) obj;
        return (this.busCompany.equals(bus.busCompany)) && (this.numOfStops == bus.numOfStops);
    }

    // Getters and Setters
    public String getBusCompany() {
        return busCompany;
    }

    public void setBusCompany(String busCompany) throws InvalidTransportDataException {
        if (busCompany == null || busCompany.trim().isEmpty()) {
            throw new InvalidTransportDataException("Bus company cannot be empty!");
        }
        this.busCompany = busCompany;
    }

    public int getNumOfStops() {
        return numOfStops;
    }

    public void setNumOfStops(int numOfStops) throws InvalidTransportDataException {
        if (numOfStops < 1) {
            throw new InvalidTransportDataException("Bus must have at least 1 stop!");
        }
        this.numOfStops = numOfStops;
    }

    @Override
    public Transportation copy() {
        try {
            return new Bus(this);
        } catch (InvalidTransportDataException e) {
            System.out.println("Error copying Bus: " + e.getMessage());
            return null;
        }
    }
}