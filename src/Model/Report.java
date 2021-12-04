package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Report {

    private LocalDate reportStartDate;
    private LocalDate reportEndDate;

    /**
     * Default constructor for objects of class Report
     */
    public Report() {
        reportStartDate = null;
        reportEndDate = null;
    }

    /**
     * Constructor for objects of class Report
     *
     * @param reportStartDate The start date of the report
     * @param reportEndDate   The end date of the report
     */
    public Report(LocalDate reportStartDate, LocalDate reportEndDate) {
        this.reportStartDate = reportStartDate;
        this.reportEndDate = reportEndDate;
    }

    /**
     * This method can get the start date of the report
     *
     * @return reportStartDate  the start date of the report
     */
    public LocalDate getReportStartDate() {
        return reportStartDate;
    }

    /**
     * This method can set the start date of the report
     *
     * @param reportStartDate the start date of the report
     */
    public void setReportStartDate(LocalDate reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    /**
     * This method can get the end date of the report
     *
     * @return reportEndDate the start date of the report
     */
    public LocalDate getReportEndDate() {
        return reportEndDate;
    }

    /**
     * This method can set the end date of the report
     *
     * @param reportEndDate  the end date of the report
     */
    public void setReportEndDate(LocalDate reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    /**
     * Get percentages per appointment type
     *
     * @param appointmentList: list of appointment within the report period
     * @return double[]: percentages per appointment type
     */
    public double[] generateReport(ArrayList<Appointment> appointmentList) {
        double size = appointmentList.size();
        double standard = 0;
        double telehealth = 0;
        double longer = 0;
        if (size > 0) {
            for (Appointment appointment: appointmentList) {
                if (appointment.getAppointmentType().getDescription().equals("Standard Consultation Face to Face (15 minutes)")) {
                    standard++;
                } else if (appointment.getAppointmentType().getDescription().equals("Telehealth (Video / Phone consult) (15 minutes)")) {
                    telehealth++;
                } else {
                    longer++;
                }
            }
            standard = standard / size * 100;
            telehealth = telehealth / size * 100;
            longer = longer / size * 100;
        }
        double[] appointmentPercentage = {size, standard, telehealth, longer};
        return appointmentPercentage;
    }
}
