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

public class Register {
    public Register() {}

    public static String register(JsonNode request) throws Exception {
        String UserRegisterQuery = "INSERT INTO users_table(first_name,last_name,email,password,user_role,account_type,telephone) VALUES (?,?,?,?,?,?,?)";
        //initialise  db connection
        Connection connection = new OnlineDbConnection().getConnection();

        JsonNode userDetails = request.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userDetails.fields();

        String userId = iterator.next().toString().split("=")[1];
        int id = Integer.parseInt(userId);
        //System.out.println(userId);
        String userName = iterator.next().toString().split("=")[1];
        //System.out.println(userName);

        PreparedStatement preparedStatement = connection.prepareStatement(UserRegisterQuery);
        preparedStatement.setString(1, userName);
        preparedStatement.setInt(2, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        ResponseStatus responseStatus = new ResponseStatus();

        if(!resultSet.next()){
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");

        }else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("User Logged In Successfully");
            responseStatus.setActionToDo("Login");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
