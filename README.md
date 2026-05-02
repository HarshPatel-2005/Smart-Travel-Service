Overview: 

This project is a Java-based application that I built to simulate a travel management system. It started as a basic object-oriented project and was gradually expanded to include data validation, file persistence, and scalable architecture using collections and generics.

The system allows users to manage clients, trips, transportation, and accommodations.

What I built:

- Implemented a full object-oriented system using inheritance, polymorphism and abstraction
- Designed a menu-driven interface to manage the user's clients, trips, transportations and accommodations
- Added strict input validation and custom exception handling to enforce business rules and prevent invalid data inputs
- Built a file persistence layer using CSV files to save and load data
- Introduced error logging to handle invalid records when loading the files without crashing the application
- Utilized dynamic collections (ArrayList, LinkedList) to improve flexibility of the software
  - Such as no limitations for the number of clients, trips, transportations and accommodations
- Implemented filtering and sorting features using predicates and comparable for more advanced data queries
- Added analytics features to view the options in a different fashion

What I learned:

- How to design and structure a scalable application using object-oriented programming
- The importance of organizing the code into clear components (UI, business logic and data handling)
- How to handle data issues through validation and exception handling
- How to build reusable and flexible systems using generics
- How to refactor and improve existing code rathern than rewriting everything

Challenges: 

- Designing generic classes that work with multiple types without breaking the logic of the program
- Handling invalid data during file loading without crashing the program
- Maintaining the structure of the program as it grew

Issues:

- Not being able to create unique IDs for the different clients, trips, transportations and accommodations if they were also loaded into the system
