package text_files;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

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
