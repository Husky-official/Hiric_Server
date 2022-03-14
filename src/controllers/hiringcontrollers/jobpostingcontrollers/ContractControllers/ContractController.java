package controllers.hiringcontrollers.jobpostingcontrollers.ContractControllers;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Biziyaremye Henriette
 * 
 * */

public class ContractController {


    public static String mainMethod(JsonNode request) throws Exception {

        String action =  "GetAllJobs";
        System.out.println(action);
        Integer id = 1;
        String response = "";


        switch (action){
            case "GetAllJobs" ->{
                response = new ContractActions().GetAllJobs(id);
                return response;
            }
        }


        return "";
    }
}
