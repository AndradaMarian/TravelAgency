package ObjectProtocol;

import Domain.Excursie;

public class BookedResponse extends UpdateResponse {
    Iterable<Excursie>excursii;

    public Iterable<Excursie> getExcursii() {
        return excursii;
    }

    public BookedResponse(Iterable<Excursie> excursii) {
        this.excursii = excursii;
    }
}
