// presenters/SearchProduct/SearchProductViewModel.java (fixed: import List, ProductDTO)
package presenters.SearchProduct;

import java.util.List;

import persistence.Product.ProductDTO;

public class SearchProductViewModel {
    public List<ProductDTO> products;
    public int totalCount;
}