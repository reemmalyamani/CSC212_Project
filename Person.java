public abstract class Person implements IPerson {
  protected int id;
  protected String name;
  protected String phoneNumber;
  protected LinkedList<IRide> rideHistory;
  
public int getId(){
  return 0;
} //id getter
public String getName() {
  return "";
}// name getter
public void setName (String name) {
  this.name = name;
}// name setter
} // person class
