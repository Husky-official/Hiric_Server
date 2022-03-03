package controllers.billing;

import com.fasterxml.jackson.databind.JsonNode;

public class PayrollControllers{
    public String mainMethod(JsonNode request) throws Exception{
        String action = request.get("action").asText();
        String response = "";
        switch(action){
            case "create":
                response = new PayrollActions().createAndSavePayroll(request);
                return response;
        }
        return "";
    }

}
