package DTO;

import java.io.Serializable;

public class AngajatDTO implements Serializable {
    String Nume;
    String parola;

    @Override
    public String toString() {
        return "AngajatDTO{" +
                "Nume='" + Nume + '\'' +
                ", parola='" + parola + '\'' +
                '}';
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

    public AngajatDTO(String nume, String parola) {
        Nume = nume;
        this.parola = parola;
    }
}
