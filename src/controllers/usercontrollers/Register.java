/*
@author: UWENAYO Alain Pacifique
@description: Register controller
@date:   25/04/2020
 */
package controllers.usercontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Register {
    public Register() {}
    public static String register(JsonNode request) throws Exception {
        try{
        String UserRegisterQuery = "INSERT INTO users_table(first_name,last_name,email,password,account_type,telephone) VALUES (?,?,?,?,?,?)";
        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode userDetails = request.get("object");
        String firstName = userDetails.get("firstName").asText();
        String lastName = userDetails.get("lastName").asText("");
        String email = userDetails.get("email").asText();
        String password = userDetails.get("password").asText();
        int accountType = userDetails.get("accountType").asInt(1);
        int telephone = userDetails.get("telephone").asInt();
        System.out.println(firstName+" "+lastName+" "+email+" "+""+password+" "+accountType+" "+telephone);
        PreparedStatement preparedStatement = connection.prepareStatement(UserRegisterQuery);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, password);
        preparedStatement.setInt(5, accountType);
        preparedStatement.setInt(6, telephone);
        preparedStatement.executeUpdate();
        return new ObjectMapper().writeValueAsString(new ResponseStatus(200, "User registered successfully", "register"));
        }catch (Exception e){
            e.printStackTrace();
            return new ObjectMapper().writeValueAsString(new ResponseStatus(400, "Error", "register"));
        }
    }
}
