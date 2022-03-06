//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.hiringcontrollers.jobapplicationcontrollers.JobApplicationController;
import controllers.billing.PaymentController;
import controllers.usercontrollers.UserControllers;
import dbconnection.DbConnectionVariables;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public void startServer() throws Exception{
        String url = "jdbc:mysql://remotemysql.com:3306/ZKZ7qI2OW3?"+"autoReconnect=true&useSSL=false";
        String user = "ZKZ7qI2OW3";
        String password = "pWgWkTztns";

        DbConnectionVariables connectionVariables = new DbConnectionVariables(url, user, password, "3306", 1200L);
        connectionVariables.saveDbConnectionVariablesInFile();

    }

    public static void main(String[] args) throws Exception {

        ServerSocket server = null;

        try{
            server = new ServerSocket(8888);
            server.setReuseAddress(true);

            //running infinite loop to accept
            //incoming clients

            while (true){

                // socket object to receive incoming client requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected "
                        + client.getInetAddress()
                        .getHostAddress());

                //Create new thread object
                ClientHandler clientSocket = new ClientHandler(client);

                //this thread will handler new client separately
                new Thread(clientSocket).start();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ClientHandler implements Runnable{
        private final Socket socket;

        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        public void run() {
            try{
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                String requestBody = "";

                while (!requestBody.equals("exit")){

                    requestBody = in.readUTF();
//changing the normal user into json strings
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(requestBody);

                    String url = jsonNode.get("url").asText();

                    System.out.println(jsonNode);

                    switch (url) {
                        case "/users" -> {
                            out.flush();
                            out.writeUTF(new UserControllers().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/payment" -> {
                            out.flush();
                            out.writeUTF(new PaymentController().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/jobPost" -> {
                            out.flush();
                            // out.writeUTF(new JobPostingControllers().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/createApplication" ->{
                            out.flush();
                            out.writeUTF(new JobApplicationController().mainMethod(jsonNode));
                            out.flush();
                        }
//                        case "/viewApplications" -> {
//                            out.flush();
//                            out.writeUTF(new JobApplicationController().mainMethod(jsonNode));
//                            out.flush();
//                        }
                        case "/group_messaging" -> {
                            out.flush();
                            out.writeUTF("New group");
                            out.flush();
                        }
                        default -> System.out.println("something went wrong");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Error ===> " +e.getMessage());
            }
        }
        }
}
