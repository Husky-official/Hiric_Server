import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

<<<<<<< HEAD
import controllers.hiring.jobPosting.jobApplication.JobApplicationController;
=======
import controllers.confirmationAndCancelling.ConfirmationAndCancellingController;
>>>>>>> 83fbee0ccbb517da2352620dc40f797de49813c8
import controllers.messagecontrollers.MessageControllers;
import controllers.billing.BillingMain;
import controllers.invoicecontrollers.InvoiceControllers;
import controllers.groupmessaging.GroupControllers;
import controllers.hiring.jobPosting.JobPostingControllers;
import controllers.shortListing.ShortListingController;
import controllers.usercontrollers.UserControllers;
import controllers.ArchiveController.ArchiveController;
import dbconnection.DbConnectionVariables;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public void startServer() throws Exception{
        String url = "jdbc:mysql://remotemysql.com:3306/ZKZ7qI2OW3";
        String user = "ZKZ7qI2OW3";
        String password = "pWgWkTztns";

        DbConnectionVariables connectionVariables = new DbConnectionVariables(url, user, password, "3306", 8000l);
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

    public static class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                String requestBody = "";

                while (!requestBody.equals("exit")) {
                    requestBody = in.readUTF();
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(requestBody);

                    String url = jsonNode.get("url").asText();
<<<<<<< HEAD
                    System.out.println(url);
=======

                    if (url.contains("get_job_posts")) {
                        url = "/get_job_posts";
                    } else if (url.contains("get_job_applications")) {
                        url = "/get_job_applications";
                    } else if (url.contains("payment")) {
                        url = "/payment";
                    } else if(url.contains("getShortList")) {
                        url = "/getShortList";
                    }
                    String urlDup = url;

                    System.out.println(jsonNode);

>>>>>>> 83fbee0ccbb517da2352620dc40f797de49813c8
                    switch (url) {
                        case "/users" -> {
                            out.flush();
                            out.writeUTF(new UserControllers().mainMethod(jsonNode));
                            out.flush();
                        }

                        case "/invoices" -> {
                            out.flush();
                            out.writeUTF(new InvoiceControllers().mainMethod(jsonNode));
                        }
                        case "/Archives" -> {
                            out.flush();
                            out.writeUTF(new ArchiveController().mainMethod(jsonNode));
                            out.flush();

                        }

                        case "/payment" -> {
                            out.flush();
                            out.writeUTF(new BillingMain().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/createApplication" ->{
                            out.flush();
                            out.writeUTF(new JobApplicationController().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/group_messaging" -> {
                            out.flush();
                            out.writeUTF(new GroupControllers().mainMethod(jsonNode));
                            out.flush();
                        }

                        case "/get_job_posts" -> {
                            System.out.println("get job posts");
                            out.flush();
                            out.writeUTF(new JobPostingControllers().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/get_job_applications" -> {
                            System.out.println("get job applications");
                            out.flush();
                            out.writeUTF(new JobApplicationController().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/shortList" -> {
                            out.flush();
                            out.writeUTF(new ShortListingController().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/getShortList" -> {
                            System.out.println("get shortlist");
                            out.flush();
                            out.writeUTF(new ShortListingController().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/confirmationAndCancelling" -> {
                            out.flush();
                            out.writeUTF(new ConfirmationAndCancellingController().mainMethod(jsonNode));
                            out.flush();
                        }
                        case "/messages" -> {
                            out.flush();
                            out.writeUTF(new MessageControllers().mainMethod(jsonNode));
                            out.flush();
                        }
                        default -> System.out.println("something went wrong");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error ===> " + e.getMessage());
            }
        }
        }
}
