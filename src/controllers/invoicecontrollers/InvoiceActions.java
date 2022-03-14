package controllers.invoicecontrollers;
/**
 *@author: Ineza Jost Chance
 * @description : Invoice actions - generating and printing invoice
 * */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.InvoiceContent;
import models.ResponseStatus;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;


public class InvoiceActions {
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    public InvoiceActions() throws Exception {
        connect();
    }

    public void connect() throws Exception {
        Connection connection = new OnlineDbConnection().getConnection();
    }

    public String createAndStoreInvoiceInDB(JsonNode requestData) throws Exception{
        JsonNode userData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
        String invoiceNumber = iterator.next().toString().split("=")[1];
        String jobIdStr = iterator.next().toString().split("=")[1];
        int jobId = Integer.parseInt(jobIdStr);
        String userIdStr = iterator.next().toString().split("=")[1];
        int userId = Integer.parseInt(userIdStr);
        String invoiceCreationQuery = "INSERT INTO invoices_table(invoiceNumber,jobId,userId, createdAt) values(?,?,?, curdate())";
        PreparedStatement preparedStatement = connection.prepareStatement(invoiceCreationQuery);
        preparedStatement.setString(1, invoiceNumber);
        preparedStatement.setInt(2, jobId);
        preparedStatement.setInt(3, userId);
        int rowsAffected = statement.executeUpdate(invoiceCreationQuery);
        ResponseStatus responseStatus = new ResponseStatus();
        if(rowsAffected == 1){
            responseStatus.setStatus(200);
            responseStatus.setMessage("Invoice Created Successfully");
            responseStatus.setActionToDo("Create invoice");
        }else{
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong with the server");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
    public String getInvoiceDetails(JsonNode request) throws Exception{
        JsonNode userData = request.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
        String invoiceNumber = iterator.next().toString().split("=")[1];
        String invoiceDetailsQuery = "SELECT inv.*, jo.*, us.* FROM `invoices_table` as inv INNER JOIN jobPosts as jo ON jo.jobId = inv.jobId INNER JOIN users_table as us ON inv.userId = us.id where inv.invoiceNumber = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(invoiceDetailsQuery);
        preparedStatement.setString(1,invoiceNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        InvoiceContent invoiceContent = new InvoiceContent();
        while(resultSet.next()){
            invoiceContent.setInvoiceId(resultSet.getInt("invoiceId"));
            invoiceContent.setInvoiceNumber(resultSet.getString("invoiceNumber"));
            invoiceContent.setJobId(resultSet.getInt("jobId"));

            Date date = resultSet.getDate("createdAt");
            invoiceContent.setCreatedAt(date);
            invoiceContent.setJobTitle(resultSet.getString("jobTitle"));
            invoiceContent.setJobDescription(resultSet.getString("jobDescription"));
            invoiceContent.setJobRequirements(resultSet.getString("jobRequirements"));
            invoiceContent.setLocation(resultSet.getString("location"));
            invoiceContent.setDuration(resultSet.getString("duration"));
            invoiceContent.setSalary(resultSet.getLong("salary"));
            invoiceContent.setFirstname(resultSet.getString("firstname"));
            invoiceContent.setLastname(resultSet.getString("lastname"));
            invoiceContent.setEmail(resultSet.getString("email"));
        }
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatus(200);
        responseStatus.setMessage("Returned invoice content");
        responseStatus.setObject(invoiceContent);
        responseStatus.setActionToDo("Print invoice");

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
