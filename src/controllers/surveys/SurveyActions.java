package controllers.surveys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;
import models.SurveyData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SurveyActions {
    public static String postAsurvey(JsonNode request) throws Exception {
        ResponseStatus response = new ResponseStatus();
        JsonNode postedSurvey = request.get("object");
        Connection connection = new OnlineDbConnection().getConnection();

        String userid = postedSurvey.get("userId").asText();
        String survey = postedSurvey.get("survey").asText();

        String storeSurvey = "INSERT INTO Surveys(userId,surveyData) VALUES (?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(storeSurvey);
        preparedStatement.setString(1,userid);
        preparedStatement.setString(2,survey);
        if(preparedStatement.executeUpdate() == 1){
            response.setStatus(201);
            response.setMessage("registered successfully");
            response.setActionToDo("proceed");
            return  new ObjectMapper().writeValueAsString(response);
        }
        response.setStatus(501);
        response.setMessage("can't store");
        response.setActionToDo("retry");
        return  new ObjectMapper().writeValueAsString(response);
    }
    public static String takeAsurvey(JsonNode request) throws Exception {
        Connection connection = new OnlineDbConnection().getConnection();
        ResponseStatus response = new ResponseStatus();

        JsonNode object = request.get("object");

        String id = object.get("surveyId").asText();
        String answer = object.get("surveyAnswer").asText();

        String recordSurvey = "INSERT INTO surveyAnswers(surveyId,answerData) VALUES("+id+","+answer+")";
        PreparedStatement preparedStatement = connection.prepareStatement(recordSurvey);

        if(preparedStatement.executeUpdate() == 1){
            response.setStatus(302);
            response.setMessage("Recorded the survey successfully");
            response.setActionToDo("take another one");
            return new ObjectMapper().writeValueAsString(response);
        }
        response.setStatus(404);
        response.setMessage("cannot insert into the database");
        response.setActionToDo("retry");
        return new ObjectMapper().writeValueAsString(response);
    }
    public static String getSurveys(JsonNode request) throws Exception {
        Connection connection = new OnlineDbConnection().getConnection();
        ResponseStatus response = new ResponseStatus();
        String userId = request.get("object").asText();
        String getSurveys = "SELECT surveyId,surveyData FROM Surveys WHERE userId ="+userId+"";
        PreparedStatement preparedStatement = connection.prepareStatement(getSurveys);

        if(preparedStatement.execute()==true){
            ResultSet rs = preparedStatement.executeQuery();
            List<SurveyData> surveyDatas = new ArrayList<SurveyData>();
            while (rs.next()){
                surveyDatas.add(new SurveyData(rs));
            }
            response.setStatus(302);
            response.setMessage("here is your data");
            response.setActionToDo("choose one");
            response.setObject(surveyDatas);
            return new ObjectMapper().writeValueAsString(response);
        }
        response.setStatus(404);
        response.setMessage("not found");
        response.setActionToDo("retry");
        return new ObjectMapper().writeValueAsString(response);
    }
}
