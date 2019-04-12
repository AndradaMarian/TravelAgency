package Domain;

import Utils.HasID;

public class Angajat implements HasID<String> {
    String ID;
    String Nume;
    String parola;


    public Angajat(String ID, String nume, String parola) {
        this.ID = ID;
        Nume = nume;
        this.parola = parola;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNume() {
        return Nume;
    }

    public void setNume(String nume) {
        Nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
