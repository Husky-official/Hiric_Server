package controllers.jobApplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.hiring.JobPosting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JobApplicationActions {
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
}
