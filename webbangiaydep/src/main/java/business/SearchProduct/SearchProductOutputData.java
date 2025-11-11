package business.SearchProduct;

import java.util.List;

import persistence.Product.ProductDTO;

public class SearchProductOutputData {
    private final List<ProductDTO> products;
    private final int totalCount;

    public SearchProductOutputData(List<ProductDTO> products, int totalCount) {
        this.products = products;
        this.totalCount = totalCount;
    }

    public List<ProductDTO> getProducts() { return products; }
    public int getTotalCount() { return totalCount; }
}