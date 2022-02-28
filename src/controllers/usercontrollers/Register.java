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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.Map;

public class Register {
    public Register() {}
    public static String register(JsonNode request) throws Exception {
        try{
        String UserRegisterQuery = "INSERT INTO users_table(first_name,last_name,email,password,user_role,account_type,telephone) VALUES (?,?,?,?,?,?,?)";
        Connection connection = new OnlineDbConnection().getConnection();
        System.out.println(request);
        JsonNode userDetails = request.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userDetails.fields();
        String firstName = "";
        String lastName = "";
        String email = "";
        String password = "";
        String userRole = "";
        String accountType = "";
        String telephone = "";

        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            if (key.equals("firstName")) {
                firstName = value.asText();
            }
            if (key.equals("lastName")) {
                lastName = value.asText();
            }
            if (key.equals("email")) {
                email = value.asText();
            }
            if (key.equals("password")) {
                password = value.asText();
            }
            if (key.equals("userRole")) {
                userRole = value.asText();
            }
            if (key.equals("accountType")) {
                accountType = value.asText();
            }
            if (key.equals("telephone")) {
                telephone = value.asText();
            }
        }

        PreparedStatement preparedStatement = connection.prepareStatement(UserRegisterQuery);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, password);
        preparedStatement.setString(5, userRole);
        preparedStatement.setString(6, accountType);
        preparedStatement.setString(7, telephone);
        preparedStatement.executeUpdate();
        connection.close();
        return new ObjectMapper().writeValueAsString(new ResponseStatus(200, "User registered successfully", "register"));
        }catch (Exception e){
            e.printStackTrace();
            return new ObjectMapper().writeValueAsString(new ResponseStatus(400, "Error", "register"));
        }
    }
}
