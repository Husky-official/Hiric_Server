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

    /*
        Our simple static class that demonstrates how to create and decode JWTs.
     */
    public String login(JsonNode requestData) throws Exception{
        //initialise  db connection
        Connection connection = new OnlineDbConnection().getConnection();

        JsonNode userData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
        //getting password
        String userPassword = iterator.next().toString().split("=")[1];
//        System.out.println(userPassword);
//        System.out.println(userPassword.getClass().getSimpleName());
        //getting email
        String email = iterator.next().toString().split("=")[1];
//        System.out.println(email);
//        System.out.println(email.getClass().getSimpleName());
        //query
        String loginUserQuery = "SELECT * FROM users_table WHERE email = "+email+" and password= "+userPassword+"";
        PreparedStatement preparedstatement = connection.prepareStatement(loginUserQuery);
        ResultSet resultSet = preparedstatement.executeQuery();
//        System.out.println(resultSet);
        ResponseStatus responseStatus = new ResponseStatus();
        if(resultSet.next()) {
            String tokenQuery="insert into token (userid) values("+resultSet.getString("id")+")";
            String checkIfUserIsLoggedIn="select * from token where userid="+resultSet.getString("id")+"";
            PreparedStatement preparedstatement2 = connection.prepareStatement(checkIfUserIsLoggedIn);
            ResultSet rs=preparedstatement2.executeQuery();
            if(rs.next()) {
                responseStatus.setStatus(200);
                responseStatus.setMessage("You are already logged in.");
                responseStatus.setActionToDo("Already in.");
                return new ObjectMapper().writeValueAsString(responseStatus);
            }
            PreparedStatement preparedstatement3 = connection.prepareStatement(tokenQuery);
            preparedstatement3.execute();
            responseStatus.setStatus(200);
            responseStatus.setMessage("User logged in successfully");
            responseStatus.setActionToDo("Login");
        }else {
            responseStatus.setStatus(400);
            responseStatus.setMessage("Invalid email or password");
            responseStatus.setActionToDo("Something went wrong");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}