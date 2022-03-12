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
        }
        return "";
    }
}
