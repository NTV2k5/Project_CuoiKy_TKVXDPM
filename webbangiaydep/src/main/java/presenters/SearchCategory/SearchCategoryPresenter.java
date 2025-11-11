// presenters/SearchCategory/SearchCategoryPresenter.java
package presenters.SearchCategory;

import business.SearchCategory.SearchCategoryOutputBoundary;
import business.SearchCategory.SearchCategoryOutputData;

import java.util.List;

import persistence.Category.CategoryDTO;

public class SearchCategoryPresenter implements SearchCategoryOutputBoundary {
    private SearchCategoryViewModel viewModel;

    @Override
    public void present(SearchCategoryOutputData outputData) {
        this.viewModel = new SearchCategoryViewModel();
        viewModel.categories = outputData.getCategories();
        viewModel.totalCount = outputData.getTotalCount();
    }

    public SearchCategoryViewModel getViewModel() {
        return viewModel;
    }
}