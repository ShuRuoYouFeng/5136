package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Queue {

    private Clinic queueClinic;
    private ArrayList<Account> queuePatients;
    private ArrayList<LocalTime> checkInTime;
    private ArrayList<LocalDate> checkInDate;

    /**
     * Constructor for objects of class Questionnaire
     *
     * @param queueClinic  the clinic of this queue
     * @param patient      the patients of this queue
     * @param checkInTime  the check in time of this queue
     * @param checkInDate  the check in date of this queue
     */
    public Queue(Clinic queueClinic, Account patient, LocalTime checkInTime, LocalDate checkInDate) {
        this.queueClinic = queueClinic;
        queuePatients = new ArrayList<>();
        queuePatients.add(patient);
        this.checkInTime = new ArrayList<>();
        this.checkInTime.add(checkInTime);
        this.checkInDate = new ArrayList<>();
        this.checkInDate.add(checkInDate);
    }

    /**
     * This method can get the clinic of this queue
     *
     * @return queueClinic the clinic of this queue
     */
    public Clinic getQueueClinic() {
        return queueClinic;
    }

    /**
     * This method can set the clinic of this queue
     *
     * @param queueClinic the clinic of this queue
     */
    public void setQueueClinic(Clinic queueClinic) {
        this.queueClinic = queueClinic;
    }

    /**
     * This method can get the patients of this queue
     *
     * @return queuePatients  the patients of this queue
     */
    public ArrayList<Account> getQueuePatients() {
        return queuePatients;
    }

    /**
     * This method can set the patients of this queue
     *
     * @param queuePatients  the patients of this queue
     */
    public void setQueuePatients(ArrayList<Account> queuePatients) {
        this.queuePatients = queuePatients;
    }

    /**
     * This method can get the check in time of this queue
     *
     * @return checkInTime  the check in time of this queue
     */
    public ArrayList<LocalTime> getCheckInTime() {
        return checkInTime;
    }

    /**
     * This method can set the check in time of this queue
     *
     * @param checkInTime the check in time of this queue
     */
    public void setCheckInTime(ArrayList<LocalTime> checkInTime) {
        this.checkInTime = checkInTime;
    }

    /**
     * This method can set the check in date of this queue
     *
     * @return checkInDate  the check in date of this queue
     */
    public ArrayList<LocalDate> getCheckInDate() {
        return checkInDate;
    }

    /**
     * This method can set the check in date of this queue
     *
     * @param checkInDate  the check in date of this queue
     */
    public void setCheckInDate(ArrayList<LocalDate> checkInDate) {
        this.checkInDate = checkInDate;
    }
    /**
     * This method is used to receive parameters to create a queue
     *
     * @param patient: Account class
     */
    public void addNewPatient(Account patient) {
        queuePatients.add(patient);
        checkInTime.add(LocalTime.now());
        checkInDate.add(LocalDate.now());
    }

    /**
     * This method is used to receive parameters to create a queue
     *
     * @param patient: Account class
     * @param time: Time the queue was created
     * @param date: date the queue was created
     */
    public void addNewPatient(Account patient, LocalTime time, LocalDate date) {
        queuePatients.add(patient);
        checkInTime.add(time);
        checkInDate.add(date);
    }

    /**
     * This method is used to determine whether the current queue is empty
     *
     * @return boolean
     */
    public boolean isNotEmptyQueue() {
        if (queuePatients.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * This method displays all the queues of the currently viewed appointment clinic,
     * and outputs the queue number and waiting time.
     *
     * @param patientEmile: The patient email is used to find the patient's queue
     */
    public void showQueue(String patientEmile) {
        ArrayList<Integer> timeoutQueue = new ArrayList();
        if (checkInTime.size() == 0) {
            System.out.println("There is currently no queue at the clinic.");
        }
        else {
            for (int i = 0; i < checkInTime.size(); i ++) {
                LocalTime now = LocalTime.now();
                if (checkInTime.get(i).plusMinutes(45).isBefore(now)) {
                    timeoutQueue.add(i);
                    break;
                }
                int queueTime = (now.getHour() - checkInTime.get(i).getHour()) * 60 + now.getMinute() - checkInTime.get(i).getMinute();
                if (queuePatients.get(i).getAccountEmail().equals(patientEmile)) {
                    System.out.println("Queue " + (i + 1) + " (Your current queue: Checked in " + queueTime + " minutes ago)");
                    System.out.println();
                }
                else {
                    System.out.println("Queue " + (i + 1) + " (Checked in " + queueTime + " minutes ago)");
                    System.out.println();
                }
            }
            for (int i: timeoutQueue) {
                removingTimeoutQueue(i);
            }
        }
    }

    /**
     * This method deletes the corresponding queue by receiving an index
     *
     * @param index: Queue number
     */
    public void removingTimeoutQueue(int index) {
        queuePatients.remove(index);
        checkInTime.remove(index);
        checkInDate.remove(index);
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
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm");
        String patientsInformation = "";
        for (Account patients: queuePatients) {
            if (patientsInformation.length() > 0) {
                patientsInformation = patientsInformation + "/" + patients.getAccountEmail();
            }
            else {
                patientsInformation = patients.getAccountEmail();
            }

        }
        String timesInformation = "";
        for (LocalTime time: checkInTime) {
            if (timesInformation.length() > 0) {
                timesInformation = timesInformation + "/" + time.format(f);
            }
            else {
                timesInformation = time.format(f);
                //16:52:24.219987/16:52:24.219987!16:52!16:52:24.219987!16:52
                //time.toString()+ "/" +time +"!"+ time.format(f) + "!"+time.toString() + "!" + time.of(time.getHour(), time.getMinute())
            }

        }
        String datesInformation = "";
        for (LocalDate date: checkInDate) {
            if (datesInformation.length() > 0) {
                datesInformation = datesInformation + "/" + date.format(formatters);
            }
            else {
                datesInformation = date.format(formatters);
            }

        }
        String queueInformation = queueClinic.getClinicAddress() + ";" +
                patientsInformation + ";" +
                timesInformation + ";" +
                datesInformation;
        return queueInformation;
    }
}
