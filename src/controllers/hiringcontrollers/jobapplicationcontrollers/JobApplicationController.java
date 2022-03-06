package controllers.hiringcontrollers.jobapplicationcontrollers;

import com.fasterxml.jackson.databind.JsonNode;
/**
 *@author: ITETERO Ariane
 * @description : the action to take place is described here
 * */

public class JobApplicationController {

        public String mainMethod(JsonNode request) throws Exception{
            String action = request.get("action").asText();

            String response = "";

            switch (action){
                case "createApplication":
                    response = new jobApplicationActions().createApplication(request);
                    return  response;
                case "viewApplication":
                    response=new jobApplicationActions().viewApplications();
                    return response;
                default:
                    System.out.println("Unknown action");
            }

            return "";
        }
}
