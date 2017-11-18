package model;

import java.util.ArrayList;

/**
 * Class that represent a participant of the app
 * @author Sébastien Bouttier
 */
public class Participant {

    /** The name of the participant */
    private String name;

    /** Events of the participant */
    private ArrayList<Event> events;

    /**
     * Constructor of a participant
     * @param name the name of the participant
     */
    public Participant(String name) {
        this.name = name;
        this.events = new ArrayList<>();
    }

    /**
     * Get the name of the participant
     * @return The name of the participant
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the participant
     * @param name the name of the participant
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the ArrayList of participant's events
     * @return Participant's events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Generate a String format of all the attributes of the class
     * @return A String of the class's info
     */
    @Override
    public String toString() {
        return "Participant{" +
                "name='" + name + '\'' +
                ", events=" + events +
                '}';
    }
}
