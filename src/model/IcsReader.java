package model;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Class to read ics files
 * @author Sébastien Bouttier
 */
public class IcsReader {

    /**
     * Initialize the app by created participants and their events
     * @return An ArrayList of Participant
     */
	protected static ArrayList<Participant> createParticipants() {

        ArrayList<Participant> participants = new ArrayList<>();

        File actualCalendar = new File("./calendar");
        for( File calendarFile : actualCalendar.listFiles()){
            String[] fileNameParts = calendarFile.getName().split(".ics");
            Participant participant = new Participant(fileNameParts[0]);
            addEventsToParticipant(participant);
            participants.add(participant);
        }

        return participants;
    }

    /**
     * Read the ics file of one participant and create his event
     * @param participant the participant that we want to initialize his events
     */
    private static void addEventsToParticipant(Participant participant) {

        File file = new File("./calendar/" + participant.getName() + ".ics");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {

                if(line.equals("BEGIN:VEVENT")) {

                    Event event = new Event();
                    participant.getEvents().add(event);

                    initializeEvent(br, event);
                }
            }
        }catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Initialize an event with start and end date and its description
     * @param br the buffer of the ics file
     * @param event event to initialize
     * @throws IOException
     */
    private static void initializeEvent(BufferedReader br, Event event) throws IOException {

        String line;
        while (!(line = br.readLine()).equals("END:VEVENT")) {

            if(line.contains("DTSTART")) {

                createStartDate(event, line);
            }
            else if(line.contains("DTEND")) {

                createEndDate(event, line);
            }
            else if(line.contains("DESCRIPTION")) {

                createDescription(event, line);
            }
        }
    }

    /**
     * Create the description of an event
     * @param event event to set description
     * @param line the String that contain the description
     */
    private static void createDescription(Event event, String line) {

        if(line.length() >= 13 && descriptionIsCorrect(line)) {
            event.setDescription(line.substring(12, 13).toUpperCase());
        }
        else {
            event.setDescription("A");
        }
    }

    /**
     * Create the end date of an event
     * @param event event to set end date
     * @param line the String that contain the end date
     */
    private static void createEndDate(Event event, String line) {
        //date with time zone reference
        if(line.contains("TZID")) {
            event.setEndDate(createDateZoneRef(line));
        }
        //time UTC
        else if(line.contains("Z")) {
            String dateString = line.substring(6);
            event.setEndDate(createDateUTC(dateString));
        }
        //local time
        else {
            String dateString = line.substring(6);
            event.setStartDate(createDateLocal(dateString));
        }
    }

    /**
     * Create the start date of an event
     * @param event event to set start date
     * @param line the String that contain the start date
     */
    private static void createStartDate(Event event, String line) {
        //date with time zone reference
        if(line.contains("TZID")) {
            event.setStartDate(createDateZoneRef(line));
        }
        //time UTC
        else if(line.contains("Z")) {
            String dateString = line.substring(8);
            event.setStartDate(createDateUTC(dateString));
        }
        //local time
        else {
            String dateString = line.substring(8);
            event.setStartDate(createDateLocal(dateString));
        }
    }

    /**
     * Create a local date with a string
     * @param dateString the String of the date, like : 19980118T230000
     * @return the Date created
     */
    private static Date createDateLocal(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        Date date = null;

        try {
            date = format.parse(dateString);
        }catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return date;
    }

    /**
     * Create a UTC date with a String
     * @param dateString the String of the date, like : 19980119T070000Z
     * @return the Date created
     */
    private static Date createDateUTC(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;

        try {
            date = format.parse(dateString);
        }catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return date;
    }

    /**
     * Create a date with time zone reference
     * @param line the String that contain the date, like : DTSTART;TZID=US-Eastern:19980119T020000
     * @return the Date created
     */
    private static Date createDateZoneRef(String line) {
        String[] parts = line.split("=");
        parts = parts[1].split(":");

        DateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        format.setTimeZone(TimeZone.getTimeZone(parts[0]));
        Date date = null;

        try {
            date = format.parse(parts[1]);
        }catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return date;
    }

    /**
     * Check if the description of an event is correct
     * @param line the line that contain the description
     * @return true if the description is "A","O" or "I"
     */
    private static boolean descriptionIsCorrect(String line) {
        String description = line.substring(12, 13);
        description.toUpperCase();

        if(description.equals("A") || description.equals("O") || description.equals("I")) {
            return true;
        }

        return false;
    }

}
