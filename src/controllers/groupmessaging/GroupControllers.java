package controllers.groupmessaging;

/**
 * @author : DABAGIRE Valens
 * @description : Handling group messaging actions
 */

import com.fasterxml.jackson.databind.JsonNode;

public class GroupControllers {

    public String mainMethod(JsonNode request) throws Exception{
        String action = request.get("action").asText();
        String response = "";

        switch (action){
            case "createGroup" -> {
                response = new GroupActions().createGroup(request);
            }
            case "joinGroup" ->{
                response = new GroupActions().joinGroup(request);
            }
            case "deleteGroup" ->{
                response = new GroupActions().deleteGroup(request);
            }
            case "leaveGroup" ->{
                response = new GroupActions().leaveGroup(request);
            }
            case "checkMemberShip" -> {
                response = new GroupActions().checkMemberShip(request);
            }
            case "sendMessage" ->{
                response = new GroupActions().sendMessage(request);
            }
            case "editMessage" ->{
                response = new GroupActions().editMessage(request);
            }
            case "allMessages" ->{
                response = new GroupActions().allGroupMessages(request);
            }
            case "chat" ->{
                new GroupActions().chat(request);
            }
            default -> {
                response = "Something went wrong";
                return response;
            }
        }

        return response;
    }

}
