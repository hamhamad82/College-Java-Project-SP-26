// HospitalSystem.java
import java.util.ArrayList;
import java.util.Scanner;

public class HospitalSystem {
    private ArrayList<Doctor> doctors;
    private ArrayList<Patient> patients;
    private ArrayList<Appointment> appointments;
    private Scanner scanner;
    private User currentUser;
    
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    
    public HospitalSystem() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
        scanner = new Scanner(System.in);
        currentUser = null;
        
        FileManager.createDataDirectory();
        loadData();
        setDataReferences();
    }
    
    // Pass references to all user objects
    private void setDataReferences() {
        for (Doctor d : doctors) {
            d.setDataRefs(doctors, patients, appointments, scanner);
        }
        for (Patient p : patients) {
            p.setDataRefs(doctors, patients, appointments, scanner);
        }
    }
    
    public void start() {
        while (true) {
            showMainMenu();
            int choice = getIntInput("Choose: ");
            
            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    doctorLogin();
                    break;
                case 3:
                    patientLogin();
                    break;
                case 4:
                    saveData();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private void showMainMenu() {
        System.out.println("\n=== Hospital Management System ===");
        System.out.println("1. Login as Admin");
        System.out.println("2. Login as Doctor");
        System.out.println("3. Login as Patient");
        System.out.println("4. Exit");
    }
    
    private void adminLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            Admin admin = new Admin(0, "Administrator", ADMIN_USERNAME, ADMIN_PASSWORD);
            admin.setDataRefs(doctors, patients, appointments, scanner);
            currentUser = admin;
            System.out.println("Admin login successful!");
            adminMenu(admin);
        } else {
            System.out.println("Invalid admin credentials!");
        }
    }
    
    private void doctorLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        for (Doctor d : doctors) {
            if (d.getUsername().equals(username) && d.getPassword().equals(password)) {
                currentUser = d;
                System.out.println("Welcome Dr. " + d.getName());
                doctorMenu(d);
                return;
            }
        }
        System.out.println("Invalid doctor credentials!");
    }
    
    private void patientLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        for (Patient p : patients) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                currentUser = p;
                System.out.println("Welcome " + p.getName());
                patientMenu(p);
                return;
            }
        }
        System.out.println("Invalid patient credentials!");
    }
    
    // ==================== ADMIN MENU ====================
    
    private void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add Doctor");
            System.out.println("2. Register Patient");
            System.out.println("3. Assign Patient to Doctor");
            System.out.println("4. Create Appointment");
            System.out.println("5. View All Doctors");
            System.out.println("6. View All Patients");
            System.out.println("7. View All Appointments");
            System.out.println("8. Search Patient by ID");
            System.out.println("9. Search Doctor by ID");
            System.out.println("10. Generate Reports");
            System.out.println("11. Save Data");
            System.out.println("12. Logout");
            
            int choice = getIntInput("Choose: ");
            
            switch (choice) {
                case 1:
                    admin.addDoctor();
                    break;
                case 2:
                    admin.registerPatient();
                    break;
                case 3:
                    admin.assignPatientToDoctor();
                    break;
                case 4:
                    admin.createAppointment();
                    break;
                case 5:
                    admin.viewAllDoctors();
                    break;
                case 6:
                    admin.viewAllPatients();
                    break;
                case 7:
                    admin.viewAllAppointments();
                    break;
                case 8:
                    admin.searchPatientById();
                    break;
                case 9:
                    admin.searchDoctorById();
                    break;
                case 10:
                    admin.generateReports();
                    break;
                case 11:
                    saveData();
                    break;
                case 12:
                    currentUser = null;
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    // ==================== DOCTOR MENU ====================
    
    private void doctorMenu(Doctor doctor) {
        while (true) {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Patients");
            System.out.println("3. View My Appointments");
            System.out.println("4. Update Appointment Status");
            System.out.println("5. Logout");
            
            int choice = getIntInput("Choose: ");
            
            switch (choice) {
                case 1:
                    doctor.viewProfile();
                    break;
                case 2:
                    doctor.viewAssignedPatients();
                    break;
                case 3:
                    doctor.viewAppointments();
                    break;
                case 4:
                    doctor.updateAppointmentStatus();
                    break;
                case 5:
                    currentUser = null;
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    // ==================== PATIENT MENU ====================
    
    private void patientMenu(Patient patient) {
        while (true) {
            System.out.println("\n=== Patient Menu ===");
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Doctor");
            System.out.println("3. View My Appointments");
            System.out.println("4. Book Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Logout");
            
            int choice = getIntInput("Choose: ");
            
            switch (choice) {
                case 1:
                    patient.viewProfile();
                    break;
                case 2:
                    patient.viewAssignedDoctor();
                    break;
                case 3:
                    patient.viewAppointments();
                    break;
                case 4:
                    patient.bookAppointment();
                    break;
                case 5:
                    patient.cancelAppointment();
                    break;
                case 6:
                    currentUser = null;
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    // ==================== HELPER ====================
    
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
    
    // ==================== DATA LOAD/SAVE ====================
    
    private void loadData() {
        doctors = FileManager.loadDoctors();
        patients = FileManager.loadPatients();
        appointments = FileManager.loadAppointments();
        
        // Rebuild relationships
        for (Patient p : patients) {
            if (p.getAssignedDoctorId() != -1) {
                for (Doctor d : doctors) {
                    if (d.getId() == p.getAssignedDoctorId()) {
                        d.addAssignedPatient(p.getId());
                        break;
                    }
                }
            }
        }
        
        for (Appointment a : appointments) {
            for (Doctor d : doctors) {
                if (d.getId() == a.getDoctorId()) {
                    d.addAppointment(a.getId());
                    break;
                }
            }
            for (Patient p : patients) {
                if (p.getId() == a.getPatientId()) {
                    p.addAppointment(a.getId());
                    break;
                }
            }
        }
    }
    
    private void saveData() {
        FileManager.saveDoctors(doctors);
        FileManager.savePatients(patients);
        FileManager.saveAppointments(appointments);
        System.out.println("All data saved successfully!");
    }
}