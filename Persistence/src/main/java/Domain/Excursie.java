package Domain;

import Utils.HasID;

import java.io.Serializable;
import java.time.LocalTime;

public class Excursie implements HasID<String>, Serializable {
    String ID;
    String ObiectivTuristic;
    String FirmaTransport;
    LocalTime oraPlecare;
    double pret;
    int nrLocuri;

    public Excursie(String ID, String obiectivTuristic, String firmaTransport, LocalTime oraPlecare, double pret, int nrLocuri) {
        this.ID = ID;
        ObiectivTuristic = obiectivTuristic;
        FirmaTransport = firmaTransport;
        this.oraPlecare = oraPlecare;
        this.pret = pret;
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String getID() {
        return ID;
    }


    @Override
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getObiectivTuristic() {
        return ObiectivTuristic;
    }

    public void setObiectivTuristic(String obiectivTuristic) {
        ObiectivTuristic = obiectivTuristic;
    }

    public String getFirmaTransport() {
        return FirmaTransport;
    }

    public void setFirmaTransport(String firmaTransport) {
        FirmaTransport = firmaTransport;
    }

    public LocalTime getOraPlecare() {
        return oraPlecare;
    }

    public void setOraPlecare(LocalTime oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String toString() {
        return "Excursie{" +
                "FirmaTransport='" + FirmaTransport + '\'' +
                ", oraPlecare=" + oraPlecare +
                ", pret=" + pret +
                ", nrLocuri=" + nrLocuri +
                '}';
    }
}
