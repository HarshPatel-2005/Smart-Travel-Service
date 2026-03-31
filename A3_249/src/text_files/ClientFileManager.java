package text_files;

//Assignment 2
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498) and Pratik Patel (40330468)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

// This file handles loading the data of the client into the system and saving the information from the system onto a file

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import client_package.Client;
import exceptions.DuplicateEmailException;
import exceptions.InvalidClientDataException;


public class ClientFileManager {
	
	public static void saveClients(Client[] clients, int clientCount, String filePath) throws IOException {
		
		File csvOutputFile = new File(filePath);
		csvOutputFile.getParentFile().mkdirs();
		
		if(clientCount == 0) {
			System.out.println("There are no clients to save!");
			return;
		}
		else {
			PrintWriter writer = new PrintWriter(new FileWriter(csvOutputFile));
			
			for(int i = 0; i < clientCount; i++) {
				writer.println(clients[i].getClientID() + ";" + clients[i].getFirstName() + ";" + clients[i].getLastName() + ";" + clients[i].getEmail());
			}
			
			writer.close();
			
			System.out.println("Clients Successfully saved!");
		}
		
	}
	
	public static int loadClients(Client[] clients, int clientCount, String filePath) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    String line;

	    while ((line = reader.readLine()) != null) {
	        try {
	            String[] content = line.split(";");

	            if (content.length != 4) { // Exception if there aren't enough contents in the line
	                throw new InvalidClientDataException("InvalidClientDataException: Invalid number of columns.");
	            }

	            if (content[0] == null || content[0].isEmpty() || content[0].charAt(0) != 'C') { // Exception if the ID is incorrect
	                throw new InvalidClientDataException("InvalidClientDataException: Invalid client ID: " + content[0]);
	            }

	            boolean emailRepeat = false;
	            for (int i = 0; i < clientCount; i++) {
	                if (clients[i] != null && clients[i].getEmail().equalsIgnoreCase(content[3])) {
	                    emailRepeat = true;
	                    break;
	                }
	            }

	            if (emailRepeat) { // If the email has been seen already duplicateEmailException is called
	                throw new DuplicateEmailException("DuplicateEmailException: Duplicate email: " + content[3]);
	            }

	            Client client = new Client(content[0], content[1], content[2], content[3]);
	            clients[clientCount++] = client;

	        } catch (InvalidClientDataException e) {
	        	ErrorLogger.logError("Client", line, e.getMessage());
	        } catch (DuplicateEmailException e) {
	        	ErrorLogger.logError("Client", line, e.getMessage());
	        }
	    }

	    reader.close();
	    return clientCount;
	}
	
	
}
