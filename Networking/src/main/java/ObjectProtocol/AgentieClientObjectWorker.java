package ObjectProtocol;

import DTO.AngajatDTO;
import DTO.DTOUtils;
import DTO.SearchDTO;
import DTO.SearchResponseDTO;
import Domain.Angajat;
import Domain.Excursie;
import Domain.Rezervare;
import Services.IAgentieObserver;

import Services.IAgentieServer;
import Services.ServerExcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;

public class AgentieClientObjectWorker implements Runnable, IAgentieObserver {
    private IAgentieServer iAgentieServer;
    private Socket connection;

    public AgentieClientObjectWorker(IAgentieServer iAgentieServer, Socket connection) {
        this.iAgentieServer = iAgentieServer;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;


    @Override
    public void bookingAdded(Iterable<Excursie> excursii) {
        try {
            sendResponse(new BookedResponse(excursii));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Object response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse((Response) response);
                }
            } catch (IOException e) {
               // sendResponse( new ErrorResponse(e.getMessage()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        output.writeObject(response);
        output.flush();


    }

    private Response handleRequest(Request request) {
        Response response=null;
        if(request instanceof LoginRequest){
            System.out.println("Login request ...");
            LoginRequest logReq=(LoginRequest)request;
            AngajatDTO angajatDTO=logReq.getAngajatDTO();
            Angajat angajat= DTOUtils.getFromDTO(angajatDTO);
            try {
                iAgentieServer.login(angajat,this);
                return new ValidAccountResponse();
            } catch (ServerExcp e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof SearchRequest){
            System.out.println("Search request ...");
            SearchRequest searchReques= (SearchRequest) request;
            SearchDTO searchDTO=searchReques.getSearchDTO();
            String obiectiv=searchDTO.getObiectiv();
            LocalTime start=searchDTO.getStart();
            LocalTime stop=searchDTO.getStop();
            try {
                Iterable<Excursie>excursii=iAgentieServer.searchTrips(obiectiv,start,stop);
                return new SuccesfullSearchResponse(new SearchResponseDTO(excursii));
            } catch (ServerExcp serverExcp) {
                //connected=false;
                return new ErrorResponse(serverExcp.getMessage());
            }
        }
        if(request instanceof RezervareRequest){
            RezervareRequest rezervareRequest= (RezervareRequest) request;
            Rezervare rezervare=rezervareRequest.getRezervare();
            try {
                iAgentieServer.addBooking(rezervare);
                return new SuccesfullBooked();
            } catch (ServerExcp serverExcp) {
                return new ErrorResponse(serverExcp.getMessage());
            }
        }
        return response;

    }
}
