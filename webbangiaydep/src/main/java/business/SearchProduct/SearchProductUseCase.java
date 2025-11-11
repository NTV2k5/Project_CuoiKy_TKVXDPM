// business/SearchProduct/SearchProductUseCase.java (fixed: import List, ProductDTO; add getTotalProductCount call)
package business.SearchProduct;

import java.util.List;

import persistence.Product.ProductDTO;
import persistence.Product.ProductGateway;

public class SearchProductUseCase implements SearchProductInputBoundary {
    private final ProductGateway productGateway;

    public SearchProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void execute(SearchProductInputData input, SearchProductOutputBoundary presenter) {
        List<ProductDTO> products = productGateway.searchProducts(input.getKeyword(), input.getCategoryId(), input.getLimit());
        int totalCount = productGateway.getTotalProductCount(input.getKeyword(), input.getCategoryId());

        presenter.present(new SearchProductOutputData(products, totalCount));
    }
}