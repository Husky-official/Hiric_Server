package controllers.jobApplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.hiring.Job;
import models.hiring.JobApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class JobApplicationActions {
    String createApplicationQuery = "INSERT INTO jobApplication(id , jobPostId, userId, locationId, paymentMethod, referenceName, referencePhone, resume, certificate)" +
            "VALUES(?,?,?,?,?,?,?,?,?)";

    public String getJobApplications(JsonNode requestData, int jobPostId) throws Exception {
        String getJobApplicationsQuery = "SELECT * FROM jobApplication INNER JOIN users_table ON jobApplication.userId = users_table.id WHERE jobPostId = " + jobPostId;
        Connection connection = new OnlineDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getJobApplicationsQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();
        if(!resultSet.next()){
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");

        }else {
            resultSet.beforeFirst();
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved the job applications for the job post");
            responseStatus.setActionToDo("getJobPosts");
            ArrayList<JobApplication> jobApplications = new ArrayList<JobApplication>();
            while(resultSet.next()) {
                JobApplication jobApplication = new JobApplication(resultSet.getInt("id"), resultSet.getInt("jobPostId"), resultSet.getInt("userId"), resultSet.getString("paymentMethod"), resultSet.getInt("locationId"), resultSet.getString("referenceName"), resultSet.getString("referencePhone"), resultSet.getString("resume"), resultSet.getString("certificate"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"));
                jobApplications.add(jobApplication);
            }
            responseStatus.setObject(jobApplications);
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
    public String createApplication(JsonNode requestData) throws Exception {
        //initialise  db connection
        Connection connection = new OnlineDbConnection().getConnection();
//changing the object into string as it cannot pass in TCP channel as object
        JsonNode userData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
        String newId = iterator.next().toString().split("=")[1];
        int jobAppId = Integer.parseInt(newId);
        // String userId = iterator.next().toString().split("=")[1];
        String Id = iterator.next().toString().split("=")[1];
        int id = Integer.parseInt(Id);
        String userId = iterator.next().toString().split("=")[1];
        int userIdd = Integer.parseInt(userId);
        String jobId = iterator.next().toString().split("=")[1];
        int idJob = Integer.parseInt(jobId);
        String paymentMethod = iterator.next().toString().split("=")[1];
        String referenceName = iterator.next().toString().split("=")[1];
        String referencePhone = iterator.next().toString().split("=")[1];
        String resume = iterator.next().toString().split("=")[1];
        String certificate = iterator.next().toString().split("=")[1];
        PreparedStatement preparedStatement = connection.prepareStatement(createApplicationQuery);
        preparedStatement.setInt(1,jobAppId);
        preparedStatement.setInt(2, idJob);
        preparedStatement.setInt(3, userIdd);
        preparedStatement.setInt(4, id);
        preparedStatement.setString(5, paymentMethod);
        preparedStatement.setString(6, referenceName);
        preparedStatement.setString(7, referencePhone);
        preparedStatement.setString(8, resume);
        preparedStatement.setString(9, certificate);

        int resultSet = preparedStatement.executeUpdate();

        ResponseStatus responseStatus = new ResponseStatus();

        if (resultSet == 0) {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");

        } else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("Job created Successfully");
            responseStatus.setActionToDo("CreateApplication");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
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
            ArrayList<Job> jobs = new ArrayList<>();
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


}
