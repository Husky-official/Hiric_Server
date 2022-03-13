package controllers.groupmessaging;

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

    public String deleteGroup(JsonNode requestBody) throws Exception{

        ResponseStatus responseStatus = new ResponseStatus();

        Connection connection = new OnlineDbConnection().getConnection();
        JsonNode groupInfo = requestBody.get("object");

        Iterator<Map.Entry<String, JsonNode>> iterator = groupInfo.fields();

        int group_id = Integer.parseInt(iterator.next().toString().split("=")[1]);

        String deleteGroupQuery = "DELETE FROM `Groups` WHERE `Groups`.`id` = ?";
        PreparedStatement st = connection.prepareStatement(deleteGroupQuery);
        st.setInt(1, group_id);

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
}
