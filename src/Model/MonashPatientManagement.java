package Model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class MonashPatientManagement {

    private ArrayList<Gp> gpList;
    private ArrayList<Appointment> appointmentList;
    private ArrayList<Clinic> clinicList;
    private ArrayList<Account> accountList;
    private ArrayList<Queue> queueList;
    private AppointmentType[] appointmentTypeList;

    /**
     * Default constructor for objects of class MonashPatientManagement
     *
     */
    public MonashPatientManagement() {
        gpList = new ArrayList<>();
        readGpFile("data/GpDatabase.txt");

        appointmentList = new ArrayList<>();

        clinicList = new ArrayList<>();
        readClinicFile("data/ClinicDatabase.txt");

        accountList = new ArrayList<>();
        readAccountFile("data/AccountDatabase.txt");

        queueList = new ArrayList<>();
        appointmentTypeList = new AppointmentType[3];
        appointmentTypeList[0] = new AppointmentType("Standard Consultation Face to Face (15 minutes)", 15);
        appointmentTypeList[1] = new AppointmentType("Telehealth (Video / Phone consult) (15 minutes)", 15);
        appointmentTypeList[2] = new AppointmentType("Long consultation face to face (30 minutes)", 30);

        readAppointmentDatabase("data/AppointmentDatabase.txt");
        readQueueDatabase("data/QueueDatabase.txt");
    }

    /**
     * Save the data from the AppointmentDatabase
     * in the program by running this method
     *
     * @param s: The file path
     */
    public void readAppointmentDatabase(String s) {
        Account appointmentAccount = new Account();
        Clinic appointmentClinic = new Clinic();
        Gp appointmentGp = new Gp();
        AppointmentType appointmentType = new AppointmentType();

        // Call the readFile method in the AppointmentList class to get the list of text information
        ArrayList<String[]> appointmentListString = readAppointmentFile(s);
        for (String[] list: appointmentListString) {
            for (Account patient: getAccountList()) {
                if (patient.getAccountEmail().equals(list[0])) {
                    appointmentAccount = patient;
                    break;
                }

            }
            for (Clinic clinic: getClinicList()) {
                if (clinic.getClinicAddress().equals(list[1])) {
                    appointmentClinic = clinic;
                }
            }
            for (Gp gp: getGpList()) {
                if (gp.getGpName().equals(list[2])) {
                    appointmentGp = gp;
                }
            }
            for (int i = 0; i < 3; i ++) {
                if (list[4].equals(appointmentTypeList[i].getDescription())) {
                    appointmentType = appointmentTypeList[i];
                    break;
                }
            }
            // Set the LocalTime format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            // Set the LocalDate format
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            setAppointmentItemsAdd(appointmentAccount, appointmentClinic, appointmentGp, list[3], appointmentType, LocalDate.parse(list[5], df), LocalTime.parse(list[6], formatter));

        }
    }

    /**
     * Save the data from the QueueDatabase
     * in the program by running this method
     *
     * @param s: The file path
     */
    public void readQueueDatabase(String s) {
        // Set the LocalTime format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        // Set the LocalDate format
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // Call the readFile method in the QueueList class to get the list of text information
        ArrayList<String[]> queueLists = readQueueFile(s);
        // Traverse the String list Convert the String information into an object and store it in the Arraylist.
        for (String[] queueInfromationList: queueLists) {
            String[] patientEmails = queueInfromationList[1].split("/");
            String[] checkinTimes = queueInfromationList[2].split("/");
            String[] checkinDates = queueInfromationList[3].split("/");
            //While reading the Queue list, determine whether the queue meets the requirement within 45 minutes of the day.
            // Those that do not meet the requirement are not read.
            for (int i = 0; i < patientEmails.length; i ++) {
                if (LocalDate.parse(checkinDates[i], df).equals(LocalDate.now())) {
                    if (LocalTime.parse(checkinTimes[i], formatter).plusMinutes(45).isAfter(LocalTime.now())) {
                        if (checkIfNoCheckIn(queueInfromationList[0], patientEmails[i])) {
                            int index = 0;
                            for (String clinicAppointmentAddress: getQueueClinicList()) {
                                if (clinicAppointmentAddress.equals(queueInfromationList[0])) {
                                    insertQueue(findByEmail(patientEmails[i]), index, LocalTime.parse(checkinTimes[i], formatter), LocalDate.parse(checkinDates[i], df));
                                    break;
                                }
                                index ++;
                            }
                            if (index == getQueueList().size()) {
                                addNewQueue(findClinicByAddress(queueInfromationList[0]),findByEmail(patientEmails[i]), LocalTime.parse(checkinTimes[i], formatter), LocalDate.parse(checkinDates[i], df));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method can get the description of this appointment type
     *
     * @param index: index of the appointment type
     * @return description  the description of this appointment type
     */
    public String getAppointmentTypeDescription(int index) {
        return appointmentTypeList[index].getDescription();
    }

    /**
     * This method can get the duration of this type of appointment
     *
     * @param index: index of the appointment type
     * @return duration  the duration of this type of appointment
     */
    public int getAppointmentTypeDuration(int index) {
        return appointmentTypeList[index].getDuration();
    }

    /**
     * When the reservation information is confirmed, this method will be called.
     * This method is responsible for saving the reservation information made by the current user.
     *
     */
    public void addAppointment(String userEmile, String address, String gpName, String patientState, int index, LocalDate date, LocalTime time) {
        Account appointmentAccount = new Account();
        Clinic appointmentClinic = new Clinic();
        Gp appointmentGp = new Gp();

        for (Account patient: getAccountList()) {
            if (patient.getAccountEmail().equals(userEmile)) {
                appointmentAccount = patient;
                break;
            }

        }
        for (Clinic clinic: getClinicList()) {
            if (clinic.getClinicAddress().equals(address)) {
                appointmentClinic = clinic;
            }
        }
        for (Gp gp: getGpList()) {
            if (gp.getGpName().equals(gpName)) {
                appointmentGp = gp;
            }
        }
        setAppointmentItemsAdd(appointmentAccount, appointmentClinic, appointmentGp, patientState, appointmentTypeList[index], date, time);
    }

    /**
     * This method can get the GP list
     *
     * @return gpList  the gp list
     */
    public ArrayList<Gp> getGpList() {
        return gpList;
    }

    /**
     * This method can set the GP list
     *
     * @param gpList  the new gp list
     */
    public void setGpList(ArrayList<Gp> gpList) {
        this.gpList = gpList;
    }

    /**
     * This method reads the text stream from the database
     *
     * @param filename: The name of the file to be read
     */
    public void readGpFile(String filename)
    {
        try
        {
            FileReader inputFile = new FileReader(filename);
            try
            {
                Scanner parser = new Scanner(inputFile);
                String gpInformation;
                while (parser.hasNextLine())
                {
                    gpInformation = parser.nextLine();
                    String[] gpInfromationList = gpInformation.split(";");
                    setGpItemsAdd(gpInfromationList);
                }
            }
            finally
            {
                inputFile.close();
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println(filename + " not found");
        }
        catch (IOException exception)
        {
            System.out.println("Unexpected I/O error occurred");
        }
    }

    /**
     * Receive parameters to add a new gp to the list
     *
     * @param gpInfromationList: Contains a list of gp information
     */
    public void setGpItemsAdd(String[] gpInfromationList) {
        Gp newGp = new Gp(gpInfromationList[0],
                gpInfromationList[1],
                gpInfromationList[2],
                gpInfromationList[3],
                new ArrayList<String>(Arrays.asList(gpInfromationList[4].split("/"))));
        gpList.add(newGp);
    }

    /**
     * This method can set the appointment list
     *
     * @return appointmentList  the appointment list
     */
    public ArrayList<Appointment> getAppointmentList() {
        return appointmentList;
    }

    /**
     * This method can get the appointment list during a period
     *
     * @param startDate: start date
     * @param startDate: end date
     * @return appointmentList  A list of appointment during this period
     */
    public ArrayList<Appointment> getAppointmentList(LocalDate startDate, LocalDate endDate) {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        for (Appointment appointment: getAppointmentList()) {
            if ((appointment.getAppointmentDate().isAfter(startDate) || appointment.getAppointmentDate().equals(startDate))
                    && (appointment.getAppointmentDate().isBefore(endDate) || appointment.getAppointmentDate().equals(endDate))) {
                appointmentList.add(appointment);
            }
        }
        return appointmentList;
    }

    /**
     * This method can get the appointment list of one gp, int a specific date
     *
     * @param date             the date of appintment
     * @param gp               the target Gp
     * @return appointmentList A list of appointment of this gp in that day
     */
    public ArrayList<Appointment> getAppointmentListByDate(LocalDate date, String gp) {
        ArrayList<Appointment> appointmentGpAndDate = new ArrayList<>();
        for (Appointment appointment: appointmentList) {
            if (appointment.getAppointmentGp().getGpName().equals(gp) && appointment.getAppointmentDate().equals(date)) {
                appointmentGpAndDate.add(appointment);
            }
        }
        return appointmentGpAndDate;
    }

    /**
     * This method can set the appointment list
     *
     * @param appointmentList the new appointment list
     */
    public void setAppointmentList(ArrayList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    /**
     * This method is used to remove the specified object in the current list.
     *
     * @param appointment: Specified deletion object
     */
    public void removeAppointment(Appointment appointment) {
        for (int i = 0; i < appointmentList.size(); i ++) {
            if (appointmentList.get(i).equals(appointment)) {
                appointmentList.remove(i);
                break;
            }
        }
    }

    /**
     * This method returns part of appointment list
     * by looking up GP information from the whole appointment list
     *
     * @param gpName: Search for appointments information by name
     *
     * @return appointmentList
     */
    public ArrayList<Appointment> findAppointmentByGpName(String gpName) {
        ArrayList<Appointment> appointmentList = new ArrayList();
        for (Appointment appointment: appointmentList) {
            if (appointment.getAppointmentGp().getGpName().equals(gpName)) {
                appointmentList.add(appointment);
            }
        }
        return appointmentList;
    }

    /**
     * This method returns part of appointment list
     * by looking up appointment date from the whole appointment list
     *
     * @param date: Search for appointments information by date
     *
     * @return appointmentList
     */
    public ArrayList<Appointment> findAppointmentByDate(LocalDate date) {
        ArrayList<Appointment> appointmentList = new ArrayList();
        for (Appointment appointment: appointmentList) {
            if (appointment.getAppointmentDate().equals(date)) {
                appointmentList.add(appointment);
            }
        }
        return appointmentList;
    }

    /**
     * This method adds a new element to the list
     *
     * @param appointment: new appointment
     */
    public void add(Appointment appointment) {
        appointmentList.add(appointment);
    }

    /**
     * This method reads the text stream from the database and returns String [] type ArrayList.
     *
     * @param filename: The name of the file to be read
     * @return appointmentList: String [] type ArrayList, used to save the appointment content read from the text.
     */
    public ArrayList<String[]> readAppointmentFile(String filename)
    {
        ArrayList<String[]> appointmentList = new ArrayList();
        try
        {
            FileReader inputFile = new FileReader(filename);
            try
            {
                Scanner parser = new Scanner(inputFile);
                String appointmentInformation;
                while (parser.hasNextLine())
                {
                    appointmentInformation = parser.nextLine();
                    String[] appointmentInfromationList = appointmentInformation.split(";");
                    appointmentList.add(appointmentInfromationList);
                }
            }
            finally
            {
                inputFile.close();
                return appointmentList;
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println(filename + " not found");
        }
        catch (IOException exception)
        {
            System.out.println("Unexpected I/O error occurred");
        }
        return appointmentList;
    }

    /**
     * Receive parameters to add a new appointment to the list
     *
     * @param patientEmile: Patient cliss
     * @param appointmentClinic: Clinic cliass
     * @param appointmentGp: Gp class
     * @param patientStatus: User status
     * @param appointmentType: AppointmentType class
     * @param appointmentDate: Appointment start date
     * @param appointmentTime: Appointment time
     */
    public void setAppointmentItemsAdd(Account patientEmile, Clinic appointmentClinic, Gp appointmentGp, String patientStatus, AppointmentType appointmentType, LocalDate appointmentDate, LocalTime appointmentTime) {
        Appointment newAppointment = new Appointment(patientEmile, appointmentClinic, appointmentGp, patientStatus, appointmentType, appointmentDate, appointmentTime);
        appointmentList.add(newAppointment);
    }

    /**
     * This method writes the class to the database
     *
     * @param filename: The name of the file to write
     */
    public void writeAppointmentFile(String filename)
    {
        try
        {
            PrintWriter outputFile = new PrintWriter(filename);
            try
            {
                System.out.println();
                for (Appointment appointmentInformation: appointmentList)
                {
                    outputFile.println(appointmentInformation);
                }
            }
            finally
            {
                outputFile.close();
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println(filename + " not found");
        }
        catch (IOException exception)
        {
            System.out.println("Unexpected I/O error occurred");
        }
    }

    /**
     * This method can get the clinic list
     *
     * @return clinicList A list of clinics
     */
    public ArrayList<Clinic> getClinicList() {
        return clinicList;
    }

    /**
     * This method can set the clinic list
     *
     * @param clinicList  A list of clinics
     */
    public void setClinicList(ArrayList<Clinic> clinicList) {
        this.clinicList = clinicList;
    }

    /**
     * This method reads the text stream from the database
     *
     * @param filename: The name of the file to be read
     */
    public void readClinicFile(String filename)
    {
        try
        {
            FileReader inputFile = new FileReader(filename);
            try
            {
                Scanner parser = new Scanner(inputFile);
                String clinicInformation;
                while (parser.hasNextLine())
                {
                    clinicInformation = parser.nextLine();
                    String[] clinicInfromationList = clinicInformation.split(";");
                    setClinicItemsAdd(clinicInfromationList);
                }
            }
            finally
            {
                inputFile.close();
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println(filename + " not found");
        }
        catch (IOException exception)
        {
            System.out.println("Unexpected I/O error occurred");
        }
    }

    /**
     * Receive parameters to add a new Clinic to the list
     *
     * @param clinicInfromationList: Contains a list of clinic information
     */
    public void setClinicItemsAdd(String[] clinicInfromationList) {
        Clinic newClinic = new Clinic(clinicInfromationList[0],
                clinicInfromationList[1],
                clinicInfromationList[2],
                LocalTime.parse(clinicInfromationList[3]),
                LocalTime.parse(clinicInfromationList[4]));
        clinicList.add(newClinic);
    }

    /**
     * This method returns a clinic
     * by looking up clinic address from the list
     *
     * @param clinicAddress: Search for clinic by address
     *
     * @return clinic
     */
    public Clinic findClinicByAddress(String clinicAddress) {
        for (Clinic clinic: clinicList) {
            if (clinic.getClinicAddress().equals(clinicAddress)) {
                return clinic;
            }
        }
        return null;
    }

    /**
     * This method can get the account list
     *
     * @return accountList  the account list
     */
    public ArrayList<Account> getAccountList() {
        return accountList;
    }

    /**
     * This method can set the account list
     *
     * @param accountList the account list
     */
    public void setAccountList(ArrayList<Account> accountList) {
        this.accountList = accountList;
    }

    /*
     * This method returns the Patient class by matching the patient email
     *
     * @param patientEmail: Search for clinic by address
     *
     * @return patient
     */
    /*public Patient findByEmail(String patientEmail) {
        for (Patient patient: listOfPatients) {
            if (patient.getAccountEmail().equals(patientEmail)) {
                return patient;
            }
        }
        return null;
    }*/
    // modify it from above

    /**
     * This method can find the account using email
     *
     * @param accountEmail  the email
     * @return account      the target account
     */
    public Account findByEmail(String accountEmail) {
        for (Account account: accountList) {
            if (account.getAccountEmail().equals(accountEmail)) {
                return account;
            }
        }
        return null;
    }

    /**
     * This method reads the text stream from the database and returns String [] type ArrayList.
     *
     * @param filename: The name of the file to be read
     * @return accountList: String [] type ArrayList, used to save the appointment content read from the text.
     */
    public void readAccountFile(String filename)
    {
        try
        {
            FileReader inputFile = new FileReader(filename);
            try
            {
                Scanner parser = new Scanner(inputFile);
                String accountInformation;
                while (parser.hasNextLine())
                {
                    accountInformation = parser.nextLine();
                    String[] accountInfromationList = accountInformation.split(";");
                    setAccountItemsAdd(accountInfromationList);
                }
            }
            finally
            {
                inputFile.close();
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println(filename + " not found");
        }
        catch (IOException exception)
        {
            System.out.println("Unexpected I/O error occurred");
        }
    }

    /**
     * Receive parameters to add a new account to the list
     *
     * @param data: Account details
     */
    public void setAccountItemsAdd(String[] data) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Account oneAccount;
        if(data.length == 3){
            oneAccount = new Account(data[0], data[1], data[2]);
        }else{
            oneAccount = new Patient(data[0], data[1], data[2], data[3], data[4], LocalDate.parse(data[5], df), data[6].charAt(0), data[7]);
        }
        accountList.add(oneAccount);
    }

    /**
     * get the administrator for the system
     *
     * @return administrator: administrator's Account
     */
    public Account getAdministrator(){
        Account administrator = new Administrator();
        for(Account oneAccount: accountList){
            if(oneAccount.getAccountType().equals("Administrator")){
                administrator = oneAccount;
            }
        }
        return administrator;
    }

    /**
     * This method can get the queue list
     *
     * @return queueList the queue list
     */
    public ArrayList<Queue> getQueueList() {
        return queueList;
    }

    /**
     * This method can set the queue list
     *
     * @param queueList  the new queue list
     */
    public void setQueueList(ArrayList<Queue> queueList) {
        this.queueList = queueList;
    }

    /**
     * This method displays the queue by receiving the clinic address and patient email
     *
     * @param clinicAddress: clinic address
     * @param patientEmile: patient email
     */
    public void findQueueByClinic(String clinicAddress, String patientEmile) {
        if (queueList.size() > 0) {
            for (Queue queue: queueList) {
                if (queue.getQueueClinic().getClinicAddress().equals(clinicAddress)) {
                    queue.showQueue(patientEmile);
                    break;
                }
                else {
                    System.out.println("******There is currently no queue at the clinic.******");
                }
            }
        }
        else {
            System.out.println("******There is currently no queue at the clinic.******");
            System.out.println();
        }

    }

    /**
     * This method creates a new waiting queue.
     *
     * @param clinic: clinic address
     * @param patient: Account class
     */
    public void addNewQueue(Clinic clinic, Account patient) {
        queueList.add(new Queue(clinic, patient, LocalTime.now(), LocalDate.now()));
    }

    /**
     * This method creates a new waiting queue.
     *
     * @param clinic: clinic address
     * @param patient: Account class
     */
    public void addNewQueue(Clinic clinic, Account patient, LocalTime time, LocalDate date) {
        queueList.add(new Queue(clinic, patient, time, date));
    }

    /**
     * This method inserts the user into the queue pointed to by the index.
     *
     * @param patient: Account class
     * @param index: Queue index
     */
    public void insertQueue(Account patient, int index) {
        queueList.get(index).addNewPatient(patient);
    }

    /**
     * This method inserts the user into the queue pointed to by the index.
     *
     * @param patient: Account class
     * @param index: Queue index
     */
    public void insertQueue(Account patient, int index, LocalTime time, LocalDate date) {
        queueList.get(index).addNewPatient(patient, time, date);
    }

    /**
     * Check whether the user has check-in in the clinic by accepting clinic address and patient email
     *
     * @param clinicAddress: clinic address
     * @param patientEmile: patient email
     */
    public boolean checkIfNoCheckIn(String clinicAddress, String patientEmile) {
        for (Queue queues: queueList) {
            if (queues.getQueueClinic().getClinicAddress().equals(clinicAddress)) {
                for (int i = 0; i < queues.getQueuePatients().size(); i ++) {
                    if (queues.getQueuePatients().get(i).getAccountEmail().equals(patientEmile)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * This method is used to get the clinic address of all queues
     *
     * @return list: All clinic addresses in the queue
     */
    public ArrayList<String> getQueueClinicList() {
        ArrayList<String> list = new ArrayList();
        for (Queue queues: queueList) {
            list.add(queues.getQueueClinic().getClinicAddress());
        }
        return list;
    }

    /**
     * This method reads the text stream from the database and returns String [] type ArrayList
     *
     * @param filename: The name of the file to be read
     * @return queueLists: String [] type ArrayList, used to save the appointment content read from the text.
     */
    public ArrayList<String[]> readQueueFile(String filename)
    {
        ArrayList<String[]> queueLists = new ArrayList();
        try
        {
            FileReader inputFile = new FileReader(filename);
            try
            {
                Scanner parser = new Scanner(inputFile);
                String queueInformation;
                while (parser.hasNextLine())
                {
                    queueInformation = parser.nextLine();
                    String[] queueInfromationList = queueInformation.split(";");
                    queueLists.add(queueInfromationList);
                }
            }
            finally
            {
                inputFile.close();
                return queueLists;
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println(filename + " not found");
        }
        catch (IOException exception)
        {
            System.out.println("Unexpected I/O error occurred");
        }
        return queueLists;
    }

    /**
     * This method writes the class to the database
     *
     * @param filename: The name of the file to write
     */
    public void writeQueueFile(String filename)
    {
        try
        {
            PrintWriter outputFile = new PrintWriter(filename);
            try
            {
                System.out.println();
                for (Queue queue: queueList)
                {
                    if (queue.isNotEmptyQueue()) {
                        outputFile.println(queue);
                    }
                }
            }
            finally
            {
                outputFile.close();
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println(filename + " not found");
        }
        catch (IOException exception)
        {
            System.out.println("Unexpected I/O error occurred");
        }
    }

    /**
     * This method is responsible for generating the detailed information of
     * the GP and passing the output content to UserInterface
     *
     */
    public ArrayList<ArrayList<String>> gpDetailsMenu(String address) {
        ArrayList<ArrayList<String>> gpDetails = new ArrayList();
        for (Gp gp : getGpList()) {
            for (String clinic : gp.getGpClinics()) {
                if (clinic.equals(address)) {
                    ArrayList<String> gpDetail = new ArrayList();
                    gpDetail.add(gp.getGpFirstName() + " " + gp.getGpLastName());
                    gpDetail.add(gp.getGpPhone());
                    gpDetail.add(gp.getGpInterestArea());
                    for (String clinicAddress : gp.getGpClinics()) {
                        gpDetail.add(clinicAddress);
                    }
                    gpDetails.add(gpDetail);
                    break;
                }
            }
        }
        return gpDetails;
    }

    /**
     * This method displays GP based on clinic address
     *
     * @param address: clinic address
     * @return gpList: String type ArrayList, which contains the name of GP
     */
    public ArrayList<String> showGpByClinic(String address) {
        ArrayList<String> gpList = new ArrayList();
        for (Gp gp : getGpList()) {
            for (String clinic : gp.getGpClinics()) {
                if (clinic.equals(address)) {
                    gpList.add(gp.getGpFirstName() + " " +gp.getGpLastName());
                }
            }
        }

        return gpList;
    }

    /**
     * This method outputs the addresses of all clinics in ascending order.
     *
     * @return addressList: String type ArrayList, which contains the address of clinic
     */
    public ArrayList<String[]> findClinicByAddress() {
        ArrayList<String[]> addressList = new ArrayList();
        for (Clinic clinic : getClinicList()) {
            String[] clinicDetail = new String[5];
            if (clinic.getClinicName() == "") {
                clinicDetail[0] = "Monash clinic (Unnamed)";
            }
            else {
                clinicDetail[0] = clinic.getClinicName();
            }
            clinicDetail[1] = clinic.getClinicAddress();
            clinicDetail[2] = clinic.getClinicPhone();
            clinicDetail[3] = clinic.getClinicOpeningTime().toString();
            clinicDetail[4] = clinic.getClinicClosingTime().toString();
            addressList.add(clinicDetail);
        }
        Collections.sort(addressList, (t1, t2) -> (t1[1].compareTo(t2[1])));
        Collections.sort(addressList, (t1, t2) -> (t1[0].compareTo(t2[0])));
        return addressList;
    }

    /**
     * This method is used to cancel the current appointment
     *
     * @param appointmentOption: Appointment order selected in the previous method
     */
    public void cancelAppointment(int appointmentOption, String userEmile) {
        ArrayList<Appointment> appointmentList = new ArrayList();
        for (Appointment appointment: getAppointmentList()) {
            if (appointment.getAppointmentPatient().getAccountEmail().equals(userEmile)) {
                appointmentList.add(appointment);
            }
        }
        removeAppointment(appointmentList.get(appointmentOption));

    }

    /**
     * This method is responsible for passing the detailed information of the appointment
     *
     * @return appointmentList: Return to the list of saved appointment details
     */
    public ArrayList<String[]> showAppointmentDetail(String userEmile) {
        ArrayList<String[]> appointmentList = new ArrayList();
        for (Appointment appointment: getAppointmentList()) {
            if (appointment.getAppointmentPatient().getAccountEmail().equals(userEmile)) {
                appointmentList.add(new String[]{appointment.getAppointmentDate().toString(),
                        appointment.getAppointmentTime().toString(),
                        appointment.getAppointmentGp().getGpName(),
                        appointment.getPatientStatus(),
                        appointment.getAppointmentType().getDescription(),
                        appointment.getAppointmentClinic().getClinicAddress()});
            }
        }
        return appointmentList;
    }

    /**
     * This method returns a list of GP appointments on a given day
     *
     * @param date: The appointment date selected by the user
     * @param gp: The gp selected by the user
     * @return startAndEndTimeList:  a list of GP appointments on a given day
     */
    public ArrayList<LocalTime[]> getOpeningTimeByDateAndGp(LocalDate date, String gp) {
        ArrayList<LocalTime[]> startAndEndTimeList = new ArrayList<>();
        for (Appointment appointment: getAppointmentListByDate(date, gp)) {
            LocalTime[] startAndEndTime = new LocalTime[2];
            startAndEndTime[0] = appointment.getAppointmentTime();
            startAndEndTime[1] = startAndEndTime[0].plusMinutes(appointment.getAppointmentType().getDuration());
            startAndEndTimeList.add(startAndEndTime);
        }
        return startAndEndTimeList;
    }

    /**
     * This method can get the appointment type list
     *
     * @return AppointmentType[]: appointment type list
     */
    public AppointmentType[] getAppointmentTypeList() {
        return appointmentTypeList;
    }

    /**
     * This method can set the appointment type list
     *
     * @param appointmentTypeList: appointment type list
     */
    public void setAppointmentTypeList(AppointmentType[] appointmentTypeList) {
        this.appointmentTypeList = appointmentTypeList;
    }
}
