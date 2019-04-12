package Domain;

import Utils.HasID;

import java.io.Serializable;

public class Rezervare implements HasID<String>, Serializable {
    String ID;
    Excursie excursie;
    String numeClient;
    String numarTelefon;
    int numarBilete;

    public Rezervare(String ID, Excursie excursie, String numeClient, String numarTelefon, int numarBilete) {
        this.ID = ID;
        this.excursie = excursie;
        this.numeClient = numeClient;
        this.numarTelefon = numarTelefon;
        this.numarBilete = numarBilete;
    }

    @Override
    public String getID() {
        return ID;
    }


    @Override
    public void setID(String ID) {
        this.ID = ID;
    }

    public Excursie getExcursie() {
        return excursie;
    }

    public void setExcursie(Excursie excursie) {
        this.excursie = excursie;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public int getNumarBilete() {
        return numarBilete;
    }

    public void setNumarBilete(int numarBilete) {
        this.numarBilete = numarBilete;
    }
}
