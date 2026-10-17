public class Rider extends Person implements IRider {
    private String email;
    private String homeCity;
//constructor
    public Rider(int riderId, String name, String email, String phoneNumber,
                 String homeCity) {
        super(riderId, name, phoneNumber);
        this.email = email;
        this.homeCity = homeCity;
    }
//setters and getters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public int compareTo(IRider other) {
        return getId() - other.getId();
    }

    public String toString() {
        return "Rider " + super.toString() + " | " + email + " | " + homeCity;
    }
}
