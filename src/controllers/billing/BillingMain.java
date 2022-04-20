package controllers.billing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.hiring.jobPosting.JobPostingActions;
import models.ResponseStatus;

public class BillingMain {
    public String mainMethod(JsonNode request) throws Exception{
        String action = request.get("action").asText();

        String response = "";

        switch (action) {
            case "payment":
                response = new PaymentActions().createPayment(request);
                return  response;
            case "createPayroll":
                response = new PayrollActions().createAndSavePayroll(request);
                return response;
            case "listJobs":
                String url = request.get("url").asText();
                String[] url_parts = url.split("=");
                int userId = Integer.parseInt(url_parts[1]);
                response = new PayrollActions().listOfJobsByEmployer(request, userId);
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
