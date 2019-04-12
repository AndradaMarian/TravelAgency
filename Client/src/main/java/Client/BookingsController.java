package Client;

import Domain.Excursie;
import Domain.Rezervare;
import Services.IAgentieServer;
import Services.ServerExcp;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class BookingsController  {
    @FXML
    Stage stage;
    @FXML
    TextField nume;
    @FXML
            TextField telefon;
    @FXML
            TextField locuri;

    IAgentieServer server;
    Excursie excursie;
    public void setServer(IAgentieServer server, Excursie excursie,Stage stage) {
        this.server=server;
        this.excursie=excursie;
        this.stage=stage;
    }
    @FXML
    void rezervaButton(){
        String name=nume.getText();

        String tel=telefon.getText();
        String loc=locuri.getText();
       int noLoc=Integer.valueOf(loc);

        try {
            Rezervare rezervare=new Rezervare("1",excursie,name,tel,noLoc);
            server.addBooking(rezervare);
            stage.close();
        } catch (ServerExcp serverExcp) {
            serverExcp.printStackTrace();
        }
    }

}
