package travel_package;
import client_package.Client;
import exceptions.*;
import interfaces.CsvPersistable;
import interfaces.Identifiable;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

public abstract class Transportation implements Identifiable, CsvPersistable, Comparable<Transportation>{

    private String transportID;
    private String companyName;
    private String departureCity;
    private String arrivalCity;
    private static int counter = 3001;

    // Default constructor
    public Transportation() throws InvalidTransportDataException {
        transportID = "TR" + (counter);
        this.companyName = "";
        this.departureCity = "";
        this.arrivalCity = "";
        counter++;
    }

    // Parameterized constructor
    public Transportation(String companyName, String departureCity, String arrivalCity) throws InvalidTransportDataException {
        transportID = "TR" + (counter);
        setCompanyName(companyName);
        setDepartureCity(departureCity);
        setArrivalCity(arrivalCity);
        counter++;
    }
    
    public Transportation(String transportID, String companyName, String departureCity, String arrivalCity) throws InvalidTransportDataException {
		this.transportID = transportID;
		setCompanyName(companyName);
        setDepartureCity(departureCity);
        setArrivalCity(arrivalCity);
		counter++;
	}

    // Copy constructor
    public Transportation(Transportation transportation) throws InvalidTransportDataException {
        transportID = "TR" + (counter);
        setCompanyName(transportation.companyName);
        setDepartureCity(transportation.departureCity);
        setArrivalCity(transportation.arrivalCity);
        counter++;
    }
    
    // METHODS //

    // Abstract methods
    public abstract double calculateCost(int numOfDays);
    
    public abstract Transportation copy();

    // toString method
    @Override
    public String toString() {
        return "\nTransport ID: " + transportID + "\nCompany Name: " + companyName + "\nDeparture City: " + departureCity + "\nArrival City: " + arrivalCity;
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
        Transportation transportation = (Transportation) obj;
        return (this.companyName.equals(transportation.companyName))
                && (this.departureCity.equals(transportation.departureCity))
                && (this.arrivalCity.equals(transportation.arrivalCity));
    }
    
    @Override
	public int compareTo(Transportation o) { // Comparable Interface Method
		
		if(o.calculateCost(0) > this.calculateCost(0))
			return 1;
		else if(o.calculateCost(0) == this.calculateCost(0))
			return 0;
		else
			return -1;
		
	}

    // GETTERS AND SETTERS //
    
    @Override
    public String getID() {
        return transportID;
    }

    public void setTransportID(String transportID) {
        this.transportID = transportID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) throws InvalidTransportDataException {
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new InvalidTransportDataException("Company name cannot be empty!");
        }
        this.companyName = companyName;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) throws InvalidTransportDataException {
        if (departureCity == null || departureCity.trim().isEmpty()) {
            throw new InvalidTransportDataException("Departure city cannot be empty!");
        }
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) throws InvalidTransportDataException {
        if (arrivalCity == null || arrivalCity.trim().isEmpty()) {
            throw new InvalidTransportDataException("Arrival city cannot be empty!");
        }
        this.arrivalCity = arrivalCity;
    }
    
}