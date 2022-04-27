package controllers.hiring.jobApplication;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *@author: ITETERO Ariane
 * @description : The action to insert into database the job application details
 * */
public class JobApplicationController {
    public String mainMethod(JsonNode request) throws Exception {
        String action = request.get("action").asText();
        String response="";
        switch (action) {
            case "createApplication":
                response = new JobApplicationActions().createApplication(request);
                return response;
            case "getJobApplications":
                response=new JobApplicationActions().getJobApplications(request);
                return response;
            case "getJobPosts":
                response = new JobApplicationActions().getJobPosts(request);
                return response;
        }
        return "";
    }
}