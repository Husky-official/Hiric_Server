package controllers.groupmessaging;

import com.fasterxml.jackson.databind.JsonNode;

public class GroupControllers {

    public String mainMethod(JsonNode request) throws Exception{
        String action = request.get("action").asText();
        String response = "";

        switch (action){
            case "createGroup" -> {
                response = new GroupActions().createGroup(request);
            }
            default -> {
                response = "Something went wrong";
                return response;
            }
        }

        return response;
    }

}
