package travel_package;
import exceptions.*;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
//are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
//can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
//feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

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

    // METHODS //
    
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
    
    @Override
   	public String toCsvRow() { // CsvPersistable Interface method inside of this class because accommodation does not have access to subclass variables
   		return this.getClass().getSimpleName().toUpperCase() + ";" + super.getID() + ";" + super.getCompanyName() + ";" + super.getDepartureCity() + ";" + super.getArrivalCity()
   		+ ";" + calculateCost(0) + ";" + this.getNumOfStops();
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
   		
   		int numOfStops = Integer.parseInt(content[6]);
		double price = Double.parseDouble(content[5]);
		t = new Bus(content[1], "Expedia", content[3], content[4], content[2], numOfStops, price);

   		return t;
   		
   	}

    // GETTERS AND SETTERS //
    
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