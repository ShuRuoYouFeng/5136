package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

    private Account appointmentAccount;
    private Clinic appointmentClinic;
    private Gp appointmentGp;
    private String patientStatus;
    private AppointmentType appointmentType;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    /**
     * Default constructor for objects of class Appointment
     *
     */
    public Appointment() {
        appointmentDate = null;
        appointmentTime = null;
        appointmentGp = new Gp();
        appointmentClinic = new Clinic();
        patientStatus = "";
        appointmentType = new AppointmentType();
        appointmentAccount = null;
    }

    /**
     * Constructor for objects of class Appointment
     *
     * @param appointmentPatient The patient participating in the appointment
     * @param appointmentClinic  The place of appointment
     * @param appointmentGp      The GP participating in the appointment
     * @param patientStatus      The status of the patient
     * @param appointmentType    The type of appointment
     * @param appointmentDate    The date of appointment
     * @param appointmentTime    The time of appointment
     */
    public Appointment(Account appointmentPatient,
                       Clinic appointmentClinic,
                       Gp appointmentGp,
                       String patientStatus,
                       AppointmentType appointmentType,
                       LocalDate appointmentDate,
                       LocalTime appointmentTime) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentGp = appointmentGp;
        this.appointmentClinic = appointmentClinic;
        this.patientStatus = patientStatus;
        this.appointmentType = appointmentType;
        this.appointmentAccount = appointmentPatient;
    }

    /**
     * This method can get the patient of this appointment
     *
     * @return appointmentAccount  the patient's appointment
     */
    public Account getAppointmentPatient() {
        return appointmentAccount;
    }

    /**
     * This method can set the patient of this appointment
     *
     * @param appointmentPatient  the new patient of this appointment
     */
    public void setAppointmentPatient(Account appointmentPatient) {
        this.appointmentAccount = appointmentPatient;
    }

    /**
     * This method can get the date of this appointment
     *
     * @return appointmentDate  the date of this appointment
     */
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * This method can get the time of this appointment
     *
     * @return appointmentTime the time of this appointment
     */
    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * This method can set the date of this appointment
     *
     * @param appointmentDate the date of this appointment
     */
    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * This method can set the time of this appointment
     *
     * @param appointmentTime  the time of this appointment
     */
    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * This method can get the GP of this appointment
     *
     * @return appointmentGp  the GP of this appointment
     */
    public Gp getAppointmentGp() {
        return appointmentGp;
    }

    /**
     * This method can set the GP of this appointment
     *
     * @param appointmentGp  the new GP of this appointment
     */
    public void setAppointmentGp(Gp appointmentGp) {
        this.appointmentGp = appointmentGp;
    }

    /**
     * This method can get the Clinic of this appointment
     *
     * @return appointmentClinic  Clinic of this appointment
     */
    public Clinic getAppointmentClinic() {
        return appointmentClinic;
    }

    /**
     * This method can set the Clinic of this appointment
     *
     * @param appointmentClinic  new clinic of this appointment
     */
    public void setAppointmentClinic(Clinic appointmentClinic) {
        this.appointmentClinic = appointmentClinic;
    }

    /**
     * This method can get the status of this appointment
     *
     * @return patientStatus the status of this appointment
     */
    public String getPatientStatus() {
        return patientStatus;
    }

    /**
     * This method can set the status of this appointment
     *
     * @param patientStatus  the new status of this appointment
     */
    public void setPatientStatus(String patientStatus) {
        this.patientStatus = patientStatus;
    }

    /**
     * This method can get the type of this appointment
     *
     * @return appointmentType   the type of this appointment
     */
    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * Format the current object as String
     * Used for file writing.
     *
     * @return information: The formatted result of the current class.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String information = appointmentAccount.getAccountEmail() + ";" +
                appointmentClinic.getClinicAddress() + ";" +
                appointmentGp.getGpName() + ";" +
                patientStatus + ";" +
                appointmentType.getDescription() + ";" +
                appointmentDate.format(formatters) + ";" +
                appointmentTime.toString();
        return information;
    }
}
