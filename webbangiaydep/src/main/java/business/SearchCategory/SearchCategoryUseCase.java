// business/SearchCategory/SearchCategoryUseCase.java
package business.SearchCategory;

import java.util.List;

import persistence.Category.CategoryDTO;
import persistence.Category.CategoryGateway;

public class SearchCategoryUseCase implements SearchCategoryInputBoundary {
    private final CategoryGateway categoryGateway;

    public SearchCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(SearchCategoryInputData input, SearchCategoryOutputBoundary presenter) {
        List<CategoryDTO> categories = categoryGateway.searchCategories(input.getKeyword(), input.getParentId(), input.getLimit());
        int totalCount = categoryGateway.getTotalCategoryCount(input.getKeyword(), input.getParentId());

        presenter.present(new SearchCategoryOutputData(categories, totalCount));
    }
}