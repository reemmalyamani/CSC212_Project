public class Driver extends Person implements IDriver {
    private String vehiclePlate;
    private VehicleType vehicleType;
//constructor
    public Driver(int driverId, String name, String phoneNumber,
                  String vehiclePlate, VehicleType vehicleType) {
        super(driverId, name, phoneNumber);
        setVehiclePlate(vehiclePlate);
        setVehicleType(vehicleType);
    }
//setters and getters
    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        if (vehiclePlate == null || vehiclePlate.length() != 7)
            throw new IllegalArgumentException("the vehicle plate must have exactly 7 characters");
        for (int i = 0; i < 3; i++) {
            char c = vehiclePlate.charAt(i);
            if (c < 'A' || c > 'Z')
                throw new IllegalArgumentException("the first 3 characters must be uppercase letters");
        }
        for (int i = 3; i < 7; i++) {
            char c = vehiclePlate.charAt(i);
            if (c < '0' || c > '9')
                throw new IllegalArgumentException("the last 4 characters must be digits");
        }
        this.vehiclePlate = vehiclePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        if (vehicleType == null)
            throw new IllegalArgumentException("vehicle type cannot be null");
        this.vehicleType = vehicleType;
    }

    public int compareTo(IDriver other) {
        return getId() - other.getId();
    }

    public String toString() {
        return "Driver " + super.toString() + " | " + vehiclePlate + " | " + vehicleType;
    }
}
