package models.interviewing;

import java.sql.Date;
import java.sql.Time;

/**
 * @author I_Clarisse
 * @description event scheduling model to retrieve and add data into the eventSchedule tables
 */
public class EventScheduling {
    private int id;
    private int jobPostId;
    private String eventName;
    private String eventType;
    private Date eventDate;
    private Time startTime;
    private Time endTime;
    private int eventCreator;

    public EventScheduling() {}

    public EventScheduling(int jobPostId, String eventName, String eventType, Date eventDate, Time startTime, Time endTime){
        this.jobPostId = jobPostId;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EventScheduling(int id, String eventName) {
        this.id = id;
        this.eventName = eventName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(int jobPostId) {
        this.jobPostId = jobPostId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getEventCreator() {
        return eventCreator;
    }

    public void setEventCreator(int eventCreator) {
        this.eventCreator = eventCreator;
    }
}
