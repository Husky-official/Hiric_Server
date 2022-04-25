package controllers.jobApplication;

import com.fasterxml.jackson.databind.JsonNode;
import models.RequestBody;
import models.hiring.JobApplication;

/**
 *@author: ITETERO Ariane
 * @description : The action to insert into database the job application details
 * */
public class JobApplicationController {
    public String mainMethod(JsonNode request) throws Exception {
        String action = request.get("action").asText();

        String response = "";
        String response1 = "";
        String response2 = "";
        String response3 = "";
        switch (action) {
            case "createApplication":
                response1 = new JobApplicationActions().createApplication(request);
                return response1;
//            case "searchId":
//                String url = request.get("url").asText();
//                String[] url_parts = url.split("=");
//                int jobPostId = Integer.parseInt(url_parts[1]);
//                response = new JobApplicationActions().searchId(request,jobPostId);
//                return response;
            /*
             * author: MPANO Christian
             * desc: This is a controller that handles actions including getting, creating, updating and deleting a job post.
             *
             */
//            case "get job applications":
//                String url = request.get("url").asText();
//                String[] url_parts = url.split("=");
//                int jobPostId = Integer.parseInt(url_parts[1]);
//                response = new JobApplicationActions().getJobApplications(request, jobPostId);
//                return response;
            case "getJobPosts":
                response2 = new JobApplicationActions().getJobs(request);
                return response2;
//          case "deleteApplication":
//                response3 =new JobApplicationActions().deleteApplication((JobApplication) request)
//        }
        }
        return "";
    }
}