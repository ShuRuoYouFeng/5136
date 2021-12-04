package Model;

import java.util.ArrayList;

public class Questionnaire {

    private ArrayList<String> questionList;

    /**
     * Default constructor for objects of class Questionnaire
     *
     */
    public Questionnaire() {
        questionList = new ArrayList<>();
        questionList.add("Do you or the person who needs the appointment have ANY of the following symptoms: fever, chills, cough, sore throat, shortness of breath, runny nose, loss of smell or loss of taste or have you experienced one or several of these in the past 14 days?");
        questionList.add("Have you or the person who needs the appointment been to a COVID-19 hot spot or outbreak area in the last two weeks?");
        questionList.add("Have you or the person who needs the appointment been in direct contact with someone with suspected coronavirus (COVID-19) or who has returned from international or interstate travel in the past 14 days?");
    }

    /**
     * This method can get the question list of this questionnaire
     *
     * @return questionList the question list
     */
    public ArrayList<String> getQuestionList() {
        return questionList;
    }

    /**
     * This method can set the question list of this questionnaire
     *
     * @param questionList  the question list
     */
    public void setQuestionList(ArrayList<String> questionList) {
        this.questionList = questionList;
    }

    /**
     * This method receives a question number and agrees to the corresponding questions and options.
     *
     * @param questionNum: Question number
     */
    public void printQuestionnaire(int questionNum) {
        System.out.println(questionList.get(questionNum));
        System.out.println();
        System.out.println("     1. Yes");
        System.out.println("     2. No");
        System.out.println("     0. Back to the previous page");
    }
}
