public abstract class Ride implements IRide {

private static int rideIdCounter = 1;
protected final int rideId;
protected String pickupLocation;
protected final IDateTime pickupTime ;
protected final IDateTime dropoffTime;
protected String dropoffLocation ;
protected IDriver driver;

public Ride (String pickupLocation,IDateTime pickupTime , IDateTime dropoffTime,String dropoffLocation ,IDriver driver) {
this.rideId = rideIdCounter++;
this.pickupLocation = pickupLocation;
this.pickupTime = pickupTime ;
this.dropoffTime = dropoffTime ;
this.dropoffLocation = dropoffLocation;
this.driver = driver; }

@Override
public int getRideId() {
return rideId; }

@Override
public String getPickupLocation()  {
return pickupLocation ; }

@Override
public void setPickupLocation(String pickupLocation) {
this.pickupLocation = pickupLocation; }

@Override
public IDateTime getPickupTime() {
return pickupTime; }

@Override
public IDateTime getDropoffTime() { 
return dropoffTime; }

@Override
public String getDropoffLocation() {
return dropoffLocation ; }

@Override
public void setDropoffLocation(String dropoffLocation) {
this.dropoffLocation=dropoffLocation; }


@Override
public IDriver getDriver() {
return driver; } 


@Override
public void setDriver(IDriver driver) {
this.driver=driver; }

@Override 
public abstract boolean hasRider(int riderId);


@Override
public int compareTo(IRide other) {
return this.pickupLocation.compareToIgnoreCase(other.getPickupLocation()) ; }


@Override
public String toString()  {
return "RideID :" + rideId + ", pickupLocation :" + pickupLocation + ", pickupTime : " + pickupTime.format() + ", Drop-off Location: " + dropoffLocation + ", Drop-off Time : " + dropoffTime.format() + ", Driver ID: " + (driver ==null ? "None" : driver.getId()); }


}