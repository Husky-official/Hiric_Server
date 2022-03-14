package models.interviewing;

import java.sql.Time;
import java.util.Date;

/**
 * @author I_Clarisse
 * @description event scheduling model to retrieve and add data into the eventSchedule tables
 */
public class EventScheduling {
    private int id;
    private int jobPostId;
    private String eventName;
    private String eventType;
    //    private java.util.Date eventDate;
    private String eventDate;
    //    private Time startTime;
    private String startTime;
    //    private Time endTime;
    private String endTime;
    private int eventCreator;
//    private Date eventTime;

    public EventScheduling() {}

    public EventScheduling(int jobPostId, String eventName, String eventType, String eventDate, String startTime, String endTime){
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

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getEventCreator() {
        return eventCreator;
    }

    public void setEventCreator(int eventCreator) {
        this.eventCreator = eventCreator;
    }
}
