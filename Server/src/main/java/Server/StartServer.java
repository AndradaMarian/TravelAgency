package Server;

import NetworkUtils.AbstractServer;
import NetworkUtils.AgentieConcurrentServer;
import Repository.AngajatiRepoBD;
import Repository.ExcursieRepoBD;
import Repository.RezervariRepoBD;
import Services.IAgentieServer;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartServer {
    public static int defaultPort=55555;

    public static void main(String[] args) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartServer.class.getResourceAsStream("/agentieserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find agentieserver.properties "+e);
            return;
        }
        AngajatiRepoBD angajatiRepoBD=new AngajatiRepoBD(serverProps);
        ExcursieRepoBD excursieRepoBD=new ExcursieRepoBD(serverProps);
        RezervariRepoBD rezervariRepoBD=new RezervariRepoBD(serverProps);
        IAgentieServer iAgentieServer=new AgentieServerImpl(angajatiRepoBD,excursieRepoBD,rezervariRepoBD);
        int agentieServerPort=defaultPort;
        try{
        agentieServerPort=Integer.parseInt(serverProps.getProperty("agentie.server.port"));}
        catch (NumberFormatException nfe){
            System.err.println("Wrong  Port Number"+nfe.getMessage());
            System.err.println("Using default port "+defaultPort);

        }
        System.out.println("Starting server on port: "+agentieServerPort);
        AbstractServer agentieServer=new AgentieConcurrentServer(agentieServerPort,iAgentieServer);
        try {
            agentieServer.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());

        }

    }
}
