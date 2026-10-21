import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RideSharingSystem implements IRideSharingSystem {
    private RiderList riders;
    private DriverList drivers;
    private RideList rides;
//reem:
  public RideSharingSystem() {
        riders = new RiderList();
        drivers = new DriverList();
        rides = new RideList();
    }
    
    public boolean loadRidersFromCSV(String ridersFilePath) {
        try {
            Scanner file = new Scanner(new File(ridersFilePath));
            while (file.hasNextLine()) {
                String[] f = file.nextLine().split(",");
                try {
                    addRider(new Rider(Integer.parseInt(f[0]), f[1], f[2], f[3], f[4]));
                } catch (Exception e) {
                }
            }
            file.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public boolean addRider(IRider rider) {
        if (rider == null)
            return false;
        if (riders.findByEmail(rider.getEmail()) != null)
            return false;
        return riders.add(rider);
    }

    public IRider searchRiderById(int riderId) {
        return riders.findById(riderId);
    }

    public IRider searchRiderByEmail(String email) {
        return riders.findByEmail(email);
    }

    public LinkedList<IRider> searchRidersByName(String fullName) {
        return riders.findByName(fullName);
    }

    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        return riders.findByHomeCity(homeCity);
    }

    public LinkedList<IRider> getAllRiders() {
        return riders.getAll();
    }

    // removeRider() is incomplete and still needs to handle shared rides.
    public boolean removeRider(int riderId) {
        IRider rider = riders.findById(riderId);
        if (rider == null)
            return false;

        LinkedList<IRide> h = rider.getRideHistory();
        while (!h.empty()) {
            h.findFirst();
            IRide ride = h.retrieve();

            if (ride instanceof PrivateRide) {
                deleteRide(ride);
            } else if (ride instanceof SharedRide) {
                break;
            }
        }

        return riders.removeById(riderId);
    }
    
//Alanoud:
  public boolean loadDriversFromCSV(String driversFilePath) {
        try {
            Scanner file = new Scanner(new File(driversFilePath));
            while (file.hasNextLine()) {
                String[] f = file.nextLine().split(",");
                try {
                    addDriver(new Driver(Integer.parseInt(f[0].trim()),
                            f[1].trim(), f[2].trim(), f[3].trim(),
                            VehicleType.valueOf(f[4].trim())));
                } catch (Exception e) {
                }
            }
            file.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public boolean addDriver(IDriver driver) {
        if (driver == null)
            return false;
        if (drivers.findByVehiclePlate(driver.getVehiclePlate()) != null)
            return false;
        return drivers.add(driver);
    }

    public IDriver searchDriverById(int driverId) {
        return drivers.findById(driverId);
    }

    public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
        return drivers.findByVehiclePlate(vehiclePlate);
    }

    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        return drivers.findByVehicleType(vehicleType);
    }

    public LinkedList<IDriver> getAllDrivers() {
        return drivers.getAll();
    }

    public boolean removeDriver(int driverId) {
        IDriver driver = drivers.findById(driverId);
        if (driver == null)
            return false;
        LinkedList<IRide> h = driver.getRideHistory();
        while (!h.empty()) {
            h.findFirst();
            deleteRide(h.retrieve());
        }
        return drivers.removeById(driverId);
    }
//Aljohara:

}
