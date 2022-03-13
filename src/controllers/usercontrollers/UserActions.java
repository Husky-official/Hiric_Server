package controllers.usercontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static utils.ComparingPassword.checkPassword;

/**
 * @author: SHUMBUSHO David
 * @description: user login controller
 */
public class UserActions {
    public UserActions() throws Exception {}

    /*
        Our simple static class that demonstrates how to create and decode JWTs.
     */

    public String login(JsonNode requestData) throws Exception{

        try {
            //initialise  db connection
            Connection connection = new OnlineDbConnection().getConnection();

            JsonNode userData = requestData.get("object");
            Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();

            //getting password
            String userPassword = iterator.next().toString().split("=")[1];
//            System.out.println(userPassword);
//        System.out.println(userPassword.getClass().getSimpleName());

            //getting email
            String email = iterator.next().toString().split("=")[1];
            //query

            String loginUserQuery = "SELECT * FROM users_table WHERE email = " + email + "";
            PreparedStatement preparedstatement = connection.prepareStatement(loginUserQuery);
            ResultSet resultSet = preparedstatement.executeQuery();
//        System.out.println(resultSet);

            ResponseStatus responseStatus = new ResponseStatus();
            if (resultSet.next()) {
                //all queries
                //Authorize user query
                String tokenQuery = "insert into token (userid) values(" + resultSet.getString("id") + ")";
                //adding user in tokens table
                String checkIfUserIsLoggedIn = "select * from token where userid=" + resultSet.getString("id") + " and tokenused=false";
                PreparedStatement preparedstatement2 = connection.prepareStatement(checkIfUserIsLoggedIn);
                ResultSet rs = preparedstatement2.executeQuery();

                if (rs.next()) {
                    responseStatus.setStatus(200);
                    responseStatus.setMessage("You are already logged in.");
                    responseStatus.setActionToDo("Already in.");
                    return new ObjectMapper().writeValueAsString(responseStatus);
                }
                else {
                    //comparing password
                    boolean ok = checkPassword(userPassword.replaceAll("\"",""), resultSet.getString("password"));
                    if(!ok) {
                        responseStatus.setStatus(400);
                        responseStatus.setMessage("Invalid email or password");
                        responseStatus.setActionToDo("Login");
                        return new ObjectMapper().writeValueAsString(responseStatus);
                    }
                    //adding user in token's table
                    PreparedStatement preparedstatement3 = connection.prepareStatement(tokenQuery);
                    preparedstatement3.execute();
//                    preparedstatement4.execute();
                    responseStatus.setStatus(200);
                    responseStatus.setMessage("User logged in successfully");
                    responseStatus.setActionToDo("Login");
                }
            } else {
                responseStatus.setStatus(400);
                responseStatus.setMessage("Invalid email or password");
                responseStatus.setActionToDo("Something went wrong");
            }
            return new ObjectMapper().writeValueAsString(responseStatus);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            if(Objects.equals(e.getMessage(), "Invalid salt version")){
                return new ObjectMapper().writeValueAsString(new ResponseStatus(400, "Invalid email or password", "Login"));
            }
            return new ObjectMapper().writeValueAsString(new ResponseStatus(500, "Internal server error", "Login"));
        }
    }
    public String logout(JsonNode requestData) throws Exception{
        //initialise  db connection
        Connection connection = new OnlineDbConnection().getConnection();

        JsonNode userData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();

        //getting email
        String email = iterator.next().toString().split("=")[1];
        //query
        String logOutQuery = "SELECT * FROM users_table WHERE email = "+email+"";
        PreparedStatement preparedstatement = connection.prepareStatement(logOutQuery);
        ResultSet resultSet = preparedstatement.executeQuery();
//        System.out.println(resultSet);

        ResponseStatus responseStatus = new ResponseStatus();
        if(resultSet.next()) {
            //all queries
            //Authorize user query
            //logout query
            String logoutQuery = "update token set tokenused=true,loggedoutat=current_timestamp where userid="+resultSet.getString("id")+" and tokenused=false";
            //updating session
            String updateSession="update session set loggedoutat=current_timestamp where userid="+resultSet.getString("id")+"";
            PreparedStatement preparedstatement4 = connection.prepareStatement(logoutQuery);
//            PreparedStatement preparedstatement5 = connection.prepareStatement(updateSession);
            int result=preparedstatement4.executeUpdate();
//            System.out.println(result);

            if(result==1) {
                responseStatus.setStatus(200);
                responseStatus.setMessage("Logged out.");
                responseStatus.setActionToDo("Logged out successfully.");
//                preparedstatement5.executeUpdate();
                return new ObjectMapper().writeValueAsString(responseStatus);
            }
            responseStatus.setStatus(500);
            responseStatus.setMessage("Unable to log you out");
            responseStatus.setActionToDo("Something went wrong.");
        }else {
            responseStatus.setStatus(404);
            responseStatus.setMessage("User not found.");
            responseStatus.setActionToDo("Something went wrong");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
