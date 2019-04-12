package Client;

import Domain.Excursie;
import ObjectProtocol.AgentieServerObjectProxy;
import Services.IAgentieObserver;
import Services.IAgentieServer;

import java.io.IOException;
import java.time.LocalTime;
import Domain.Angajat;
import Services.ServerExcp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;

public class LogInController implements IAgentieObserver {

    IAgentieServer server;

    Angajat angajat;
    ExcursiiListModel excursiiListModel;
    @FXML
    TextField numeTextField;
    @FXML
    PasswordField parolaTextField;
    @FXML
    Button logInButton;
    SearchTripsController searchTripsController;

    public LogInController() {
        excursiiListModel=new ExcursiiListModel();
    }

    void setServer(IAgentieServer server){
        this.server=server;
    }
    public ListModel getTripsModel(){
        return excursiiListModel;
    }
    @Override
    public void bookingAdded(Iterable<Excursie> excursii) {
        System.out.println("ajunge");
searchTripsController.update(excursii);
    }


    public void logInUI(){
        String user=numeTextField.getText();
        String parola=parolaTextField.getText();
        try {
            angajat=new Angajat("1",user,parola);
            server.login(angajat,this);
            System.out.println("Log in succeded");
            Stage primaryStage=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/SearchTrips.fxml"));
            AnchorPane anchorPane= null;
            try {
                anchorPane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            searchTripsController =loader.getController();
            searchTripsController.setServer(server);
            Scene scene=new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (ServerExcp serverExcp) {
            //alerta
        }
    }
}
