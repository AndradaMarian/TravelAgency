package Server;

import Domain.Angajat;
import Domain.Excursie;
import Domain.Rezervare;
import Repository.AngajatiRepoBD;
import Repository.ExcursieRepoBD;
import Repository.RezervariRepoBD;
import Services.IAgentieObserver;
import Services.IAgentieServer;
import Services.ServerExcp;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgentieServerImpl implements IAgentieServer {
    public AngajatiRepoBD angajatiRepoBD;
    public ExcursieRepoBD excursieRepoBD;
    public RezervariRepoBD rezervariRepoBD;
    public Map<String, IAgentieObserver> userList;


    public AgentieServerImpl(AngajatiRepoBD angajatiRepoBD, ExcursieRepoBD excursieRepoBD, RezervariRepoBD rezervariRepoBD) {
        this.angajatiRepoBD = angajatiRepoBD;
        this.excursieRepoBD = excursieRepoBD;
        this.rezervariRepoBD = rezervariRepoBD;
        userList=new HashMap<>();

    }


    public synchronized void login(Angajat angajat, IAgentieObserver agentieObserver) throws ServerExcp {

        if(!angajatiRepoBD.verifyPassword(angajat)){

            throw new ServerExcp("parola gresita");
        }
        userList.putIfAbsent(angajat.getNume(),agentieObserver);
        return;
    }


    public synchronized Iterable<Excursie> searchTrips(String Obiectiv, LocalTime start, LocalTime stop) throws ServerExcp {
        return excursieRepoBD.findObiectivInterval(Obiectiv,start,stop);
    }
    private final int defaultThreadsNo=5;
    @Override
    public synchronized void addBooking(Rezervare rezervare) throws ServerExcp{
         rezervariRepoBD.save(rezervare);

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        userList.forEach((x,y)->{

                executor.execute(()->{
                    try {
                        System.out.println("Notifying Observer");
                        y.bookingAdded(searchTrips(rezervare.getExcursie().getObiectivTuristic(),LocalTime.of(0,0),LocalTime.of(23,59)));
                    } catch (ServerExcp serverExcp) {
                        serverExcp.printStackTrace();
                    }
                });


        });
        executor.shutdown();
    }
}
