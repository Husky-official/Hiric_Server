package controllers.billing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.hiring.JobPosting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class PayrollActions {
    public static void main(String[] args) {

    }
    public String createAndSavePayroll(JsonNode request) throws Exception{
        Connection connection = new OnlineDbConnection().getConnection();
        String savePayrollQuery = "INSERT INTO Payroll(userID, jobID, amount, currency, createdAt ,invoiceID) values(?,?,?,?,?,?)";

        JsonNode payrollData = request.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = payrollData.fields();
        PreparedStatement preparedStatement = connection.prepareStatement(savePayrollQuery);
        return "";
    }
    public String listOfJobsByEmployer(JsonNode request, int userId) throws Exception{

        Connection connection = new OnlineDbConnection().getConnection();
        String listAllJobsQuery = "select * from jobPosts INNER JOIN jobs ON jobPosts.jobId = jobs.id WHERE userId = "+userId;

        PreparedStatement preparedStatement = connection.prepareStatement(listAllJobsQuery);
        ResultSet rs = preparedStatement.executeQuery();

        ResponseStatus responseStatus = new ResponseStatus();

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<JobPosting> userJobs = new ArrayList<JobPosting>();

        if (rs.next() == false) {
            responseStatus.setStatus(500);
            responseStatus.setMessage("NOT FOUND ERROR");
            responseStatus.setActionToDo("No jobs found for the user with that email.");
            return new ObjectMapper().writeValueAsString(responseStatus);
        } else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("Retrieved the job posts successfully");
            responseStatus.setActionToDo("getJobPosts");
            do {
                JobPosting jobPosting = new JobPosting(
                        rs.getInt("id"),
                        rs.getInt("jobId"),
                        rs.getInt("userId"),
                        rs.getString("jobDesc"),
                        "requirements",
                        rs.getInt("locationId"),
                        rs.getDate("startDate"),
                        rs.getTime("startTime"),
                        rs.getString("duration"),
                        rs.getInt("salary"),
                        rs.getString("salaryType"),
                        rs.getInt("workers"),
                        rs.getInt("paymentStatus"),
                        rs.getString("status")
                );
                userJobs.add(jobPosting);
            } while (rs.next());
        }
        responseStatus.setObject(userJobs);
        System.out.println(userJobs);

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
