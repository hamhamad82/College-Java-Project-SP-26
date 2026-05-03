// Doctor.java
import java.util.ArrayList;
import java.util.Scanner;

public class Doctor extends User {
    private String specialization;
    private String department;
    private String phone;
    private boolean isAvailable;
    private ArrayList<Integer> assignedPatientIds;
    private ArrayList<String> appointmentIds;
    
    // Reference to system data (will be set by HospitalSystem)
    private transient ArrayList<Doctor> allDoctors;
    private transient ArrayList<Patient> allPatients;
    private transient ArrayList<Appointment> allAppointments;
    private transient Scanner scanner;
    
    public Doctor(int id, String name, String username, String password,
                  String specialization, String department, String phone, boolean isAvailable) {
        super(id, name, username, password);
        this.specialization = specialization;
        this.department = department;
        this.phone = phone;
        this.isAvailable = isAvailable;
        this.assignedPatientIds = new ArrayList<>();
        this.appointmentIds = new ArrayList<>();
    }
    
    // Set references (called by HospitalSystem)
    public void setDataRefs(ArrayList<Doctor> doctors, ArrayList<Patient> patients, 
                            ArrayList<Appointment> appointments, Scanner sc) {
        this.allDoctors = doctors;
        this.allPatients = patients;
        this.allAppointments = appointments;
        this.scanner = sc;
    }
    
    // Getters
    public String getSpecialization() { return specialization; }
    public String getDepartment() { return department; }
    public String getPhone() { return phone; }
    public boolean isAvailable() { return isAvailable; }
    public ArrayList<Integer> getAssignedPatientIds() { return assignedPatientIds; }
    public ArrayList<String> getAppointmentIds() { return appointmentIds; }
    
    // Setters
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setDepartment(String department) { this.department = department; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    public void addAssignedPatient(int patientId) {
        if (!assignedPatientIds.contains(patientId)) {
            assignedPatientIds.add(patientId);
        }
    }
    
    public void addAppointment(String appointmentId) {
        if (!appointmentIds.contains(appointmentId)) {
            appointmentIds.add(appointmentId);
        }
    }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Doctor Info ===");
        System.out.println("ID: D" + id);
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Specialization: " + specialization);
        System.out.println("Department: " + department);
        System.out.println("Phone: " + phone);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
        System.out.println("Assigned Patients: " + assignedPatientIds.size());
        System.out.println("Appointments: " + appointmentIds.size());
    }
    
    // ==================== DOCTOR METHODS ====================
    
    public void viewProfile() {
        displayInfo();
    }
    
    public void viewAssignedPatients() {
        if (assignedPatientIds.isEmpty()) {
            System.out.println("No assigned patients.");
            return;
        }
        System.out.println("\n=== My Assigned Patients ===");
        for (int pid : assignedPatientIds) {
            for (Patient p : allPatients) {
                if (p.getId() == pid) {
                    p.displayInfo();
                    System.out.println("---");
                    break;
                }
            }
        }
    }
    
    public void viewAppointments() {
        if (appointmentIds.isEmpty()) {
            System.out.println("No appointments.");
            return;
        }
        System.out.println("\n=== My Appointments ===");
        for (String aid : appointmentIds) {
            for (Appointment a : allAppointments) {
                if (a.getId().equals(aid)) {
                    a.displayAppointmentDetails();
                    System.out.println("---");
                    break;
                }
            }
        }
    }
    
    public void updateAppointmentStatus() {
        viewAppointments();
        if (appointmentIds.isEmpty()) return;
        
        System.out.print("Enter Appointment ID to update: ");
        String aid = scanner.nextLine();
        
        Appointment target = null;
        for (Appointment a : allAppointments) {
            if (a.getId().equals(aid)) {
                target = a;
                break;
            }
        }
        
        if (target == null) {
            System.out.println("Appointment not found!");
            return;
        }
        
        System.out.println("1. Confirmed  2. Completed  3. Cancelled");
        System.out.print("Choose status: ");
        int choice;
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid! Enter number: ");
            scanner.next();
        }
        choice = scanner.nextInt();
        scanner.nextLine();
        
        String status = "";
        switch (choice) {
            case 1: status = "confirmed"; break;
            case 2: status = "completed"; break;
            case 3: status = "cancelled"; break;
            default: System.out.println("Invalid choice!"); return;
        }
        
        target.updateStatus(status);
        System.out.println("Status updated to: " + status);
    }
}