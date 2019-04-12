package Client;


import Domain.Excursie;

import Services.IAgentieServer;
import Services.ServerExcp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SearchTripsController {
    IAgentieServer server;
    @FXML
    TextField obiectiv;
    @FXML
    TextField oraStart;
    @FXML
    TextField minStart;
    @FXML
    TextField oraStop;
    @FXML
    TextField minStop;
    @FXML
    TableView<Excursie>excursieTableView;
ObservableList<Excursie>excursieObservableList;
    Iterable<Excursie>excursii;
    public SearchTripsController() {

    }
    @FXML
    public void searchTrip() {

            String obi=obiectiv.getText();
            LocalTime sta=LocalTime.of(Integer.valueOf(oraStart.getText()),Integer.valueOf(minStart.getText()));
            LocalTime sto=LocalTime.of(Integer.valueOf(oraStop.getText()),Integer.valueOf(minStop.getText()));
        try {
            excursii= server.searchTrips(obi, sta, sto);
            excursieObservableList= FXCollections.observableList(StreamSupport.stream(excursii.spliterator(),false).collect(Collectors.toList()));
            initTable();
            excursieTableView.setItems(excursieObservableList);
        } catch (ServerExcp serverExcp) {
            serverExcp.printStackTrace();
        }
    }
    void initTable(){
        TableColumn<Excursie,String>firma=new TableColumn<Excursie, String>("FirmaTransport");
        firma.setCellValueFactory(new PropertyValueFactory<>("FirmaTransport"));

        TableColumn<Excursie,String>pret=new TableColumn<Excursie, String>("Pret");
        pret.setCellValueFactory(new PropertyValueFactory<>("Pret"));

        TableColumn<Excursie,String>ora=new TableColumn<Excursie, String>("OraPlecare");
        ora.setCellValueFactory(new PropertyValueFactory<>("OraPlecare"));


        TableColumn<Excursie,String>locuri=new TableColumn<Excursie, String>("NrLocuri");
        locuri.setCellValueFactory(new PropertyValueFactory<>("NrLocuri"));
        excursieTableView.getColumns().addAll(firma,pret,ora,locuri);
    }
    public void setServer(IAgentieServer server) {
        this.server=server;
    }

    public void update(Iterable<Excursie> excursii) {

       List<Excursie> list=StreamSupport.stream(excursii.spliterator(),false).collect(Collectors.toList());
       Excursie excursie=list.get(0);
       if(excursie.getObiectivTuristic().equals(obiectiv.getText())){
            excursieObservableList=FXCollections.observableList(StreamSupport.stream(excursii.spliterator(),false).collect(Collectors.toList()));
            excursieTableView.getItems().clear();
            excursieTableView.setItems(excursieObservableList);
       }
    }
    @FXML
    void rezervaButton(){
        Stage stage =new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Bookings.fxml"));
        AnchorPane anchorPane= null;
        try {
            anchorPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BookingsController bookingsController =loader.getController();
        Excursie excursie=excursieTableView.getSelectionModel().getSelectedItem();
        bookingsController.setServer(server,excursie,stage);
        Scene scene=new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }
}
