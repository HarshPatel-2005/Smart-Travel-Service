package client_package;
import exceptions.*;
import interfaces.CsvPersistable;
import interfaces.Identifiable;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

// The client class is where the creation of the client happens. Within this assignment, we are now tasked to verify that the user has input valid inputs and if not, rather than crashing the client we simply create
// an exception to handle it and let the user know that is not possible and send them back to the start

public class Client implements Identifiable, CsvPersistable, Comparable<Client>{
    private String clientID;
    private String firstName;
    private String lastName;
    private String email;
    private static int counter = 1001;
	private double amountSpent;

    // Default constructor
    public Client() throws InvalidClientDataException { 
        this.clientID = "C" + counter;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        counter++;
    }

    // Parameterized constructor
    
    // Constructor can now throw an exception if the creation of the client has an incorrect input. This input would be from the first name, last name and email
    public Client(String firstName, String lastName, String email) throws InvalidClientDataException {
        this.clientID = "C" + counter;
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        counter++;
    }
    
    // For CVS file
    
    // Constructor can now throw an exception if the creation of the client has an incorrect input. This input would be from the first name, last name and email
    public Client(String clientID, String firstName, String lastName, String email) throws InvalidClientDataException{
	    this.clientID = clientID;
	    setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
	    counter++;
	}

    // Copy constructor
    
    // Constructor can now throw an exception if the creation of the client has an incorrect input. This input would be from the first name, last name and email
    public Client(Client other) throws InvalidClientDataException {
        this.clientID = "C" + counter;
        setFirstName(other.firstName);
        setLastName(other.lastName);
        setEmail(other.email);
        counter++;
    }
    
    // METHODS //
    
    public void addAmountSpent(double amountSpent) {
	    this.amountSpent = amountSpent;
	}

	@Override
	public int compareTo(Client o) { // Comparable Interface Method
		
		if(o.amountSpent > this.amountSpent)
			return 1;
		else if(o.amountSpent == this.amountSpent)
			return 0;
		else
			return -1;
		
	}

	@Override
	public String toCsvRow() { // CsvPersistable Interface method
		return this.getID() + ";" + this.getFirstName() + ";" + this.getLastName() + ";" + this.getEmail();
	}
	
	public static Client fromCsvRow(String csvLine) throws InvalidClientDataException {
		
		Client client = null;

        String[] content = csvLine.split(";");

        if (content.length != 4) { // Exception if there aren't enough contents in the line
            throw new InvalidClientDataException("InvalidClientDataException: Invalid number of columns.");
        }

        if (content[0] == null || content[0].isEmpty() || content[0].charAt(0) != 'C') { // Exception if the ID is incorrect
            throw new InvalidClientDataException("InvalidClientDataException: Invalid client ID: " + content[0]);
        }

        client = new Client(content[0], content[1], content[2], content[3]); // The client currently does not pay attention to the email duplication as it will be compared and handled elsewhere.

		return client;
		
	}
	
	// toString method
    @Override
    public String toString() {
        return "Client ID: " + this.clientID + ", Name: " + this.firstName + " " + this.lastName + ", Email: " + this.email;
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
        Client other = (Client) obj;
        return this.firstName.equals(other.firstName) && this.lastName.equals(other.lastName) && this.email.equals(other.email);
    }
    
    // These two methods will be used to calculate how much each client has spent as they can have multiple trips
	public double getTotalSpent() {
	    return amountSpent;
	}

    // GETTERS AND SETTERS //
    
    @Override
    public String getID() { // Identifiable Interface Method
        return this.clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    // The first name now has certain constraints that the user must oblige to. If the first name is empty, it will throw InvalidClientDataException with the message. 
    // The exception will also be called if the name is longer than 50 characters.
    public void setFirstName(String firstName) throws InvalidClientDataException {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidClientDataException("First name cannot be empty!");
        }
        if (firstName.length() > 50) {
            throw new InvalidClientDataException("First name cannot exceed 50 characters!");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    // The last name has certain constraints that the user must oblige to. If the last name is empty, it will throw InvalidClientDataException with the message. 
    // The exception will also be called if the name is longer than 50 characters.
    public void setLastName(String lastName) throws InvalidClientDataException {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidClientDataException("Last name cannot be empty!");
        }
        if (lastName.length() > 50) {
            throw new InvalidClientDataException("Last name cannot exceed 50 characters!");
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    // The email now has multiple constraints to make sure they put an actual email. It cannot be empty, it must contain the characters @ and ., it cannot have any spaces and cannot be longer than 100 characters.
    // If so we throw an exception so that it can be caught later on
    public void setEmail(String email) throws InvalidClientDataException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidClientDataException("Email cannot be empty!");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidClientDataException("Email must contain '@' and '.'!");
        }
        if (email.contains(" ")) {
            throw new InvalidClientDataException("Email cannot contain spaces!");
        }
        if (email.length() > 100) {
            throw new InvalidClientDataException("Email cannot exceed 100 characters!");
        }
        this.email = email;
    }

}