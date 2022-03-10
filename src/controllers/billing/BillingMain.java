package controllers.billing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.ResponseStatus;

public class BillingMain {
    public String mainMethod(JsonNode request) throws Exception{
        String action = request.get("action").asText();

        String response = "";

        switch (action){
            case "payment":
                response = new PaymentActions().createPayment(request);
                return  response;
            case "createPayroll":
                response = new PayrollActions().createAndSavePayroll(request);
                return response;
            case "listJobs":
                response = new PayrollActions().listOfJobsByEmployer(request);
                return response;
            default:
                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setStatus(500);
                responseStatus.setMessage("INVALID REQUEST");
                responseStatus.setActionToDo("Try another option available");
                return new ObjectMapper().writeValueAsString(responseStatus);
        }
    }
}
