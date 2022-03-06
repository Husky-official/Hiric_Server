package controllers.jobApplication;

import com.fasterxml.jackson.databind.JsonNode;
/*
 * author: Gashugi Aderline
 * desc: This is a controller that handles actions including getting, creating, updating and deleting a job post.
 *
 */

public class JobApplicationController {
    public String mainMethod(JsonNode request) throws Exception {
        String action = request.get("action").asText();

        String response = "";

        switch (action) {
            case "get job applications":
                String url = request.get("url").asText();
                String[] url_parts = url.split("=");
                int jobPostId = Integer.parseInt(url_parts[1]);
                response = new JobApplicationActions().getJobApplications(request, jobPostId);
                return response;
        }
        return "";
    }
}
