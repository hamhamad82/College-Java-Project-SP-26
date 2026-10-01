# 🏥 Hospital Management System (Java OOP)

A console-based Hospital Management System built in pure Java to demonstrate core Object-Oriented Programming principles, role-based access control, and persistent File I/O — no external database required.

This project simulates a real hospital workflow: admins onboard doctors and patients, patients book appointments with their assigned doctor, and doctors manage their schedules and patient lists.

---

## 🚀 Features

### 🔐 Role-Based Login System
- **Admin** — hardcoded credentials (`admin` / `admin123`) for system administration
- **Doctor** — logs in with credentials created by the Admin
- **Patient** — logs in with credentials created by the Admin

### 👨‍💼 Admin Capabilities
- Add new doctors (name, specialization, department, phone)
- Register new patients (age, gender, phone, medical history)
- Assign patients to doctors
- Create appointments on behalf of patients
- View all doctors, patients, and appointments
- Search patients and doctors by ID
- Generate reports (totals, appointment status breakdown, top doctors)
- Manually save data at any time

### 🩺 Doctor Capabilities
- View personal profile
- View assigned patients
- View upcoming/past appointments
- Update appointment status (Confirmed / Completed / Cancelled)

### 🧑‍🦱 Patient Capabilities
- View personal profile
- View assigned doctor
- View all appointments
- Book new appointments (with conflict detection for the doctor's schedule)
- Cancel existing appointments

### 💾 Data Persistence
All data is stored in plain-text `.txt` files inside a `data/` directory, loaded on startup and saved on exit — so your records survive between sessions.

---

## 🧠 OOP Concepts Demonstrated

| Concept | Where It's Used |
|---|---|
| **Inheritance** | `Admin`, `Doctor`, and `Patient` all extend the base `User` class |
| **Encapsulation** | All model classes use `private` fields with public getters/setters |
| **Polymorphism** | `displayInfo()` is overridden in each subclass |
| **Abstraction** | `HospitalSystem` acts as the controller; `FileManager` hides all I/O logic |
| **Composition** | `HospitalSystem` composes `ArrayList`s of `Doctor`, `Patient`, and `Appointment` |
| **Transient References** | Subclass objects get injected with system-wide data references rather than duplicating them |

---

## 📂 Project Structure

```text
├── src/
│   ├── Main.java                 # Entry point — boots up the system
│   ├── HospitalSystem.java       # Core controller, menus, login, load/save
│   ├── User.java                 # Abstract base class for all users
│   ├── Admin.java                # Admin logic (add doctor, register patient, reports)
│   ├── Doctor.java               # Doctor model + doctor-side operations
│   ├── Patient.java              # Patient model + patient-side operations
│   ├── Appointment.java          # Appointment model
│   └── FileManager.java          # Static utility for reading/writing .txt files
│
├── data/                         # Auto-created on first run
│   ├── doctors.txt               # Format: D001,name,username,password,spec,dept,phone,available
│   ├── patients.txt              # Format: P001,name,username,password,age,gender,phone,history,D001
│   └── appointments.txt          # Format: A001,P001,D001,date,time,status
│
└── README.md
```

> ⚠️ **Note:** The `data/` folder is created automatically by `FileManager.createDataDirectory()` the first time you run the program. If it doesn't exist, the app will simply start with empty lists.

---

## 🛠️ Prerequisites

- **Java JDK 8** or higher
- Any IDE (IntelliJ, Eclipse, VS Code) or a terminal

---

## 🏁 How to Run

### Option 1 — Command Line

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/hospital-management-system.git
   cd hospital-management-system
   ```

2. **Compile all Java files:**
   ```bash
   javac *.java
   ```

3. **Run the program:**
   ```bash
   java Main
   ```

### Option 2 — IDE
Just open the project, make sure `Main.java` is set as the run configuration, and hit ▶️ Run.

---

## 🎮 Usage Walkthrough

1. **Start the app** → you'll see the main menu.
2. **Log in as Admin** using `admin` / `admin123`.
3. **Add a Doctor** (e.g., Dr. Smith — Cardiology).
4. **Register a Patient** (e.g., John Doe).
5. **Assign the Patient to the Doctor.**
6. **Logout**, then **log back in as the patient** using the credentials you just created.
7. **Book an appointment** — try booking the same time slot twice to see the conflict check in action.
8. **Log in as the doctor** to view and update the appointment status.
9. **Exit** — data is saved automatically to the `data/` folder.

### 🔑 Default Admin Credentials
```
Username: admin
Password: admin123
```

---

## 🗂️ Data File Formats

| File | Format | Example |
|---|---|---|
| `doctors.txt` | `D###,name,username,password,specialization,department,phone,available` | `D001,Smith,dr.smith,pass,Cardiology,Heart,555-1234,true` |
| `patients.txt` | `P###,name,username,password,age,gender,phone,history,D###` | `P001,John Doe,john,pw,30,Male,555-5678,None,D001` |
| `appointments.txt` | `A###,P###,D###,date,time,status` | `A001,P001,D001,2025-01-15,10:00,confirmed` |

---

## 🔮 Future Improvements

- [ ] Replace hardcoded admin credentials with a proper user store
- [ ] Hash passwords instead of storing plaintext
- [ ] Migrate from `.txt` files to SQLite/MySQL via JDBC
- [ ] Build a GUI with JavaFX or Swing
- [ ] Add proper date/time validation (currently trusts user input format)
- [ ] Implement `equals()` and `hashCode()` on model classes for safer list operations
- [ ] Write unit tests with JUnit

---

## 🤝 Contributing

This is a learning project, but PRs and suggestions are welcome! Fork it, break it, fix it, and send a pull request.

---

## 👤 Author

**[Your Name]**
- GitHub: [@your-username](https://github.com/your-username)
- LinkedIn: [Your Profile](https://linkedin.com/in/your-profile)

---

## 📜 License

This project is open source and available under the [MIT License](LICENSE).

---

*Built as part of a Java OOP learning journey — practicing inheritance, encapsulation, polymorphism, file I/O, and multi-menu console architecture.*
