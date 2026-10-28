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
//  helper to delete NOT!! part of IRideSharingSystem
private void deleteRide(IRide ride) {
    rides.removeRideById(ride.getRideId());

    if (ride.getDriver() != null)
        removeFromHistory(ride.getDriver().getRideHistory(), ride);

    if (ride instanceof IPrivateRide) {
        removeFromHistory(((IPrivateRide) ride).getRider().getRideHistory(), ride);
    } else if (ride instanceof ISharedRide) {
        LinkedList<IRider> ps = ((ISharedRide) ride).getParticipants();
        if (!ps.empty()) {
            ps.findFirst();
            while (true) {
                removeFromHistory(ps.retrieve().getRideHistory(), ride);
                if (ps.last()) break;
                ps.findNext();
            }
        }
    }
}

private void removeFromHistory(LinkedList<IRide> h, IRide ride) {
    if (h.empty()) return;
    h.findFirst();
    while (true) {
        if (h.retrieve() == ride) { h.remove(); return; }
        if (h.last()) return;
        h.findNext();
    }
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
                SharedRide sharedRide = (SharedRide) ride;
                sharedRide.removeParticipantById(riderId);
                removeFromHistory(h, ride);
                if (sharedRide.isEmpty()) {
                    deleteRide(ride);
                }
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
@Override
   public LinkedList<IRide> getAllRidesAlphabetically(){
      return this.rides.getAllAlphabetically(); }
 
   @Override
   public LinkedList<IRide> searchRidesByRiderName(String riderName) { 
      return this.rides.findByRiderName(riderName);}
 
 
   @Override
   public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
      return this.rides.findByPickupLocation(pickupLocation); }

   private boolean isOverlap(IDateTime start1, IDateTime end1, IDateTime start2,IDateTime end2) {
      return start1.compareTo(end2) < 0 && start2.compareTo(end1) < 0; }

   private boolean hasConflict (LinkedList <IRide> rideHistory, IDateTime pickupTime,IDateTime dropoffTime){
      if(rideHistory.empty()) {
         return false; }
      rideHistory.findFirst();
      while(true) {
         IRide ride =rideHistory.retrieve();
         if(isOverlap(pickupTime,dropoffTime,ride.getPickupTime(),ride.getDropoffTime())) {
            return true; }
         if(!rideHistory.last())
            rideHistory.findNext(); 
         else
            break; }
      return false; } 

   @Override
   public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId){
      IDriver driver = this.drivers.findById(driverId);
      IRider rider = this.riders.findById(riderId);
      if(rider==null || driver==null) {
         return false; }
      if(pickupTime == null || dropoffTime == null || pickupLocation == null || dropoffLocation == null) {
         return false; }
      if(pickupTime.compareTo(dropoffTime) >= 0) {
         return false; }
      if (hasConflict(driver.getRideHistory(),pickupTime,dropoffTime)) {
         return false;}
      if (hasConflict(rider.getRideHistory(),pickupTime,dropoffTime)) {
         return false;}
   
      PrivateRide ride = new PrivateRide(pickupLocation,pickupTime,dropoffTime,dropoffLocation,driver,rider);
      if(!this.rides.addRide(ride)) {
         return false; }
      rider.getRideHistory().insert(ride);   
      driver.getRideHistory().insert(ride);   
      return true;}
  
   @Override 
   public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId){
      IDriver driver = this.drivers.findById(driverId);
      if(driver == null || riderIds == null || riderIds.length == 0 ) {
         return false;}
      if(pickupTime == null || dropoffTime == null ||pickupLocation == null || dropoffLocation == null) {
         return false;}
      
      if(pickupTime.compareTo(dropoffTime) >= 0) {
         return false; }
   
      IRider [] riders = new IRider[riderIds.length];
      for(int i =0; i<riderIds.length; i++) {
         riders[i]= this.riders.findById(riderIds[i]);
         if (riders [i] == null){
            return false;}
           
         for(int j = 0; j < i; j++) {
            if(riderIds[i] == riderIds[j]) {
               return false;}}}
            
      if (hasConflict(driver.getRideHistory(),pickupTime,dropoffTime)){
         return false;}
   
      for(int i=0; i<riders.length; i++) {
         if (hasConflict(riders[i].getRideHistory(),pickupTime,dropoffTime)){
            return false; }} 
   
      SharedRide ride = new SharedRide(pickupLocation,pickupTime,dropoffTime,dropoffLocation,driver);
      
      for(int i = 0; i < riders.length; i++) {
         if(!ride.addParticipant(riders[i])) {
            return false; }}
      if(!this.rides.addRide(ride)) {
         return false;} 
      
            
      driver.getRideHistory().insert(ride);
      for(int i = 0; i < riders.length; i++) {
         riders[i].getRideHistory().insert(ride);}
   
      return true;}      
   @Override
   public boolean loadRidesFromCSV(String ridesFilePath){
      try {
         Scanner read = new Scanner(new File(ridesFilePath));
         while(read.hasNextLine()) {
            String[] d = read.nextLine().trim().split(",");
            
            if(d[0].equalsIgnoreCase("rideType")) {
               continue;}
            
            
            
            String rideType = d[0];
            String pickupLocation = d[1];
            DateTime pickupTime = DateTime.parse(d[2]);
            DateTime dropoffTime = DateTime.parse(d[3]);
            String dropoffLocation = d[4];
            int driverId = Integer.parseInt(d[5]);
            if(rideType.equalsIgnoreCase("PRIVATE")) {
               int riderId = Integer.parseInt(d[6]);
              
              
               if(!schedulePrivateRide(pickupLocation,pickupTime,dropoffTime,dropoffLocation,riderId, driverId)) {
                  read.close();
                  return false;}}
            
            else if (rideType.equalsIgnoreCase("SHARED")) {
               String[] riderStrings = d[6].split(";");
               int[] riderIds = new int [riderStrings.length];
            
               for (int i=0; i <  riderStrings.length; i++) {
                  riderIds[i] = Integer.parseInt(riderStrings[i].trim()); }
            
               if(! scheduleSharedRide(pickupLocation,pickupTime,dropoffTime,dropoffLocation,riderIds, driverId)){
                  read.close();
                  return false;}}
              
            else {
               read.close();
               return false;}}
         read.close();
         return true;}
      catch(Exception e) {
         return false;}}
   
   
                      
               
   @Override
   public LinkedList<IRider> getSharedRideParticipants(String pickupLocation){
      LinkedList<IRide> rides = this.rides.findByPickupLocation(pickupLocation);
      if (rides.empty()) {
         return new LinkedList<IRider>(); }
      rides.findFirst();
      while (true) {
         IRide r = rides.retrieve();
         if (r instanceof ISharedRide) {
            return ((ISharedRide) r).getParticipants();}
         if (!rides.last())
            rides.findNext();
         else
            return new LinkedList<IRider>();}} 
            
            
}//Class end
