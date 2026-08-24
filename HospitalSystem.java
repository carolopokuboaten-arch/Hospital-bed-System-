

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HospitalSystem {
    private final List<Patient> patients;
    private final Ward ward;

    public HospitalSystem() {
        patients = new ArrayList<>();
        ward = new Ward();
    }

    public boolean isDuplicatePatientID(String patientID) {
        return searchPatientByID(patientID) != null;
    }

    public void registerPatient(Patient patient) throws IllegalArgumentException {
        if (isDuplicatePatientID(patient.getPatientID())) {
            throw new IllegalArgumentException("Duplicate Patient ID: " + patient.getPatientID());
        }
        patients.add(patient);
    }

    public Patient searchPatientByID(String patientID) {
        for (Patient p : patients) {
            if (p.getPatientID().equalsIgnoreCase(patientID)) {
                return p;
            }
        }
        return null;
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    public boolean updatePatient(String patientID, String newFirstName, String newLastName,
                                 int newAge, String newGender, String newCondition) throws Exception {
        Patient p = searchPatientByID(patientID);
        if (p == null) throw new Exception("Patient not found.");

        if (!newFirstName.isBlank()) p.setFirstName(newFirstName);
        if (!newLastName.isBlank()) p.setLastName(newLastName);
        if (newAge > 0) p.setAge(newAge);
        if (!newGender.isBlank()) p.setGender(newGender);
        if (!newCondition.isBlank()) p.setMedicalCondition(newCondition);
        return true;
    }

    public boolean deletePatient(String patientID) throws Exception {
        Patient p = searchPatientByID(patientID);
        if (p == null) throw new Exception("Patient not found.");

        if (p instanceof Inpatient) {
            String bedID = ward.findBedByPatient(patientID);
            if (bedID != null) ward.releaseBed(bedID);
        }
        return patients.remove(p);
    }

    public String allocateBed(String patientID) throws Exception {
        Patient p = searchPatientByID(patientID);
        if (p == null) throw new Exception("Patient not found.");
        if (!(p instanceof Inpatient)) throw new Exception("Only Inpatients require beds.");

        Inpatient ip = (Inpatient) p;
        if (ip.getBedNumber() != null) throw new Exception("Patient already has a bed allocated.");

        String bedID = ward.allocateBed(patientID);
        ip.setBedNumber(bedID);
        ip.setWardNumber("Ward 1");
        return bedID;
    }

    public void releaseBed(String bedID) throws Exception {
        String patientID = ward.findBedByPatient(bedID);
        if (patientID == null) throw new Exception("Bed not found or not occupied.");

        if (!ward.releaseBed(bedID)) throw new Exception("Failed to release bed.");

        Patient p = searchPatientByID(patientID);
        if (p instanceof Inpatient ip) {
            ip.setBedNumber(null);
            ip.setWardNumber(null);
        }
    }

    public Patient[] getSortedByLastName() {
    List<Patient> sorted = new ArrayList<>(patients);
    Collections.sort(sorted, (Patient p1, Patient p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName()));
    return sorted.toArray(Patient[]::new);
}

public Patient[] getSortedByID() {
    List<Patient> sorted = new ArrayList<>(patients);
    Collections.sort(sorted, (Patient p1, Patient p2) -> p1.getPatientID().compareToIgnoreCase(p2.getPatientID()));
    return sorted.toArray(Patient[]::new);
}

    public Ward getWard() { return ward; }
}