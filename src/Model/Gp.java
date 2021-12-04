package Model;

import java.util.ArrayList;

public class Gp {

    private String gpFirstName;
    private String gpLastName;
    private String gpPhone;
    private String gpInterestArea;
    private ArrayList<String> gpClinics;

    /**
     * Default constructor for objects of class Gp
     *
     */
    public Gp() {
        gpFirstName = "";
        gpLastName = "";
        gpPhone = "";
        gpInterestArea = "";
        gpClinics = new ArrayList<>();
    }

    /**
     * Constructor for objects of class AppointmentType
     *
     * @param gpFirstName    The first name of the GP
     * @param gpLastName     The last name of the GP
     * @param gpPhone        The phone of the GP
     * @param gpInterestArea The interest area of the GP
     * @param gpClinics      The clinic the GP belongs to
     */
    public Gp(String gpFirstName, String gpLastName, String gpPhone, String gpInterestArea, ArrayList<String> gpClinics) {
        this.gpFirstName = gpFirstName;
        this.gpLastName = gpLastName;
        this.gpPhone = gpPhone;
        this.gpInterestArea = gpInterestArea;
        this.gpClinics = gpClinics;
    }

    /**
     * This method can get the first name of this GP
     *
     * @return gpFirstName the first name of this GP
     */
    public String getGpFirstName() {
        return gpFirstName;
    }

    /**
     * This method can get the full name of this GP
     *
     * @return name the full name of this GP
     */
    public String getGpName() {
        return gpFirstName + " " + gpLastName;
    }

    /**
     * This method can set the first name of this GP
     *
     * @param gpFirstName the first name of this GP
     */
    public void setGpFirstName(String gpFirstName) {
        this.gpFirstName = gpFirstName;
    }

    /**
     * This method can get the last name of this GP
     *
     * @return gpLastName  the last name of this GP
     */
    public String getGpLastName() {
        return gpLastName;
    }

    /**
     * This method can set the last name of this GP
     *
     * @param gpLastName  the last name of this GP
     */
    public void setGpLastName(String gpLastName) {
        this.gpLastName = gpLastName;
    }

    /**
     * This method can get the phone of this GP
     *
     * @return gpPhone  the phone of this GP
     */
    public String getGpPhone() {
        return gpPhone;
    }

    /**
     * This method can set the phone of this GP
     *
     * @param gpPhone  the phone of this GP
     */
    public void setGpPhone(String gpPhone) {
        this.gpPhone = gpPhone;
    }

    /**
     * This method can get the interest area of this GP
     *
     * @return gpInterestArea  the interest area of this GP
     */
    public String getGpInterestArea() {
        return gpInterestArea;
    }

    /**
     * This method can get the interest area of this GP
     *
     * @param gpInterestArea  the interest area of this GP
     */
    public void setGpInterestArea(String gpInterestArea) {
        this.gpInterestArea = gpInterestArea;
    }

    /**
     * This method can get the Clinic of this GP
     *
     * @return gpClinics  the Clinic of this GP
     */
    public ArrayList<String> getGpClinics() {
        return gpClinics;
    }

    /**
     * This method can set the clinic of this GP
     *
     * @param gpClinics the clinic of this GP
     */
    public void setGpClinics(ArrayList<String> gpClinics) {
        this.gpClinics = gpClinics;
    }
}
