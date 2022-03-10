package controllers.usercontrollers;
import com.fasterxml.jackson.databind.JsonNode;
<<<<<<< HEAD
import static controllers.usercontrollers.Register.register;
=======

import static controllers.usercontrollers.Register.register;

>>>>>>> ee307fd18e44e8ad16cf77ee6008044696a3f34f
public class UserControllers {
    public String mainMethod(JsonNode request) throws Exception{
        
        String action = request.get("action").asText();
//      System.out.println(action);
        String response = "";

        switch (action) {
<<<<<<< HEAD
            case "login":
                response = new UserActions().login(request);
                return  response;
            case "logout":
                response = new UserActions().logout(request);
                return  response;
        case "register":
                response = register(request);
                return response;
        default:
            System.out.println("Unknown action");
=======
            case "login" -> {
                response = new UserActions().login(request);
                return response;
            }
            case "register" -> {
                response = register(request);
                return response;
            }
            default -> System.out.println("Unknown action");
>>>>>>> ee307fd18e44e8ad16cf77ee6008044696a3f34f
        }

        return "";
    }
}
