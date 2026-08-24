

public class Inpatient extends Patient {
    private String wardNumber;
    private String bedNumber;

    public Inpatient(String patientID, String firstName, String lastName, int age,
                     String gender, String medicalCondition, PatientCategory category) {
        super(patientID, firstName, lastName, age, gender, medicalCondition, category);
        this.wardNumber = null;
        this.bedNumber = null;
    }

    public String getWardNumber() { return wardNumber; }
    public void setWardNumber(String wardNumber) { this.wardNumber = wardNumber; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }

    @Override
    public void displayDetails() {
        super.displayDetails();
        if (bedNumber != null) {
            System.out.println("   -> Ward: " + wardNumber + " | Bed: " + bedNumber);
        } else {
            System.out.println("   -> No bed allocated yet");
        }
    }
}
