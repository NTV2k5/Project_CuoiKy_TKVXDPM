// presenters/SearchProduct/SearchProductPresenter.java (fixed: import List, ProductDTO)
package presenters.SearchProduct;

import business.SearchProduct.SearchProductOutputBoundary;
import business.SearchProduct.SearchProductOutputData;



public class SearchProductPresenter implements SearchProductOutputBoundary {
    private SearchProductViewModel viewModel;

    @Override
    public void present(SearchProductOutputData outputData) {
        this.viewModel = new SearchProductViewModel();
        viewModel.products = outputData.getProducts();
        viewModel.totalCount = outputData.getTotalCount();
    }

    public SearchProductViewModel getViewModel() {
        return viewModel;
    }
}