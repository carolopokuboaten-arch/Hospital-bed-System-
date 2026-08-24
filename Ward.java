

public class Ward {
    private final Bed[][] beds;
    private static final int ROWS = 4;
    private static final int COLS = 5;

    public Ward() {
        beds = new Bed[ROWS][COLS];
        char rowLetter = 'B';
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                String bedID = rowLetter + String.format("%02d", c + 1);
                beds[r][c] = new Bed(bedID);
            }
            rowLetter++;
        }
    }

    public int getTotalBeds() { return ROWS * COLS; }

    public int getOccupiedCount() {
        int count = 0;
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                if (beds[r][c].isOccupied()) count++;
        return count;
    }

    public int getAvailableCount() { return getTotalBeds() - getOccupiedCount(); }

    public String allocateBed(String patientID) throws Exception {
        if (getAvailableCount() == 0)
            throw new Exception("No beds available — all beds occupied.");

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Bed bed = beds[r][c];
                if (!bed.isOccupied()) {
                    bed.allocate(patientID);
                    return bed.getBedID();
                }
            }
        }
        throw new Exception("No beds available.");
    }

    public boolean releaseBed(String bedID) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (beds[r][c].getBedID().equalsIgnoreCase(bedID)) {
                    return beds[r][c].release();
                }
            }
        }
        return false;
    }

    public String findBedByPatient(String patientID) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (patientID.equals(beds[r][c].getAllocatedPatientID())) {
                    return beds[r][c].getBedID();
                }
            }
        }
        return null;
    }

    public void displayLayout() {
        System.out.println("\n--- Ward Layout (4×5) ---");
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                String status = beds[r][c].isOccupied() ? "[X]" : "[ ]";
                System.out.print(beds[r][c].getBedID() + status + "  ");
            }
            System.out.println();
        }
    }

    public void displayAvailableBeds() {
        System.out.println("\n--- Available Beds ---");
        int count = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (!beds[r][c].isOccupied()) {
                    System.out.print(beds[r][c].getBedID() + "  ");
                    count++;
                }
            }
        }
        if (count == 0) System.out.println("None");
        System.out.println();
    }

    public void displayOccupiedBeds() {
        System.out.println("\n--- Occupied Beds ---");
        int count = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (beds[r][c].isOccupied()) {
                    System.out.print(beds[r][c].getBedID() + " → Patient: " + beds[r][c].getAllocatedPatientID() + "  ");
                    count++;
                }
            }
        }
        if (count == 0) System.out.println("None");
        System.out.println();
    }
}