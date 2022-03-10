package dbconnection;

import java.sql.*;

/**
<<<<<<< HEAD
 *author: DABAGIRE Valens
 * description : Provide the class to enable us to connect to online database
=======
 *@author: DABAGIRE Valens
 * @description : Provide the class to enable us to connect to online database
>>>>>>> 6089a1f77e6fea3736f330fcaa4a539ec80e15ba
 * */

public class OnlineDbConnection {
    public Connection getConnection() throws Exception {

        Connection connection = null;

        //read db connection properties from file

        try {
<<<<<<< HEAD
            String url = storedProperties.getProperty("dbUrl");
            String username = storedProperties.getProperty("dbUsername");
            String password = storedProperties.getProperty("dbPassword");
           // Class.forName("com.mysql.cj.jdbc.Driver");

//            Class.forName("com.mysql.cj.jdbc.Driver")
            Class.forName("com.mysql.jdbc.Driver");
=======
            String url = "jdbc:mysql://remotemysql.com:3306/ZKZ7qI2OW3?useSSL=false";
            String username = "ZKZ7qI2OW3";
            String password = "pWgWkTztns";

//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Class.forName("com.mysql.jdbc.Driver");

>>>>>>> 6089a1f77e6fea3736f330fcaa4a539ec80e15ba
            connection = DriverManager.getConnection(url, username, password);

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