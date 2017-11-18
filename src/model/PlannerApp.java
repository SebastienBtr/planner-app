package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Class that give methods to interact with the app
 * @author Sébastien Bouttier
 */
public class PlannerApp {

    /** Array of app's participants */
    private ArrayList<Participant> participants;

    /**
     * Constructor that call IcsReader to initialize the app
     */
    public PlannerApp() {
        participants = IcsReader.createParticipants();
    }

    /**
     * Being given a list of participants and a time slot of 15min, determines the status of the participants
     * by using the method participantStatus
     * @param participants the list of participant
     * @param timeStart the start date of the time slot
     * @return An HashMap with participants and their status for the time slot
     */
    public HashMap<Participant,String> participantsStatus(ArrayList<Participant> participants, Date timeStart) {

        if(!timeIsCorrect(timeStart)) {
            throw new IllegalArgumentException();
        }

        HashMap<Participant,String> participantsStatusMap = new HashMap<>();

        for(Participant p : participants) {
            String status = participantStatus(p,timeStart);
            participantsStatusMap.put(p,status);
        }
        return participantsStatusMap;
    }

    /**
     * Being given a participant and a time slot of 15min, determine the status of the participant.
     * "available" if he does not have event in the time slot
     * "unavailable" if he has mandatory event in the time slot
     * "potentially" if he has event that he can cancel.
     * @param participant the participant to test
     * @param timeStart the start date of the time slot
     * @return A String that represent the participant's status (see above)
     */
    public String participantStatus(Participant participant, Date timeStart) {

        if(!timeIsCorrect(timeStart)) {
            throw new IllegalArgumentException();
        }

        String status = "available";
        ArrayList<Event> events = participant.getEvents();

        for(Event e : events) {

            if( eventIntersect(e.getStartDate(), e.getEndDate(), timeStart) ) {

                if(e.getDescription().equals("O")) {
                    status = "unavailable";
                    return status;
                }else if(e.getDescription().equals("A") || e.getDescription().equals("I")) {
                    status = "potentially";
                    return status;
                }
            }
        }
        return status;
    }

    private boolean timeIsCorrect(Date time) {
        return time.getMinutes()%15 == 0 && time.getHours() < 20 && time.getHours() >= 8;
    }

    private Date getTimeEnd(Date timeStart) {
        Date timeEnd = timeStart;
        timeEnd.setMinutes(timeStart.getMinutes()+15);
        return timeEnd;
    }

    private boolean eventIntersect(Date eventStart, Date eventEnd, Date timeStart) {
        return (eventStart.before(timeStart) && eventEnd.after(timeStart)) ||
                ((eventStart.after(timeStart) || eventStart.equals(timeStart))
                        && eventStart.before(getTimeEnd(timeStart)));
    }

    /**
     * Get the ArrayList of participants
     * @return Participants
     */
    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    /**
     * Generate a String format of all the attributes of the class
     * @return A String of the class's info
     */
    @Override
    public String toString() {
        return "PlannerApp{" +
                "participants=" + participants +
                '}';
    }
}
