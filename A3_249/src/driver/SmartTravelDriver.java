package driver;
import java.io.IOException;
import java.util.Scanner;
import exceptions.*;
import service.SmartTravelService;
import visualization.DashboardGenerator;

//Assignment 3
//Question: Smart Travel Agency
//Written by: Harsh Patel (40341498)

// The purpose of this assignment is to practice file I/O, exception handling and abstract classes. It will also work on arrays and inheritance furthermore.
// This assignment is meant to add onto the first one by adding persistence, which is to save and load data in order to make programs that retain memory. It will also work on making sure that certain inputs are correctly
// written and that it won't crash if it is not

// This driver file simply keeps the menu with the options that the user can pick from. This class now redirects the logic towards the smartTravelService file where everything is happening

public class SmartTravelDriver {

    private static Scanner input = new Scanner(System.in);
    
    static SmartTravelService service = new SmartTravelService(input);

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
            while (mainChoice != 6) {
                mainChoice = runMainMenu();
                runSubMenu(mainChoice);
            }
        }

        input.close();
    }

    // Display Welcome Message
    private static void displayWelcomeMessage() {
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
        System.out.println("7. Exit");
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
                System.out.print("Thank you for using the Smart Travel Software!");
                break;

            default:
                System.out.println("Invalid choice!");
                break;
        }
    }
}