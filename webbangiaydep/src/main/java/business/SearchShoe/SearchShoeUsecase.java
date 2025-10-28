package business.SearchShoe;

import java.util.List;
import persistence.SearchShoe.SearchShoeDTO;
import persistence.SearchShoe.SearchShoeGateway;

public class SearchShoeUsecase implements SearchShoeInputBoundary {

    private final SearchShoeOutputBoundary output;
    private final SearchShoeGateway dao;

    public SearchShoeUsecase(SearchShoeGateway dao, SearchShoeOutputBoundary output) {
        this.dao = dao;
        this.output = output;
    }

    @Override
    public void execute(String keyword) throws Exception {
        List<SearchShoeDTO> shoes = dao.searchShoes(keyword);
        output.present(shoes);
    }
}
