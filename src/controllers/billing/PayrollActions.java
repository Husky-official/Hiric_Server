package controllers.billing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        String listAllJobsQuery = "Select * from jobPosts where userId = ?";
        JsonNode payrollData = request.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = payrollData.fields();
        iterator.next();

        String employerId = iterator.next().toString().split("=")[1];
        System.out.println(employerId);
        return employerId;
    }
}
