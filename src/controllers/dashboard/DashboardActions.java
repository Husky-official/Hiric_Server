package controllers.dashboard;

import dbconnection.OnlineDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class DashboardActions {
    public DashboardActions() {};
    public static String adminDashboard() throws Exception {

        Connection connection = new OnlineDbConnection().getConnection();

        String getAllUsers = "SELECT * FROM users_table";
        PreparedStatement preparedStatement = connection.prepareStatement(getAllUsers);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println(resultSet);
        return getAllUsers;
    }
}
