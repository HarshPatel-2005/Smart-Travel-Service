package driver;
import java.io.IOException;
import java.util.Scanner;
import exceptions.*;
import service.SmartTravelService;
import visualization.DashboardGenerator;

// Assignment 3
// Question: Smart Travel Agency
// Written by: Harsh Patel (40341498)

// The purpose of this assignment is to practice using Interfaces, Generics, ArrayList and LinkedList. The interfaces are used so that we can share common features between multiple classes that are not connected. Generics
// are useful when it doesn't matter which file we put but the files are able to be placed there. These generics usually need a specific type which is why we use the interfaces and abstract classes to specify which classes
// can be put in the generics. This assignment also enhances usability so that it can be with no limited spaces, using arrayList. This allows us to store multiple instances without a limit. LinkedList will be used as a real
// feature that is seen, which are the recently viewed objects. When the user views a specific object the recently viewed will save which objects were seen recently

// This driver file simply keeps the menu with the options that the user can pick from. This class now redirects the logic towards the smartTravelService file where everything is happening. We now have a new menu that allows
// the user to view advanced information about the travels

public class SmartTravelDriver {

    private static Scanner input = new Scanner(System.in);
    
    static SmartTravelService service = new SmartTravelService(input);
    // Test
    // Main method
    public static void main(String[] args) {
        int mainChoice = 0;
        int userChoicePriorStart = 0;
        displayWelcomeMessage();
        System.out.print("Choice: ");
        userChoicePriorStart = input.nextInt();

        while (userChoicePriorStart != 1 && userChoicePriorStart != 2) {
            System.out.print("Invalid Input. Try Again: ");
            userChoicePriorStart = input.nextInt();
        }

        if (userChoicePriorStart == 2) {
        	service.predefinedScenario();
        } else {
            while (mainChoice != 8) {
                mainChoice = runMainMenu();
                runSubMenu(mainChoice);
            }
        }

        input.close();
    }

    // Display Welcome Message
    private static void displayWelcomeMessage() {
    	System.out.println("Program made by : Harsh Patel ");
    	
        System.out.println("-------------------------------------------");
        System.out.println("Welcome to Smart Travel Planner System!");
        System.out.println("-------------------------------------------");
        System.out.println("Before we start, would you like to test a predefined scenario or make your own?"
                + "\n1. Bring me to Management!" + "\n2. Test out a Predefined Scenario!");
    }

    // Prompt for Choice of management Section
    private static int runMainMenu() {
        System.out.println("\nPlease select a management section:");
        System.out.println("1. Client Management");
        System.out.println("2. Trip Management");
        System.out.println("3. Transportation Management");
        System.out.println("4. Accomodation Management");
        System.out.println("5. Additional Operations");
        System.out.println("6. Generate Dashboard");
        System.out.println("7. Advanced Analytics");
        System.out.println("8. Exit");
        System.out.print("Your choice: ");
        int mainChoice = input.nextInt();
        input.nextLine();
        return mainChoice;
    }

    // Switch case for management sections
    private static void runSubMenu(int mainChoice) {
        switch (mainChoice) {
            case 1:
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. Add a client");
                System.out.println("2. Edit a client");
                System.out.println("3. Delete a client");
                System.out.println("4. List all clients");
                System.out.println("5. Exit to Main Menu");
                System.out.print("Enter your choice: ");
                int choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1: service.addClient(); break;
                    case 2: service.editClient(); break;
                    case 3: service.deleteClient(); break;
                    case 4: service.listAllClients(); break;
                    case 5: System.out.println("Returning to Main Menu"); return;
                    default: System.out.println("Invalid choice!"); break;
                }
                break;

            case 2:
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. Create a trip");
                System.out.println("2. Edit a trip information");
                System.out.println("3. Cancel a trip");
                System.out.println("4. List all trips");
                System.out.println("5. List all trips for a specific client");
                System.out.println("6. Exit to Main Menu");
                System.out.print("Enter your choice: ");
                choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1: service.createTrip(); break;
                    case 2: service.editTrip(); break;
                    case 3: service.cancelTrip(); break;
                    case 4: SmartTravelService.listAllTrips(); break;
                    case 5: service.listTripsByClient(); break;
                    case 6: System.out.println("Returning to Main Menu!"); return;
                    default: System.out.println("Invalid choice!"); break;
                }
                break;

            case 3:
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. Add a transportation option");
                System.out.println("2. Remove a transportation option");
                System.out.println("3. List transporation options by type (Flight, Train, Bus)");
                System.out.println("4. Exit to Main Menu");
                System.out.print("Enter your choice: ");
                choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1: service.addTransportation(); break;
                    case 2: service.removeTransportation(); break;
                    case 3: service.listTransportationByType(); break;
                    case 4: System.out.println("Returning to Main Menu!"); return;
                    default: System.out.println("Invalid choice!"); break;
                }
                break;

            case 4:
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. Add an accomodation option");
                System.out.println("2. Remove an accomodation");
                System.out.println("3. List accommodations by type (Hotel, Hostel)");
                System.out.println("4. Exit to Main Menu");
                System.out.print("Enter your choice: ");
                choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1: service.addAccomodation(); break;
                    case 2: service.removeAccomodation(); break;
                    case 3: service.listAccomodationByType(); break;
                    case 4: System.out.println("Returning to Main Menu!"); return;
                    default: System.out.println("Invalid choice!"); break;
                }
                break;

            case 5:
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. Display the most expensive trip");
                System.out.println("2. Calculate and display the total cost of a trip");
                System.out.println("3. Create a deep copy of the transportation array");
                System.out.println("4. Create a deep copy of the accommodation array");
                System.out.println("5. List All Data");
				System.out.println("6. Load All Data from Files");
				System.out.println("7. Save All Data to Files");
				System.out.println("8. Exit to Main Menu");
                System.out.print("Enter your choice: ");
                choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1: service.displayMostExpensiveTrip(); break;
                    case 2: service.displayCostOfTrip(); break;
                    case 3: service.copyTransportArray(); break;
                    case 4: service.copyAccommodationArray(); break;
                    case 5: SmartTravelService.listAllData(); break;
					case 6: try {
						SmartTravelService.LoadAllData("output/data/");
					} catch (InvalidClientDataException | InvalidTransportDataException
							| InvalidAccommodationDataException | InvalidTripDataException e) {
						e.printStackTrace();
					} break;
					case 7: SmartTravelService.SaveAllData("output/data/"); break;
					case 8: System.out.println("Returning to Main Menu!"); return;
                    default: System.out.println("Invalid choice!"); break;
                }
                break;

            case 6:
				try {
					DashboardGenerator.generateDashboard(service);
				} catch (IOException e) {
					e.printStackTrace();
				}
            	break;
            	
            case 7:
            	System.out.println("\nWhat would you like to do?");
            	System.out.println("1. Trips by Destination");
            	System.out.println("2. Trips by Cost Range");
            	System.out.println("3. Top Clients By Spending");
            	System.out.println("4. Recent Trips");
            	System.out.println("5. Smart Sort Collections");
            	System.out.println("6. Back to Main Menu");
            	System.out.print("Enter your choice: ");
            	choice = input.nextInt();
            	input.nextLine();
            	
            	switch(choice) {
            		case 1: service.tripsByDestination(); break;
            		case 2: service.tripsByCostRange(); break;
            		case 3: service.topClientsBySpending(); break;
            		case 4: service.viewRecentTrips(); break;
            		case 5: service.SmartSortCollections(); break;
            		case 6: System.out.println("Returning to Main Menu!"); return;
            		default: System.out.println("Invalid Choice!"); break;
            	}
            	
                break;
            case 8:
                System.out.print("Thank you for using the Smart Travel Software!");
                break;

            default:
                System.out.println("Invalid choice!");
                break;
        }
    }
}