package controllers.billing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        JsonNode payrollData = request.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = payrollData.fields();
        iterator.next();

        StringBuffer sb = new StringBuffer(iterator.next().toString().split("=")[1]);
        sb.deleteCharAt(sb.length()-1);
        String employerEmail = sb.toString().substring(1, sb.length());
        System.out.println(employerEmail);

        String listAllJobsQuery = "SELECT * FROM `jobPosts` as jp INNER JOIN users_table as us ON us.id = jp.userId where us.email = '" + employerEmail +"' ";

        PreparedStatement preparedStatement = connection.prepareStatement(listAllJobsQuery);
        ResultSet rs = preparedStatement.executeQuery();

        ResponseStatus responseStatus = new ResponseStatus();

        int i = 1;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        if (rs.next() == false) {
            responseStatus.setStatus(500);
            responseStatus.setMessage("NOT FOUND ERROR");
            responseStatus.setActionToDo("No jobs found for the user with that email.");
            return new ObjectMapper().writeValueAsString(responseStatus);
        } else {
            do {
                JobPosting jobPosting = new JobPosting();
                responseStatus.setStatus(200);
                responseStatus.setMessage("Payment done Successfully");
                responseStatus.setActionToDo("Payment");
                jobPosting.setJobId(String.valueOf(rs.getInt("jobId")) + ", ");
                jobPosting.setJobDescription(rs.getString("jobDesc"));
                jobPosting.setSalary(rs.getDouble("salary"));
                jobPosting.setUserId(rs.getString("userId"));
                objectNode.put(""+i, String.valueOf(jobPosting));
                i++;
            } while (rs.next());
        }
        responseStatus.setObject(objectNode);
        System.out.println(objectNode);

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
