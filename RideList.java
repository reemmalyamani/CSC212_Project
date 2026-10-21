public class RideList implements IRideList {
   private LinkedList<IRide> all_rides;
   public RideList() {
      all_rides = new LinkedList<>(); } 

   @Override
   public LinkedList<IRide> getAllAlphabetically() {
      return all_rides; }


   @Override 
   public int size() {
      return all_rides.size(); }

   @Override 
   public boolean addRide(IRide ride) {
      if (ride == null) {
         return false; }
      if (all_rides.empty()) {
         all_rides.insert(ride);
         return true; }
         
      all_rides.findFirst();
      while(true) {
         IRide current = all_rides.retrieve();
         if (ride.compareTo(current)<0) {
            all_rides.insert_before_current(ride);
            return true; }
         if (all_rides.last()) {
            break; }
         all_rides.findNext();}
      all_rides.insert(ride);
      return true; } 
 
   @Override 
   
   public boolean removeRideById(int rideId){
      if (all_rides.empty()) {
         return false;
      }
      all_rides.findFirst();
      while(true) {
         IRide current = all_rides.retrieve();
         if(current.getRideId() == rideId) {
            all_rides.remove();
            return true; }
         if(!all_rides.last()) {
            all_rides.findNext(); }
         else
            return false; }} 


   @Override
   public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
      LinkedList<IRide> result = new LinkedList<>();
      if (pickupLocation ==null  || all_rides.empty()) {
         return result; }
      all_rides.findFirst();
      while(true) {
         IRide current = all_rides.retrieve();
         if(current.getPickupLocation().equalsIgnoreCase(pickupLocation)) {
            result.insert(current); }
         if (all_rides.last()) {
            break; }
         all_rides.findNext(); }                                                  
      return result; }

   private boolean isRiderInPrivateRide(IPrivateRide ride, String riderFullName) {
      return ride!=null && ride.getRider() != null&& riderFullName!=null && riderFullName.equals(ride.getRider().getName()) ; }


   private boolean isRiderInSharedRide (ISharedRide ride , String riderFullName) { 
      if(ride== null) {
         return false; }
      LinkedList<IRider> participants = ride.getParticipants();
      if(participants == null || participants.empty()) {
         return false ; }
      participants.findFirst();
      while(true) {
         IRider rider = participants.retrieve();
         if(rider != null && riderFullName !=null && riderFullName.equals(rider.getName())) {
            return true; } 
         if(participants.last()) {
            break; }
         participants.findNext(); }
      return false ; }



   @Override
   public LinkedList<IRide> findByRiderName(String riderFullName) {
      LinkedList<IRide>  result = new LinkedList<>(); 
    
      if (riderFullName == null || all_rides.empty()) {
         return result; }
    
      all_rides.findFirst(); 
      while(true) {
         IRide current = all_rides.retrieve();
         boolean found = false ;
      
         if (current instanceof IPrivateRide) {
            found = isRiderInPrivateRide((IPrivateRide)current , riderFullName); }
         
         
         else if (current instanceof ISharedRide) {
            found = isRiderInSharedRide((ISharedRide)current , riderFullName); }
      
         if (found) {
            result.insert(current); }
      
         if (all_rides.last()) {
            break; }
      
         all_rides.findNext() ; }
      return result;
   }
 
}


