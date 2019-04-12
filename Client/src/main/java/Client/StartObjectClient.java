package Client;

import ObjectProtocol.AgentieServerObjectProxy;
import Services.IAgentieServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class StartObjectClient extends Application {
    private static int defaultAgentiePort=55555;
    private static String defaultServer="localhost";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("aici");
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartObjectClient.class.getResourceAsStream("/agentieclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find agentieclient.properties "+e);
            return;
        }

        String serverIP=clientProps.getProperty("agentie.server.host",defaultServer);
        int serverPort=defaultAgentiePort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("agentie.server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultAgentiePort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);
        IAgentieServer server=new AgentieServerObjectProxy(serverIP,serverPort);
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/LogIn.fxml"));
        AnchorPane anchorPane=loader.load();
        LogInController logInController =loader.getController();
        logInController.setServer(server);
        Scene scene=new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
