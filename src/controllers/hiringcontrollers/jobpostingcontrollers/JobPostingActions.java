package controllers.hiringcontrollers.jobpostingcontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

public class JobPostingActions {
    public JobPostingActions() throws Exception {}
    public String createJobPost(JsonNode requestData) throws Exception {
        String createJobPostQuery = "INSERT INTO jobPosts(jobTitle, jobDescription, jobRequirements, location, startDate, duration, salary) values ( ?, ?, ?, ?, ?, ?, ?)";
        //initialise  db connection
        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode jobPostData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = jobPostData.fields();
        String jobTitle = iterator.next().toString().split("=")[1];
        String jobDescription = iterator.next().toString().split("=")[1];
        String jobRequirements = iterator.next().toString().split("=")[1];
        String location = iterator.next().toString().split("=")[1];
        String startDate = iterator.next().toString().split("=")[1];
        String duration = iterator.next().toString().split("=")[1];
        String money = iterator.next().toString().split("=")[1];
        int salary = Integer.parseInt(money);

        PreparedStatement preparedStatement = connection.prepareStatement(createJobPostQuery);
        preparedStatement.setString(1, jobTitle);
        preparedStatement.setString(2, jobDescription);
        preparedStatement.setString(3, jobRequirements);
        preparedStatement.setString(4, location);
        preparedStatement.setString(5, startDate);
        preparedStatement.setString(6, duration);
        preparedStatement.setInt(7, salary);


//        ResultSet resultSet = preparedStatement.executeUpdate();
        int resultSet = preparedStatement.executeUpdate();
        ResponseStatus responseStatus = new ResponseStatus();

        if(resultSet == 0){
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");

        }else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("Posted Job Successfully");
            responseStatus.setActionToDo("createJobPost");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
    public String getJobPosts(JsonNode requestData) throws Exception {
        String getJobPostsQuery = "select * from JobPosts";
        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode jobPostData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = jobPostData.fields();
        String jobTitle = iterator.next().toString().split("=")[1];
        String jobDescription = iterator.next().toString().split("=")[1];
        String jobRequirements = iterator.next().toString().split("=")[1];
        String location = iterator.next().toString().split("=")[1];
        String startDate = iterator.next().toString().split("=")[1];
        String duration = iterator.next().toString().split("=")[1];
        String money = iterator.next().toString().split("=")[1];
        int salary = Integer.parseInt(money);
        PreparedStatement preparedStatement = connection.prepareStatement(getJobPostsQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();

        if(!resultSet.next()){
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");

        }else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved the jobs successfully");
            responseStatus.setActionToDo("getJobPosts");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
