package travel_package;
import client_package.Client;
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