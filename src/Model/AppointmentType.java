package Model;

public class AppointmentType {

    private String description;
    private int duration;

    /**
     * Default constructor for objects of class AppointmentType
     */
    public AppointmentType() {
        description = "";
        duration = 0;
    }

    /**
     * Constructor for objects of class AppointmentType
     *
     * @param description  the description of this appointment type
     * @param duration     the duration of this type of appointment
     */
    public AppointmentType(String description, int duration) {
        this.description = description;
        this.duration = duration;
    }

    /**
     * This method can get the description of this appointment type
     *
     * @return description  the description of this appointment type
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method can set the description of this appointment type
     *
     * @param description  the new description of this appointment type
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method can get the duration of this type of appointment
     *
     * @return duration  the duration of this type of appointment
     */
    public int getDuration() {
        return duration;
    }

    /**
     * This method can set the duration of this type of appointment
     *
     * @param duration  the new duration of this type of appointment
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
