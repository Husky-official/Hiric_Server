package controllers.usercontrollers;

import dbconnection.OnlineDbConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Token {
    Connection connection = new OnlineDbConnection().getConnection();

    public Token() throws Exception {
    }

    public LoggedInUser getToken() throws Exception{
        LoggedInUser returnValue=new LoggedInUser();
        try {
            File myFile = new File("token.txt");
            Scanner myReader = new Scanner(myFile);
            if (myFile.exists()) {
                while(myReader.hasNextLine()){
                    String data = myReader.nextLine();
                    System.out.println(data);
                    String loginUserQuery = "SELECT * FROM users_table WHERE id = " + data + "";
                    PreparedStatement preparedstatement = connection.prepareStatement(loginUserQuery);
                    ResultSet resultSet = preparedstatement.executeQuery();
                    if (resultSet.next()){
                        returnValue.setId(resultSet.getString("id"));
                        returnValue.setFname(resultSet.getString("firstname"));
                        returnValue.setLname(resultSet.getString("lastname"));
                        returnValue.setGender(resultSet.getString("gender"));
                        returnValue.setRole(resultSet.getString("role"));
                        returnValue.setStatus(resultSet.getString("user_status"));
                        return returnValue;
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("You are not logged in.");
        }
        return returnValue;
    }
}