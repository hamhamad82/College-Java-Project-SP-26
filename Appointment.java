// Appointment.java
public class Appointment {
    private String id;
    private int patientId;
    private int doctorId;
    private String date;
    private String time;
    private String status;
    
    public Appointment(String id, int patientId, int doctorId, String date, String time, String status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = status;
    }
    
    public String getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
    
    public void setId(String id) { this.id = id; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    
    public void setStatus(String status) {
        if (this.status != null && this.status.equals("cancelled") && !status.equals("cancelled")) {
            System.out.println("Cannot change cancelled appointment!");
            return;
        }
        this.status = status;
    }
    
    public void updateStatus(String newStatus) {
        setStatus(newStatus);
    }
    
    public void displayAppointmentDetails() {
        System.out.println("=== Appointment ===");
        System.out.println("ID: " + id);
        System.out.println("Patient ID: P" + patientId);
        System.out.println("Doctor ID: D" + doctorId);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Status: " + status);
    }
}