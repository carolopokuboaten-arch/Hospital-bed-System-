

import java.util.List;
import java.util.Scanner;

public class Main {
    private static HospitalSystem system = new HospitalSystem();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   MEDICARE HOSPITAL PATIENT SYSTEM");
        System.out.println("=========================================");

        while (true) {
            printMenu();
            try {
                System.out.print("\nEnter option: ");
                String input = sc.nextLine().trim();
                if (input.isBlank()) continue;
                int option = Integer.parseInt(input);

                switch (option) {
                    case 0:
                        System.out.println("Exiting... Goodbye!");
                        sc.close();
                        return;
                    case 1:
                        registerPatient();
                        break;
                    case 2:
                        searchPatient();
                        break;
                    case 3:
                        updatePatient();
                        break;
                    case 4:
                        deletePatient();
                        break;
                    case 5:
                        displayAllPatients();
                        break;
                    case 6:
                        allocateBed();
                        break;
                    case 7:
                        releaseBed();
                        break;
                    case 8:
                        displayWardLayout();
                        break;
                    case 9:
                        showReports();
                        break;
                    default:
                        System.out.println("Invalid option — try 0-9");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Register Patient");
        System.out.println("2. Search Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");
        System.out.println("5. Display All Patients");
        System.out.println("6. Allocate Bed to Inpatient");
        System.out.println("7. Release Bed");
        System.out.println("8. View Ward Layout");
        System.out.println("9. Reports");
        System.out.println("0. Exit");
    }

    private static void registerPatient() {
        System.out.println("\n--- Register New Patient ---");
        System.out.print("Patient ID: ");
        String id = sc.nextLine().trim();

        if (system.isDuplicatePatientID(id)) {
            System.out.println("ERROR: Patient ID " + id + " already exists!");
            return;
        }

        System.out.print("First Name: ");
        String fn = sc.nextLine().trim();
        System.out.print("Last Name: ");
        String ln = sc.nextLine().trim();
        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Gender: ");
        String gender = sc.nextLine().trim();
        System.out.print("Medical Condition: ");
        String cond = sc.nextLine().trim();

        System.out.print("Category (1=Inpatient, 2=Outpatient, 3=Emergency): ");
        int catChoice = Integer.parseInt(sc.nextLine().trim());
        PatientCategory cat;
        switch (catChoice) {
            case 1 -> cat = PatientCategory.INPATIENT;
            case 2 -> cat = PatientCategory.OUTPATIENT;
            case 3 -> cat = PatientCategory.EMERGENCY;
            default -> throw new IllegalArgumentException("Invalid category");
        }

        Patient p;
        if (cat == PatientCategory.INPATIENT) {
            p = new Inpatient(id, fn, ln, age, gender, cond, cat);
        } else {
            p = new Patient(id, fn, ln, age, gender, cond, cat);
        }

        system.registerPatient(p);
        System.out.println("✅ Patient registered successfully!");
    }

    private static void searchPatient() {
        System.out.println("\n--- Search Patient ---");
        System.out.print("Enter Patient ID: ");
        String id = sc.nextLine().trim();
        Patient p = system.searchPatientByID(id);
        if (p == null) System.out.println("❌ Patient not found.");
        else { System.out.println("✅ Patient found:"); p.displayDetails(); }
    }

    private static void updatePatient() {
        System.out.println("\n--- Update Patient ---");
        System.out.print("Enter Patient ID to update: ");
        String id = sc.nextLine().trim();
        Patient existing = system.searchPatientByID(id);
        if (existing == null) { System.out.println("❌ Patient not found."); return; }

        System.out.println("Leave blank to keep current value.");
        System.out.print("First Name [" + existing.getFirstName() + "]: ");
        String fn = sc.nextLine().trim();
        if (fn.isBlank()) fn = existing.getFirstName();

        System.out.print("Last Name [" + existing.getLastName() + "]: ");
        String ln = sc.nextLine().trim();
        if (ln.isBlank()) ln = existing.getLastName();

        System.out.print("Age [" + existing.getAge() + "]: ");
        String ageStr = sc.nextLine().trim();
        int age = ageStr.isBlank() ? existing.getAge() : Integer.parseInt(ageStr);

        System.out.print("Gender [" + existing.getGender() + "]: ");
        String gen = sc.nextLine().trim();
        if (gen.isBlank()) gen = existing.getGender();

        System.out.print("Medical Condition [" + existing.getMedicalCondition() + "]: ");
        String cond = sc.nextLine().trim();
        if (cond.isBlank()) cond = existing.getMedicalCondition();

        try {
            system.updatePatient(id, fn, ln, age, gen, cond);
        } catch (Exception ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        System.out.println("✅ Patient updated successfully!");
    }

    private static void deletePatient() {
        System.out.println("\n--- Delete Patient ---");
        System.out.print("Enter Patient ID to delete: ");
        String id = sc.nextLine().trim();
        try {
            system.deletePatient(id);
        } catch (Exception ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        System.out.println("✅ Patient deleted successfully!");
    }

    private static void displayAllPatients() {
        System.out.println("\n--- All Registered Patients ---");
        List<Patient> all = system.getAllPatients();
        if (all.isEmpty()) { System.out.println("No patients registered."); return; }

        System.out.print("Sort by: (1) Last Name  (2) Patient ID: ");
        String choice = sc.nextLine().trim();
        Patient[] sorted = choice.equals("2") ? system.getSortedByID() : system.getSortedByLastName();

        for (Patient p : sorted) p.displayDetails();
    }

    private static void allocateBed() {
        System.out.println("\n--- Allocate Bed ---");
        System.out.print("Enter Inpatient ID: ");
        String id = sc.nextLine().trim();
        String bedID = null;
        try {
            bedID = system.allocateBed(id);
        } catch (Exception ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        System.out.println("✅ Bed " + bedID + " allocated to patient " + id);
    }

    private static void releaseBed() {
        System.out.println("\n--- Release Bed ---");
        System.out.print("Enter Bed ID (e.g., B01): ");
        String bedID = sc.nextLine().trim();
        try {
            system.releaseBed(bedID);
        } catch (Exception ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        System.out.println("✅ Bed " + bedID + " released successfully!");
    }

    private static void displayWardLayout() {
        system.getWard().displayLayout();
    }

    private static void showReports() {
        System.out.println("\n===== REPORTS =====");
        List<Patient> all = system.getAllPatients();
        Ward ward = system.getWard();

        System.out.println("Total Registered Patients: " + all.size());
        System.out.println("Total Beds in Ward: " + ward.getTotalBeds());
        System.out.println("Occupied Beds: " + ward.getOccupiedCount());
        System.out.println("Available Beds: " + ward.getAvailableCount());

        double pct = ((double) ward.getOccupiedCount() / ward.getTotalBeds()) * 100;
        System.out.printf("Ward Occupancy: %.2f%%%n", pct);

        ward.displayAvailableBeds();
        ward.displayOccupiedBeds();
    }
}