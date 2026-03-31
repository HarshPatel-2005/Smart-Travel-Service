package exceptions;

//Assignment 2
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498) and Pratik Patel (40330468)

// The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
// This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
// written and that it won't crash if it is not

// This class handles the duplicateEmailException. The reason why it extends RuntimeException is because it is an unchecked exception, which means that the compiler won't handle it and only at runtime will this exception
// exist. This is why for this it is a runtimeException that must be called in order to handle it.

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }

}
