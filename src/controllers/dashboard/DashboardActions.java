package controllers.dashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class DashboardActions {
    public DashboardActions() {};
    public static String adminDashboard() throws Exception {

        ResponseStatus responseStatus = new ResponseStatus();

        Connection connection = new OnlineDbConnection().getConnection();

        String getAllUsers = "SELECT * FROM `users_table`";

        PreparedStatement preparedStatement = connection.prepareStatement(getAllUsers);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.first()){
            responseStatus.setStatus(200);
            responseStatus.setActionToDo("GET ALL USERS");
            responseStatus.setMessage("You have successfully returned all users!");
        }else{
            responseStatus.setStatus(400);
            responseStatus.setActionToDo("GET ALL USERS");
            responseStatus.setMessage("No users found.");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
