package controllers.shortListing;

import com.fasterxml.jackson.databind.JsonNode;


public class ShortListingController {
    public static String mainMethod(JsonNode request) throws Exception {
        String action = request.get("action").asText();
        String response = "";
        switch (action) {
            case "add to shortlist":
                response = new ShortListingActions().addToShortList(request);
                return response;
        }
        return response;
    }
}
