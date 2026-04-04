package text_files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import client_package.Client;
import exceptions.DuplicateEmailException;
import exceptions.InvalidClientDataException;
import interfaces.CsvPersistable;
import travel_package.Accommodation;
import travel_package.Bus;
import travel_package.Flight;
import travel_package.Hostel;
import travel_package.Hotel;
import travel_package.Train;
import travel_package.Transportation;
import travel_package.Trip;

public class GenericFileManager <T extends CsvPersistable>{
	
	public static <T extends CsvPersistable> void save(List<T> items, String filePath) throws IOException {
		
		PrintWriter writer = new PrintWriter(new FileWriter(filePath));
		
		for(T item : items) {
			writer.println(item.toCsvRow());
		}
		
		writer.close();
		
	}
	
	public static <T extends CsvPersistable> List<T> load(String filePath, Class<T> clazz) throws IOException {
		
		List<T> list = new ArrayList<>();
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		String line = "";
		
		T object = null;
		
		while((line = reader.readLine()) != null) {
			
			if(clazz == Client.class) {
				try {					
					Client client = Client.fromCsvRow(line);
					
					String[] content = line.split(";");
					
					boolean emailRepeat = false;
		            for (T c : list) {
		            	Client clientToCheck = (Client) c;
		                if (clientToCheck != null && clientToCheck.getEmail().equalsIgnoreCase(content[3])) {
		                    emailRepeat = true;
		                    break;
		                }
		            }

		            if (emailRepeat) { // If the email has been seen already duplicateEmailException is called
		                throw new DuplicateEmailException("DuplicateEmailException: Duplicate email: " + content[3]);
		            }
					
		            list.add((T) client);
		            
				} catch (InvalidClientDataException e) {
		        	ErrorLogger.logError("Client", line, e.getMessage());
		        } catch (DuplicateEmailException e) {
		        	ErrorLogger.logError("Client", line, e.getMessage());
		        }
			}
//			else if(clazz == Accommodation.class) {
//				try {
//					
//				}
//			}
		}
		
		return list;
		
	}
	
}
