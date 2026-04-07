package client_package;
import exceptions.*;
import interfaces.CsvPersistable;
import interfaces.Identifiable;

// Assignment 3
// Question: Smart Travel Agency
// Written by: Harsh Patel (40341498)

// The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
// are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
// can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
// feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

// The client class is where the creation of the client happens. Within this assignment, we are now tasked with implementing interfaces to use it elsewhere, where all of these features are shared. Identifiable is utilized to
// find the IDs, which exist in client, trips, accommodations and transportations even when they're not related. CsvPersistable is used for generics to specify in the file that we want specifically a CsvPersistable implemented
// class within the generic. These exist in all 4 of the classes. Comparable is utilized to compare itself to other objects of the same type which is used for smart sorting. This is also implemented in multiple classes

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
		
		if(o.getTotalSpent() > this.getTotalSpent())
			return 1; // Collections looks at the return and sees that it's 1, which means that if 1 is returned then other is bigger than the currently viewed client and this is useful for returning the totalSpent from the clients in descending order
		else if(o.getTotalSpent() < this.getTotalSpent())
			return -1; // If it's -1, then the currently viewed object is bigger than the object it's being compared to. This is useful again for descending order of totalSpent
		else
			return 0; // If they're equal then it returns 0
		
	}

	@Override
	public String toCsvRow() { // CsvPersistable Interface method
		return this.getID() + ";" + this.getFirstName() + ";" + this.getLastName() + ";" + this.getEmail(); // This prints the values of the client to a csv row that is permissible 
	}
	
	public static Client fromCsvRow(String csvLine) throws InvalidClientDataException { // This method check if the csvLine is valid and then creates a client from it
		
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