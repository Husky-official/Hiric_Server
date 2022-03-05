package controllers.dashboard;

import com.fasterxml.jackson.databind.JsonNode;

public class DashboardControllers {
    public static String mainMethod(JsonNode request) throws Exception{
        String action = request.get("action").asText();
        String response;

        switch (action){
            case "adminDashboard" :
                response = new DashboardActions().adminDashboard();
                return response;
        }

        return "";
    }
}
