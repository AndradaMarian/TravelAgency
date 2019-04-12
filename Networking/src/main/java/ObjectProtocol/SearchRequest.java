package ObjectProtocol;

import DTO.SearchDTO;

import java.time.LocalTime;

public class SearchRequest implements Request {
private SearchDTO searchDTO;

    public SearchRequest(SearchDTO searchDTO) {
        this.searchDTO = searchDTO;
    }

    public SearchDTO getSearchDTO() {
        return searchDTO;
    }
}
