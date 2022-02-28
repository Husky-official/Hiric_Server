package controllers.invoicecontrollers;
/**
 *@author: Ineza Jost Chance
 * @description : Invoice actions - generating and printing invoice
 * */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
        String invoiceNumberStr = iterator.next().toString().split("=")[1];
        Long invoiceNumber = Long.parseLong(invoiceNumberStr);
        String jobIdStr = iterator.next().toString().split("=")[1];
        int jobId = Integer.parseInt(jobIdStr);
        String userIdStr = iterator.next().toString().split("=")[1];
        int userId = Integer.parseInt(userIdStr);
        String InvoiceCreationQuery = "INSERT INTO invoices_table(invoiceNumber,jobId,userId, createdAt) values(?,?,?, curdate())";
        PreparedStatement preparedStatement = connection.prepareStatement(InvoiceCreationQuery);
        preparedStatement.setLong(1, invoiceNumber);
        preparedStatement.setInt(2, jobId);
        preparedStatement.setInt(3, userId);
        int rowsAffected = statement.executeUpdate(InvoiceCreationQuery);
        ResponseStatus responseStatus = new ResponseStatus();
        if((bool)rowsAffected){
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
}
