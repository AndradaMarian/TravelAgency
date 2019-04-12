package ObjectProtocol;

import Domain.Rezervare;

public class RezervareRequest implements Request {
    Rezervare rezervare;

    public Rezervare getRezervare() {
        return rezervare;
    }

    public RezervareRequest(Rezervare rezervare) {
        this.rezervare = rezervare;
    }
}
