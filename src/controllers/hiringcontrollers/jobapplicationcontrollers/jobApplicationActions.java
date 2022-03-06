package controllers.hiringcontrollers.jobapplicationcontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.hiring.JobApplication;

/**
 *@author: ITETERO Ariane
 * @description : The action to insert into database the job application details
 * */
//actions the user has to first log in then chooses id according to job post


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class jobApplicationActions {
    String createApplicationQuery = "INSERT INTO jobApplication(id , jobPostId, userId, locationId, paymentMethod, referenceName, referencePhone, resume, certificate)" +
            "VALUES(?,?,?,?,?,?,?,?,?)";

    String viewPostsQuery = "Select * from jobPosts";
    String viewApplicationsQuery = "Select * from jobApplication";

    public jobApplicationActions() throws Exception {
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
        String certicate = iterator.next().toString().split("=")[1];
        PreparedStatement preparedStatement = connection.prepareStatement(createApplicationQuery);
        preparedStatement.setInt(1,jobAppId);
        preparedStatement.setInt(2, idJob);
        preparedStatement.setInt(3, userIdd);
        preparedStatement.setInt(4, id);
        preparedStatement.setString(5, paymentMethod);
        preparedStatement.setString(6, referenceName);
        preparedStatement.setString(7, referencePhone);
        preparedStatement.setString(8, resume);
        preparedStatement.setString(9, certicate);

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

//    public String getJobPosts(JsonNode requestData) throws Exception {
//        String getJobPostsQuery = "select * from JobPosts";
//        Connection connection = new OnlineDbConnection().getConnection();
//        JsonNode jobPostData = requestData.get("object");
//        Iterator<Map.Entry<String, JsonNode>> iterator = jobPostData.fields();
//        String jobTitle = iterator.next().toString().split("=")[1];
//        String jobDescription = iterator.next().toString().split("=")[1];
//        String jobRequirements = iterator.next().toString().split("=")[1];
//        String location = iterator.next().toString().split("=")[1];
//        String startDate = iterator.next().toString().split("=")[1];
//        String duration = iterator.next().toString().split("=")[1];
//        String money = iterator.next().toString().split("=")[1];
//        Integer salary = Integer.parseInt(money);
//        PreparedStatement preparedStatement = connection.prepareStatement(getJobPostsQuery);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        ResponseStatus responseStatus = new ResponseStatus();
//
//        if(!resultSet.next()){
//            responseStatus.setStatus(500);
//            responseStatus.setMessage("INTERNAL SERVER ERROR");
//            responseStatus.setActionToDo("Something went wrong");
//
//        }else {
//            responseStatus.setStatus(200);
//            responseStatus.setMessage("Retrieved the jobs successfully");
//            responseStatus.setActionToDo("getJobPosts");
//        }
//    }

    public String viewApplications(JsonNode requestData) throws Exception {
        Connection connection = new OnlineDbConnection().getConnection();
//changing the object into string as it cannot pass in TCP channel as object
//        JsonNode userData = requestData.get("object");
//        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
//        String newId = iterator.next().toString().split("=")[1];
//        int jobAppId = Integer.parseInt(newId);
//        // String userId = iterator.next().toString().split("=")[1];
//        String Id = iterator.next().toString().split("=")[1];
//        int id = Integer.parseInt(Id);
//        String userId = iterator.next().toString().split("=")[1];
//        int userIdd = Integer.parseInt(userId);
//        String jobId = iterator.next().toString().split("=")[1];
//        int idJob = Integer.parseInt(jobId);
//        String paymentMethod = iterator.next().toString().split("=")[1];
//        String referenceName = iterator.next().toString().split("=")[1];
//        String referencePhone = iterator.next().toString().split("=")[1];
//        String resume = iterator.next().toString().split("=")[1];
//        String certicate = iterator.next().toString().split("=")[1];
        PreparedStatement preparedStatement = connection.prepareStatement(viewApplicationsQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();
        if (!resultSet.isBeforeFirst()) {
            responseStatus.setStatus(404);
            responseStatus.setMessage("No users found");
            responseStatus.setActionToDo("Something went wrong");
        }
        responseStatus.setStatus(200);
        responseStatus.setMessage("applications found");
        responseStatus.setActionToDo("viewApplications");

        ArrayList<JobApplication> applicationList=new ArrayList<>();
        while (resultSet.next()) {
            JobApplication app=new JobApplication();

            app.setId(resultSet.getInt("id"));
            app.setUserId(resultSet.getInt("userId"));
            app.setJobPostId(resultSet.getInt("jobPostId"));
            app.setLocationId(resultSet.getInt("locationId"));
            app.setPaymentMethod(resultSet.getString("paymentMethod"));
            app.setReferenceName(resultSet.getString("referenceName"));
            app.setReferencePhone(resultSet.getString("referencePhone"));
            app.setCertificate(resultSet.getString("certificate"));
            app.setResume(resultSet.getString("resume"));
           applicationList.add(app);
        }
         responseStatus.setObject(applicationList);

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
