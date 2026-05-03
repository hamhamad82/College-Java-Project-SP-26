// FileManager.java
import java.io.*;
import java.util.ArrayList;

public class FileManager {
    
    public static void createDataDirectory() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }
    
    // Doctors
    public static void saveDoctors(ArrayList<Doctor> doctors) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("data/doctors.txt"))) {
            for (Doctor d : doctors) {
                pw.printf("D%03d,%s,%s,%s,%s,%s,%s,%b%n", 
                    d.getId(), d.getName(), d.getUsername(), d.getPassword(), 
                    d.getSpecialization(), d.getDepartment(), d.getPhone(), d.isAvailable());
            }
        } catch (IOException e) {
            System.out.println("Error saving doctors: " + e.getMessage());
        }
    }
    
    public static ArrayList<Doctor> loadDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/doctors.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    int id = Integer.parseInt(parts[0].substring(1));
                    boolean available = parts[7].equals("true");
                    Doctor d = new Doctor(id, parts[1], parts[2], parts[3], 
                                          parts[4], parts[5], parts[6], available);
                    doctors.add(d);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing doctors file.");
        } catch (IOException e) {
            System.out.println("Error loading doctors.");
        }
        return doctors;
    }
    
    // Patients
    public static void savePatients(ArrayList<Patient> patients) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("data/patients.txt"))) {
            for (Patient p : patients) {
                pw.printf("P%03d,%s,%s,%s,%d,%s,%s,%s,D%03d%n", 
                    p.getId(), p.getName(), p.getUsername(), p.getPassword(), 
                    p.getAge(), p.getGender(), p.getPhone(), p.getMedicalHistory(), 
                    p.getAssignedDoctorId());
            }
        } catch (IOException e) {
            System.out.println("Error saving patients.");
        }
    }
    
    public static ArrayList<Patient> loadPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/patients.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 9) {
                    int id = Integer.parseInt(parts[0].substring(1));
                    int assignedDoc = Integer.parseInt(parts[8].substring(1));
                    Patient p = new Patient(id, parts[1], parts[2], parts[3], 
                                            Integer.parseInt(parts[4]), parts[5], 
                                            parts[6], parts[7]);
                    p.setAssignedDoctorId(assignedDoc);
                    patients.add(p);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing patients file.");
        } catch (IOException e) {
            System.out.println("Error loading patients.");
        }
        return patients;
    }
    
    // Appointments
    public static void saveAppointments(ArrayList<Appointment> appointments) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("data/appointments.txt"))) {
            for (Appointment a : appointments) {
                pw.printf("%s,P%03d,D%03d,%s,%s,%s%n", 
                    a.getId(), a.getPatientId(), a.getDoctorId(), 
                    a.getDate(), a.getTime(), a.getStatus());
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments.");
        }
    }
    
    public static ArrayList<Appointment> loadAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/appointments.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    int patientId = Integer.parseInt(parts[1].substring(1));
                    int doctorId = Integer.parseInt(parts[2].substring(1));
                    Appointment a = new Appointment(parts[0], patientId, doctorId, 
                                                    parts[3], parts[4], parts[5]);
                    appointments.add(a);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing appointments file.");
        } catch (IOException e) {
            System.out.println("Error loading appointments.");
        }
        return appointments;
    }
}