//
//package hiric.controllers.hiringcontrollers.jobpostingcontrollers;
//
//public class JobPostingActions {
//package controllers.hiring.jobPosting;
//
//import models.hiring.Job;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import dbconnection.OnlineDbConnection;
//import models.ResponseStatus;
//
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.Time;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.Iterator;
//import java.util.Map;
//
//public class JobPostingActions {
//    public JobPostingActions() throws Exception {}
//    public String getJobs(JsonNode requestData) throws Exception {
//        String getJobsQuery = "SELECT * FROM jobs";
//        Connection connection = new OnlineDbConnection().getConnection();
////        JsonNode jobsData = requestData.get("object");
////        Iterator<Map.Entry<String, JsonNode>> iterator = jobsData.fields();
////        String jobId = iterator.next().toString().split("=")[1];
////        Integer id= Integer.parseInt(jobId);
////        String title= iterator.next().toString().split("=")[1];
//        PreparedStatement preparedStatement = connection.prepareStatement(getJobsQuery);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        ResponseStatus responseStatus = new ResponseStatus();
//
//        if(!resultSet.next()){
//            responseStatus.setStatus(500);
//            responseStatus.setMessage("INTERNAL SERVER ERROR");
//            responseStatus.setActionToDo("Something went wrong");
//        }else {
//            responseStatus.setStatus(200);
//            responseStatus.setMessage("Retrieved Jobs successfully!");
//            responseStatus.setActionToDo("getJobs");
//            Job job = new Job();
//            while(resultSet.next()) {
//                job.setId(resultSet.getInt("id"));
//                job.setJobTitle(resultSet.getString("jobTitle"));
//            }
//            responseStatus.setObject(job);
//        }
//        return new ObjectMapper().writeValueAsString(responseStatus);
//    }
//
//    public String createJobPost(JsonNode requestData) throws Exception {
//        String createJobPostQuery = "INSERT INTO jobPosts(jobId, userId, jobTitle, jobDescription, jobRequirements, location, startDate, duration, salary) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        //initialise  db connection
//        Connection connection = new OnlineDbConnection().getConnection();
//        JsonNode jobPostData = requestData.get("object");
//        Iterator<Map.Entry<String, JsonNode>> iterator = jobPostData.fields();
//        String jobTitle = iterator.next().toString().split("=")[1];
//        String jobDescription = iterator.next().toString().split("=")[1];
//        String jobRequirements = iterator.next().toString().split("=")[1];
//        String location = iterator.next().toString().split("=")[1];
//        String date = iterator.next().toString().split("=")[1];
//        Date startDate=Date.valueOf(date);
//        String time = iterator.next().toString().split("=")[1];
//        Time duration = Time.valueOf(time);
//        String money = iterator.next().toString().split("=")[1];
//        Integer salary = Integer.parseInt(money);
//
//        PreparedStatement preparedStatement = connection.prepareStatement(createJobPostQuery);
//        preparedStatement.setString(1, jobTitle);
//        preparedStatement.setString(2, jobDescription);
//        preparedStatement.setString(3, jobRequirements);
//        preparedStatement.setString(4, location);
//        preparedStatement.setDate(5, startDate);
//        preparedStatement.setTime(6, duration);
//        preparedStatement.setInt(7, salary);
//
//        int resultSet = preparedStatement.executeUpdate();
//        ResponseStatus responseStatus = new ResponseStatus();
//
//        if(resultSet == 0){
//            responseStatus.setStatus(500);
//            responseStatus.setMessage("INTERNAL SERVER ERROR");
//            responseStatus.setActionToDo("Something went wrong");
//
//        }else {
//            responseStatus.setStatus(200);
//            responseStatus.setMessage("Posted Job Successfully");
//            responseStatus.setActionToDo("createJobPost");
//        }
//
//        return new ObjectMapper().writeValueAsString(responseStatus);
//    }
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
//}
