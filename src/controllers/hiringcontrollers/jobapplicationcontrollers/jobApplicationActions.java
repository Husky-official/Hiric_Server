package controllers.hiringcontrollers.jobapplicationcontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

/**
 *@author: ITETERO Ariane
 * @description : The action to insert into database the job application details
 * */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.Map;
public class jobApplicationActions {

        String createApplicationQuery = "Insert into job_applications(id, userId, jobId, currentAddress, positionAppliedFor," +
                " availableDate, salaryDesired, paymentMethod, prevEmployer, prevEmpPhone, prevEmpEmail, prevPosition," +
                " reason, referenceName, referencePhone, resume, certificate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        public jobApplicationActions() throws Exception {}

        public String createApplication(JsonNode requestData) throws Exception{

            //initialise  db connection
            Connection connection = new OnlineDbConnection().getConnection();
//changing the object into string as it cannot pass in TCP channel as object
            JsonNode userData = requestData.get("object");
            Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();
            String Id = iterator.next().toString().split("=")[1];
            int id = Integer.parseInt(Id);
            String userId = iterator.next().toString().split("=")[1];
            int userIdd = Integer.parseInt(userId);
            String jobId = iterator.next().toString().split("=")[1];
            int idJob = Integer.parseInt(jobId);
            String currentAddress = iterator.next().toString().split("=")[1];
            String positionAppliedFor = iterator.next().toString().split("=")[1];
            String availableDate= iterator.next().toString().split("=")[1];
            String salaryDesired = iterator.next().toString().split("=")[1];
            String paymentMethod = iterator.next().toString().split("=")[1];
            String prevEmployer = iterator.next().toString().split("=")[1];
            String prevEmpPhone = iterator.next().toString().split("=")[1];
            String prevEmpEmail = iterator.next().toString().split("=")[1];
            String prevPosition = iterator.next().toString().split("=")[1];
            String reason = iterator.next().toString().split("=")[1];
            String referenceName = iterator.next().toString().split("=")[1];
            String referencePhone = iterator.next().toString().split("=")[1];
            String resume= iterator.next().toString().split("=")[1];
            String certicate = iterator.next().toString().split("=")[1];
            PreparedStatement preparedStatement = connection.prepareStatement(createApplicationQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, userIdd);
            preparedStatement.setInt(3, idJob);
            preparedStatement.setString(4, currentAddress);
            preparedStatement.setString(5, positionAppliedFor);
            preparedStatement.setString(6, availableDate);
            preparedStatement.setString(7, salaryDesired);
            preparedStatement.setString(8, paymentMethod);
            preparedStatement.setString(9, prevEmployer);
            preparedStatement.setString(10, prevEmpPhone);
            preparedStatement.setString(11, prevEmpEmail);
            preparedStatement.setString(12, prevPosition);
            preparedStatement.setString(13, reason);
            preparedStatement.setString(14, referenceName);
            preparedStatement.setString(15, referencePhone);
            preparedStatement.setString(16, resume);
            preparedStatement.setString(17, certicate);

            int resultSet = preparedStatement.executeUpdate();

            ResponseStatus responseStatus = new ResponseStatus();

            if(resultSet == 0){
                responseStatus.setStatus(500);
                responseStatus.setMessage("INTERNAL SERVER ERROR");
                responseStatus.setActionToDo("Something went wrong");

            }else {
                responseStatus.setStatus(200);
                responseStatus.setMessage("Job created Successfully");
                responseStatus.setActionToDo("CreateApplication");
            }

            return new ObjectMapper().writeValueAsString(responseStatus);
        }

}

