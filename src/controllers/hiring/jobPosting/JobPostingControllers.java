package controllers.hiring.jobPosting;

import com.fasterxml.jackson.databind.JsonNode;
/*
 * author: Gashugi Aderline
 * desc: This is a controller that handles actions including getting, creating, updating and deleting a job post.
 *
 */

public class JobPostingControllers {
    public String mainMethod(JsonNode request) throws Exception {
        String action = request.get("action").asText();

        String response = "";

        switch (action) {
            case "createJobPost":
                response = new JobPostingActions().createJobPost(request);
                return response;
            case "getJobs":
                response = new JobPostingActions().getJobs(request);
                return response;
            case "get jobs":
                String url = request.get("url").asText();
                String[] url_parts = url.split("=");
                int userId = Integer.parseInt(url_parts[1]);
                response = new JobPostingActions().getUserJobs(request, userId);
                return response;
            case "deleteJobPost":
                response = new JobPostingActions().deleteJobPost(request);
                return response;
            case "getProvinces":
                response =  new JobPostingActions().getProvinces(request);
                return response;
            case "getDistricts":
                response = new JobPostingActions().getDistricts(request);
                return response;
            case "getJobPostById":
                response = new JobPostingActions().getJobPostById(request);
                return response;
//            case "updateJobPost":
//                response = new JobPostingActions().updateJobPost(request);
//                return response;
        }
        return "";
    }
}
