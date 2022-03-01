package controllers.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.usercontrollers.UserActions;

public class JobControllers {
    public String mainMethod(JsonNode request) throws Exception{
        String action = request.get("action").asText();

        String response = "";

        switch (action){
            case "get_user_jobs":
                response = new JobActions().getJobs(request);
                System.out.println(response);
                return  response;
            default:
                System.out.println("Unknown action");
        }

        return "";
    }
}
