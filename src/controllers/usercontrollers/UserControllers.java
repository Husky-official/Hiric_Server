package controllers.usercontrollers;

import com.fasterxml.jackson.databind.JsonNode;

public class UserControllers {
    public String mainMethod(JsonNode request) throws Exception{
        
        String action = request.get("action").asText();
//        System.out.println(action);
        String response = "";

        switch (action){
            case "login":
                response = new UserActions().login(request);
                return  response;
            default:
                System.out.println("Unknown action");
        }

        return "";
    }
}
