package model;

import java.util.Date;

/**
 * Class that represent an event of a participant
 * @author Sébastien Bouttier
 */
public class Event {

    /** The UTC start date of the event */
    private Date startDate;

    /** The UTC end date of the event */
    private Date endDate;

    /**
     * The description of the event that help to know its importance
     * A O for mandatory event, A for voidable event and I for informative event
     */
    private String description;

    /**
     * Get the start date
     * @return the start date of the event
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set the start date of the event
     * @param startDate the date to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the end date
     * @return the end date of the event
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set the end date of the event
     * @param endDate the date to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Get the description of the event
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the event
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Generate a String format of all the attributes of the class
     * @return A String of the class's info
     */
    @Override
    public String toString() {
        return "Event{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
