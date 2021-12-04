package Model;

import java.time.LocalDate;

public class Patient extends Account {

    private String patientFirstName;
    private String patientLastName;
    private LocalDate patientDateOfBirth;
    private char patientGender;
    private String patientMobileNumber;

    /**
     * Default constructor for objects of class Patient
     *
     */
    public Patient() {
        super("", "","Patient");
        patientFirstName = "";
        patientLastName = "";
        patientDateOfBirth = null;
        patientGender = ' ';
        patientMobileNumber = "";
    }

    /**
     * Constructor for objects of class Patient
     *
     * @param accountEmail    the email of the patient
     * @param accountPassword the password of the patient
     */
    public Patient(String accountEmail, String accountPassword) {
        super(accountEmail, accountPassword, "Patient");
    }

    /**
     * Constructor for objects of class Patient
     *
     * @param accountEmail        the email of the patient
     * @param accountPassword     the password of the patient
     * @param accountType         the type of the patient
     * @param patientFirstName    the first name of the patient
     * @param patientLastName     the last name of the patient
     * @param patientDateOfBirth  the birthday of the patient
     * @param patientGender       the gender of the patient
     * @param patientMobileNumber the mobile number of the patient
     */
    public Patient(String accountEmail, String accountPassword, String accountType, String patientFirstName, String patientLastName,LocalDate patientDateOfBirth, char patientGender, String patientMobileNumber) {
        super(accountEmail, accountPassword, accountType);
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientDateOfBirth = patientDateOfBirth;
        this.patientGender = patientGender;
        this.patientMobileNumber = patientMobileNumber;
    }

    /**
     * This method can get the first name of this patient
     * @return patientFirstName the first name of this patient
     */
    public String getPatientFirstName() {
        return patientFirstName;
    }

    /**
     * This method can set the first name of this patient
     *
     * @param patientFirstName the first name of this patient
     */
    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    /**
     * This method can get the last name of this patient
     *
     * @return patientLastName  the last name of this patient
     */
    public String getPatientLastName() {
        return patientLastName;
    }

    /**
     * This method can get the last name of this patient
     *
     * @param patientLastName the last name of this patient
     */
    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    /**
     * This method can get the birthday of this patient
     *
     * @return patientDateOfBirth  the birthday of this patient
     */
    public LocalDate getPatientDateOfBirth() {
        return patientDateOfBirth;
    }

    /**
     * This method can set the birthday of this patient
     *
     * @param patientDateOfBirth  the birthday of this patient
     */
    public void setPatientDateOfBirth(LocalDate patientDateOfBirth) {
        this.patientDateOfBirth = patientDateOfBirth;
    }

    /**
     * This method can get the gender of this patient
     *
     * @return patientGender gender of this patient
     */
    public char getPatientGender() {
        return patientGender;
    }

    /**
     * This method can set the gender of this patient
     *
     * @param patientGender the gender of this patient
     */
    public void setPatientGender(char patientGender) {
        this.patientGender = patientGender;
    }

    /**
     * This method can get the mobile number of this patient
     *
     * @return patientMobileNumber  the mobile number of this patient
     */
    public String getPatientMobileNumber() {
        return patientMobileNumber;
    }

    /**
     * This method can set the mobile number of this patient
     *
     * @param patientMobileNumber  the mobile number of this patient
     */
    public void setPatientMobileNumber(String patientMobileNumber) {
        this.patientMobileNumber = patientMobileNumber;
    }
}
