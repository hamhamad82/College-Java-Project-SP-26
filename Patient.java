// Patient.java
import java.util.ArrayList;
import java.util.Scanner;

public class Patient extends User {
    private int age;
    private String gender;
    private String phone;
    private String medicalHistory;
    private int assignedDoctorId;
    private ArrayList<String> appointmentIds;
    
    // References to system data
    private transient ArrayList<Doctor> allDoctors;
    private transient ArrayList<Patient> allPatients;
    private transient ArrayList<Appointment> allAppointments;
    private transient Scanner scanner;
    
    public Patient(int id, String name, String username, String password,
                   int age, String gender, String phone, String medicalHistory) {
        super(id, name, username, password);
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.medicalHistory = medicalHistory;
        this.assignedDoctorId = -1;
        this.appointmentIds = new ArrayList<>();
    }
    
    // Set references
    public void setDataRefs(ArrayList<Doctor> doctors, ArrayList<Patient> patients,
                            ArrayList<Appointment> appointments, Scanner sc) {
        this.allDoctors = doctors;
        this.allPatients = patients;
        this.allAppointments = appointments;
        this.scanner = sc;
    }
    
    // Getters
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }
    public String getMedicalHistory() { return medicalHistory; }
    public int getAssignedDoctorId() { return assignedDoctorId; }
    public ArrayList<String> getAppointmentIds() { return appointmentIds; }
    
    // Setters
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
    public void setAssignedDoctorId(int assignedDoctorId) { this.assignedDoctorId = assignedDoctorId; }
    
    public void addAppointment(String appointmentId) {
        if (!appointmentIds.contains(appointmentId)) {
            appointmentIds.add(appointmentId);
        }
    }
    
    private Doctor findDoctorById(int id) {
        for (Doctor d : allDoctors) {
            if (d.getId() == id) return d;
        }
        return null;
    }
    
    private Appointment findAppointmentById(String id) {
        for (Appointment a : allAppointments) {
            if (a.getId().equals(id)) return a;
        }
        return null;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Patient Info ===");
        System.out.println("ID: P" + id);
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Phone: " + phone);
        System.out.println("Medical History: " + medicalHistory);
        System.out.println("Assigned Doctor ID: " + (assignedDoctorId == -1 ? "None" : "D" + assignedDoctorId));
        System.out.println("Appointments: " + appointmentIds.size());
    }
    
    // ==================== PATIENT METHODS ====================
    
    public void viewProfile() {
        displayInfo();
    }
    
    public void viewAssignedDoctor() {
        if (assignedDoctorId == -1) {
            System.out.println("No doctor assigned.");
            return;
        }
        Doctor d = findDoctorById(assignedDoctorId);
        if (d != null) {
            d.displayInfo();
        }
    }
    
    public void viewAppointments() {
        if (appointmentIds.isEmpty()) {
            System.out.println("No appointments.");
            return;
        }
        System.out.println("\n=== My Appointments ===");
        for (String aid : appointmentIds) {
            Appointment a = findAppointmentById(aid);
            if (a != null) {
                a.displayAppointmentDetails();
                System.out.println("---");
            }
        }
    }
    
    public void bookAppointment() {
        if (assignedDoctorId == -1) {
            System.out.println("No doctor assigned! Contact admin.");
            return;
        }
        
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Time (HH:MM): ");
        String time = scanner.nextLine();
        
        // Check if doctor is available
        for (Appointment a : allAppointments) {
            if (a.getDoctorId() == assignedDoctorId && 
                a.getDate().equals(date) && 
                a.getTime().equals(time)) {
                System.out.println("Doctor not available at this time!");
                return;
            }
        }
        
        String aptId = "A" + String.format("%03d", allAppointments.size() + 1);
        Appointment apt = new Appointment(aptId, this.id, assignedDoctorId, date, time, "confirmed");
        allAppointments.add(apt);
        
        this.addAppointment(aptId);
        
        // Add to doctor's list
        Doctor d = findDoctorById(assignedDoctorId);
        if (d != null) {
            d.addAppointment(aptId);
        }
        
        System.out.println("Appointment booked: " + aptId);
    }
    
    public void cancelAppointment() {
        viewAppointments();
        if (appointmentIds.isEmpty()) return;
        
        System.out.print("Enter Appointment ID to cancel: ");
        String aid = scanner.nextLine();
        
        Appointment target = findAppointmentById(aid);
        if (target == null || target.getPatientId() != this.id) {
            System.out.println("Appointment not found!");
            return;
        }
        
        if (target.getStatus().equals("cancelled")) {
            System.out.println("Already cancelled.");
            return;
        }
        
        target.updateStatus("cancelled");
        System.out.println("Appointment cancelled.");
    }
}