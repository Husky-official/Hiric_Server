package controllers.messagecontrollers;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class MessageControllers {
    public String mainMethod(JsonNode request) throws IOException {
        try {
            String action = request.get("action").asText();
            String response = "";

            switch (action){
                case "users":
                    response = new MessageActions().getUsers();
                    return response;
                case "checkUser":
                    response = new MessageActions().checkUserMemberShip();
                    return response;
                case "firstChatInit":
                    response = new MessageActions().storeFirstChatClient();
                    return response;
                case "sendMessage":
                    response = new MessageActions().sendMessage(request);
                    return response;
                case "olderMessages":
                    response = new MessageActions().getOlderMessages(request);
                    return response;
                default:
                    System.out.println("Unknown action");
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return "";
    }

}
