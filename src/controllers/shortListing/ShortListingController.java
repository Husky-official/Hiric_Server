package controllers.shortListing;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.hiring.jobPosting.JobPostingActions;

public class ShortListingController {
    public static String mainMethod(JsonNode request) throws Exception {
        String action = request.get("action").asText();
        String response = "";
        switch (action) {
            case "add to shortlist":
                response = new ShortListingActions().addToShortList(request);
                return response;

            case "get shortlist":
                String url = request.get("url").asText();
                String[] url_parts = url.split("=");
                int postId = Integer.parseInt(url_parts[1]);

                response = new ShortListingActions().getShortList(request, postId);
                return response;
        }
        return response;
    }
}
