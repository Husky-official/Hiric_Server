package controllers.interviewingController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.hiring.JobApplication;
import models.hiring.ShortlistedEmployees;
import models.interviewing.EventScheduling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author I_Clarisse
 * @description event scheduling actions
 */
public class EventSchedulingActions {
    public EventSchedulingActions() throws Exception{}

    Connection connection = new OnlineDbConnection().getConnection();

    public String scheduleEvent(JsonNode requestData) throws Exception{
        String scheduleEventQuery = "INSERT INTO eventScheduling(jobPostId, eventName, eventType, eventDate, startTime, endTime, eventCreator, scheduledAt) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        JsonNode eventSchedulingData = requestData.get("object");

        Integer jobPostId = eventSchedulingData.get("jobPostId").asInt();
        String eventName = eventSchedulingData.get("eventName").asText("");
        String eventType = eventSchedulingData.get("eventType").asText("CALL");
        String eventDate = eventSchedulingData.get("eventDate").asText();
        String startTime = eventSchedulingData.get("startTime").asText();
        String endTime = eventSchedulingData.get("endTime").asText();
        Integer eventCreator = eventSchedulingData.get("eventCreator").asInt();

        PreparedStatement preparedStatement = connection.prepareStatement(scheduleEventQuery);
        preparedStatement.setInt(1, jobPostId);
        preparedStatement.setString(2, eventName);
        preparedStatement.setString(3,eventType);
        preparedStatement.setString(4, eventDate);
        preparedStatement.setString(5,startTime);
        preparedStatement.setString(6,endTime);
        preparedStatement.setInt(7, eventCreator);

        int resultSet = preparedStatement.executeUpdate();
        ResponseStatus responseStatus = new ResponseStatus();

        if(resultSet == 0){
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
        }else{
            responseStatus.setStatus(200);
            responseStatus.setMessage("Scheduled Event Successfully");
            responseStatus.setActionToDo("scheduleEvent");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String getAllEvents(JsonNode requestData) throws Exception{
        //select query
        String getAllEventsQuery = "SELECT * FROM eventScheduling";

        PreparedStatement preparedStatement = connection.prepareStatement(getAllEventsQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();

        if(resultSet.next()){
            resultSet.beforeFirst();
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved Event schedules successfully");

            responseStatus.setActionToDo("getAllScheduledEvents");
            ArrayList<EventScheduling> eventSchedules = new ArrayList<EventScheduling>();

            while (resultSet.next()){
                eventSchedules.add(new EventScheduling(resultSet.getInt("id"), resultSet.getString("eventName")));
            }

            System.out.println(eventSchedules);
            responseStatus.setObject(eventSchedules);
        }
        else {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
            return "0";
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String getShortlistedEmployees(JsonNode requestData) throws SQLException, JsonProcessingException {
        //sql query
        String getJobPostIdQuery = "SELECT jobPostId FROM eventScheduling WHERE id = ? ";

        JsonNode jobPostIdData = requestData.get("object");
        Integer eventId = jobPostIdData.get("eventId").asInt();
        PreparedStatement preparedStatement = connection.prepareStatement(getJobPostIdQuery);
        preparedStatement.setInt(1,eventId);
        ResultSet resultSet = preparedStatement.executeQuery();
        int jobPostId = 0;
        if (resultSet.next()){
            jobPostId = resultSet.getInt("jobPostId");
        }

        String getShortlistedEmployeesQuery = "SELECT userId, referenceName FROM jobApplication WHERE jobPostId= ? AND Status='shortlisted'";
        PreparedStatement preparedStatement1 = connection.prepareStatement(getShortlistedEmployeesQuery);
        preparedStatement1.setInt(1, jobPostId);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();

        if (resultSet1.next()){
            resultSet1.beforeFirst();
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved shortlisted employees successfully!");
            responseStatus.setActionToDo("getShortlistedEmployees");
            ArrayList<ShortlistedEmployees> shortlistedEmployees = new ArrayList<>();
            while (resultSet1.next()){
                shortlistedEmployees.add(new ShortlistedEmployees(resultSet1.getInt("userId"), resultSet1.getString("referenceName")));
            }
            System.out.println(shortlistedEmployees);
            responseStatus.setObject(shortlistedEmployees);
        }else {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
            return "0";
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
    public String addParticipant(JsonNode requestData) throws Exception{
        String addParticipantQuery = "INSERT INTO eventParticipation(scheduleId, participantId, participationStatus) VALUES( ?, ?, DEFAULT)";

        JsonNode addParticipantData = requestData.get("object");

        Integer scheduleId = addParticipantData.get("scheduleId").asInt();
        Integer participantId = addParticipantData.get("participantId").asInt();

        PreparedStatement preparedStatement = connection.prepareStatement(addParticipantQuery);
        preparedStatement.setInt(1,scheduleId);
        preparedStatement.setInt(2,participantId);

        int resultSet = preparedStatement.executeUpdate();
        ResponseStatus responseStatus = new ResponseStatus();

        if(resultSet == 0){
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
        }else{
            responseStatus.setStatus(200);
            responseStatus.setMessage("Scheduled Event Successfully");
            responseStatus.setActionToDo("scheduleEvent");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
