package exceptions;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

//This class handles if the client data is incorrect from the user's end

public class InvalidClientDataException  extends Exception {
    public InvalidClientDataException(String message) {
        super(message);
    }

}
