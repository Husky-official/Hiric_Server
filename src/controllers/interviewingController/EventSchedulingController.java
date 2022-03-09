package controllers.interviewingController;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author I_Clarisse
 * @description This a controller that handles creating, reading and updating scheduled events
 */
public class EventSchedulingController {
    public String mainMethod(JsonNode request) throws Exception{
        String action = request.get("action").asText();
        String response = " ";

        switch (action){
            case "scheduleEvent":
                response = new EventSchedulingActions().scheduleEvent(request);
                return response;
            case "getAllScheduledEvents":
                response = new EventSchedulingActions().getAllEvents(request);
                return response;
        }
        return "";
    }

}
