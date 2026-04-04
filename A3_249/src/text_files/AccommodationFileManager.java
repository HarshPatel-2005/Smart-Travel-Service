package text_files;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
//This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
//written and that it won't crash if it is not

// This file handles loading the data of the accommodation into the system and saving the information from the system onto a file

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import exceptions.InvalidAccommodationDataException;
import travel_package.Accommodation;
import travel_package.Hotel;
import travel_package.Hostel;

public class AccommodationFileManager {

	public static void saveAccommodation(List<Accommodation> accomodationOptions, int accommodationCount, String filePath) throws IOException {
		
		File csvOutputFile = new File(filePath); // Creates the file
		csvOutputFile.getParentFile().mkdirs(); // Makes sure that the folder exist
		
		if(accommodationCount == 0) {
			System.out.println("There are no accommodations to save!");
			return;
		}
		else {
			PrintWriter writer = new PrintWriter(new FileWriter(csvOutputFile)); // Writes the information onto a file
			
			for(Accommodation a : accomodationOptions) {
				
				String accommodationType = a.getClass().getSimpleName();
				String capitalizedAccommodationType = accommodationType.toUpperCase();
				
				if(accommodationType.equals("Hotel")) {
					Hotel hotel = (Hotel) a;
					
					writer.println(capitalizedAccommodationType + ";" + a.getID() + ";" + a.getName()
							+ ";" + a.getLocation() + ";" + a.getPricePerNight() + ";" + hotel.getStarRating());
				}
				else {
					Hostel hostel = (Hostel) a;
					
					writer.println(capitalizedAccommodationType + ";" + a.getID() + ";" + a.getName()
							+ ";" + a.getLocation() + ";" + a.getPricePerNight() + ";" + hostel.getNumOfSharedBeds());
				}
			}
			
			writer.close(); // Avoids leaking resources
			
			System.out.println("Accommodation Successfully saved!");
			
		}
		
	}
	
	public static void loadAccommodation(List<Accommodation> accomodationOptions, int accommodationCount, String filePath) throws IOException, InvalidAccommodationDataException {
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath)); // Reader variable to read the information from a file
		
		String line;
		
		while((line = reader.readLine()) != null) { // Checks if the line that is being read has content
			try {
				String[] content = line.split(";"); // Splitter to split the content into an array
				
				if(content.length != 6) { // The array must contain 6 columns of data otherwise it's invalid
					throw new InvalidAccommodationDataException("InvalidAccommodationDataException: Invalid number of columns.");
				}
				
				if(content[1] == null || content[1].isEmpty() || content[1].charAt(0) != 'A') { // Accommodation ID always starts with an A hence it should be otherwise it won't be loaded
					throw new InvalidAccommodationDataException("InvalidAccommodationDataException: Incorrect Accommodation ID");
				}
				
				double pricePerNight = Double.valueOf(content[4]);
				
				Accommodation currentAccommodation;
				
				if(content[0].equals("HOTEL")) {
					int starRating = Integer.valueOf(content[5]); 
					currentAccommodation = new Hotel(content[1], content[2], content[3], pricePerNight, starRating);
				}
				else if(content[0].equals("HOSTEL")){
					int numSharedBeds = Integer.valueOf(content[5]);					
					currentAccommodation = new Hostel(content[1], content[2], content[3], pricePerNight, numSharedBeds);
				}
				else { // If the type is none of the two, produce an exception
					throw new InvalidAccommodationDataException("InvalidAccommodationDataException: Unknown Accommodation Type: " + content[0]);
				}

				accomodationOptions.add(currentAccommodation);
			} catch (InvalidAccommodationDataException e) {
				ErrorLogger.logError("Accommodation", line, e.getMessage());
			}
		}
		
		reader.close(); // Avoids leaking resources
		
	}
	
}
