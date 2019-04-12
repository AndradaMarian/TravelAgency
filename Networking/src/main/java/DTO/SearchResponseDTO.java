package DTO;

import Domain.Excursie;

import java.io.Serializable;

public class SearchResponseDTO implements Serializable {
    Iterable<Excursie>excursies;

    public SearchResponseDTO(Iterable<Excursie> excursies) {
        this.excursies = excursies;
    }

    public Iterable<Excursie> getExcursies() {
        return excursies;
    }
}
