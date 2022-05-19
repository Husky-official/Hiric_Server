package controllers.hiring.jobApplication;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.hiring.jobPosting.JobPostingActions;

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
            case "getJobs":
                response = new JobApplicationActions().getJobs(request);
                return response;
            case "getJobPosts":
                response = new JobApplicationActions().getJobPosts(request);
                return response;
//            case "get jobs":
//                String url = request.get("url").asText();
//                String[] url_parts = url.split("=");
//                int userId = Integer.parseInt(url_parts[1]);
//                response = new JobApplicationActions().getUserJobs(request, userId);
//                return response;

            case "getProvinces":
                response = new JobApplicationActions().getProvinces(request);
                return response;
            case "getNextLocation":
                response = new JobApplicationActions().getNextLocation(request);
                return response;
            case "getLocation":
                response = new JobApplicationActions().getLocations(request);
                return response;
//            case "deleteJobApplication":
//                response=new JobApplicationActions().deleteJobApplication(request);
//                return response;
        }
        return "";
    }
}