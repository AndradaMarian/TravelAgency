package DTO;

import Domain.Angajat;

public class DTOUtils {
    public static Angajat getFromDTO(AngajatDTO angajatDTO){
        return new Angajat(null,angajatDTO.getNume(),angajatDTO.getParola());
    }

    public static AngajatDTO getDTO(Angajat angajat) {
        return new  AngajatDTO(angajat.getNume(),angajat.getParola());
    }
}
