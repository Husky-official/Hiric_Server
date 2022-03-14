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
        String createPaymentQuery = "INSERT INTO Transactions(Employer,Employee,PaymentMethod, transactionType, Amount) VALUES(?,?,?,?,?)";
        try {
            //initialise  db connection
            Connection connection = new OnlineDbConnection().getConnection();
            Statement statement = connection.createStatement();
            Statement updateEmployerWallet = connection.createStatement();
            Statement updateEmployeeWallet = connection.createStatement();
            Statement transactionRecording = connection.createStatement();

            JsonNode userData = requestData.get("object");
            Iterator<Map.Entry<String, JsonNode>> iterator = userData.fields();

            iterator.next();

            String idForJob = iterator.next().toString().split("=")[1];
            Long jobId = Long.parseLong(idForJob);

            String receivedOgAmount = iterator.next().toString().split("=")[1];
            Double originalAmount = Double.parseDouble(receivedOgAmount);

            String paymentMethod = iterator.next().toString().split("=")[1];

            iterator.next();

            iterator.next();

            String receivedEmployeeId = iterator.next().toString().split("=")[1];
            Long employeeId = Long.parseLong(receivedEmployeeId);

            String receivedEmployerId = iterator.next().toString().split("=")[1];
            Long employerId = Long.parseLong(receivedEmployerId);

            System.out.println("employerId: " + employerId + ", employeeId: " + employeeId + ", payment method: " + paymentMethod + ", amount: " + originalAmount);

            PreparedStatement preparedStatement = connection.prepareStatement(createPaymentQuery);
            preparedStatement.setLong(1, employerId);
            preparedStatement.setLong(2, employeeId);
            preparedStatement.setString(3, paymentMethod);
            preparedStatement.setLong(4, 1);
            preparedStatement.setDouble(5, originalAmount);

            preparedStatement.executeUpdate();
            updateEmployerWallet.execute("UPDATE `Wallet` SET amount_money = amount_money - " + originalAmount +" WHERE ownerId = " + employerId + ";");
            updateEmployeeWallet.execute("UPDATE `Wallet` SET amount_money = amount_money + " + originalAmount +" WHERE ownerId = " + employeeId + ";");
            transactionRecording.execute("INSERT INTO Transactions (Employer, Employee, PaymentMethod, transactionType, Amount) VALUES("+employerId +", "+ employeeId +", "+paymentMethod+", 1, "+originalAmount+");");
            ResultSet rs = statement.executeQuery("select * from Transactions where Employee='" + employeeId + "' AND Employer='" +employerId + "'");

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
                payment.setOriginalAmount(rs.getDouble("Amount"));
                payment.setPaymentMethod(String.valueOf(rs.getInt("PaymentMethod")));
                payment.setDateOfPayment(String.valueOf(rs.getDate("transactionDate")));
                payment.setEmployeeId(rs.getLong("Employee"));
                payment.setEmployerId(rs.getLong("Employer"));
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
