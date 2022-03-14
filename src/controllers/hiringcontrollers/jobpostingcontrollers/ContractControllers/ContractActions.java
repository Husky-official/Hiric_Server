package controllers.hiringcontrollers.jobpostingcontrollers.ContractControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ContractActions {


    public ContractActions(){}

    public String GetAllJobs(Integer id) throws Exception {

        String GetAlljobsQuery = " SELECT J.jobTitle FROM jobPosts j inner join jobs J ON j.jobId = J.id where userId ="+id;

        Connection connection = new OnlineDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GetAlljobsQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResponseStatus responseStatus = new ResponseStatus();



        if(resultSet.next()){
         responseStatus.setStatus(302);
         responseStatus.setMessage("Found");
         responseStatus.setActionToDo("the jobs have been found");
//            List<String> AllJobs = new List<String>()
//            ResponseBody responseBody = new ResponseBody();
        }
        else{
            responseStatus.setStatus(404);
            responseStatus.setMessage("Not found");
            responseStatus.setActionToDo("something went wrong");

        }
        return new ObjectMapper().writeValueAsString(responseStatus);

    }
}
