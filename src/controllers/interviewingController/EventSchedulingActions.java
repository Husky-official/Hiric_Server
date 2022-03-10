package controllers.interviewingController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.interviewing.EventScheduling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author I_Clarisse
 * @description
 */
public class EventSchedulingActions {
    public EventSchedulingActions() throws Exception{}

    Connection connection = new OnlineDbConnection().getConnection();

    public String scheduleEvent(JsonNode requestData) throws Exception{
        String scheduleEventQuery = "INSERT INTO eventScheduling(eventName, eventType, eventDate, startTime, endTime, eventCreator, eventTime) VALUES (?, ?, ?, ?, ?, ?, NOW())";
        // creating db connection

        JsonNode eventSchedulingData = requestData.get("object");
        System.out.println(eventSchedulingData);
        Iterator<Map.Entry<String, JsonNode>> iterator = eventSchedulingData.fields();

        String eventName = iterator.next().toString().split("=")[1];
        String eventType = iterator.next().toString().split("=")[1];

        String eventDate = iterator.next().toString().split("=")[1];
//        Date eventDate = new SimpleDateFormat("dd/MM/yyy").parse(date);

        String startTime= iterator.next().toString().split("=")[1];
//        Time startTime = Time.valueOf(from);

        String endTime = iterator.next().toString().split("=")[1];
//        Time endTime = Time.valueOf(to);

        String creator = iterator.next().toString().split("=")[1];
        int eventCreator = Integer.valueOf(creator);

        PreparedStatement preparedStatement = connection.prepareStatement(scheduleEventQuery);
        preparedStatement.setString(1, eventName);
        preparedStatement.setString(2,eventType);
//        preparedStatement.setDate(4, (java.sql.Date) eventDate);
        preparedStatement.setString(3, eventDate);
//        preparedStatement.setTime(5,startTime);
        preparedStatement.setString(4,startTime);
//        preparedStatement.setTime(6,endTime);
        preparedStatement.setString(5,endTime);
        preparedStatement.setInt(6, eventCreator);

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
}
