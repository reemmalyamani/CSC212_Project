public class DriverList implements IDriverList {
    private Node<IDriver> head;
    private int size;

    public DriverList() {
        head = null;
        size = 0;
    }

    public boolean add(IDriver driver) {
        if (driver == null || findById(driver.getId()) != null
                || findByVehiclePlate(driver.getVehiclePlate()) != null)
            return false;

        if (head == null || driver.compareTo(head.data) < 0) {
            Node<IDriver> tmp = new Node<IDriver>(driver);
            tmp.next = head;
            head = tmp;
            size++;
            return true;
        }

        Node<IDriver> p = head;
        while (p.next != null && p.next.data.compareTo(driver) < 0)
            p = p.next;

        Node<IDriver> tmp = new Node<IDriver>(driver);
        tmp.next = p.next;
        p.next = tmp;
        size++;
        return true;
    }

    public IDriver findById(int driverId) {
        Node<IDriver> p = head;
        while (p != null) {
            if (p.data.getId() == driverId)
                return p.data;
            p = p.next;
        }
        return null;
    }

    public LinkedList<IDriver> findByName(String fullName) {
        LinkedList<IDriver> result = new LinkedList<IDriver>();
        if (fullName == null)
            return result;

        Node<IDriver> p = head;
        while (p != null) {
            if (p.data.getName().equals(fullName))
                result.insert(p.data);
            p = p.next;
        }
        return result;
    }

    public IDriver findByVehiclePlate(String vehiclePlate) {
        if (vehiclePlate == null)
            return null;

        Node<IDriver> p = head;
        while (p != null) {
            if (p.data.getVehiclePlate().equalsIgnoreCase(vehiclePlate))
                return p.data;
            p = p.next;
        }
        return null;
    }

    public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType) {
        LinkedList<IDriver> result = new LinkedList<IDriver>();
        if (vehicleType == null)
            return result;

        Node<IDriver> p = head;
        while (p != null) {
            if (p.data.getVehicleType() == vehicleType)
                result.insert(p.data);
            p = p.next;
        }
        return result;
    }

    public LinkedList<IDriver> getAll() {
        LinkedList<IDriver> result = new LinkedList<IDriver>();
        Node<IDriver> p = head;
        while (p != null) {
            result.insert(p.data);
            p = p.next;
        }
        return result;
    }

    public boolean removeById(int driverId) {
        if (head == null)
            return false;

        if (head.data.getId() == driverId) {
            head = head.next;
            size--;
            return true;
        }

        Node<IDriver> p = head;
        while (p.next != null) {
            if (p.next.data.getId() == driverId) {
                p.next = p.next.next;
                size--;
                return true;
            }
            p = p.next;
        }
        return false;
    }

    public boolean removeByVehiclePlate(String vehiclePlate) {
        if (vehiclePlate == null || head == null)
            return false;

        if (head.data.getVehiclePlate().equalsIgnoreCase(vehiclePlate)) {
            head = head.next;
            size--;
            return true;
        }

        Node<IDriver> p = head;
        while (p.next != null) {
            if (p.next.data.getVehiclePlate().equalsIgnoreCase(vehiclePlate)) {
                p.next = p.next.next;
                size--;
                return true;
            }
            p = p.next;
        }
        return false;
    }

    public int removeByName(String fullName) {
        if (fullName == null)
            return 0;

        int count = 0;
        while (head != null && head.data.getName().equals(fullName)) {
            head = head.next;
            count++;
        }

        Node<IDriver> p = head;
        while (p != null && p.next != null) {
            if (p.next.data.getName().equals(fullName)) {
                p.next = p.next.next;
                count++;
            } else {
                p = p.next;
            }
        }
        size -= count;
        return count;
    }

    public int removeByVehicleType(VehicleType vehicleType) {
        if (vehicleType == null)
            return 0;

        int count = 0;
        while (head != null && head.data.getVehicleType() == vehicleType) {
            head = head.next;
            count++;
        }

        Node<IDriver> p = head;
        while (p != null && p.next != null) {
            if (p.next.data.getVehicleType() == vehicleType) {
                p.next = p.next.next;
                count++;
            } else {
                p = p.next;
            }
        }
        size -= count;
        return count;
    }

    public int size() {
        return size;
    }
}
