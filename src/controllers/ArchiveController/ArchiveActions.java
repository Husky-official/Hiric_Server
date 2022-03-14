package controllers.ArchiveController;
//import com.mysql.jdbc.Statement;
import java.sql.Statement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.Archive;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author Biziyaremye Henriette
 * @description class for controlling archives actions either inserting  or viewing
 * */

public class ArchiveActions {
    ResponseStatus responseStatus = new ResponseStatus();
    ResultSet rs = null;
    Statement st = null;
    String selectQuery = " SELECT messageType ,messageText,senderId,receiverId from messages";
    String insertQuery = "INSERT INTO Archives(ArchivedDate,ArchiveItemType, ArchivedContent,ArchivedContentSenderId,ArchivedContentReceiverId) VALUES(NOW(),?,?,?,?)";

    /**
     * @author Biziyaremye Henriette
     * @description method for transfering the data included in messages table to the archives table
     */

    public String ArchiveMessages() throws JsonProcessingException {

        try {
            //getting all data from the selected fields
            Connection connection = new OnlineDbConnection().getConnection();
//          connection = getConnection();
            PreparedStatement prestatement = connection.prepareStatement(selectQuery);
            PreparedStatement insertingStatement = connection.prepareStatement(insertQuery);
            rs = prestatement.executeQuery();

            while (rs.next()) {

                String ArchivedItemType = rs.getString("messageType");
                insertingStatement.setString(1, ArchivedItemType);


                String ArchivedContent = rs.getString("messageText");
                insertingStatement.setString(2, ArchivedContent);

                Integer ArchivedContentSenderId = rs.getInt("senderId");
                insertingStatement.setInt(3, ArchivedContentSenderId);

                Integer ArchivedContentReceiverId = rs.getInt("receiverId");
                insertingStatement.setInt(4, ArchivedContentReceiverId);


                int result = insertingStatement.executeUpdate();

                if (result == 1) {
                    System.out.println("inserted succesffuly");
                    responseStatus.setStatus(201);
                    responseStatus.setMessage("Created");
                    responseStatus.setActionToDo("Archiving data was success");
                } else {
                    responseStatus.setStatus(400);
                    responseStatus.setMessage("Bad request");
                    responseStatus.setActionToDo("There occured a problem when backing up your messages");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new ObjectMapper().writeValueAsString(responseStatus);
    }


    /**
     * @author Biziyaremye Henriette
     * @description method to read from the table of archives
     * @throws Exception
     */


}

