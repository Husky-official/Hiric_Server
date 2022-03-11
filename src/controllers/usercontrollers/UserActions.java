package controllers.usercontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
//decode password
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
//            System.out.println(email);
//        System.out.println(email.getClass().getSimpleName());

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
                //adding in session's table
//                String addingInSession = "insert into session(userid) values(" + resultSet.getString("id") + ")";
//                PreparedStatement preparedstatement4 = connection.prepareStatement(addingInSession);
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
                    boolean ok = Boolean.parseBoolean(checkPassword(userPassword, resultSet.getString("password")));
                    System.out.println( Boolean.parseBoolean(checkPassword(userPassword, resultSet.getString("password"))));
                    if(ok) {
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
                responseStatus.setMessage("Invalid email or passwordfghj");
                responseStatus.setActionToDo("Something went wrong");
            }
            return new ObjectMapper().writeValueAsString(responseStatus);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ObjectMapper().writeValueAsString(new ResponseStatus(500, "Internal server error", "Login"));
        }
    }
    public String logout(JsonNode requestData) throws Exception{
        //initialise  db connection
        Connection connection = new OnlineDbConnection().getConnection();

        JsonNode userData = requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
//        System.out.println(userData);

        //getting email
        String email = iterator.next().toString().split("=")[1];
//       System.out.println(email);
//        System.out.println(email.getClass().getSimpleName());

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
    static Integer userId=0;
    public static String sendEmail(JsonNode requestData) throws Exception{
        // @author=Beulla
        Connection connection=new OnlineDbConnection().getConnection();
        JsonNode userData=requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
        String id = iterator.next().toString().split("=")[1];
        String email = iterator.next().toString().split("=")[1];
        String query="SELECT * FROM users_table where email="+email;

        Statement statement=connection.createStatement();
        statement.execute(query);
        ResultSet resultSet=statement.executeQuery(query);
        ResponseStatus responseStatus = new ResponseStatus();
        if(resultSet.next()){
            userId=resultSet.getInt("id");
            String token= UUID.randomUUID().toString();

            String tokenInsertionQuery="insert into token values ('"+userId+"','"+token+"');";
            statement.executeUpdate(tokenInsertionQuery);
            String recipient=email;
            String sender="beullarugero8@gmail.com";

            Properties properties=System.getProperties();
            String host="127.0.0.1";
            properties.setProperty("mail.smtp.host","smtp.gmail.com");
            properties.setProperty("mail.smtp.port","587");
            properties.setProperty("mail.smtp.socketFactory.port","587");
            properties.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.smtp.starttls.enable","true");
            properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
            properties.setProperty("mail.smtp.user","beullarugero8@gmail.com");
            properties.setProperty("mail.smtp.auth","true");

            Session session=Session.getDefaultInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication("beullarugero8@gmail.com","increaseby2");
                }
            });

            try{

                MimeMessage message=new MimeMessage(session);
                message.setFrom(new InternetAddress(sender));
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
                message.setSubject("Password reset");
                message.setText(token);
                Transport.send(message);
                responseStatus.setStatus(200);
                responseStatus.setMessage("EMAIL SENT TO THE PROVIDED ADDRESS");
                responseStatus.setActionToDo("check your email for continuation with password verification");
            }
            catch(MessagingException mex){
                mex.printStackTrace();
                responseStatus.setStatus(500);
                responseStatus.setMessage("INTERNAL SERVER ERROR");
                responseStatus.setActionToDo("try again");
            }

        }
        else{
            responseStatus.setStatus(404);
            responseStatus.setMessage("INVALID EMAIL PROVIDED");
            responseStatus.setActionToDo("try again");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public static String verifyToken(JsonNode requestData) throws Exception{
        // @author=Beulla
        Connection connection=new OnlineDbConnection().getConnection();
        JsonNode userData=requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
        String id = iterator.next().toString().split("=")[1];
        String email = iterator.next().toString().split("=")[1];
        String token=iterator.next().toString().split("=")[1];
        String query="SELECT * FROM token where tokenValue="+token;

        Statement statement=connection.createStatement();
        statement.execute(query);
        ResultSet resultSet=statement.executeQuery(query);
        ResponseStatus responseStatus = new ResponseStatus();
        if(resultSet.next()){
            userId=resultSet.getInt("userId");
            responseStatus.setStatus(200);
            responseStatus.setMessage("PROCEED");
            responseStatus.setActionToDo("you can proceed with password reset");
        }
        else{
            responseStatus.setStatus(404);
            responseStatus.setMessage("INVALID EMAIL PROVIDED");
            responseStatus.setActionToDo("try again");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
    public static String setPassword(JsonNode requestData) throws Exception{
        // @author=Beulla
        Connection connection=new OnlineDbConnection().getConnection();
        JsonNode userData=requestData.get("object");
        Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
        String password = iterator.next().toString().split("=")[1];
        String userid=userId.toString();
        String query="Update users_table set password= " +password+" where id= "+userid;

        Statement statement=connection.createStatement();
        statement.executeUpdate(query);

        ResponseStatus responseStatus = new ResponseStatus();

        responseStatus.setStatus(200);
        responseStatus.setMessage("SUCCESS");
        responseStatus.setActionToDo("SUCCESSFULLY RESET PASSWORD");


        return new ObjectMapper().writeValueAsString(responseStatus);
    }

}
