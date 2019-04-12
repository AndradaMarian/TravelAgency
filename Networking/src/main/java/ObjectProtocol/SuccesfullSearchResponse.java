package ObjectProtocol;

import DTO.SearchResponseDTO;
import Domain.Excursie;

public class SuccesfullSearchResponse implements Response {
    SearchResponseDTO responseDTO;

    public SuccesfullSearchResponse(SearchResponseDTO searchResponseDTO) {
        this.responseDTO=searchResponseDTO;
    }

    public SearchResponseDTO searchResponseDTO() {
        return responseDTO;
    }
}
