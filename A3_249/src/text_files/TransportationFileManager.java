package text_files;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

//The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
//are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
//can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
//feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import exceptions.InvalidTransportDataException;
import travel_package.Transportation;
import travel_package.Bus;
import travel_package.Flight;
import travel_package.Train;

public class TransportationFileManager {

	public static void saveTransportation(List<Transportation> transportOptions, int transportationCount, String filePath) throws IOException {
		
		File csvOutputFile = new File(filePath);
		csvOutputFile.getParentFile().mkdirs();
		
		if(transportationCount == 0) {
			System.out.println("There are no transportation to save!");
			return;
		}
		else {
			PrintWriter writer = new PrintWriter(new FileWriter(csvOutputFile));
			
			for(Transportation t : transportOptions) {
				
				String transportType = t.getClass().getSimpleName();
				String capitalizedTransportType = transportType.toUpperCase();
				
				if(t.getClass().getSimpleName().equals("Flight")) {
					Flight flight = (Flight) t;
					
					writer.println(capitalizedTransportType + ";" + t.getID() + ";" + t.getCompanyName() + ";" + t.getDepartureCity() + ";" + t.getArrivalCity()
							+ ";" + t.calculateCost(0) + ";" + flight.getLuggageAllowance());
				}
				else if(t.getClass().getSimpleName().equals("Train")){
					Train train = (Train) t;
					
					writer.println(capitalizedTransportType + ";" + t.getID() + t.getCompanyName() + ";" + t.getDepartureCity() + ";" + t.getArrivalCity()
							+ ";" + t.calculateCost(0) + ";" + train.getTrainType());
				}
				else if(t.getClass().getSimpleName().equals("Bus")){
					Bus bus = (Bus) t;
					
					writer.println(capitalizedTransportType + ";" + t.getID() + t.getCompanyName() + ";" + t.getDepartureCity() + ";" + t.getArrivalCity()
							+ ";" + t.calculateCost(0) + ";" + bus.getNumOfStops());
				}
				else {
					continue;
				}
				
			}
			
			writer.close();
			
			System.out.println("Transportations Successfully saved!");
		}
		
	}
	
	public static void loadTransportation(List<Transportation> transportOptions, int transportationCount, String filePath) throws IOException, InvalidTransportDataException {
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		String line;
		
		while((line = reader.readLine()) != null) {
			
			try {
				String[] content = line.split(";");
				
				if(content.length != 7) {
					throw new InvalidTransportDataException("InvalidTransportDataException: Invalid number of columns");
				}
				
				if(content[1] == null || content[1].isEmpty() || !content[1].substring(0, 2).equals("TR")) {
					throw new InvalidTransportDataException("InvalidTransportDataException: Incorrect Transportation ID: " + content[1]);
				}
				
				Transportation currentTransportation;
				
				if(content[0].equals("FLIGHT")) {
					double luggage = Double.parseDouble(content[6]);
					double price = Double.parseDouble(content[5]);
					currentTransportation = new Flight(content[1], "Expedia", content[3], content[4], content[2], luggage, price);
				}
				else if(content[0].equals("TRAIN")){
					double price = Double.parseDouble(content[5]);
					currentTransportation = new Train(content[1], content[2], content[3], content[4], content[6], "0", price);
				}
				else if(content[0].equals("BUS")){
					int numOfStops = Integer.parseInt(content[6]);
					double price = Double.parseDouble(content[5]);
					currentTransportation = new Bus(content[1], "Expedia", content[3], content[4], content[2], numOfStops, price);
				}
				else {
					throw new InvalidTransportDataException("InvalidTransportDataException: Unknown Transportation Type: " + content[0]);
				}

				transportOptions.add(currentTransportation);
			} catch (InvalidTransportDataException e) {
				ErrorLogger.logError("Transportation", line, e.getMessage());
			}
		}
		
		reader.close();

	}
	
}
