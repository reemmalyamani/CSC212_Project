import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        RideSharingSystem system = new RideSharingSystem();

        boolean ridersLoaded = system.loadRidersFromCSV("riders_100.csv");
        boolean driversLoaded = system.loadDriversFromCSV("drivers_30.csv");
        boolean ridesLoaded = system.loadRidesFromCSV("rides_40.csv");

        if (!ridersLoaded || !driversLoaded || !ridesLoaded) {
            System.out.println("Some CSV files could not be loaded. Please check the file paths.");
        }

        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        printList(system.getAllRiders());
                        break;

                    case 2:
                        printList(system.searchRidersByHomeCity(readText("Enter home city: ")));
                        break;

                    case 3:
                        printList(system.searchRidersByName(readText("Enter rider name: ")));
                        break;

                    case 4:
                        addRider(system);
                        break;

                    case 5:
                        printList(system.getAllDrivers());
                        break;

                    case 6:
                        printList(system.searchDriversByVehicleType(readVehicleType()));
                        break;

                    case 7:
                        addDriver(system);
                        break;

                    case 8:
                        printList(system.getAllRidesAlphabetically());
                        break;

                    case 9:
                        printList(system.searchRidesByPickupLocation(readText("Enter pickup location: ")));
                        break;

                    case 10:
                        printList(system.searchRidesByRiderName(readText("Enter rider name: ")));
                        break;

                    case 11:
                        printList(system.getSharedRideParticipants(readText("Enter pickup location: ")));
                        break;

                    case 12:
                        addRide(system);
                        break;

                    case 0:
                        System.out.println("Exiting Ride Sharing System...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please select an option from the menu.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }

        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("========== Ride Sharing System ==========");
        System.out.println("1. Display all riders");
        System.out.println("2. Search riders by home city");
        System.out.println("3. Search riders by name");
        System.out.println("4. Add a new rider");
        System.out.println("5. Display all drivers");
        System.out.println("6. Search drivers by vehicle type");
        System.out.println("7. Add a new driver");
        System.out.println("8. Display all rides alphabetically");
        System.out.println("9. Search rides by pickup location");
        System.out.println("10. Search rides by rider name");
        System.out.println("11. Display participants of a shared ride");
        System.out.println("12. Add a new ride");
        System.out.println("0. Exit");
        System.out.println("=========================================");
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readText(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter an integer.");
            }
        }
    }

    private static VehicleType readVehicleType() {
        while (true) {
            try {
                return VehicleType.valueOf(readText("Enter vehicle type (SEDAN, LUXURY_SEDAN, SUV, VAN): ").toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid vehicle type. Please try again.");
            }
        }
    }

    private static DateTime readDateTime(String prompt) {
        while (true) {
            try {
                return DateTime.parse(readText(prompt + " (MM/DD/YYYY HH:MM): "));
            } catch (RuntimeException e) {
                System.out.println("Invalid date or time. Please try again.");
            }
        }
    }

    private static <T> void printList(LinkedList<T> list) {
        if (list == null || list.empty()) {
            System.out.println("No matching results found.");
            return;
        }

        list.findFirst();
        while (true) {
            System.out.println(list.retrieve());
            if (list.last())
                break;
            list.findNext();
        }
    }

    private static void addRider(RideSharingSystem system) {
        int id = readInt("Enter rider ID: ");
        String name = readText("Enter full name: ");
        String email = readText("Enter email: ");
        String phone = readText("Enter phone number (10 digits): ");
        String city = readText("Enter home city: ");

        boolean added = system.addRider(new Rider(id, name, email, phone, city));

        if (added) {
            System.out.println("Rider added successfully.");
        } else {
            System.out.println("Could not add rider. Please check the entered information.");
        }
    }

    private static void addDriver(RideSharingSystem system) {
        int id = readInt("Enter driver ID: ");
        String name = readText("Enter full name: ");
        String phone = readText("Enter phone number (10 digits): ");
        String plate = readText("Enter vehicle plate (AAA1234): ");
        VehicleType type = readVehicleType();

        boolean added = system.addDriver(new Driver(id, name, phone, plate, type));

        if (added) {
            System.out.println("Driver added successfully.");
        } else {
            System.out.println("Could not add driver. Please check the entered information.");
        }
    }

    private static void addRide(RideSharingSystem system) {
        String type = readText("Enter ride type (PRIVATE or SHARED): ").toUpperCase();

        if (!type.equals("PRIVATE") && !type.equals("SHARED")) {
            System.out.println("Invalid ride type. Please enter PRIVATE or SHARED.");
            return;
        }

        String pickupLocation = readText("Enter pickup location: ");
        DateTime pickupTime = readDateTime("Enter pickup date/time");
        DateTime dropoffTime = readDateTime("Enter drop-off date/time");
        String dropoffLocation = readText("Enter drop-off location: ");
        int driverId = readInt("Enter driver ID: ");

        boolean scheduled;

        if (type.equals("PRIVATE")) {
            int riderId = readInt("Enter rider ID: ");

            scheduled = system.schedulePrivateRide(pickupLocation, pickupTime, dropoffTime,
                    dropoffLocation, riderId, driverId);

        } else {
            int count;

            do {
                count = readInt("Enter number of riders (at least 2): ");

                if (count < 2) {
                    System.out.println("A shared ride must have at least 2 riders.");
                }
            } while (count < 2);

            int[] riderIds = new int[count];

            for (int i = 0; i < count; i++) {
                riderIds[i] = readInt("Enter rider ID " + (i + 1) + ": ");
            }

            scheduled = system.scheduleSharedRide(pickupLocation, pickupTime, dropoffTime,
                    dropoffLocation, riderIds, driverId);
        }

        if (scheduled) {
            System.out.println("Ride added successfully.");
        } else {
            System.out.println("Could not add ride. Please check the entered information.");
        }
    }
}
