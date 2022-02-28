package controllers.invoicecontrollers;
/**
 *@author: Ineza Jost Chance
 * @description : Invoice controllers
 * */
import dbconnection.OnlineDbConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.OnlineDbConnection;
import models.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
import java.sql.*;
import java.lang.String;

public class InvoiceControllers {
    public String mainMethod(JsonNode request) throws Exception {
        String action = request.get("action");
        String response = "";
        switch (action) {
            case "generate":
                response = new InvoiceActions().createAndStoreInvoiceInDB(request);
                return response;
        }
        return "";
    }
}
