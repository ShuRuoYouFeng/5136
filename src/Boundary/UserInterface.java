package Boundary;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class UserInterface {

    private String accountEmail; //do we still need this attribute since we have userEmail attribute in Control Class

    /**
     * Default constructor for objects of class UserInterface
     *
     */
    public UserInterface() {
        accountEmail = "";
    }

    /**
     * This method can get the email of current user
     *
     * @return accountEmail   the account email of current user
     */
    public String getAccountEmail() {
        return accountEmail;
    }

    /**
     * This method can set the email of current user
     *
     * @param accountEmail   the new email account
     */
    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    /**
     * This method displays the header information
     *
     */
    public void displayPatientHeadMenu() {
        System.out.println("     ||=========================||");
        System.out.println("     ||    Patient Management   ||");
        System.out.println("     ||=========================||");
        System.out.println();
        System.out.printf("--- Welcome %s to Monash Patient Management System (MPMS) ---", accountEmail);
        System.out.println("");
        System.out.println("     --- Account type: Patient");
    }

    /**
     * This method displays the check menu information
     *
     */
    public void displayCheckMenu() {
        System.out.println("     ||=========================||");
        System.out.println("     || Appointment Management  ||");
        System.out.println("     ||=========================||");
        System.out.println();
        System.out.printf("--- Welcome %s to Monash Patient Management System (MPMS) ---", accountEmail);
        System.out.println("");
        System.out.println("     --- Account type: Patient");
    }

    /**
     * This method displays the main menu information
     *
     */
    public int displayPatientMenu() {
        displayPatientHeadMenu();
        System.out.println("*********");
        System.out.println("Main Menu");
        System.out.println("*********");
        System.out.println();
        System.out.println("******Please enter the option (only numbers are accepted).******");
        System.out.println();
        System.out.println("     1. Make a new appointment (Choose a clinic)");
        System.out.println("     2. Read/ Modify current appointment");
        System.out.println("     3. Exit");
        System.out.println("     0. Log out of current user");
        System.out.println();
        System.out.println("Please enter your option:");
        return 3;
    }

    /**
     * This method displays the clinic information menu
     *
     * @param clinicAddressList: Receive a list of clinics for display
     */
    public int displayClinicMenu(ArrayList<String[]> clinicAddressList) {
        displayPatientHeadMenu();
        System.out.println("***********");
        System.out.println("Clinic Menu");
        System.out.println("***********");
        System.out.println();
        System.out.println("******Please enter the clinic option you want to visit (only numbers are accepted).******");
        System.out.println();
        int num = 0;
        for (String clinic[]: clinicAddressList) {
            System.out.println("     " + ++num + ". ----------");
            System.out.println("        " + clinic[0]);
            System.out.println("        Address       --------- " + clinic[1]);
            System.out.println("        Phone         --------- " + clinic[2]);
            System.out.println("        Opening hours --------- " + clinic[3] + " to " + clinic[4]);
            System.out.println();
        }
        System.out.println("     0. Back to the previous screen ");
        System.out.println();
        System.out.println("Please enter your option:");
        return clinicAddressList.size();
    }

    /**
     * This method displays the gp information menu
     *
     * @param gpList: Receive a list of GPs for display
     */
    public int displayGpMenu(ArrayList<ArrayList<String>> gpList) {
        displayPatientHeadMenu();
        System.out.println("*******");
        System.out.println("GP Menu");
        System.out.println("*******");
        System.out.println();
        System.out.println("******Please enter the GP option you want to visit (only numbers are accepted).******");
        System.out.println();
        int num = 0;
        for (ArrayList<String> gp: gpList) {
            System.out.println("     " + ++num + ". ----------");
            System.out.println("        Name: " + gp.get(0));
            System.out.println("        Interest areas: " + gp.get(2));
            /*System.out.println("        Assigned clinics: ");
            for (int i = 3; i < gp.size(); i ++) {
                System.out.println("              " + gp.get(i));
            }*/
            System.out.println();
        }
        if (gpList.size() > 0) {
            System.out.println("     " + ++num + ". ----------");
            System.out.println("        Any GP ");
            System.out.println();
            System.out.println("     0. Back to the previous screen ");
            System.out.println();
            System.out.println("Please enter your option:");
            return gpList.size() + 1;
        }
        else {
            System.out.println("*****There is currently no GP at the clinic*****");
            System.out.println("     0. Back to the previous screen ");
            System.out.println();
            System.out.println("Please enter your option:");
            return 0;
        }

    }

    /**
     * This method displays the patient status option
     *
     */
    public int displayPatientStateMenu() {
        displayPatientHeadMenu();
        System.out.println("*******************");
        System.out.println("Patient Status Menu");
        System.out.println("*******************");
        System.out.println();
        System.out.println("******Please enter your current status option (only numbers are accepted).******");
        System.out.println();
        System.out.println("     1. Existing Patient");
        System.out.println("     2. New Patient");
        System.out.println("     0. Back to the previous screen ");
        System.out.println();
        System.out.println("Please enter your option:");
        return 2;
    }

    /**
     * This method displays the gp option menu
     *
     */
    public int displayAppointmentTypeMenu() {
        displayPatientHeadMenu();
        System.out.println("*********************");
        System.out.println("Appointment Type Menu");
        System.out.println("*********************");
        System.out.println();
        System.out.println("******Please select your reason for seeing the GP (only numbers are accepted).******");
        System.out.println();
        System.out.println("     1. Standard Consultation Face to Face (15 minutes)");
        System.out.println("     2. Telehealth (Video / Phone consult) (15 minutes)");
        System.out.println("     3. Long consultation face to face (30 minutes)");
        System.out.println("     0. Back to the previous screen ");
        System.out.println();
        System.out.println("Please enter your option:");
        return 3;
    }

    /**
     * This method displays the date entry screen
     *
     */
    public void displayDateEnterMenu() {
        SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
        displayPatientHeadMenu();
        System.out.println("*********************");
        System.out.println("Appointment Date Menu");
        System.out.println("*********************");
        System.out.println();
        System.out.println("******Please enter the appointment date (only date format in dd/mm/yyyy are accepted)");
        System.out.println(" e.g. " + ft.format(new Date()) + ". ******");
        System.out.println("******If you want to back to the previous interface, please just enter the number \"0\"******");
        System.out.println();
        System.out.println("Please enter the date you want to make an appointment:");
    }

    /**
     * This method displays the Time selection screen
     *
     */
    public int displayTimeSelectMenu(ArrayList<String[]> timeList, int appointmentType) {
        displayPatientHeadMenu();
        System.out.println("*******************");
        System.out.println("Time Selection Menu");
        System.out.println("*******************");
        System.out.println();
        System.out.println("******Please choose the time that suits you (only numbers are accepted).******");
        System.out.println();
        int num = 0;
        for (String[] time: timeList) {
            System.out.printf("     " + ++num + ". " + time[0] + "-" + time[1] + " (%d minutes)" , appointmentType);
            System.out.println();
        }
        System.out.println();
        System.out.println("     0. Back to the previous screen ");
        System.out.println();
        System.out.println("Please enter your option:");
        return timeList.size();
    }

    /**
     * This method displays the Questionnaire screen
     *
     */
    public void displayQuestionnaireMenu(int questionNum) {
        displayPatientHeadMenu();
        System.out.println("******************");
        System.out.println("Questionnaire Menu");
        System.out.println("******************");
        System.out.println();
        System.out.println("******According to the latest policy, you need to be screened for COVID before making an appointment.******");
        System.out.println();
        System.out.printf("This is question %d/3.", questionNum);
        System.out.println();
        System.out.println();
        System.out.println("******Please enter the option (only numbers are accepted).******");
        System.out.println();
    }

    /**
     * This method displays the Questionnaire error message screen
     *
     */
    public void displayErrorMessage() {
        displayPatientHeadMenu();
        System.out.println("******************");
        System.out.println("Questionnaire Menu");
        System.out.println("******************");
        System.out.println();
        System.out.println("******ERROR: According to the screening results, you do not meet the appointment conditions.******");
        System.out.println();
        System.out.println("******ERROR: Please search on \"health.gov.au\" and attend a free COVID-19 respiratory clinic.******");
        System.out.println();
        System.out.println("******ERROR: You can't make an appointment. Press Enter to return to the main menu.******");
        Scanner input = new Scanner(System.in);
        input.nextLine().trim();
    }

    /**
     * This method displays the Confirmation screen
     *
     */
    public int displayConfirmationMenu(String[] confirmationInformation) {
        displayPatientHeadMenu();
        System.out.println("*****************");
        System.out.println("Confirmation Menu");
        System.out.println("*****************");
        System.out.println();
        System.out.println("******Please confirm your appointment information (only numbers are accepted). ******");
        System.out.println();
        System.out.printf("     Date time: %s %s", confirmationInformation[0], confirmationInformation[1]);
        System.out.println();
        System.out.printf("     GP: %s", confirmationInformation[2]);
        System.out.println();
        /*System.out.printf("     Patient status: %s", confirmationInformation[3]);
        System.out.println();*/
        System.out.printf("     Type of appointment: %s", confirmationInformation[4]);
        System.out.println();
        System.out.printf("     Location: %s", confirmationInformation[5]);
        System.out.println();
        System.out.println();
        System.out.println("******Please enter the option (only numbers are accepted).******");
        System.out.println();
        System.out.println("     1. Confirm");
        System.out.println("     0. Back to the previous screen ");
        System.out.println();
        System.out.println("Please enter your option:");
        return 1;
    }

    /**
     * This method displays the successful appointment screen
     *
     */
    public void displayAppointmentSuccessMenu() {
        displayPatientHeadMenu();
        System.out.println("*****************");
        System.out.println("Confirmation Menu");
        System.out.println("*****************");
        System.out.println();
        System.out.println("******The appointment is successful. ****** ");
        System.out.println();
        System.out.println("******Press Enter to return to the main menu.******");
        Scanner input = new Scanner(System.in);
        input.nextLine().trim();
    }

    /**
     * This method displays the Appointment List screen
     *
     */
    public int displayAppointmentListMenu(ArrayList<String[]> appointmentList) {
        displayCheckMenu();
        System.out.println("****************");
        System.out.println("Appointment List");
        System.out.println("****************");
        System.out.println();
        System.out.println("******Your current appointments as below. *****");
        System.out.println("******Please select the corresponding option to view the current queue or cancel the appointment (only numbers are accepted). ******");
        System.out.println();
        int num = 0;
        for (String[] appointmentInformation: appointmentList) {
            System.out.println("     " + ++num + ". -----------");
            System.out.printf("     Date time: %s %s", appointmentInformation[0], appointmentInformation[1]);
            System.out.println();
            System.out.printf("     GP: %s", appointmentInformation[2]);
            System.out.println();
            System.out.printf("     Patient status: %s", appointmentInformation[3]);
            System.out.println();
            System.out.printf("     Type of appointment: %s", appointmentInformation[4]);
            System.out.println();
            System.out.printf("     Location: %s", appointmentInformation[5]);
            System.out.println();
            System.out.println();
        }
        System.out.println("     0. Back to the previous screen ");
        System.out.println();
        System.out.println("Please enter your option:");
        return appointmentList.size();
    }

    /**
     * This method displays the appointment operation screen
     *
     */
    public int displayCheckAppointmentMenu() {
        displayCheckMenu();
        System.out.println("*********************");
        System.out.println("Appointment Operation");
        System.out.println("*********************");
        System.out.println();
        System.out.println("Please enter the option (only numbers are accepted).");
        System.out.println();
        System.out.println("     1. Check in");
        System.out.println("     2. View current queue");
        System.out.println("     3. Cancel appointment");
        System.out.println("     0. Back to the previous screen");
        System.out.println();
        System.out.println("Please enter your option:");
        return 3;
    }

    /**
     * This method displays the Successful Check-In screen
     *
     */
    public void displaySuccessfulCheckIn() {
        displayCheckMenu();
        System.out.println("*********************");
        System.out.println("Appointment Operation");
        System.out.println("*********************");
        System.out.println();
        System.out.println("******You have checked in successfully. ****** ");
        System.out.println();
        System.out.println("     ********Press Enter to return to the previous screen********");
        Scanner input = new Scanner(System.in);
        input.nextLine().trim();
    }

    /**
     * This method displays the Current Queue screen
     *
     */
    public void displayCurrentQueue() {
        displayCheckMenu();
        System.out.println("*************");
        System.out.println("Queue Display");
        System.out.println("*************");
        System.out.println();
        System.out.println("******Current queue information. ****** ");
        System.out.println();
    }

    /**
     * This method displays the Successful Cancel Appointment screen
     *
     */
    public void displayCancelAppointment() {
        displayCheckMenu();
        System.out.println("*********************");
        System.out.println("Appointment Operation");
        System.out.println("*********************");
        System.out.println();
        System.out.println("******You have successfully cancelled this appointment. ****** ");
        System.out.println();
        System.out.println("     ********Press Enter to return to the previous screen********");
        Scanner input = new Scanner(System.in);
        input.nextLine().trim();
    }

    /**
     * Login interface display
     *
     */
    public void displayHomeScreen(){
        System.out.println();
        System.out.println();
        System.out.println("     ||==================================||");
        System.out.println("     || Monash Patient Management System ||");
        System.out.println("     ||==================================||");
        System.out.println("");
        System.out.println("**************************************************");
        System.out.println("Welcome to Monash Patient Management System (MPMS)");
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("******Please enter the option (only numbers are accepted).******");
        System.out.println();
        System.out.println("     1. Login");
        //System.out.println("     2. Create new account");
        System.out.println("     2. Exit");
        System.out.println();
        System.out.println("please enter your option: ");
    }

    /**
     * Enter account interface display
     * This is the interface for users to enter mailbox and password
     *
     */
    public void displayLoginScreen(){
        System.out.println();
        System.out.println();
        System.out.println("     ||=========================||");
        System.out.println("     ||        User login       ||");
        System.out.println("     ||=========================||");
        System.out.println("");
        System.out.println("**************************************************");
        System.out.println("Welcome to Monash Patient Management System (MPMS)");
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("******Please enter your login credentials.******");
        System.out.println("******If you want to back to the previous interface, please just enter the number \"0\".******");
        System.out.println();
    }

    /**
     * This method displays the login failure message
     *
     */
    public void displayLoginFailureMessage() {
        System.out.println("     !!!User credential incorrect!!!");
        System.out.println();
        System.out.println("******Press Enter to return to the login screen.******");
    }

    /**
     * This method displays the administrator main screen
     *
     */
    public void displayAdministratorMenu() {
        System.out.println("     ||=========================||");
        System.out.println("     || Administrator management||");
        System.out.println("     ||=========================||");
        System.out.println();
        System.out.printf("--- Welcome %s to Monash Patient Management System (MPMS) ---", accountEmail);
        System.out.println("");
        System.out.println("     --- Account type: Administrator");
        System.out.println("***********************");
        System.out.println("Administrator Main Menu");
        System.out.println("***********************");
        System.out.println();
        System.out.println("******Please enter the option (only numbers are accepted).******");
        System.out.println();
        /*System.out.println("     1. Clinic Management");
        System.out.println("     2. Create new account");
        System.out.println("     3. GP Management");
        System.out.println("     4. Covid Alert Questionnaire Management");*/
        System.out.println("     1. Status dashboard");
        System.out.println("     2. Exit");
        System.out.println("     0. Log out of current user");
        System.out.println();
        System.out.println("Please enter your option:");
    }

    /**
     * This method displays the Status Dashboard Head Section
     *
     */
    public void displayStatusDashboard() {
        System.out.println();
        System.out.println();
        System.out.println("     ||=========================||");
        System.out.println("     || Administrator Management||");
        System.out.println("     ||=========================||");
        System.out.println();
        System.out.printf("--- Welcome %s to Monash Patient Management System (MPMS) ---", accountEmail);
        System.out.println("");
        System.out.println("     --- Account type: Administrator");
        System.out.println("***************");
        System.out.println("StatusDashboard");
        System.out.println("***************");
        System.out.println();
    }

    /**
     * This method displays the report selection screen
     *
     */
    public void displayReportMenu() {
        displayStatusDashboard();
        System.out.println("******Please select the type of report you want to generate (only numbers are accepted).******");
        System.out.println();
        System.out.println("     1. The percentages of reasons for patient appointments during a period");
        System.out.println("     0. Back to main menu");
        System.out.println();
        System.out.println("Please enter your option: ");
    }

    /**
     * This method displays the report start date entry screen
     *
     */
    public void displayReportStartDate() {
        SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
        displayStatusDashboard();
        System.out.println("******Please enter the starting date of the report generated (only date format in dd/mm/yyyy are accepted)");
        System.out.println(" e.g. " + ft.format(new Date()) + ". ******");
        System.out.println("******If you want to back to the previous interface, please just enter the number \"0\".******");
        System.out.println();
        System.out.println("Starting date: ");
    }

    /**
     * This method displays the report end date entry screen
     *
     */
    public void displayReportEndDate() {
        SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
        displayStatusDashboard();
        System.out.println("******Please enter the ending date of the report generated (only date format in dd/mm/yyyy are accepted)");
        System.out.println(" e.g. " + ft.format(new Date()) + ". ******");
        System.out.println("******If you want to back to the previous interface, please just enter the number \"0\".******");
        System.out.println();
        System.out.println("Ending date: ");
    }

    /**
     * This method displays the report confirmation screen
     *
     */
    public void displayReportConfirmation(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        displayStatusDashboard();
        System.out.println("******Please confirm the report information.******");
        System.out.println();
        System.out.printf("     Starting date: %s", startDate.format(df));
        System.out.println();
        System.out.printf("     Ending date: %s", endDate.format(df));
        System.out.println();
        System.out.println();
        System.out.println("******Please enter the option (only numbers are accepted).******");
        System.out.println();
        System.out.println("     1. Confirm");
        System.out.println("     0. Back to the previous screen ");
        System.out.println();
        System.out.println("Please enter your option:");
    }

    /**
     * This method displays the appointment report screen
     *
     */
    public void displayAppointmentReport(LocalDate startDate, LocalDate endDate, double[] appointmentPercentage) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        NumberFormat nf = new DecimalFormat("#0.00");
        displayStatusDashboard();
        System.out.println("******Report is generated as below!******");
        System.out.println();
        System.out.printf("Starting date: %s", startDate.format(df));
        System.out.println();
        System.out.printf("Ending date: %s", endDate.format(df));
        System.out.println();
        System.out.println("Number of appointment: " + (int)appointmentPercentage[0]);
        System.out.println("Percentages of reasons for patient appointments:");
        System.out.println("     Standard Consultation Face to Face - " + nf.format(appointmentPercentage[1]) + "%");
        System.out.println("     Telehealth (Video / Phone consult) - " + nf.format(appointmentPercentage[2]) + "%");
        System.out.println("     Long consultation face to face - " + nf.format(appointmentPercentage[3]) + "%");
        System.out.println();
        System.out.println();
        System.out.println("******Press Enter to return to the main menu.******");
    }
}
