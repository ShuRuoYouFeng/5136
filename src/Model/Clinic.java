package Model;

import java.time.LocalTime;

public class Clinic {

    private String clinicName;
    private String clinicAddress;
    private String clinicPhone;
    private LocalTime clinicOpeningTime;
    private LocalTime clinicClosingTime;

    /**
     * Default constructor for objects of class Clinic
     *
     */
    public Clinic() {
        clinicName = "";
        clinicAddress = "";
        clinicPhone = "";
        clinicOpeningTime = null;
        clinicClosingTime = null;
    }

    /**
     * Constructor for objects of class Clinic
     *
     * @param clinicName        the name of the clinic
     * @param clinicAddress     the address of the clinic
     * @param clinicPhone       the phone of the clinic
     * @param clinicOpeningTime the opening time of the clinic
     * @param clinicClosingTime the closing time of the clinic
     */
    public Clinic(String clinicName, String clinicAddress, String clinicPhone, LocalTime clinicOpeningTime, LocalTime clinicClosingTime) {
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.clinicPhone = clinicPhone;
        this.clinicOpeningTime = clinicOpeningTime;
        this.clinicClosingTime = clinicClosingTime;
    }

    /**
     * This method can get the name of this clinic
     *
     * @return clinicName  the name of this clinic
     */
    public String getClinicName() {
        return clinicName;
    }

    /**
     * This method can set the name of this clinic
     *
     * @param clinicName  the new name of this clinic
     */
    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    /**
     * This method can get the address of this clinic
     *
     * @return clinicAddress the address of this clinic
     */
    public String getClinicAddress() {
        return clinicAddress;
    }

    /**
     * This method can set the address of this clinic
     *
     * @param clinicAddress  the new address of this clinic
     */
    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    /**
     * This method can get the phone of this clinic
     *
     * @return  clinicPhone the phone of this clinic
     */
    public String getClinicPhone() {
        return clinicPhone;
    }

    /**
     * This method can set the phone of this clinic
     *
     * @param clinicPhone  the new phone of this clinic
     */
    public void setClinicPhone(String clinicPhone) {
        this.clinicPhone = clinicPhone;
    }

    /**
     * This method can get the opening time of this clinic
     *
     * @return clinicOpeningTime the opening time of this clinic
     */
    public LocalTime getClinicOpeningTime() {
        return clinicOpeningTime;
    }

    /**
     * This method can set the opening time of this clinic
     *
     * @param clinicOpeningTime the new opening time of this clinic
     */
    public void setClinicOpeningTime(LocalTime clinicOpeningTime) {
        this.clinicOpeningTime = clinicOpeningTime;
    }

    /**
     * This method can get the closing time of this clinic
     *
     * @return clinicClosingTime the closing time of this clinic
     */
    public LocalTime getClinicClosingTime() {
        return clinicClosingTime;
    }

    /**
     * This method can set the closing time of this clinic
     *
     * @param clinicClosingTime  the new closing time of this clinic
     */
    public void setClinicClosingTime(LocalTime clinicClosingTime) {
        this.clinicClosingTime = clinicClosingTime;
    }
}
