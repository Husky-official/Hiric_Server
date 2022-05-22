package controllers.usercontrollers;
import com.fasterxml.jackson.databind.JsonNode;
import static controllers.usercontrollers.Register.register;
public class UserControllers {
    public String mainMethod(JsonNode request) throws Exception{
        String action = request.get("action").asText();
    System.out.println(action);
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
            case "deleteUser":
                response = new UserActions().deleteUser(request);
                return response;
            case "updateUser":
                response = new UserActions().updateUser(request);
                return response;
        default:
            System.out.println("Unknown action");
        }

        return "";
    }
}
