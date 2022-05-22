package controllers.usercontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

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
//       Token token=new Token();
//        System.out.println(token.getToken().getFname());
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
                    //creating file
                    File myFile=new File("token.txt");
                    if (myFile.createNewFile()){
                        System.out.println("file created "+myFile.getName());
                        FileWriter myWriter=new FileWriter("token.txt");
                        myWriter.write(resultSet.getString("id")+"\n");
//                        myWriter.write("fname:"+resultSet.getString("firstName")+"\n");
//                        myWriter.write("lname:"+resultSet.getString("lastName")+"\n");
//                        myWriter.write("gender:"+resultSet.getString("gender")+"\n");
//                        myWriter.write("role:"+resultSet.getString("role")+"\n");
//                        myWriter.write("status:"+resultSet.getString("user_status")+"\n");
                        myWriter.close();
                        System.out.println("wrote to file");
                    }
                    else {
                        System.out.println("File already exists.");
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
                //deleting token.txt
                File myFile=new File("token.txt");
                myFile.delete();
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
 /**
  * @author: KAYIGIRE NGABIRE Kethia
  * @description Updating the user and deleting a user
 */

    public static String deleteUser(JsonNode requestData) throws Exception{
        Connection connection = new OnlineDbConnection().getConnection();

        JsonNode userToDelete = requestData.get("object");
        //getting user email
        String email = userToDelete.get("email").asText();
//            System.out.println("This email is from client to delete: "+email);

        String deleteUserQuery = "Update users_table set user_status = ? where email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteUserQuery);
        preparedStatement.setString(1, "Inactive");
        preparedStatement.setString(2, email);
        int res = preparedStatement.executeUpdate();
        ResponseStatus responseStatus = new ResponseStatus();
        if (res == 1) {
            responseStatus.setStatus(200);
            responseStatus.setMessage("User Deleted Successfully.");
            responseStatus.setActionToDo("deleteUser");
        } else {
            responseStatus.setStatus(400);
            responseStatus.setMessage("Something went wrong!");
            responseStatus.setActionToDo("Unable to delete the user.");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
    public static String updateUser(JsonNode request) throws Exception {
        Connection connection = new OnlineDbConnection().getConnection();

        JsonNode userUpdate = request.get("object");
        // getting the email to be updated;
        String email = userUpdate.get("id").asText();

        //getting the user information that is to be updated
        String firstName = userUpdate.get("firstName").asText();
        String lastName = userUpdate.get("lastName").asText();
        String newEmail = userUpdate.get("email").asText();
        String gender = userUpdate.get("gender").asText();
        String role = userUpdate.get("role").asText();
        String DOB = new SimpleDateFormat("yyyy-MM-dd").format(userUpdate.get("dob").asLong());

        String updateUserQuery = "Update users_table set firstName = ?, lastName = ?, gender = ?, email = ?, role = ? and DOB = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, gender);
        preparedStatement.setString(4, newEmail);
        preparedStatement.setString(5, role);
        preparedStatement.setString(6, DOB);
        preparedStatement.setString(7, email);

        int result = preparedStatement.executeUpdate();
        ResponseStatus responseStatus = new ResponseStatus();
        if (result == 1) {
            responseStatus.setStatus(200);
            responseStatus.setMessage("User information is updated!");
            responseStatus.setActionToDo("updateUser");
        } else {
            responseStatus.setStatus(400);
            responseStatus.setMessage("User information not updated!");
            responseStatus.setActionToDo("Something went wrong.");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String getToken() throws Exception{
        try {
            File myFile = new File("token.txt");
            Scanner myReader = new Scanner(myFile);
            if (myFile.exists()) {
                while(myReader.hasNextLine()){
                    String data = myReader.nextLine();
                    System.out.println(data);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("You are not logged in.");
        }
        return "";
    }
    public String tokenExist(JsonNode requestData) throws Exception{
        //initialise  db connection
        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode userData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
        ResponseStatus responseStatus = new ResponseStatus();
        //if  file exist
        File myFile=new File("token.txt");
        if (myFile.exists()) {
            responseStatus.setStatus(200);
            responseStatus.setMessage("Token exists");
            responseStatus.setActionToDo("Checking token.");
        }
        else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("No token found");
            responseStatus.setActionToDo("Checking token.");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
