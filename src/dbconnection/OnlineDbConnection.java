package dbconnection;

import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

/**
*@author: DABAGIRE Valens
 * @description : Provide the class to enable us to connect to online database
* */

public class OnlineDbConnection {
    public Connection getConnection() throws Exception {

        Connection connection = null;

        try {
            String url = "jdbc:mysql://localhost:3306/husky";
            String username = "root";
            String password = "mysql";

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("connection");
            return connection;
        } catch (SQLException e) {
            System.out.println("sql connection exception is occurring ... ");
            System.out.println("" + e);
        } catch (Exception e) {
            System.out.println("High level failure of the system .... ");
            System.out.println(""+e);
        }
        return connection;
    }

    /*
    CREATE NEW DATABASE CONNECTION
    * */

    public void checkDbConnection(Connection connection) throws Exception{
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select  * from test_tb");
        System.out.println(" -------------------------- TEST TABLE DATA  ------------------------- ");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("user");
            System.out.println(" Id : " + id + "\t\t name: " + name);
            System.out
                    .println(" ------------------------------------------------------------------------------------ ");
        }
    }

    public static void main(String[] args) throws Exception {
        OnlineDbConnection dbConnection = new OnlineDbConnection();
        System.out.println(dbConnection.getConnection());
        dbConnection.checkDbConnection(dbConnection.getConnection());
    }
}
