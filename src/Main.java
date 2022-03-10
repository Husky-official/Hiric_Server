import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.ArchiveController.ArchiveController;
import controllers.billing.BillingMain;
import controllers.groupmessaging.GroupControllers;
import controllers.hiring.jobPosting.JobPostingControllers;
import controllers.invoicecontrollers.InvoiceControllers;
import controllers.jobApplication.JobApplicationController;
import controllers.usercontrollers.UserControllers;
import dbconnection.DbConnectionVariables;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public void startServer() throws Exception{
        String url = "jdbc:mysql://localhost:3306/hiric";
        String user = "root";
        String password = "password@2001";

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

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(requestBody);

                    String url = jsonNode.get("url").asText();
                    if(url.contains("get_job_posts")){
                        url = "/get_job_posts";
                    }else if(url.contains("get_job_applications")){
                        url = "/get_job_applications";
                    }
                    String urlDup = url;

                    System.out.println(jsonNode);

                    switch (url) {
                        case "/users" -> {
                            out.flush();
                            out.writeUTF(new UserControllers().mainMethod(jsonNode));
                            out.flush();
                        }

                        case "/Archives"->{
                            out.flush();
                            out.writeUTF(new ArchiveController().mainMethod(jsonNode));
                            out.flush();

                        }


                        case "/invoices" -> {
                            out.flush();
                            out.writeUTF(new InvoiceControllers().mainMethod(jsonNode));
                        }

                        case "/payment" -> {
                            out.flush();
                            out.writeUTF(new BillingMain().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/jobPost" -> {
                            out.flush();
                            out.writeUTF(new JobPostingControllers().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/group_messaging" -> {
                            out.flush();
                            out.writeUTF(new GroupControllers().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/get_job_posts" -> {
                            out.flush();
                            out.writeUTF(new JobPostingControllers().mainMethod(jsonNode));
                        }
                        case "/get_job_applications" -> {
                            out.flush();
                            out.writeUTF(new JobApplicationController().mainMethod(jsonNode));
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
