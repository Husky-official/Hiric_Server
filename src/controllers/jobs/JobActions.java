package controllers.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.hiring.JobPosting;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class JobActions {

    public JobActions() throws Exception {}

    public String getJobs(JsonNode requestData) throws Exception{

        //initialise  db connection
        Connection connection = new OnlineDbConnection().getConnection();

        JsonNode userData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();

        String userId = iterator.next().toString().split("=")[1];
        int id = Integer.parseInt(userId);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM jobPosts WHERE userId = "+id);
        ResponseStatus responseStatus = new ResponseStatus();

        if(!resultSet.next()){
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");

        }else {
            ArrayList<JobPosting> jobPosts = new ArrayList<JobPosting>();
            while(resultSet.next()) {
                System.out.println("Hello world");
                String jobId = resultSet.getString("jobId");
                String user = resultSet.getString("userId");
                String jobTitle = resultSet.getString("jobTitle");
                String jobDescription = resultSet.getString("jobDescription");
                String jobRequirements = resultSet.getString("jobRequirements");
                String location = resultSet.getString("location");
                String startDate = resultSet.getString("startDate");
                String duration = resultSet.getString("duration");
                int salary = resultSet.getShort("salary");
                JobPosting jobPosting = new JobPosting(jobId, user, jobTitle, jobDescription, jobRequirements, location, startDate, duration, salary);
                jobPosts.add(jobPosting);
            }
            System.out.println(jobPosts);
            responseStatus.setStatus(200);
            responseStatus.setMessage("Jobs fetched");
            responseStatus.setActionToDo("get user jobs");
            responseStatus.setObject(new ReturnJobs(jobPosts));
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
