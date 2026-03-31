package travel_package;
import exceptions.*;

//Assignment 2
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498) and Pratik Patel (40330468)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

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

    // Methods
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

    // Getters and Setters
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