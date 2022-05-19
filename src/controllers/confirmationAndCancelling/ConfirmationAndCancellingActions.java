package controllers.confirmationAndCancelling;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ConfirmationAndCancellingActions {
    public static String confirmAndCancel(JsonNode request) throws Exception {
        JsonNode requestData = request.get("object");
        ObjectMapper objectMapper = new ObjectMapper();
        Integer confirmed[] = objectMapper.treeToValue(requestData, Integer[].class);
        for(int i = 0; i < confirmed.length; i++){
            System.out.println(confirmed[i]);
        }
        String inArrayStr  = "";
        for(int i = 0; i < confirmed.length; i++) {
            if(i == confirmed.length - 1) {
                inArrayStr = inArrayStr + String.valueOf(confirmed[i]);
            }else {
                inArrayStr = inArrayStr + String.valueOf(confirmed[i]) + ",";
            }
        }
        System.out.println(inArrayStr);
        String confirmQuery = "UPDATE jobApplication SET status = 'confirmed' WHERE id IN ("+ inArrayStr +")";
        String cancelQuery = "UPDATE jobApplication SET status = 'cancelled' WHERE id NOT IN(" + inArrayStr + ")";

        Connection connection = new OnlineDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(confirmQuery);
        PreparedStatement preparedStatement1 = connection.prepareStatement(cancelQuery);
        int updatedRows = preparedStatement.executeUpdate();
        int cancelledRow = preparedStatement1.executeUpdate();
        ResponseStatus responseStatus = new ResponseStatus();
        if(updatedRows <= 0){
            responseStatus.setStatus(500);
            responseStatus.setMessage("INTERNAL SERVER ERROR");
            responseStatus.setActionToDo("Something went wrong");
        }else {
            responseStatus.setStatus(200);
            responseStatus.setMessage("confirmation and cancelling finished");
            responseStatus.setActionToDo(request.get("action").asText());
        }
        return new ObjectMapper().writeValueAsString(responseStatus);
    }
}
