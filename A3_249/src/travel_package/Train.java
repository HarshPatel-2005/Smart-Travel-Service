package travel_package;
import exceptions.*;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
//are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
//can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
//feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

public class Train extends Transportation {

    private String trainType;
    private String seatClass;
    private double ticketPrice;

    // Default constructor
    public Train() throws InvalidTransportDataException {
        super();
        this.trainType = "";
        this.seatClass = "";
        this.ticketPrice = 250;
    }

    // Parameterized constructor
    public Train(String companyName, String departureCity, String arrivalCity, String trainType, String seatClass) throws InvalidTransportDataException {
        super(companyName, departureCity, arrivalCity);
        setTrainType(trainType);
        setSeatClass(seatClass);
        this.ticketPrice = 250;
    }
    
    public Train(String transportID, String companyName, String departureCity, String arrivalCity, String trainType, String seatClass, double ticketPrice) throws InvalidTransportDataException { 
		// For the CVS file
		super(transportID, companyName, departureCity, arrivalCity);
		setTrainType(trainType);
        setSeatClass(seatClass);
		this.ticketPrice = ticketPrice;
	}

    // Copy constructor
    public Train(Train train) throws InvalidTransportDataException {
        super(train);
        setTrainType(train.trainType);
        setSeatClass(train.seatClass);
        this.ticketPrice = train.ticketPrice;
    }

    // METHODS //
    
    @Override
    public double calculateCost(int numOfDays) {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTrain Type: " + trainType + "\nSeat Class: " + seatClass;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Train train = (Train) obj;
        return (this.trainType.equals(train.trainType)) && (this.seatClass.equals(train.seatClass));
    }

    @Override
    public Transportation copy() {
        try {
            return new Train(this);
        } catch (InvalidTransportDataException e) {
            System.out.println("Error copying Train: " + e.getMessage());
            return null;
        }
    }
    
    @Override
	public String toCsvRow() { // CsvPersistable Interface method inside of this class because accommodation does not have access to subclass variables
		return this.getClass().getSimpleName().toUpperCase() + ";" + super.getID() + ";" + super.getCompanyName() + ";" + super.getDepartureCity() + ";" + super.getArrivalCity()
		+ ";" + calculateCost(0) + ";" + this.getTrainType();
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
		
		double price = Double.parseDouble(content[5]);
		t = new Train(content[1], content[2], content[3], content[4], content[6], "0", price);

		return t;
		
	}

    // GETTERS AND SETTERS //
    
    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) throws InvalidTransportDataException {
        if (trainType == null || trainType.trim().isEmpty()) {
            throw new InvalidTransportDataException("Train type cannot be empty!");
        }
        this.trainType = trainType;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) throws InvalidTransportDataException {
        if (seatClass == null || seatClass.trim().isEmpty()) {
            throw new InvalidTransportDataException("Seat class cannot be empty!");
        }
        this.seatClass = seatClass;
    }
}