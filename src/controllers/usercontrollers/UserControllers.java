package controllers.usercontrollers;
import com.fasterxml.jackson.databind.JsonNode;

import static controllers.usercontrollers.Register.register;

import static controllers.usercontrollers.Register.register;

public class UserControllers {
    public String mainMethod(JsonNode request) throws Exception{
        
        String action = request.get("action").asText();
//      System.out.println(action);
        String response = "";

        switch (action) {
            case "login":
                response = new UserActions().login(request);
                return  response;
            case "logout":
                response = new UserActions().logout(request);
                return  response;
        case "register":
                response = register(request);
                return response;
            case "sendEmail":
                response=new UserActions().sendEmail(request);
                return response;
            case "verifyToken":
                response=new UserActions().verifyToken(request);
                return response;
            case "setPassword":
                response=new UserActions().setPassword(request);
                return response;
        default:
            System.out.println("Unknown action");
        }

        return "";
    }
}
