package controllers.hiringcontrollers.jobapplicationcontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
public class jobApplicationActions {

        String createPostsQuery = "Insert into job_applications values";

        public jobApplicationActions() throws Exception {}

        public String createApplication(JsonNode requestData) throws Exception{

            //initialise  db connection
            Connection connection = new OnlineDbConnection().getConnection();

            JsonNode userData = requestData.get("object");
            Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();

            String userId = iterator.next().toString().split("=")[1];
            int id = Integer.parseInt(userId);
            String userName = iterator.next().toString().split("=")[1];

            PreparedStatement preparedStatement = connection.prepareStatement(createApplication());
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            ResponseStatus responseStatus = new ResponseStatus();

            if(!resultSet.next()){
                responseStatus.setStatus(500);
                responseStatus.setMessage("INTERNAL SERVER ERROR");
                responseStatus.setActionToDo("Something went wrong");

            }else {
                responseStatus.setStatus(200);
                responseStatus.setMessage("User Logged In Successfully");
                responseStatus.setActionToDo("Login");
            }

            return new ObjectMapper().writeValueAsString(responseStatus);
        }
    }

