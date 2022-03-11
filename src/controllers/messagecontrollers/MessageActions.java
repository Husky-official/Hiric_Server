package controllers.messagecontrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class MessageActions {
    public String getUsers() throws Exception {
        String getUsersQuery = "SELECT * FROM users_table";
        Connection connection = new OnlineDbConnection().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(getUsersQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.beforeFirst();
        String usersNames = "";
        Boolean usersAvailable = false;
        while (resultSet.next()) {
            usersAvailable = true;
            if (resultSet.getRow() == 1) {
                usersNames = resultSet.getString(3);
            } else {
                usersNames = usersNames+" "+resultSet.getString(3);
            }
        }

        ResponseStatus responseStatus = new ResponseStatus();

        if(!usersAvailable) {
            responseStatus.setStatus(404);
            responseStatus.setMessage("No users found");
            responseStatus.setActionToDo("Something went wrong");
        } else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("Users found");
            responseStatus.setObject(usersNames);
            responseStatus.setActionToDo("users");
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String checkUserMemberShip() throws JsonProcessingException {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatus(200);
        responseStatus.setMessage("Membership found.");
        responseStatus.setActionToDo("Something went wrong");
        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String storeFirstChatClient() throws JsonProcessingException {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatus(200);
        responseStatus.setMessage("Stored use chatting info");
        responseStatus.setActionToDo("Something went wrong");
        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String sendMessage(JsonNode request) throws Exception {

        /**
         * @description: store message into the database
         * - make replies to users
         */

        ResponseStatus responseStatus = new ResponseStatus();
        JsonNode userData = request.get("object");
        try {
            Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
            iterator.next();
            String messageType = iterator.next().toString().split("=")[1];
            String messageContent = iterator.next().toString().split("=")[1];
            String originalMessage = iterator.next().toString().split("=")[1];
            String sender = iterator.next().toString().split("=")[1];
            String receiver = iterator.next().toString().split("=")[1];
            String sentAt = iterator.next().toString().split("=")[1];

            String postMessageToDatabase = "INSERT INTO messages(messageType, messageContent, originalMessage, senderID, receiverID, sentAt) values ( ?, ?, ?, ?, ?, ?)";
            Connection connection = new OnlineDbConnection().getConnection();

            int senderId = Integer.parseInt(sender);
            int receiverId = Integer.parseInt(receiver);
            int originalMessageID = Integer.parseInt(originalMessage);

            PreparedStatement preparedStatement = connection.prepareStatement(postMessageToDatabase);
            preparedStatement.setString(1, messageType);
            preparedStatement.setString(2, messageContent);
            preparedStatement.setInt(3, originalMessageID);
            preparedStatement.setInt(4, senderId);
            preparedStatement.setInt(5, receiverId);
            preparedStatement.setString(6, sentAt);

            int resultSet = preparedStatement.executeUpdate();

            if (resultSet == 1) {
                responseStatus.setStatus(200);
                responseStatus.setMessage("Message reply success");
                responseStatus.setActionToDo("Reply");
                responseStatus.setObject(userData);
            } else {
                responseStatus.setStatus(400);
                responseStatus.setMessage("Failed to send message");
                responseStatus.setActionToDo("Sending failed");
                responseStatus.setObject(userData);
            }

        } catch (Exception error) {
            responseStatus.setStatus(400);
            responseStatus.setMessage("Failed to send message");
            responseStatus.setActionToDo("Sending failed");
            responseStatus.setObject(userData);
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
