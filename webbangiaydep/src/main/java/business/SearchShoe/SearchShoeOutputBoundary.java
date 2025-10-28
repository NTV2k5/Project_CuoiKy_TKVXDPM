package business.SearchShoe;

import java.util.List;
import persistence.SearchShoe.SearchShoeDTO;

public interface SearchShoeOutputBoundary {
    void present(List<SearchShoeDTO> shoes);
}
