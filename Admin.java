// Admin.java
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User {
    
    // References to system data
    private transient ArrayList<Doctor> allDoctors;
    private transient ArrayList<Patient> allPatients;
    private transient ArrayList<Appointment> allAppointments;
    private transient Scanner scanner;
    
    public Admin(int id, String name, String username, String password) {
        super(id, name, username, password);
    }
    
    // Set references
    public void setDataRefs(ArrayList<Doctor> doctors, ArrayList<Patient> patients,
                            ArrayList<Appointment> appointments, Scanner sc) {
        this.allDoctors = doctors;
        this.allPatients = patients;
        this.allAppointments = appointments;
        this.scanner = sc;
    }
    
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid! Enter number: ");
            scanner.next();
        }
        int num = scanner.nextInt();
        scanner.nextLine();
        return num;
    }
    
    private Doctor findDoctorById(int id) {
        for (Doctor d : allDoctors) {
            if (d.getId() == id) return d;
        }
        return null;
    }
    
    private Patient findPatientById(int id) {
        for (Patient p : allPatients) {
            if (p.getId() == id) return p;
        }
        return null;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Admin Info ===");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
    }
    
    // ==================== ADMIN METHODS ====================
    
    public void addDoctor() {
        System.out.println("\n--- Add New Doctor ---");
        int id = allDoctors.size() + 1;
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Specialization: ");
        String spec = scanner.nextLine();
        System.out.print("Department: ");
        String dept = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        
        Doctor d = new Doctor(id, name, username, password, spec, dept, phone, true);
        allDoctors.add(d);
        System.out.println("Doctor added with ID: D" + id);
    }
    
    public void registerPatient() {
        System.out.println("\n--- Register New Patient ---");
        int id = allPatients.size() + 1;
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Age: ");
        int age = getIntInput("");
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Medical History: ");
        String history = scanner.nextLine();
        
        Patient p = new Patient(id, name, username, password, age, gender, phone, history);
        allPatients.add(p);
        System.out.println("Patient registered with ID: P" + id);
    }
    
    public void assignPatientToDoctor() {
        if (allDoctors.isEmpty() || allPatients.isEmpty()) {
            System.out.println("Need both doctors and patients first!");
            return;
        }
        
        viewAllPatients();
        int patientId = getIntInput("Enter Patient ID to assign: ");
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found!");
            return;
        }
        
        viewAllDoctors();
        int doctorId = getIntInput("Enter Doctor ID to assign: ");
        Doctor doctor = findDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found!");
            return;
        }
        
        patient.setAssignedDoctorId(doctorId);
        doctor.addAssignedPatient(patientId);
        System.out.println("Patient assigned to Dr. " + doctor.getName());
    }
    
    public void createAppointment() {
        System.out.println("\n--- Create Appointment ---");
        
        int patientId = getIntInput("Enter Patient ID: ");
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found!");
            return;
        }
        
        if (patient.getAssignedDoctorId() == -1) {
            System.out.println("Patient has no assigned doctor!");
            return;
        }
        
        int doctorId = patient.getAssignedDoctorId();
        
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Time (HH:MM): ");
        String time = scanner.nextLine();
        
        // Check for duplicate
        for (Appointment a : allAppointments) {
            if (a.getDoctorId() == doctorId && a.getDate().equals(date) && a.getTime().equals(time)) {
                System.out.println("Doctor already has appointment at this time!");
                return;
            }
        }
        
        String aptId = "A" + String.format("%03d", allAppointments.size() + 1);
        Appointment apt = new Appointment(aptId, patientId, doctorId, date, time, "confirmed");
        allAppointments.add(apt);
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            doctor.addAppointment(aptId);
        }
        patient.addAppointment(aptId);
        System.out.println("Appointment created: " + aptId);
    }
    
    public void viewAllDoctors() {
        if (allDoctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        System.out.println("\n=== All Doctors ===");
        for (Doctor d : allDoctors) {
            d.displayInfo();
            System.out.println("---");
        }
    }
    
    public void viewAllPatients() {
        if (allPatients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        System.out.println("\n=== All Patients ===");
        for (Patient p : allPatients) {
            p.displayInfo();
            System.out.println("---");
        }
    }
    
    public void viewAllAppointments() {
        if (allAppointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        System.out.println("\n=== All Appointments ===");
        for (Appointment a : allAppointments) {
            a.displayAppointmentDetails();
            System.out.println("---");
        }
    }
    
    public void searchPatientById() {
        int id = getIntInput("Enter Patient ID: ");
        Patient p = findPatientById(id);
        if (p != null) {
            p.displayInfo();
        } else {
            System.out.println("Patient not found!");
        }
    }
    
    public void searchDoctorById() {
        int id = getIntInput("Enter Doctor ID: ");
        Doctor d = findDoctorById(id);
        if (d != null) {
            d.displayInfo();
        } else {
            System.out.println("Doctor not found!");
        }
    }
    
    public void generateReports() {
        System.out.println("\n=== REPORTS ===");
        System.out.println("Total Doctors: " + allDoctors.size());
        System.out.println("Total Patients: " + allPatients.size());
        
        int confirmed = 0, completed = 0, cancelled = 0;
        for (Appointment a : allAppointments) {
            switch (a.getStatus()) {
                case "confirmed": confirmed++; break;
                case "completed": completed++; break;
                case "cancelled": cancelled++; break;
            }
        }
        System.out.println("Appointments - Confirmed: " + confirmed + ", Completed: " + completed + ", Cancelled: " + cancelled);
        
        System.out.println("\nTop Doctors by Appointments:");
        for (Doctor d : allDoctors) {
            System.out.println(d.getName() + ": " + d.getAppointmentIds().size() + " appointments");
        }
    }
}