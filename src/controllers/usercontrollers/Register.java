/**
@author: UWENAYO Alain Pacifique
@description: Register controller to handle registration requests
@date:   25/04/2020
@version: 1.0
 */
package controllers.usercontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

/**
 * The type Register.
 */
public class Register {
    /**
     * Instantiates a new Register.
     */
    public Register() {}

    /**
     * Register string.
     *
     * @param request the request
     * @return the string
     * @throws Exception the exception
     */
    public static String register(JsonNode request) throws Exception {
        try{
        String UserRegisterQuery = "INSERT INTO users(firstName,lastName,email,gender,password,role,DOB) VALUES (?,?,?,?,?,?,?)";
        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode userDetails = request.get("object");
        String firstName = userDetails.get("firstName").asText();
        String lastName = userDetails.get("lastName").asText("");
        String email = userDetails.get("email").asText();
        String password = userDetails.get("password").asText();
        String gender = userDetails.get("gender").asText("MALE");
        String role = userDetails.get("role").asText("EMPLOYER");
        String DOB = new SimpleDateFormat("yyyy-MM-dd").format(userDetails.get("dob").asLong());
        PreparedStatement preparedStatement = connection.prepareStatement(UserRegisterQuery);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, gender);
        preparedStatement.setString(5, password);
        preparedStatement.setString(6, role);
        preparedStatement.setString(7, DOB);
        preparedStatement.executeUpdate();
        return new ObjectMapper().writeValueAsString(new ResponseStatus(200, "User registered successfully", "register"));
        }catch (Exception e){
            e.printStackTrace();
            return new ObjectMapper().writeValueAsString(new ResponseStatus(400, "Error:"+e.getMessage(), "register"));
        }
    }
}
