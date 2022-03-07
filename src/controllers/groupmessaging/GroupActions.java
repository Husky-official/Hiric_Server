package controllers.groupmessaging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
