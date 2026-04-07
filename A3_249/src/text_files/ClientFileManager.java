package text_files;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
//are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
//can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
//feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

// This file handles loading the data of the client into the system and saving the information from the system onto a file

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import client_package.Client;
import exceptions.DuplicateEmailException;
import exceptions.InvalidClientDataException;


public class ClientFileManager {
	
	public static void saveClients(List<Client> clients, int clientCount, String filePath) throws IOException {
		
		File csvOutputFile = new File(filePath);
		csvOutputFile.getParentFile().mkdirs();
		
		if(clientCount == 0) {
			System.out.println("There are no clients to save!");
			return;
		}
		else {
			PrintWriter writer = new PrintWriter(new FileWriter(csvOutputFile));
			
			for(Client c : clients) {
				writer.println(c.getID() + ";" + c.getFirstName() + ";" + c.getLastName() + ";" + c.getEmail());
			}
			
			writer.close();
			
			System.out.println("Clients Successfully saved!");
		}
		
	}
	
	public static void loadClients(List<Client> clients, int clientCount, String filePath) throws IOException {
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
	            for (Client c : clients) {
	                if (c != null && c.getEmail().equalsIgnoreCase(content[3])) {
	                    emailRepeat = true;
	                    break;
	                }
	            }

	            if (emailRepeat) { // If the email has been seen already duplicateEmailException is called
	                throw new DuplicateEmailException("DuplicateEmailException: Duplicate email: " + content[3]);
	            }

	            Client client = new Client(content[0], content[1], content[2], content[3]);
	            clients.add(client);

	        } catch (InvalidClientDataException e) {
	        	ErrorLogger.logError("Client", line, e.getMessage());
	        } catch (DuplicateEmailException e) {
	        	ErrorLogger.logError("Client", line, e.getMessage());
	        }
	    }

	    reader.close();
	}
	
	
}
