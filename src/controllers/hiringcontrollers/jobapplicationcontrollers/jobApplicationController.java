package controllers.hiringcontrollers.jobapplicationcontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.usercontrollers.UserActions;
public class jobApplicationController {

        public String mainMethod(JsonNode request) throws Exception{
            String action = request.get("action").asText();

            String response = "";

            switch (action){
                case "createApplication":
                    response = new UserActions().createApplication(request);
                    return  response;
                default:
                    System.out.println("Unknown action");
            }

            return "";
        }
    }
