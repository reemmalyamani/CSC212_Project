public abstract class Person implements IPerson {
    private final int id;
    private String name;
    private String phoneNumber;
    private LinkedList<IRide> rideHistory;

    public Person(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        setPhoneNumber(phoneNumber);
        rideHistory = new LinkedList<IRide>();
    } //constructor

  //setters and getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
// edge case of phone number
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 10)
            throw new IllegalArgumentException("the phone must have exactly 10 digits");
        for (int i = 0; i < 10; i++) {
            char c = phoneNumber.charAt(i);
            if (c < '0' || c > '9')
                throw new IllegalArgumentException("the phone must have digits only");
        }
        this.phoneNumber = phoneNumber;
    }

    public LinkedList<IRide> getRideHistory() {
        return rideHistory;
    }

    public String toString() {
        return id + " | " + name + " | " + phoneNumber;
    }
}
