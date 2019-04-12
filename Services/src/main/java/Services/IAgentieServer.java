package Services;


import Domain.Angajat;
import Domain.Excursie;
import Domain.Rezervare;

import java.time.LocalTime;

public interface IAgentieServer {
    void login(Angajat angajat,IAgentieObserver agentieObserver) throws ServerExcp;
    Iterable<Excursie> searchTrips(String Obiectiv, LocalTime start,LocalTime stop) throws ServerExcp;
    void addBooking(Rezervare rezervare) throws ServerExcp;
}
