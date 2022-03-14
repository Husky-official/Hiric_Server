package controllers.dashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.*;
import java.util.Iterator;
import java.util.Map;

public class DashboardActions {
    public DashboardActions() {};
    public static String adminDashboard() throws Exception {

        ResponseStatus responseStatus = new ResponseStatus();

        Connection connection = new OnlineDbConnection().getConnection();

        String getAllUsers = "SELECT firstName,lastName FROM `users_table`";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getAllUsers);

//        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.first()){
            responseStatus.setStatus(200);
            responseStatus.setActionToDo("GET ALL USERS");
            responseStatus.setMessage("You have successfully returned all users!");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");

            System.out.println("My name is "+firstName+" "+lastName+"/n");
        }else{
            responseStatus.setStatus(400);
            responseStatus.setActionToDo("GET ALL USERS");
            responseStatus.setMessage("No users found.");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
