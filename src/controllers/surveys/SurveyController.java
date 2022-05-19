package controllers.surveys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SurveyController {
    public String mainMethod(JsonNode request) throws Exception {
        String action = request.get("action").asText();
        String response = "";

        switch (action){
            case "posting":
                response = new SurveyActions().postAsurvey(request);
                return response;
            case "answering":
                response = new SurveyActions().takeAsurvey(request);
                return response;
            case "getting":
                response = new SurveyActions().getSurveys(request);
                return response;
            default:
                return response;
        }
    }
}
