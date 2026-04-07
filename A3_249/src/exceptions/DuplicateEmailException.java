package exceptions;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
//are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
//can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
//feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

// This class handles the duplicateEmailException. The reason why it extends RuntimeException is because it is an unchecked exception, which means that the compiler won't handle it and only at runtime will this exception
// exist. This is why for this it is a runtimeException that must be called in order to handle it.

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }

}
