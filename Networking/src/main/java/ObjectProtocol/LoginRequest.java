package ObjectProtocol;

import DTO.AngajatDTO;


public class LoginRequest implements Request {
   private AngajatDTO angajatDTO;

    public AngajatDTO getAngajatDTO() {
        return angajatDTO;
    }

    public LoginRequest(AngajatDTO angajatDTO) {
        this.angajatDTO = angajatDTO;
    }
}
