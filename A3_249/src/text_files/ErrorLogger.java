package text_files;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
//are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
//can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
//feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorLogger {

    public static void logError(String entity, String line, String message) {
    	
        try (PrintWriter writer = new PrintWriter(new FileWriter("output/logs/errors.txt", true))) {
            writer.println("Entity: " + entity);
            writer.println("Line: " + line);
            writer.println("Error: " + message);
            writer.println("-----------------------------------");
        } catch (IOException e) {
            System.out.println("Error Logger file not present");
        }
    }
    
}
