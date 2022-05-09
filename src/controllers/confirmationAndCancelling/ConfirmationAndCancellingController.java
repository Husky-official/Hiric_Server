package controllers.confirmationAndCancelling;

import com.fasterxml.jackson.databind.JsonNode;

public class ConfirmationAndCancellingController {
    public static String mainMethod(JsonNode request) throws Exception {
        String action = request.get("action").asText();
        String response = "";
        switch (action) {
            case "confirmation and cancelling":
                response = new ConfirmationAndCancellingActions().confirmAndCancel(request);
                return response;
        }
        return response;
    }
}
