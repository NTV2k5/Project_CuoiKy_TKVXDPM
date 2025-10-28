package persistence.SearchShoe;

import java.util.List;

public interface SearchShoeGateway {
    List<SearchShoeDTO> searchShoes(String keyword) throws Exception;
}
