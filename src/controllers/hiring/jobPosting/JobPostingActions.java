package controllers.hiring.jobPosting;

import models.hiring.Job;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.hiring.JobPosting;
import models.hiring.Location;
import  models.hiring.LocationLevel;

import java.io.FileReader;
import java.sql.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class JobPostingActions {
    public JobPostingActions() throws Exception {
    }
    public String getProvinces(JsonNode requestData) throws Exception {
        String getProvincesQuery = "SELECT * FROM Locations where levelId = 2";
        Connection connection = new OnlineDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getProvincesQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();

        if (resultSet.next()) {
            resultSet.beforeFirst();
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved Provinces successfully!");
            responseStatus.setActionToDo("getProvinces");
            ArrayList<Location> locations = new ArrayList<Location>();
            while (resultSet.next()) {
                locations.add(new Location(resultSet.getInt("id"), resultSet.getInt("levelId"), resultSet.getString("location"), resultSet.getInt("upper_location")));
            }
            System.out.println(locations);
            responseStatus.setObject(locations);

        } else {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
            return "0";

        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String getNextLocation(JsonNode requestData) throws Exception {
        String getLocationQuery = "SELECT * FROM Locations where upper_location = ? ";
        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode locationData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = locationData.fields();
        iterator.next();
        iterator.next();
        iterator.next();
        String loc = iterator.next().toString().split("=")[1];
        Integer upperLocation = Integer.parseInt(loc);
        PreparedStatement preparedStatement = connection.prepareStatement(getLocationQuery);
        preparedStatement.setInt(1, upperLocation);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();

        if (resultSet.next()) {
            resultSet.beforeFirst();
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved them successfully!");
            responseStatus.setActionToDo("getNextLocation");
            ArrayList<Location> locations = new ArrayList<Location>();
            while (resultSet.next()) {
                locations.add(new Location(resultSet.getInt("id"), resultSet.getInt("levelId"), resultSet.getString("location"), resultSet.getInt("upper_location")));
            }
            System.out.println(locations);
            responseStatus.setObject(locations);

        } else {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
            return "0";

        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String getJobs(JsonNode requestData) throws Exception {
        String getJobsQuery = "SELECT * FROM jobs";
        Connection connection = new OnlineDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getJobsQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();

        if (resultSet.next()) {
            resultSet.beforeFirst();
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved Jobs successfully!");
            responseStatus.setActionToDo("getJobs");
            ArrayList<Job> jobs = new ArrayList<Job>();
            while (resultSet.next()) {
                jobs.add(new Job(resultSet.getInt("id"), resultSet.getString("jobTitle")));
            }
            System.out.println(jobs);
            responseStatus.setObject(jobs);
        } else {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
            return "0";

        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String getLocations(JsonNode requestData) throws Exception {
        String getLocationsQuery = "SELECT * FROM Locations";
        Connection connection = new OnlineDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getLocationsQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();

        if (resultSet.next()) {
            System.out.println("successfull!!");
            resultSet.beforeFirst();
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved Jobs successfully!");
            responseStatus.setActionToDo("getLocations");
            ArrayList<Location> locations = new ArrayList<Location>();
            while (resultSet.next()) {
                locations.add(new Location(resultSet.getInt("id"), resultSet.getInt("levelId"), resultSet.getString("location"), resultSet.getInt("upper_location")));
            }
            System.out.println(locations);
            responseStatus.setObject(locations);
        } else {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
            return "0";

        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String createJobPost(JsonNode requestData) throws Exception {
        String createJobPostQuery = "INSERT INTO jobPosts(jobId, userId, jobDesc, jobRequirements, locationId, startDate, startTime, duration, salary, salaryType, workers) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //initialise  db connection
        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode jobPostData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = jobPostData.fields();
        System.out.println("iterator values: " + iterator.next().toString());
        String id = iterator.next().toString().split("=")[1];
        System.out.println(id);
        Integer jobId = Integer.parseInt(id);
        System.out.println("jobId: " + jobId);
        //get user id from file
        FileReader reader = new FileReader("dbConfig.properties");
        Properties storedProperties = new Properties();
        storedProperties.load(reader);
        String userrId = storedProperties.getProperty("userId");
        Integer userId = Integer.parseInt(userrId);
        System.out.println("userId: " + userrId);
        System.out.println(iterator.next());
        String jobDescription = iterator.next().toString().split("=")[1];
        System.out.println("jobDescription: " + jobDescription);
        String jobRequirements = iterator.next().toString().split("=")[1];
        System.out.println("jobRequirements: " + jobRequirements);
        String location = iterator.next().toString().split("=")[1];
        System.out.println("location: " + location);
        String date = iterator.next().toString().split("=")[1];
        java.sql.Date startDate = new java.sql.Date(Long.parseLong(date));
        String sstime = iterator.next().toString().split("=")[1];
        String stime = sstime. replaceAll("^\"|\"$", "");
        System.out.println("stime: " + stime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime starTime = LocalTime.parse(stime, formatter);
        java.sql.Time startTime = java.sql.Time.valueOf(starTime);
//        java.sql.Time startTime = java.sql.Time.valueOf(LocalTime.parse("12:55:33"));
        System.out.println("startTime: " + startTime);
        String duration = iterator.next().toString().split("=")[1];
        System.out.println("duration: " + duration);
        String money = iterator.next().toString().split("=")[1];
        System.out.println("money: " + money);
        Integer salary = Integer.parseInt(money);
        String salaryType = iterator.next().toString().split("=")[1];
        System.out.println("salaryType: " + salaryType);
        String work = iterator.next().toString().split("=")[1];
        Integer workers = Integer.parseInt(work);
        System.out.println("workers: " + workers);

        PreparedStatement preparedStatement = connection.prepareStatement(createJobPostQuery);
        preparedStatement.setInt(1, jobId);
        preparedStatement.setInt(2, userId);
        preparedStatement.setString(3, jobDescription);
        preparedStatement.setString(4, jobRequirements);
        preparedStatement.setString(5, location);
        preparedStatement.setDate(6, startDate);
        preparedStatement.setTime(7, startTime);
        preparedStatement.setString(8, duration);
        preparedStatement.setInt(9, salary);
        preparedStatement.setString(10, salaryType);
        preparedStatement.setInt(11, workers);

        int resultSet = preparedStatement.executeUpdate();
        ResponseStatus responseStatus = new ResponseStatus();

        if (resultSet == 0) {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");

        } else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("Posted Job Successfully");
            responseStatus.setActionToDo("createJobPost");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String updateJobPost(JsonNode requestData) throws  Exception {
        String updateJobPostQuery = "UPDATE jobPosts SET ? = ? where id = ?";

        Integer jobPostId, jobId, location, salary, workers;
        String jobDesc, jobRequirements, duration, salaryType;
        Date startDate;
        Time startTime;

        Connection connection = new OnlineDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateJobPostQuery);
        JsonNode jobPostData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = jobPostData.fields();
        String id = iterator.next().toString().split("=")[1];
        System.out.println(id);
        jobPostId = Integer.parseInt(id);
        preparedStatement.setInt(3, jobPostId);
        String jobIdd = iterator.next().toString().split("=")[1];
        System.out.println(jobIdd);
        System.out.println(jobIdd.equals(null) ? "null": "not null");
        if(jobIdd == null) {
            jobId = Integer.parseInt(jobIdd);
            System.out.println(jobId);
            preparedStatement.setString(1,"jobId");
            preparedStatement.setInt(2,jobId);
        }
        iterator.next();
        jobDesc = iterator.next().toString().split("=")[1];
        System.out.println(jobDesc);
        if(jobDesc != null) {
            preparedStatement.setString(1,"jobDesc");
            preparedStatement.setString(2,jobDesc);
        }
        jobRequirements = iterator.next().toString().split("=")[1];
        System.out.println(jobRequirements);
        if(jobRequirements != null) {
            preparedStatement.setString(1,"jobRequirements");
            preparedStatement.setString(2,jobRequirements);
        }
        String loc = iterator.next().toString().split("=")[1];
        System.out.println(loc);
        if(loc != null) {
            location = Integer.parseInt(loc);
            preparedStatement.setString(1,"location");
            preparedStatement.setInt(2,location);
        }
        String starDate = iterator.next().toString().split("=")[1];
        System.out.println(starDate);
        if(starDate != null) {
            startDate = new java.sql.Date(Long.parseLong(starDate));
            preparedStatement.setString(1,"startDate");
            preparedStatement.setDate(2,startDate);
        }
        String sstime = iterator.next().toString().split("=")[1];
        System.out.println(sstime);
        if(sstime != null) {
            String stime = sstime. replaceAll("^\"|\"$", "");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime starTime = LocalTime.parse(stime, formatter);
            startTime = java.sql.Time.valueOf(starTime);
            preparedStatement.setString(1,"startTime");
            preparedStatement.setTime(2,startTime);
        }
        duration = iterator.next().toString().split("=")[1];
        System.out.println(duration);
        if(duration != null) {
            preparedStatement.setString(1,"duration");
            preparedStatement.setString(2,duration);
        }
        String sal = iterator.next().toString().split("=")[1];
        System.out.println(sal);
        if(sal != null) {
            salary = Integer.parseInt(sal);
            preparedStatement.setString(1,"salary");
            preparedStatement.setInt(2,salary);
        }
        salaryType = iterator.next().toString().split("=")[1];
        System.out.println(salaryType);
        if(salaryType != null) {
            preparedStatement.setString(1,"salaryType");
            preparedStatement.setString(2,salaryType);
        }
        String worker = iterator.next().toString().split("=")[1];
        System.out.println(worker);
        if(worker != null) {
            workers = Integer.parseInt(worker);
            preparedStatement.setString(1,"workers");
            preparedStatement.setInt(2,workers);
        }

        preparedStatement.setInt(3,jobPostId);
        int resultSet = preparedStatement.executeUpdate();
        ResponseStatus responseStatus = new ResponseStatus();

        if (resultSet == 0) {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");

        } else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("Updated job successfully!");
            responseStatus.setActionToDo("UpdateJobPost");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String getJobPostById(JsonNode requestData) throws Exception {
        String getJobPostByIdQuery = "select * from jobPosts where id = ?";
        System.out.println("reached here!!");
        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode jobPostData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = jobPostData.fields();
        iterator.next();
        String id = iterator.next().toString().split("=")[1];
        Integer jobPostId = Integer.parseInt(id);
        PreparedStatement preparedStatement = connection.prepareStatement(getJobPostByIdQuery);
        preparedStatement.setInt(1, jobPostId);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();

        if (!resultSet.next()) {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");

        } else {
            resultSet.beforeFirst();
            responseStatus.setStatus(200);
            responseStatus.setMessage("Got post by id successfully!");
            responseStatus.setActionToDo("getJobPostById");
            ArrayList<JobPosting> jobPosts = new ArrayList<JobPosting>();
            while (resultSet.next()) {
                Integer jobPostIdd = resultSet.getInt("id");
                Integer jobId = resultSet.getInt("jobId");
                String jobDesc = resultSet.getString("jobDesc");
                String jobRequirements = resultSet.getString("jobRequirements");
                Integer location = resultSet.getInt("locationId");
                Date startDate = resultSet.getDate("startDate");
                String duration = resultSet.getString("duration");
                Integer salary = resultSet.getInt("salary");
                String salaryType = resultSet.getString("salaryType");
                Integer workers = resultSet.getInt("workers");

                jobPosts.add(new JobPosting(jobPostIdd, jobId, jobDesc, jobRequirements, location, startDate, duration, salary, salaryType, workers));
            }
            System.out.println(jobPosts);
            responseStatus.setObject(jobPosts);
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String getJobPosts(JsonNode requestData) throws Exception {
        String getJobPostsQuery = "SELECT * from jobPosts";
        Connection connection = new OnlineDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getJobPostsQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println(resultSet.next());
        ResponseStatus responseStatus = new ResponseStatus();

        if (resultSet.next()) {
            resultSet.beforeFirst();
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved Jobs successfully!");
            responseStatus.setActionToDo("getJobPosts");
            ArrayList<JobPosting> jobPosts = new ArrayList<JobPosting>();
            while (resultSet.next()) {
                jobPosts.add(new JobPosting(resultSet.getInt("id"), resultSet.getInt("jobId"), resultSet.getString("jobDesc"), resultSet.getString("jobRequirements"), resultSet.getInt("locationId"), resultSet.getDate("startDate"), resultSet.getTime("startTime"), resultSet.getString("duration"), resultSet.getInt("salary"), resultSet.getString("salaryType"), resultSet.getInt("workers")));
            }
            System.out.println(jobPosts);
            responseStatus.setObject(jobPosts);
        } else {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
            return "0";

        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }


    public String deleteJobPost(JsonNode requestData) throws Exception {
        String deleteJobPostQuery = "UPDATE jobPosts SET status = 'DELETED' where id = ?";
        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode jobPostData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = jobPostData.fields();
        iterator.next();
        String id = iterator.next().toString().split("=")[1];
        Integer jobPostId = Integer.parseInt(id);
        PreparedStatement preparedStatement = connection.prepareStatement(deleteJobPostQuery);
        preparedStatement.setInt(1, jobPostId);
        int resultSet = preparedStatement.executeUpdate();
        ResponseStatus responseStatus = new ResponseStatus();

        if (resultSet == 0) {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");

        } else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("Deleted job successfully!");
            responseStatus.setActionToDo("deleteJobPost");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String getUserJobs(JsonNode requestData, int userId) throws Exception {
        String getJobPostsQuery = "select jobPosts.id, jobs.jobTitle, jobDesc, jobRequirements, salary, salaryType, Locations.location, startDate, startTime, duration, workers from jobPosts INNER JOIN jobs ON jobPosts.jobId = jobs.id INNER JOIN Locations on location = jobPosts.locationId = Locations.id WHERE userId = " + userId;
        Connection connection = new OnlineDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getJobPostsQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();
        if (!resultSet.next()) {
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");

        } else {
            resultSet.beforeFirst();
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved the job posts successfully");
            responseStatus.setActionToDo("getJobPosts");
            ArrayList<JobPosting> userJobs = new ArrayList<JobPosting>();
            while (resultSet.next()) {
                JobPosting jobPosting = new JobPosting(
                        resultSet.getInt("id"),
                        resultSet.getInt("jobId"),
                        resultSet.getInt("userId"),
                        resultSet.getString("jobDesc"),
                        resultSet.getString("jobRequirements"),
                        resultSet.getInt("locationId"),
                        resultSet.getDate("startDate"),
                        resultSet.getTime("startTime"),
                        resultSet.getString("duration"),
                        resultSet.getInt("salary"),
                        resultSet.getString("salaryType"),
                        resultSet.getInt("workers"),
                        resultSet.getInt("paymentStatus"),
                        resultSet.getString("status")
                );
                userJobs.add(jobPosting);
            }
            responseStatus.setObject(userJobs);
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}