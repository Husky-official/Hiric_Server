package controllers.groupmessaging;

/**
 * @author : DABAGIRE Valens
 * @description : Handling group messaging actions
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

public class GroupActions {
    public String createGroup(JsonNode requestBody) throws Exception{

        ResponseStatus responseStatus = new ResponseStatus();

        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode groupInfo = requestBody.get("object");

        Iterator<Map.Entry<String, JsonNode>> iterator = groupInfo.fields();

        int id = Integer.parseInt(iterator.next().toString().split("=")[1]);

        //Group Name
        String groupName = iterator.next().toString().split("=")[1].split("\"")[1];
        //Group Description
        String groupDescription = iterator.next().toString().split("=")[1].split("\"")[1];
        //Group Creator
        int groupCreator = Integer.parseInt(iterator.next().toString().split("=")[1]);

        //create group statement
        String groupCreationQuery = "INSERT INTO `Groups` (`groupName`, `groupDescription`, `numberOfParticipants`, `groupCreatorID`) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(groupCreationQuery);
        statement.setString(1, groupName);
        statement.setString(2, groupDescription);
        statement.setInt(3, 1);
        statement.setInt(4, groupCreator);

        int count  = statement.executeUpdate();

        if(count>0){
            responseStatus.setStatus(200);
            responseStatus.setActionToDo("CREATE GROUP");
            responseStatus.setMessage("New group was created successfully");
        }else{
            responseStatus.setStatus(400);
            responseStatus.setActionToDo("CREATE GROUP");
            responseStatus.setMessage("Unable to create new group");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String joinGroup(JsonNode requestBody) throws Exception{

        ResponseStatus responseStatus = new ResponseStatus();

        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode groupInfo = requestBody.get("object");

        Iterator<Map.Entry<String, JsonNode>> iterator = groupInfo.fields();

        int member_id = Integer.parseInt(iterator.next().toString().split("=")[1]);
        int group_id = Integer.parseInt(iterator.next().toString().split("=")[1]);

        String groupMemberCreationQuery = "INSERT INTO `Groupmembers` (`memberID`, `groupID`, `membershipStatus`, `membershipRole`) VALUES (?, ?, ?, ?)";
        PreparedStatement st = connection.prepareStatement(groupMemberCreationQuery);
        st.setInt(1,member_id);
        st.setInt(2, group_id);
        st.setString(3, "in");
        st.setString(4, "member");

        int count  = st.executeUpdate();

        if(count>0){
            responseStatus.setStatus(200);
            responseStatus.setActionToDo("JOINING GROUP");
            responseStatus.setMessage("New Member Has Joined Successfully");
        }else{
            responseStatus.setStatus(400);
            responseStatus.setActionToDo("JOINING GROUP");
            responseStatus.setMessage("Unable to JOIN  group");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String leaveGroup(JsonNode requestBody) throws Exception{

        ResponseStatus responseStatus = new ResponseStatus();

        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode groupInfo = requestBody.get("object");

        Iterator<Map.Entry<String, JsonNode>> iterator = groupInfo.fields();

        int member_id = Integer.parseInt(iterator.next().toString().split("=")[1]);
        int groupID = Integer.parseInt(iterator.next().toString().split("=")[1]);

        String leaveGroupQuery = "DELETE FROM `Groupmembers` WHERE `Groupmembers`.`memberID` = ? AND `Groupmembers`.`groupID` = ?";
        PreparedStatement st = connection.prepareStatement(leaveGroupQuery);
        st.setInt(1, member_id);
        st.setInt(2, groupID);

        int count  = st.executeUpdate();

        if(count>0){
            responseStatus.setStatus(200);
            responseStatus.setActionToDo("LEAVE GROUP");
            responseStatus.setMessage("You Left Group Successfully.");
        }else{
            responseStatus.setStatus(400);
            responseStatus.setActionToDo("LEAVE GROUP");
            responseStatus.setMessage("Unable to LEAVE  group");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String deleteGroup(JsonNode requestBody) throws Exception{

        ResponseStatus responseStatus = new ResponseStatus();

        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode groupInfo = requestBody.get("object");

        Iterator<Map.Entry<String, JsonNode>> iterator = groupInfo.fields();

        int group_id = Integer.parseInt(iterator.next().toString().split("=")[1]);

        String deleteGroupQuery = "DELETE FROM `Groups` WHERE `Groups`.`id` = ? AND `Groups`.`groupCreatorID` = ?";
        PreparedStatement st = connection.prepareStatement(deleteGroupQuery);
        st.setInt(1, group_id);
        st.setInt(2, 0);

        int count  = st.executeUpdate();

        if(count>0){
            responseStatus.setStatus(200);
            responseStatus.setActionToDo("DELETE GROUP");
            responseStatus.setMessage("Group Has Deleted Successfully");
        }else{
            responseStatus.setStatus(400);
            responseStatus.setActionToDo("DELETE GROUP");
            responseStatus.setMessage("Unable to DELETE  group");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String checkMemberShip(JsonNode requestBody) throws Exception{

        ResponseStatus responseStatus = new ResponseStatus();

        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode groupInfo = requestBody.get("object");

        Iterator<Map.Entry<String, JsonNode>> iterator = groupInfo.fields();

        int member_id = Integer.parseInt(iterator.next().toString().split("=")[1]);
        int group_id = Integer.parseInt(iterator.next().toString().split("=")[1]);

        String memberShip = "SELECT  * FROM `Groupmembers` WHERE `Groupmembers`.`memberID` = ? AND `Groupmembers`.`groupID` = ?";
        PreparedStatement statement = connection.prepareStatement(memberShip);
        statement.setInt(1, member_id);
        statement.setInt(2, group_id);

        ResultSet rs  = statement.executeQuery();

        if(rs.first()){
            responseStatus.setStatus(200);
        }else{
            responseStatus.setStatus(400);
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String sendMessage(JsonNode requestBody) throws Exception{
        ResponseStatus responseStatus = new ResponseStatus();

        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode groupInfo = requestBody.get("object");

        Iterator<Map.Entry<String, JsonNode>> iterator = groupInfo.fields();

        String id = iterator.next().toString();

        String messageType = iterator.next().toString().split("=")[1].split("\"")[1];
        String content = iterator.next().toString().split("=")[1].split("\"")[1];
        int originalMessage = Integer.parseInt(iterator.next().toString().split("=")[1]);
        int sender = Integer.parseInt(iterator.next().toString().split("=")[1]);
        int receiver = Integer.parseInt(iterator.next().toString().split("=")[1]);
        String sentAt = iterator.next().toString().split("=")[1].split("\"")[1];

        //create group statement
        String sendGroupMessage = "INSERT INTO `messages` (`messageType`, `messageContent`, `originalMessage`, `senderID`, `receiverID`, `sentAt`) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sendGroupMessage);
        statement.setString(1, messageType);
        statement.setString(2, content);
        statement.setInt(3, originalMessage);
        statement.setInt(4, sender);
        statement.setInt(5, receiver);
        statement.setString(6, sentAt);

        int count  = statement.executeUpdate();

        if(count>0){
            responseStatus.setStatus(200);
            responseStatus.setActionToDo("SEND GROUP MESSAGE");
            responseStatus.setMessage("You have successfully send message in group with id = "+receiver);
        }else{
            responseStatus.setStatus(400);
            responseStatus.setActionToDo("SEND GROUP MESSAGE");
            responseStatus.setMessage("Unable to send message in group with id = "+receiver);
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String editMessage(JsonNode requestBody) throws Exception{

        ResponseStatus responseStatus = new ResponseStatus();

        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode groupInfo = requestBody.get("object");

        Iterator<Map.Entry<String, JsonNode>> iterator = groupInfo.fields();

        int id = Integer.parseInt(iterator.next().toString().split("=")[1]);
        String messageType  = iterator.next().toString().split("=")[1];
        String content = iterator.next().toString().split("=")[1].split("\"")[1];

        //create group statement
        String sendGroupMessage = "UPDATE `messages` SET `messages`.`messageContent` = ? WHERE `messages`.`id` = ? ";
        PreparedStatement statement = connection.prepareStatement(sendGroupMessage);
        statement.setString(1, content);
        statement.setInt(2, id);

        int count  = statement.executeUpdate();

        if(count>0){
            responseStatus.setStatus(200);
            responseStatus.setActionToDo("EDIT GROUP MESSAGE");
            responseStatus.setMessage("You have successfully edited message");
        }else{
            responseStatus.setStatus(400);
            responseStatus.setActionToDo("EDIT GROUP MESSAGE");
            responseStatus.setMessage("Unable to edit message");
        }

        return new ObjectMapper().writeValueAsString(responseStatus);
    }

    public String allGroupMessages(JsonNode requestBody) throws Exception{
        return "hello .......";
    }
}
