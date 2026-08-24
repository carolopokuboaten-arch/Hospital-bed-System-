

public class Patient {
    private final String patientID;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private final PatientCategory category;

    public Patient(String patientID, String firstName, String lastName, int age,
                   String gender, String medicalCondition, PatientCategory category) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    public String getPatientID() { return patientID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getMedicalCondition() { return medicalCondition; }
    public PatientCategory getCategory() { return category; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setMedicalCondition(String medicalCondition) { this.medicalCondition = medicalCondition; }

    public void displayDetails() {
        System.out.println("ID: " + patientID + " | Name: " + firstName + " " + lastName +
                " | Age: " + age + " | Gender: " + gender +
                " | Condition: " + medicalCondition + " | Category: " + category);
    }
}