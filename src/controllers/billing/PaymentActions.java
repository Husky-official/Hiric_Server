package controllers.billing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.Payment;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class PaymentActions {

    public PaymentActions() {}

    /**
     *  Register a new billing plan.
     *  The function will receive an object of Payment
     * @return an Object of response
     * @author DUSHIME Bill Benon
     */

    public String createPayment(JsonNode requestData) throws Exception{
        String createPaymentQuery = "INSERT INTO payment(jobId,originalAmount,paymentMethod, reducedAmount, dateOfPayment, employeeId, employerId)VALUES(?,?,?,?,?,?,?)";
        try {
            //initialise  db connection
            Connection connection = new OnlineDbConnection().getConnection();
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS payment(id INT PRIMARY KEY AUTO_INCREMENT, jobId INT NOT NULL, " +
                    "originalAmount DOUBLE NOT NULL, paymentMethod VARCHAR (250), reducedAmount DOUBLE DEFAULT 0, dateOfPayment VARCHAR(200), employeeId INT NOT NULL, employerId INT NOT NULL)");

            JsonNode userData = requestData.get("object");
            Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();

            iterator.next();

            String idForJob = iterator.next().toString().split("=")[1];
            Long jobId = Long.parseLong(idForJob);

            String receivedOgAmount = iterator.next().toString().split("=")[1];
            Double originalAmount = Double.parseDouble(receivedOgAmount);

            String paymentMethod = iterator.next().toString().split("=")[1];

            String receivedReducAmt = iterator.next().toString().split("=")[1];
            Double reducedAmount = Double.parseDouble(receivedReducAmt);

            String dateOfPayment = iterator.next().toString().split("=")[1];
//            String dateOfPayment = receivedDateOfPay;

            String receivedEmployeeId = iterator.next().toString().split("=")[1];
            Long employeeId = Long.parseLong(receivedEmployeeId);

            String receivedEmployerId = iterator.next().toString().split("=")[1];
            Long employerId = Long.parseLong(receivedEmployerId);

            PreparedStatement preparedStatement = connection.prepareStatement(createPaymentQuery);
            preparedStatement.setLong(1, jobId);
            preparedStatement.setDouble(2, originalAmount);
            preparedStatement.setString(3, paymentMethod);
            preparedStatement.setDouble(4, reducedAmount);
            preparedStatement.setString(5, dateOfPayment);
            preparedStatement.setLong(6, employeeId);
            preparedStatement.setLong(7, employerId);

            preparedStatement.executeUpdate();
            ResultSet rs = statement.executeQuery("select * from payment where jobId='" + jobId + "' AND employeeId='" + employeeId + "' AND employerId='" +employerId + "'");

            ResponseStatus responseStatus = new ResponseStatus();

            if(!rs.next()){
                responseStatus.setStatus(500);
                responseStatus.setMessage("INTERNAL SERVER ERROR");
                responseStatus.setActionToDo("Creating payment failed, try again later.");

            }else {
                responseStatus.setStatus(200);
                responseStatus.setMessage("Payment done Successfully");
                responseStatus.setActionToDo("Payment");
                Payment payment = new Payment();
                payment.setJobId(rs.getLong("jobId"));
                payment.setOriginalAmount(rs.getDouble("originalAmount"));
                payment.setPaymentMethod(rs.getString("paymentMethod"));
                payment.setReducedAmount(rs.getDouble("reducedAmount"));
                payment.setDateOfPayment(rs.getString("dateOfPayment"));
                payment.setEmployeeId(rs.getLong("employeeId"));
                payment.setEmployerId(rs.getLong("employerId"));
                responseStatus.setObject(payment);
            }

            return new ObjectMapper().writeValueAsString(responseStatus);

        } catch (Exception e) {
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatus(500);
            responseStatus.setMessage("AN ERROR OCCURRED");
            responseStatus.setActionToDo(e.getMessage());
            return new ObjectMapper().writeValueAsString(responseStatus);
        }
    }
}
