public class SharedRide extends Ride implements  ISharedRide {
   private LinkedList<IRider> participants;

   public SharedRide(String pickupLocation,IDateTime pickupTime , IDateTime dropoffTime,String dropoffLocation ,IDriver driver) {
      super (pickupLocation,pickupTime,dropoffTime,dropoffLocation,driver);
      participants = new LinkedList<IRider>() ; } 

   @Override
   public  LinkedList<IRider> getParticipants(){ 
      return participants ; }

   @Override
   public boolean addParticipant(IRider rider) {
      if (rider == null)
         return false;
      if (hasRider(rider.getId()))
         return false;
      participants.insert(rider);
      return true; }

   @Override
   public boolean removeParticipantById(int riderId) {
      if (participants.empty())
         return false;
      participants.findFirst();
      while(true) {
         if (participants.retrieve().getId()== riderId) {
            participants.remove();
            return true ; }
         if(!participants.last())
            participants.findNext();
         else
            return false; }}

   @Override
   public boolean isEmpty() {
      return participants.empty() ; }

   @Override
   public boolean hasRider(int riderId) {
      if (participants.empty())
         return false;
      participants.findFirst(); 
      while(true) {
         if (participants.retrieve().getId() == riderId)
            return true;
      
         if (!participants.last())
            participants.findNext();
         else
            return false; }}

   @Override
   public String toString() {
      return "Shared Ride :" + super.toString() + ", Participants Count: " + participants.size();   }











}


