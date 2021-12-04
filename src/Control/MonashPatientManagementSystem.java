package Control;

import Boundary.UserInterface;
import Model.MonashPatientManagement;
import Model.Questionnaire;
import Model.Report;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.*;

public class MonashPatientManagementSystem {

    private UserInterface userInterface;
    private MonashPatientManagement monashPatientManagement;
    private Questionnaire questionnaire;
    private Report report;
    private boolean backMainMenu;
    private LocalDate date;
    private LocalTime time;
    private String address;
    private int appointmentTypeIndex;
    private String gp;
    private String patientStatus;
    private String userEmail;

    /**
     * Default constructor for objects of class MonashPatientManagementSystem
     *
     */
    public MonashPatientManagementSystem() {
        monashPatientManagement = new MonashPatientManagement();
        userInterface = new UserInterface();
        questionnaire = new Questionnaire();
        report = new Report();
        backMainMenu = false;
        address = "";
        gp = "";
        patientStatus = "";
        time = LocalTime.of(00, 00);
        date = LocalDate.now();
        appointmentTypeIndex = 0;
        userEmail = "";
        login();
    }

    /**
     * main menu
     * The screen displayed after the user logs in
     *
     */
    public void PatientMainMenu() {
        boolean exitSystem = false;
        do
        {
            backMainMenu = false;
            int option = inputVerification(userInterface.displayPatientMenu());
            exitSystem = patientMainMenuOption(option);
        }while (!exitSystem);
        //Write information to the database when the program exits
        monashPatientManagement.writeAppointmentFile("data/AppointmentDatabase.txt");
        monashPatientManagement.writeQueueFile("data/QueueDatabase.txt");
    }

    /**
     * Options corresponding to the main menu
     *
     * @param option: Incoming user input options
     * @return boolean: Return value to determine whether to return to the previous interface
     */
    public boolean patientMainMenuOption(int option) {
        switch (option)
        {
            case 1:
                clinicMenu(); break;
            case 2:
                AppointmentListMenu(); break;
            case 0:
                login(); return true;
            case 3:
                return true;
            default:
                System.out.println("no this option"); break;
        }
        return false;
    }

    /**
     * This method is responsible for calling the display and options related methods
     *
     */
    public void AppointmentListMenu() {
        boolean backPreviousMenu = false;
        do
        {
            // Pass the appointment details to UserInterface for display
            ArrayList<String[]> appointmentList = monashPatientManagement.showAppointmentDetail(userEmail);
            int option = MonashPatientManagementSystem.inputVerification(userInterface.displayAppointmentListMenu(appointmentList));
            if (option != 0) {
                checkAppointmentMenu(option - 1);
                backPreviousMenu = false;
            }
            else {
                backPreviousMenu = true;
            }
        }while (!backPreviousMenu);
    }

    /**
     * This method is responsible for calling the related methods of check appointment
     *
     * @param appointmentOption: Appointment order selected in the previous method
     */
    public void checkAppointmentMenu(int appointmentOption) {
        boolean backPreviousMenu = false;
        do
        {
            int option = MonashPatientManagementSystem.inputVerification(userInterface.displayCheckAppointmentMenu());
            backPreviousMenu = checkAppointmentOptions(appointmentOption, option);
        }while (!backPreviousMenu);
    }

    /**
     * This method is responsible for calling the check appointment operation
     *
     * @param appointmentOption: Appointment order selected in the previous method
     * @param option: function option selected in the previous method
     */
    public boolean checkAppointmentOptions(int appointmentOption, int option) {
        switch (option)
        {
            case 1:
                checkInAppointment(appointmentOption); break; //displayAppointmentMenu(); break;
            case 2:
                viewCurrentQueue(appointmentOption); break;
            case 3:
                monashPatientManagement.cancelAppointment(appointmentOption, userEmail);
                userInterface.displayCancelAppointment();
                return true;
            case 0:
                return true;
            default:
                System.out.println("no this option"); break;
        }
        return false;
    }

    /**
     * This method is used to view the current appointment
     *
     * @param appointmentOption: Appointment order selected in the previous method
     */
    public void viewCurrentQueue(int appointmentOption) {
        userInterface.displayCurrentQueue();
        monashPatientManagement.findQueueByClinic(monashPatientManagement.showAppointmentDetail(userEmail).get(appointmentOption)[5], userEmail);
        System.out.println("     ********Press Enter to return to the previous page********");
        Scanner input = new Scanner(System.in);
        input.nextLine().trim();
    }

    /**
     * This method is used to check in the current appointment
     *
     * @param appointmentOption: Appointment order selected in the previous method
     */
    public void checkInAppointment(int appointmentOption) {
        Scanner input = new Scanner(System.in);
        String[] checkAppointment = monashPatientManagement.showAppointmentDetail(userEmail).get(appointmentOption);
        // The following conditional nested statement is used to determine
        // whether the current appointment meets the check-in conditions
        if (monashPatientManagement.checkIfNoCheckIn(checkAppointment[5], userEmail)) {
            if (LocalDate.parse(checkAppointment[0]).equals(LocalDate.now())) {
                if (LocalTime.now().isBefore(LocalTime.parse(checkAppointment[1]).minusMinutes(10))) {
                    System.out.printf("      ******** The appointment you selected is not open for check-in, please try again after %s today. ********", LocalTime.parse(checkAppointment[1]).minusMinutes(10).toString());
                    System.out.println();
                    System.out.println("     ********Press Enter to return to the previous screen ********");
                    input.nextLine().trim();
                }
                else if (LocalTime.now().isAfter(LocalTime.parse(checkAppointment[1]))) {
                    System.out.println("      ********Sorry, your appointment is late for check-in. Come earlier next time.********");
                    System.out.println();
                    System.out.println("     ********Press Enter to return to the previous screen ********");
                    input.nextLine().trim();
                }
                else {
                    successfulCheckIn(checkAppointment[5]);
                }
            }
            else if (LocalDate.parse(checkAppointment[0]).isAfter(LocalDate.now())) {
                System.out.printf("      ********The appointment you selected is not open for check-in. \nPlease try again after %s on the %s.********", LocalTime.parse(checkAppointment[1]).minusMinutes(10).toString(), checkAppointment[0]);
                System.out.println();
                System.out.println("     ********Press Enter to return to the previous screen ********");
                input.nextLine().trim();
            }
            else {
                System.out.println("      ********The appointment you selected has expired, please make a new appointment.********");
                System.out.println();
                System.out.println("     ********Press Enter to return to the previous screen ********");
                input.nextLine().trim();
            }
        }
        else {
            System.out.println("      ********You have a waiting appointment in this clinic. Please do not double check in.********");
            System.out.println();
            System.out.println("     ********Press Enter to return to the previous screen ********");
            input.nextLine().trim();
        }

    }

    /**
     * This method will be run only when the current appointment meets the check-in conditions.
     * This method is responsible for calling the check-in method and adding the current appointment to the queue.
     *
     * @param clinicAddress: Confirm which queue to add the current appointment to by comparing clinicAddress.
     */
    public void successfulCheckIn(String clinicAddress) {
        int index = 0;
        for (String clinicAppointmentAddress: monashPatientManagement.getQueueClinicList()) {
            // If the current queue contains current clinic information
            if (clinicAppointmentAddress.equals(clinicAddress)) {
                // Insert the current appointment into the existing queue
                monashPatientManagement.insertQueue(monashPatientManagement.findByEmail(userEmail), index);
                break;
            }
            index ++;
        }
        // If all cohorts do not have current clinic information
        if (index == monashPatientManagement.getQueueList().size()) {
            // Create a new queue and put the current appointment into it
            monashPatientManagement.addNewQueue(monashPatientManagement.findClinicByAddress(clinicAddress),monashPatientManagement.findByEmail(userEmail));
        }
        userInterface.displaySuccessfulCheckIn();
    }

    /**
     * This method is responsible for calling the relevant methods shown by clinic
     *
     */
    public void clinicMenu() {
        boolean backPreviousMenu = false;
        do {
            ArrayList<String[]> addressList = monashPatientManagement.findClinicByAddress();
            int option = inputVerification(userInterface.displayClinicMenu(addressList));
            if (option != 0) {
                address = addressList.get(option - 1)[1];
                gpMenu();
                backPreviousMenu = false;
            }
            else {
                backPreviousMenu = true;
            }
        }while(!backPreviousMenu && !backMainMenu);
    }

    /**
     * This method is responsible for displaying the GP list and determining gp
     *
     */
    public void gpMenu() {
        boolean backPreviousMenu = false;
        do
        {
            ArrayList<ArrayList<String>> gpDetails = monashPatientManagement.gpDetailsMenu(address);
            int option = inputVerification(userInterface.displayGpMenu(gpDetails));
            if (option != 0) {
                // Patients choose random gp
                if (option == gpDetails.size() + 1) {
                    gp = null;
                }
                // The patient chooses a definite gp
                else {
                    gp = gpDetails.get(option - 1).get(0);
                }
                patientStateMenu();
                backPreviousMenu = false;
            }
            else {
                backPreviousMenu = true;
            }
        }while (!backPreviousMenu && !backMainMenu);
    }

    /**
     * This method is responsible for calling the relevant methods of the patient state
     *
     */
    public void patientStateMenu() {
        boolean backPreviousMenu = false;
        do
        {
            int option = inputVerification(userInterface.displayPatientStateMenu());
            backPreviousMenu = patientStateOptions(option);
        }while (!backPreviousMenu && !backMainMenu);
    }

    /**
     * This method is responsible for making judgments about the user's choice
     *
     * @return boolean: Used to determine whether to return to the previous interface
     */
    public boolean patientStateOptions(int option) {
        switch (option)
        {
            case 1:
                patientStatus = "Existing Patient"; break;
            case 2:
                patientStatus = "New Patient"; break;
            case 0:
                return true;
            default:
                System.out.println("no this option"); break;
        }
        appointmentTypeMenu();
        return false;
    }

    /**
     * This method is responsible for invoking related operations to show the appointment type
     *
     */
    public void appointmentTypeMenu() {
        boolean backPreviousMenu = false;
        do
        {
            int option = inputVerification(userInterface.displayAppointmentTypeMenu());
            backPreviousMenu = appointmentTypeOptions(option);
        }while (!backPreviousMenu && !backMainMenu);
    }

    /**
     * This method is responsible for making judgments about the user's choice
     *
     * @return boolean: Used to determine whether to return to the previous interface
     */
    public boolean appointmentTypeOptions(int option) {
        switch (option)
        {
            case 1:
                appointmentTypeIndex = 0; break;
            case 2:
                appointmentTypeIndex = 1; break;
            case 3:
                appointmentTypeIndex = 2; break;
            case 0:
                return true;
            default:
                System.out.println("no this option"); break;
        }
        dateEnterMenu();
        return false;
    }

    /**
     * This method is responsible for receiving and judging whether the user's input is valid
     *
     */
    public void dateEnterMenu() {
        boolean backPreviousMenu = false;
        do
        {
            userInterface.displayDateEnterMenu();
            backPreviousMenu = dateEnterOptions();
        }while (!backPreviousMenu && !backMainMenu);
    }

    /**
     * This method is responsible for making judgments about the user's choice
     *
     * @return boolean: Used to determine whether to return to the previous interface
     */
    public boolean dateEnterOptions() {
        boolean err = false;
        Scanner input = new Scanner(System.in);
        do
        {
            String dateInput = input.nextLine().trim();
            if (verifyBack(dateInput) == true) {
                return true;
            }
            if (verifyBack(dateInput) == false) {
                err = verifyDate(dateInput);
            }
        }while (err);
        System.out.println();
        timeSelectMenu();
        return false;
    }

    /**
     * This method is responsible for calling the list of time that gp can be appointment,
     * and the related operations of calling the selected time
     *
     */
    public void timeSelectMenu() {
        boolean backPreviousMenu = false;
        do
        {
            // If the patient did not select GP before, then GP will be automatically assigned to the patient here
            // Allocation principle: allocate according to the least appointment on the day
            if (gp == null) {
                ArrayList<String> gps = monashPatientManagement.showGpByClinic(address);
                gp = gps.get(0);
                // Set the first gp as preference
                int min = monashPatientManagement.getAppointmentListByDate(date, gp).size();
                for (String gpName: gps) {
                    // Compare the size of the current gp and the next gp reservation
                    int appointmentNum = monashPatientManagement.getAppointmentListByDate(date, gpName).size();
                    if (min > appointmentNum) {
                        System.out.println("gp" + gp);
                        System.out.println(gpName);
                        gp = gpName;
                        min = appointmentNum;
                    }
                }
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            ArrayList<String[]> timeList = showAvailableTime();
            // Pass the free time list to UserInterface for display and receive user input
            int option = inputVerification(userInterface.displayTimeSelectMenu(timeList, monashPatientManagement.getAppointmentTypeDuration(appointmentTypeIndex)));
            if (option != 0) {
                time = LocalTime.parse(timeList.get(option - 1)[0], formatter);
                questionnaireMenu();
                backPreviousMenu = false;
            }
            else {
                backPreviousMenu = true;
            }
        }while (!backPreviousMenu && !backMainMenu);
    }

    /**
     * This method is responsible for calling the related operations of the questionnaire display
     *
     */
    public void questionnaireMenu() {
        int questionNum = 0;
        while ((questionNum < 3) && (questionNum >= 0) && !backMainMenu) {
            userInterface.displayQuestionnaireMenu(questionNum + 1);
            int option = showQuestionnaire(questionNum);
            if (option == 1) {
                ErrorMessageMenu();
            }
            else if (option == 2) {
                questionNum ++;
            }
            else if (option == 0) {
                questionNum --;
            }
        }
        if (questionNum == 3) {
            confirmationMenu();
        }
    }

    /**
     * This method is responsible for calling the information confirmation page
     *
     */
    public void confirmationMenu() {
        int option = MonashPatientManagementSystem.inputVerification(userInterface.displayConfirmationMenu(showConfirmationInformation()));
        if (option == 1) {
            appointmentSuccessMenu();
        }
    }

    /**
     * This method is responsible for calling the appointment success interface
     *
     */
    public void appointmentSuccessMenu() {
        addAppointment();
        userInterface.displayAppointmentSuccessMenu();
        backMainMenu = true;
    }

    /**
     * When the reservation information is confirmed, this method will be called.
     * This method is responsible for saving the reservation information made by the current user.
     *
     */
    public void addAppointment() {
        monashPatientManagement.addAppointment(userEmail, address, gp, patientStatus, appointmentTypeIndex, date, time);
    }

    /**
     * This method is used to convert the confirmation information into a list of strings
     *
     * @return confirmationInformation: Current appointment information
     */
    public String[] showConfirmationInformation() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String[] confirmationInformation = new String[6];
        confirmationInformation[0] = date.format(formatters);
        confirmationInformation[1] = time.toString();
        confirmationInformation[2] = gp;
        confirmationInformation[3] = patientStatus;
        confirmationInformation[4] = monashPatientManagement.getAppointmentTypeDescription(appointmentTypeIndex);
        confirmationInformation[5] = address;
        return confirmationInformation;
    }

    /**
     * This method is responsible for delivering warning messages
     *
     */
    public void ErrorMessageMenu() {
        backMainMenu = true;
        userInterface.displayErrorMessage();
    }

    /**
     * This method is responsible for printing the content of the question
     *
     */
    public int showQuestionnaire(int questionNum) {
        questionnaire.printQuestionnaire(questionNum);
        System.out.println();
        System.out.println("Please enter your option:");
        int option = inputVerification(2);
        return option;
    }

    /**
     * Core code!!!
     * The soul of this Assignment 3A!!! Although it is very bad.
     *
     * This method is responsible for displaying the free time of the current GP on a specific date.
     * This method reads the appointment information in AppointmentDatabase.
     * Find the appointment information of the current GP and Date.
     * Then use the opening time of the clinic where the GP is located to calculate the final free schedule.
     *
     * Calculation method: (I actually don't want to say ^_^, it is too long)
     *          First obtain the clinic's opening time clinicOpeningTime and clinicClosingTime.
     *          Then get all the appointment records of the current GP on this day from AppointmentDatabase.
     *          Traverse all the appointment times, each traversal will do the following calculations:
     *                  Compare the current free time boundary with the start and end time of the current appointment.
     *                  If the appointment time is in the free time, make the following judgments:
     *                  if:
     *                    The start time of the appointment recording is on the right side of the left boundary of the free time.
     *                    And the end time of the appointment recording is on the left side of the right boundary of the free time.
     *                    Get two new free time.
     *                    The start time of the free time on the left is the left boundary of the parent free time,
     *                    and the end time is the start time of the appointment recording.
     *                    The end time of the free time on the right is the right boundary of the parent free time,
     *                    and the start time is the end time of the appointment record.
     *                  if:
     *                    The start time of the appointment recording is on the right side of the left boundary of the free time.
     *                    And the end time of the appointment recording is the same as the right boundary of the free time.
     *                    Get one new free time.
     *                    The start time of the free time is the left boundary of the parent free time,
     *                    and the end time is the start time of the appointment recording.
     *                  if:
     *                    The end time of the appointment recording is on the left side of the right boundary of the free time.
     *                    And the start time of the appointment recording is the same as the left boundary of the free time.
     *                    Get one new free time.
     *                    The end time of the free time is the right boundary of the parent free time,
     *                    and the state time is the end time of the appointment recording.
     *                  else:
     *                    Do the next traversal.
     *          Divide the free time obtained in the previous step.
     *          The segmentation interval is the time of the patient's appointment type.
     *                  Traverse the free time list.
     *                  if:
     *                    The free time interval is greater than the appointment type.
     *                    Divide the free time interval by the appointment type, and take the quotient of the result.
     *                    Save each segment of free time that is longer than the appointment type time,
     *                    and discard the free time that is less than the appointment type time.
     *                  if:
     *                    The free time is just as long as the appointment type time. Save the current idle time.
     *                  else:
     *                    Do the next traversal.
     *          Arrange all the free time according to the size of the starting time,
     *          and format it into String type for output.
     *
     * @return timeListString: String [] type ArrayList, which saves the free time of the current gp. Ability to print directly.
     *
     */
    public ArrayList<String[]> showAvailableTime() {
        ArrayList<LocalTime[]> timeList= new ArrayList();
        LocalTime clinicOpeningTime = monashPatientManagement.findClinicByAddress(address).getClinicOpeningTime();
        LocalTime clinicClosingTime = monashPatientManagement.findClinicByAddress(address).getClinicClosingTime();
        timeList.add(new LocalTime[]{clinicOpeningTime, clinicClosingTime});
        for (LocalTime[] startAndEndTime: monashPatientManagement.getOpeningTimeByDateAndGp(date, gp)) {
            for (LocalTime[] interval: timeList) {
                if ((interval[0].isBefore(startAndEndTime[0]) || interval[0].equals(startAndEndTime[0])) &&
                        (interval[1].isAfter(startAndEndTime[1]) || interval[1].equals(startAndEndTime[1]))) {
                    if (interval[0].isBefore(startAndEndTime[0]) && interval[1].isAfter(startAndEndTime[1])) {
                        timeList.remove(interval);
                        timeList.add(new LocalTime[]{interval[0], startAndEndTime[0]});
                        timeList.add(new LocalTime[]{startAndEndTime[1], interval[1]});
                        break;
                    }
                    else if (interval[0].equals(startAndEndTime[0]) && interval[1].isAfter(startAndEndTime[1])) {
                        timeList.remove(interval);
                        timeList.add(new LocalTime[]{startAndEndTime[1], interval[1]});
                        break;
                    }
                    else if (interval[0].isBefore(startAndEndTime[0]) && interval[1].equals(startAndEndTime[1])) {
                        timeList.remove(interval);
                        timeList.add(new LocalTime[]{interval[0], startAndEndTime[0]});
                        break;
                    }
                    else if (interval[0].equals(startAndEndTime[0]) && interval[1].equals(startAndEndTime[1])) {
                        timeList.remove(interval);
                        break;
                    }
                }
            }

        }
        ArrayList<LocalTime[]> availableTimeList= new ArrayList();
        for (LocalTime[] interval: timeList) {
            LocalTime startTime = interval[0];
            LocalTime endTime = interval[1];
            int duration = (endTime.getHour() - startTime.getHour())*60 - startTime.getMinute() + endTime.getMinute();
            int partition = duration / monashPatientManagement.getAppointmentTypeDuration(appointmentTypeIndex);
            if (partition >= 1) {
                LocalTime appointmentStartTime = startTime;
                for (int i = 0; i < partition; i ++) {
                    LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(monashPatientManagement.getAppointmentTypeDuration(appointmentTypeIndex));
                    if (appointmentStartTime.isAfter(LocalTime.now()) && date.equals(LocalDate.now())) {
                        availableTimeList.add(new LocalTime[]{appointmentStartTime, appointmentEndTime});
                    }
                    else if (date.isAfter(LocalDate.now())) {
                        availableTimeList.add(new LocalTime[]{appointmentStartTime, appointmentEndTime});
                    }
                    appointmentStartTime = appointmentEndTime;
                }
            }
        }
        ArrayList<String[]> timeListString = new ArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        for (LocalTime[] interval: availableTimeList) {
            timeListString.add(new String[]{interval[0].format(formatter), interval[1].format(formatter)});
        }
        Collections.sort(timeListString, (t1, t2) -> (t1[0].compareTo(t2[0])));
        return timeListString;
    }

    /**
     * This method is used to verify whether the entered content is a number and within the required range,
     * and if true, returns the currently entered number type. Otherwise, an error message is displayed.
     *
     * @param num: The range of numbers to be verified.
     * @return option: The integer type of the content entered by the user.
     */
    public static int inputVerification(int num) {
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine().trim();
        System.out.println();
        while (choice.length() > String.valueOf(num).length() ||
                !inputIsDigit(choice) ||
                Integer.parseInt(choice) < 0 ||
                Integer.parseInt(choice) > num) // Verify that the input meets the requirements
        {
            System.out.printf("Error: You have to enter a number, and it's between 0-%d", num);
            System.out.println();
            System.out.println("Please enter your option:");
            choice = input.nextLine().trim();
            System.out.println();
        }

        int option = Integer.parseInt(choice);
        return option;
    }

    /**
     * This method is used to determine whether the user's input is a pure number.
     *
     * @param choice: User input
     * @return boolean: Judgment result entered by the user.
     */
    public static boolean inputIsDigit(String choice) {
        if (choice.length() == 0)
            return false;
        for (int i = 0; i < choice.length(); i++)
        {
            if (!Character.isDigit(choice.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * Verify whether the user input is 0, which is used when receiving irregular input.
     *
     * @param input: User input
     * @return boolean: Whether to return
     */
    public boolean verifyBack(String input) {
        if ((input.length() == 1) && (inputIsDigit(input) == true) && (Integer.parseInt(input) == 0)){
            return true;
        }
        return false;
    }

    /**
     * Determine whether the input content is a legal date.
     *
     * @param dateInput: User input
     * @return boolean: Whether it is a legal date.
     */
    public boolean verifyDate(String dateInput) {
        SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
        date = isValidDate(dateInput);
        if (date == null) {
            return true;
        }
        if (!date.isAfter(LocalDate.now()) && !date.equals(LocalDate.now())) {
            System.out.println("******Error: Appointment dates should be later than the current date, today is: " + ft.format(new Date()) + "******");
            System.out.println("******Please enter the appointment date (e.g. " + ft.format(new Date()) + ") or \"0\".******");
            return true;
        }
        return false;
    }

    /**
     * verify whether the date entered is a valid date
     *
     * @param dateInput: Date entered by user
     * @return LocalDate: a valid date if input is valid; null if input is invalid
     */
    public LocalDate isValidDate(String dateInput) {
        SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if ((dateInput.length() != 10) ||
                (dateInput.charAt(2) != '-') ||
                (dateInput.charAt(5) != '-') ||
                (!Character.isDigit(dateInput.charAt(0))) ||
                (!Character.isDigit(dateInput.charAt(1))) ||
                (!Character.isDigit(dateInput.charAt(3))) ||
                (!Character.isDigit(dateInput.charAt(4))) ||
                (!Character.isDigit(dateInput.charAt(6))) ||
                (!Character.isDigit(dateInput.charAt(7))) ||
                (!Character.isDigit(dateInput.charAt(8))) ||
                (!Character.isDigit(dateInput.charAt(9)))) {
            System.out.println("******Error: You have entered the wrong date.******");
            System.out.println("******Please enter a valid date (e.g. " + ft.format(new Date()) + ") or \"0\".******");
            return null;
        }
        int month = Integer.parseInt(String.valueOf(dateInput.charAt(3)) + String.valueOf(dateInput.charAt(4)));
        int day = Integer.parseInt(String.valueOf(dateInput.charAt(0)) + String.valueOf(dateInput.charAt(1)));
        int year = Integer.parseInt(String.valueOf(dateInput.charAt(6)) +
                String.valueOf(dateInput.charAt(7)) +
                String.valueOf(dateInput.charAt(8)) +
                String.valueOf(dateInput.charAt(9)));
        if (month > 12)  {
            System.out.println("The month you entered is invalid, please enter again");
            return null;
        }
        List<Integer> bigMonths = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
        List<Integer> smallMonths = Arrays.asList(4, 6, 9, 11);
        if (bigMonths.contains(month)) {
            if (day > 31) {
                System.out.println("The date you entered is invalid, please enter again");
                return null;
            }
        }
        else if (smallMonths.contains(month)){
            if (day > 30) {
                System.out.println("The date you entered is invalid, please enter again");
                return null;
            }
        }
        if (month == 2) {
            if (year % 4 == 0) {
                if (day > 29) {
                    System.out.println("The date you entered is invalid, please enter again");
                    return null;
                }
            }
            else {
                if (day > 28) {
                    System.out.println("The date you entered is invalid, please enter again");
                    return null;
                }
            }
        }
        return LocalDate.parse(dateInput, df);
    }

    /**
     * login
     * The first part of the whole program, it is used to let users login.
     *
     * @return boolean   Did the program execute successfully
     */
    public boolean login(){
        outer:
        while (true) {
            if (!homeMenu()) {
                return false;
            }
            String email = "";
            String password = "";
            //This loop allows the user to enter the email repeatedly
            for (;;) {
                //The user enters the email and password, enters 0 to return to the previous level
                for (; ; ) {
                    userInterface.displayLoginScreen();
                    System.out.print("Email: ");
                    email = acceptUserInput();
                    if (email.equals("0")) {
                        continue outer;
                    }
                    System.out.print("Password: ");
                    password = acceptUserInput();
                    if (password.equals("0")) {
                        continue;
                    }
                    System.out.println();
                    break;
                }
                // Check whether the user is a administrator
                if (email.equals(monashPatientManagement.getAdministrator().getAccountEmail())
                        && password.equals(monashPatientManagement.getAdministrator().getAccountPassword())) {
                    userInterface.setAccountEmail(monashPatientManagement.getAdministrator().getAccountEmail());
                    adminMainMenu();
                    return true;
                }

                // Check whether the user is a patient
                for (int i = 0; i < monashPatientManagement.getAccountList().size(); i++) {
                    if (password.equals(monashPatientManagement.getAccountList().get(i).getAccountPassword())
                            && email.equals(monashPatientManagement.getAccountList().get(i).getAccountEmail())) {
                        userEmail = email;
                        userInterface.setAccountEmail(monashPatientManagement.getAccountList().get(i).getAccountEmail());
                        PatientMainMenu();
                        return true;
                    }
                }

                userInterface.displayLoginFailureMessage();
                acceptUserInput();
            }
        }
    }

    /**
     * Home menu
     * This is the home screen and the first interface of the whole software
     *
     * @return boolean   Whether to enter the software.
     */
    public boolean homeMenu() {
        boolean exit = false;
        userInterface.displayHomeScreen();
        while (!exit)
        {
            String userInput = acceptUserInput();
            try
            {
                int option = isValidOption(userInput, 2);
                switch (option)
                {
                    case 1:
                        return true;
                    case 2:
                        monashPatientManagement.writeAppointmentFile("data/AppointmentDatabase.txt");
                        monashPatientManagement.writeQueueFile("data/QueueDatabase.txt");
                        System.exit(0);
                    case 0:
                        System.out.println("Error: your choice must be between 1-2");
                        break;
                    default:
                        break;
                }
            }
            catch(IllegalArgumentException exception)
            {
                System.out.println(exception.getMessage());
            }
        }
        return false;
    }

    /**
     * Accept input from user
     *
     * @return String: user input
     */
    public String acceptUserInput()
    {
        Scanner console = new Scanner(System.in);
        String input = console.nextLine().trim();
        return input;
    }

    /**
     * Verify whether user input is a valid number or not
     *
     * @param userInput: a string represents user input
     * @return int: valid user input number
     * @throws IllegalArgumentException: if user input is not valid
     */
    public int isValidNumber(String userInput)
    {
        if (userInput.length() == 0 || userInput.length() > 10)
            throw new IllegalArgumentException("Error: Please enter a valid input");
        int index = 0;
        while (index < userInput.length())
        {
            char thisCharacter = userInput.charAt(index);
            if (!Character.isDigit(thisCharacter))
                throw new IllegalArgumentException("Error: Please enter a valid number");
            index++;
        }
        int inputNumber = Integer.parseInt(userInput);
        return inputNumber;
    }

    /**
     * Verify whether user input is a valid option or not
     *
     * @param userInput: a string represents user input
     * @return int: valid user input option
     * @throws IllegalArgumentException: if user input is not valid
     */
    public int isValidOption(String userInput, int optionNumber)
    {
        int option = isValidNumber(userInput);
        if (optionNumber == 1 && (option < 0 || option > optionNumber))
            throw new IllegalArgumentException("Error: your choice must be either 1 or 0");
        if (option < 0 || option > optionNumber)
            throw new IllegalArgumentException("Error: your choice must be between 1-" + optionNumber);
        return option;
    }

    /**
     * control screens displayed for each option in admin main menu
     *
     */
    public void adminMainMenu()
    {
        boolean exit = false;
        userInterface.displayAdministratorMenu();
        while (!exit)
        {
            String userInput = acceptUserInput();
            try
            {
                int option = isValidOption(userInput, 2);
                switch (option)
                {
                    case 1:
                        statusDashboard();
                        exit = true;
                        break;
                    case 2:
                        monashPatientManagement.writeAppointmentFile("data/AppointmentDatabase.txt");
                        monashPatientManagement.writeQueueFile("data/QueueDatabase.txt");
                        System.exit(0);
                    case 0:
                        login();
                        exit = true;
                        break;
                    default:
                        break;
                }
            }
            catch(IllegalArgumentException exception)
            {
                System.out.println(exception.getMessage());
            }
        }
    }

    /**
     * control screens displayed for each option in status dashboard menu
     *
     */
    public void statusDashboard() {
        boolean exit = false;
        userInterface.displayReportMenu();
        while (!exit)
        {
            String userInput = acceptUserInput();
            try
            {
                int option = isValidOption(userInput, 1);
                switch (option)
                {
                    case 1:
                        reportStartDate();
                        exit = true;
                        break;
                    case 0:
                        adminMainMenu();
                        exit = true;
                    default:
                        break;
                }
            }
            catch(IllegalArgumentException exception)
            {
                System.out.println(exception.getMessage());
            }
        }
    }

    /**
     * control screens displayed after entering report start date
     *
     */
    public void reportStartDate() {
        boolean exit = false;
        userInterface.displayReportStartDate();
        while (!exit)
        {
            String userInput = acceptUserInput();
            try
            {
                if (verifyBack(userInput) == true) {
                    statusDashboard();
                    exit = true;
                }
                else {
                    LocalDate startDate = isValidReportStartDate(userInput);
                    if (startDate != null) {
                        report.setReportStartDate(startDate);
                        reportEndDate(startDate);
                        exit = true;
                    }
                }
            }
            catch(IllegalArgumentException exception)
            {
                System.out.println(exception.getMessage());
            }
        }
    }

    /**
     * Verify whether the date input entered is a valid report start date
     *
     * @param startDateInput: User date input
     * @return LocalDate: a valid date if input is valid; null if input is invalid
     */
    public LocalDate isValidReportStartDate(String startDateInput) {
        SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
        LocalDate startDate = isValidDate(startDateInput);
        if (startDate != null && startDate.isAfter(LocalDate.now()) && !startDate.equals(LocalDate.now())) {
            System.out.println("******Error: Report start date should not be later than the current date, today is: " + ft.format(new Date()) + "******");
            System.out.println("******Please enter a valid report start date (e.g. " + ft.format(new Date()) + ") or \"0\".******");
            return null;
        }
        return startDate;
    }

    /**
     * control screens displayed after entering report end date
     *
     */
    public void reportEndDate(LocalDate startDate) {
        boolean exit = false;
        userInterface.displayReportEndDate();
        while (!exit)
        {
            String userInput = acceptUserInput();
            try
            {
                if (verifyBack(userInput) == true) {
                    reportStartDate();
                    exit = true;
                }
                else {
                    LocalDate endDate = isValidReportEndDate(userInput, startDate);
                    if (endDate != null) {
                        report.setReportEndDate(endDate);
                        reportConfirmation();
                        exit = true;
                    }
                }
            }
            catch(IllegalArgumentException exception)
            {
                System.out.println(exception.getMessage());
            }
        }
    }

    /**
     * Verify whether the date input entered is a valid report end date
     *
     * @param endDateInput: User date input
     * @return LocalDate: a valid date if input is valid; null if input is invalid
     */
    public LocalDate isValidReportEndDate(String endDateInput, LocalDate startDate) {
        SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate endDate = isValidDate(endDateInput);
        if (endDate != null && endDate.isAfter(LocalDate.now()) && !endDate.equals(LocalDate.now())) {
            System.out.println("******Error: Report start date should not be later than the current date, today is: " + ft.format(new Date()) + "******");
            System.out.println("******Please enter a valid report start date (e.g. " + ft.format(new Date()) + ") or \"0\".******");
            return null;
        }
        if (endDate != null && !endDate.isAfter(startDate) && !endDate.equals(startDate)) {
            System.out.println("******Error: Report end date should not be earlier than the report start date, report start date is: " + startDate.format(df) + "******");
            System.out.println("******Please enter a valid report start date (e.g. " + ft.format(new Date()) + ") or \"0\".******");
            return null;
        }
        return endDate;
    }

    /**
     * control screens displayed for each option in report confirmation screen
     *
     */
    public void reportConfirmation() {
        boolean exit = false;
        userInterface.displayReportConfirmation(report.getReportStartDate(), report.getReportEndDate());
        while (!exit)
        {
            String userInput = acceptUserInput();
            try
            {
                int option = isValidOption(userInput, 1);
                switch (option)
                {
                    case 1:
                        appointmentReport();
                        exit = true;
                        break;
                    case 0:
                        reportEndDate(report.getReportStartDate());
                        exit = true;
                    default:
                        break;
                }
            }
            catch(IllegalArgumentException exception)
            {
                System.out.println(exception.getMessage());
            }
        }
    }

    /**
     * This method is used to generate appointment report
     *
     */
    public void appointmentReport() {
        LocalDate startDate = report.getReportStartDate();
        LocalDate endDate = report.getReportEndDate();
        double[] appointmentPercentage = report.generateReport(monashPatientManagement.getAppointmentList(startDate, endDate));
        userInterface.displayAppointmentReport(startDate, endDate, appointmentPercentage);
        acceptUserInput();
        adminMainMenu();
    }
}


