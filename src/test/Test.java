package test;

import model.PlannerApp;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    //this test work only with 1_test.ics file

    public static void main(String[] args) {

        System.out.println(testConstructor());
        System.out.println(testParticipantStatus());

    }

    private static String testConstructor() {
        String ret = "--Test Constructor-- \n";
        int cas = 0;
        int totalCas = 0;

        File actual = new File("./calendar");
        int nbParticipant = actual.listFiles().length;

        PlannerApp plannerApp = new PlannerApp();

        totalCas++;
        if(nbParticipant == plannerApp.getParticipants().size()) {
            cas++;
        }else {
            ret += "Error : nb participant not correct \n";
        }

        ret += "Result : "+cas+"/"+totalCas+"\n\n";
        return ret;
    }

    private static String testParticipantStatus() {
        String ret = "--Test participantStatus-- \n";
        int cas = 0;
        int totalCas = 0;

        PlannerApp plannerApp = new PlannerApp();

        DateFormat format = new SimpleDateFormat("yyyy/MM/dd-HH:mm");
        Date date = null;

        //participant available
        totalCas++;
        String dateString = "2018/01/01-15:30";
        try {
            date = format.parse(dateString);
        }catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        if(plannerApp.participantStatus(plannerApp.getParticipants().get(0),date).equals("available")) {
            cas++;
        }else {
            ret += "Error : available case \n";
        }

        //participant unavailable
        totalCas++;
        dateString = "2018/01/01-09:45";
        try {
            date = format.parse(dateString);
        }catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        if(plannerApp.participantStatus(plannerApp.getParticipants().get(0),date).equals("unavailable")) {
            cas++;
        }else {
            ret += "Error : unavailable case \n";
        }

        //participant potentially
        totalCas++;
        dateString = "2018/01/02-11:00";
        try {
            date = format.parse(dateString);
        }catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        if(plannerApp.participantStatus(plannerApp.getParticipants().get(0),date).equals("potentially")) {
            cas++;
        }else {
            ret += "Error : potentially case \n";
        }

        ret += "Result : "+cas+"/"+totalCas+"\n\n";
        return ret;
    }

}
