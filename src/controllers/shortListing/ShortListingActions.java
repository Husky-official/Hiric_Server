package controllers.shortListing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.hiring.Job;
import models.hiring.JobApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ShortListingActions {
    public static String addToShortList(JsonNode request) throws Exception {
        JsonNode requestData = request.get("object");
        ObjectMapper objectMapper = new ObjectMapper();
        Integer shortListed[] = objectMapper.treeToValue(requestData, Integer[].class);
        for(int i = 0; i < shortListed.length; i++){
            System.out.println(shortListed[i]);
        }
        String inArrayStr  = "";
        for(int i = 0; i < shortListed.length; i++) {
            if(i == shortListed.length - 1) {
                inArrayStr = inArrayStr + String.valueOf(shortListed[i]);
            }else {
                inArrayStr = inArrayStr + String.valueOf(shortListed[i]) + ",";
            }
        }
        System.out.println(inArrayStr);
        String addToShortListQuery = "UPDATE jobApplication SET status = 'shortlisted' WHERE userId IN ("+ inArrayStr +")";

        Connection connection = new OnlineDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(addToShortListQuery);
        int updatedRows = preparedStatement.executeUpdate();
        ResponseStatus responseStatus = new ResponseStatus();
        if(updatedRows <= 0){
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
        }else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("Shortlisting finished");
            responseStatus.setActionToDo(request.get("action").asText());
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
