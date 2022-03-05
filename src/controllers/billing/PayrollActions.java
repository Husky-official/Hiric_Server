package controllers.billing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.hiring.JobPosting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public String listOfJobsByEmployer(JsonNode request) throws Exception{

        Connection connection = new OnlineDbConnection().getConnection();
        String listAllJobsQuery = "SELECT * FROM `jobPosts` as jp INNER JOIN users as us ON us.id = jp.userId where us.email = ?";
        JsonNode payrollData = request.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = payrollData.fields();
        iterator.next();

        String employerEmail = iterator.next().toString().split("=")[1];
        PreparedStatement preparedStatement = connection.prepareStatement(listAllJobsQuery);
        preparedStatement.setString(1, employerEmail);
        ResultSet rs = preparedStatement.executeQuery();

        ResponseStatus responseStatus = new ResponseStatus();

        if(!rs.next()){
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Creating payment failed, try again later.");

        }else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("Payment done Successfully");
            responseStatus.setActionToDo("Payment");
            JobPosting jobPosting = new JobPosting();
            jobPosting.setJobId(String.valueOf(rs.getInt("jobId")));
            responseStatus.setObject(jobPosting);
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
