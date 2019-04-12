package ObjectProtocol;

import DTO.AngajatDTO;
import DTO.DTOUtils;
import DTO.SearchDTO;
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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AgentieServerObjectProxy implements  IAgentieServer {
    private String host;
    private int port;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private IAgentieObserver iAgentieObserver;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public AgentieServerObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();

    }

    @Override
    public void login(Angajat angajat, IAgentieObserver agentieObserver) throws ServerExcp {
        initializeConnection();
        AngajatDTO angajatDTO = DTOUtils.getDTO(angajat);
        sendRequest(new LoginRequest(angajatDTO));
        Response response = readResponse();
        if (response instanceof ValidAccountResponse) {
            this.iAgentieObserver = agentieObserver;
            return;
        }
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new ServerExcp(err.getMessage());
        }


    }

    private Response readResponse() throws ServerExcp {
        Response response = null;
        try {
           /* synchronized (responses){
                responses.wait();
            }
            response = responses.remove(0);   */
            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Iterable<Excursie> searchTrips(String Obiectiv, LocalTime start, LocalTime stop) throws ServerExcp {
        SearchDTO searchDTO=new SearchDTO(Obiectiv,start,stop);
        SearchRequest searchRequest=new SearchRequest(searchDTO);
        System.out.println("Search request ");
        sendRequest(searchRequest);
        Response response=readResponse();
        if (response instanceof SuccesfullSearchResponse){
            return ((SuccesfullSearchResponse) response).searchResponseDTO().getExcursies();
        }
        if (response instanceof ErrorResponse){
         throw new ServerExcp(((ErrorResponse) response).getMessage());
        }
        return null;
    }

    @Override
    public void addBooking(Rezervare rezervare) throws ServerExcp {
        RezervareRequest rezervareRequest=new RezervareRequest(rezervare);
        System.out.println("Rezervare request");
        sendRequest(rezervareRequest);
        Response response=readResponse();
        if(response instanceof SuccesfullBooked){
            return ;
        }
        if(response instanceof  ErrorResponse){
            throw  new ServerExcp(((ErrorResponse) response).getMessage());
        }
        return ;
    }

    private void initializeConnection() throws ServerExcp {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            iAgentieObserver = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws ServerExcp {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ServerExcp("Error sending object " + e);
        }

    }

    private void startReader() {

        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (response instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) response);
                    } else {

                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

    private void handleUpdate(UpdateResponse response) {
        if(response instanceof BookedResponse){
            BookedResponse bookedResponse= (BookedResponse) response;
            Iterable<Excursie>excursies=bookedResponse.getExcursii();
            iAgentieObserver.bookingAdded(excursies);
        }

    }
}
