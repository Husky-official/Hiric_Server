package controllers.hiring.jobPosting;

import models.hiring.Job;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.io.FileReader;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JobPostingActions {
    public JobPostingActions() throws Exception {}
    public String getJobs(JsonNode requestData) throws Exception {
        String getJobsQuery = "SELECT * FROM jobs";
        Connection connection = new OnlineDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getJobsQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();

        if(resultSet.next()) {
            resultSet.beforeFirst();
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved Jobs successfully!");
            responseStatus.setActionToDo("getJobs");
            ArrayList<Job> jobs = new ArrayList<Job>();
            while(resultSet.next()) {
                jobs.add(new Job(resultSet.getInt("id"), resultSet.getString("jobTitle")));
            }
            System.out.println(jobs);
            responseStatus.setObject(jobs);
        }
        else {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
            return "0";

        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String createJobPost(JsonNode requestData) throws Exception {
        String createJobPostQuery = "INSERT INTO jobPosts(jobId, userId, jobTitle, jobDesc, jobRequirements, location, startDate, duration, salary, salaryType, workers) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //initialise  db connection
        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode jobPostData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = jobPostData.fields();
        System.out.println("iterator values: " + iterator.next().toString());
        String id = iterator.next().toString().split("=")[1];
        Integer jobId = Integer.parseInt(id);

        //get user id from file
        FileReader reader = new FileReader("dbConfig.properties");
        Properties storedProperties = new Properties();
        storedProperties.load(reader);
        String userrId = storedProperties.getProperty("userId");
        System.out.println(userrId);
        Integer userId = Integer.parseInt(userrId);
        String jobTitle = iterator.next().toString().split("=")[1];

        String jobDescription = iterator.next().toString().split("=")[1];

        String jobRequirements = iterator.next().toString().split("=")[1];

        String location = iterator.next().toString().split("=")[1];
        String date = iterator.next().toString().split("=")[1];
        java.sql.Date startDate = new java.sql.Date(Long.parseLong(date));
//        String stime = iterator.next().toString().split("=")[1];
//        System.out.println("stime: " + stime);
//        java.sql.Time startTime = java.sql.Time.valueOf(stime);
//        System.out.println(startTime);
        String duration = iterator.next().toString().split("=")[1];
        String money = iterator.next().toString().split("=")[1];
        System.out.println(money);
        int salary = Integer.parseInt(money);

        PreparedStatement preparedStatement = connection.prepareStatement(createJobPostQuery);
        preparedStatement.setInt(1,jobId);
        preparedStatement.setInt(2, userId);
        preparedStatement.setString(2, jobTitle);
        preparedStatement.setString(3, jobDescription);
        preparedStatement.setString(4, jobRequirements);
        preparedStatement.setString(5, location);
        preparedStatement.setDate(6, startDate);
//        preparedStatement.setTime(7, startTime);
        preparedStatement.setString(8, duration);
        preparedStatement.setInt(9, salary);

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
        Integer salary = Integer.parseInt(money);
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

    public void deleteJobPost(JsonNode requestData) throws Exception{

        String deleteJobPostQuery = "select * from JobPosts";
    }
}
