package controllers.ArchiveController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author  Biziyaremye Henriette
 * @description The controller for messages
 */

public class ArchiveController {

    public ArchiveController() {}

    public static String mainMethod(JsonNode request) throws JsonProcessingException {

        String requestAction = request.get("action").asText();
        String response;


        try {

            switch (requestAction) {
                case "Backup":
                    response = new ArchiveActions().ArchiveMessages();
                    return response;
                default:

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}