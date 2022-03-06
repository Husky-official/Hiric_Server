package controllers.usercontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

public class UserActions {
    public UserActions() throws Exception {}

    public String login(JsonNode requestData) throws Exception{
       //query
        String loginUserQuery = "SELECT * FROM users_table WHERE email = ? AND password =?";
        //initialise  db connection
        Connection connection = new OnlineDbConnection().getConnection();

        JsonNode userData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();

        //getting password
        String userPassword = iterator.next().toString().split("=")[1];
//        System.out.println(userPassword);
       //getting email
        String email = iterator.next().toString().split("=")[1];
//        System.out.println(email);
        PreparedStatement preparedStatement = connection.prepareStatement(loginUserQuery);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, userPassword);
        ResultSet resultSet = preparedStatement.executeQuery();
//        System.out.println(resultSet);
        ResponseStatus responseStatus = new ResponseStatus();

        if(!resultSet.next()){
            responseStatus.setStatus(404);
            responseStatus.setMessage("Invalid email or password");
            responseStatus.setMessage("No user with provided credentials");
            responseStatus.setActionToDo("Something went wrong");

        }else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("User logged in successfully");
            responseStatus.setActionToDo("Login");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
