

public class Bed {
    private final String bedID;
    private boolean isOccupied;
    private String allocatedPatientID;

    public Bed(String bedID) {
        this.bedID = bedID;
        this.isOccupied = false;
        this.allocatedPatientID = null;
    }

    public String getBedID() { return bedID; }
    public boolean isOccupied() { return isOccupied; }
    public String getAllocatedPatientID() { return allocatedPatientID; }

    public boolean allocate(String patientID) {
        if (isOccupied) return false;
        this.isOccupied = true;
        this.allocatedPatientID = patientID;
        return true;
    }

    public boolean release() {
        if (!isOccupied) return false;
        this.isOccupied = false;
        this.allocatedPatientID = null;
        return true;
    }
}
