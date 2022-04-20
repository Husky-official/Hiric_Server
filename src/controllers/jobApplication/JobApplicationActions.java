package controllers.jobApplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.Connection;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.hiring.Job;
import models.hiring.JobApplication;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class JobApplicationActions {
    static String createApplicationQuery = "INSERT INTO jobApplication(jobPostId, userId, locationId, paymentMethod, referenceName, referencePhone, resume, certificate)" +
            "VALUES(?,?,?,?,?,?,?,?)";

    public String getJobApplications(JsonNode requestData, int jobPostId) throws Exception {
        String getJobApplicationsQuery = "SELECT * FROM jobApplication INNER JOIN users_table ON jobApplication.userId = users_table.id WHERE jobPostId = " + jobPostId;
        Connection connection = (Connection) new OnlineDbConnection().getConnection();
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
    boolean searchID;
    public String searchById(JsonNode requestData,int jobPostId) throws Exception {

        String searchQuery="Select id from jobPosts where id = "+ jobPostId;
        Connection connection = (Connection) new OnlineDbConnection().getConnection();
        PreparedStatement stmt=connection.prepareStatement(searchQuery);
        ResultSet resultSet=stmt.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();

        if (!resultSet.next()) {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
            searchID=false;
        } else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("JOB POST ID RETRIEVED Successfully");
            responseStatus.setActionToDo("CreateApplication");
            searchID=true;
        }
        return new ObjectMapper().writeValueAsString(responseStatus);

    }
//    public String getProvinces(JsonNode requestData) throws Exception {
//        String getProvincesQuery = "SELECT * FROM Locations where levelId = 2";
//        Connection connection = new OnlineDbConnection().getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(getProvincesQuery);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        ResponseStatus responseStatus = new ResponseStatus();
//
//        if (resultSet.next()) {
//            resultSet.beforeFirst();
//            responseStatus.setStatus(200);
//            responseStatus.setMessage("Retrieved Provinces successfully!");
//            responseStatus.setActionToDo("getProvinces");
//            ArrayList<Location> locations = new ArrayList<Location>();
//            while (resultSet.next()) {
//                locations.add(new Location(resultSet.getInt("id"), resultSet.getInt("levelId"), resultSet.getString("location"), resultSet.getInt("upper_location")));
//            }
//            System.out.println(locations);
//            responseStatus.setObject(locations);
//
//        } else {
//            responseStatus.setStatus(500);
//            responseStatus.setMessage("INTERNAL SERVER ERROR");
//            responseStatus.setActionToDo("Something went wrong");
//            return "0";
//
//        }
//
//        return new ObjectMapper().writeValueAsString(responseStatus);
//    }

//    public String getNextLocation(JsonNode requestData) throws Exception {
//        String getLocationQuery = "SELECT * FROM Locations where upper_location = ?";
//        Connection connection = new OnlineDbConnection().getConnection();
//        JsonNode locationData = requestData.get("object");
//        Iterator<Map.Entry<String, JsonNode>> iterator = locationData.fields();
//        iterator.next();
//        iterator.next();
//        iterator.next();
//        String loc = iterator.next().toString().split("=")[1];
//        Integer upperLocation = Integer.parseInt(loc);
//        PreparedStatement preparedStatement = connection.prepareStatement(getLocationQuery);
//        preparedStatement.setInt(1, upperLocation);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        ResponseStatus responseStatus = new ResponseStatus();
//
//        if (resultSet.next()) {
//            resultSet.beforeFirst();
//            responseStatus.setStatus(200);
//            responseStatus.setMessage("Retrieved them successfully!");
//            responseStatus.setActionToDo("getNextLocation");
//            ArrayList<Location> locations = new ArrayList<Location>();
//            while (resultSet.next()) {
//                locations.add(new Location(resultSet.getInt("id"), resultSet.getInt("levelId"), resultSet.getString("location"), resultSet.getInt("upper_location")));
//            }
//            System.out.println(locations);
//            responseStatus.setObject(locations);
//
//        } else {
//            responseStatus.setStatus(500);
//            responseStatus.setMessage("INTERNAL SERVER ERROR");
//            responseStatus.setActionToDo("Something went wrong");
//            return "0";
//
//        }
//
//        return new ObjectMapper().writeValueAsString(responseStatus);
//    }
    public static String createApplication(JsonNode requestData) throws Exception {

        Connection connection = (Connection) new OnlineDbConnection().getConnection();
        JsonNode jobApplication = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = jobApplication.fields();
        System.out.println("iterator values: " + iterator.next().toString());
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
        String status=iterator.next().toString().split("=")[1];
        PreparedStatement preparedStatement = connection.prepareStatement(createApplicationQuery);
        preparedStatement.setInt(1, idJob);
        preparedStatement.setInt(2, userIdd);
        preparedStatement.setInt(3, id);
        preparedStatement.setString(4, paymentMethod);
        preparedStatement.setString(5, referenceName);
        preparedStatement.setString(6, referencePhone);
        preparedStatement.setString(7, resume);
        preparedStatement.setString(8, certificate);


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
        Connection connection = (Connection) new OnlineDbConnection().getConnection();
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