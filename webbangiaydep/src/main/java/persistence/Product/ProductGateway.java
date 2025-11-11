package persistence.Product;

import java.util.List;

import persistence.Category.CategoryDTO;

public interface ProductGateway {
    long createProduct(String sku, String name, String shortDescription, String description, String imageUrl, String brand, int categoryId, boolean isActive);
    void updateProduct(long id, String sku, String name, String shortDescription, String description, String imageUrl, String brand, int categoryId, boolean isActive);
    void deleteProduct(long id);
    List<ProductDTO> searchProducts(String keyword, Integer categoryId, int limit);
    ProductDTO getProductById(long id);
    boolean skuExists(String sku); // Added
    boolean isSkuOwnedByProduct(String sku, long productId); // Added
    int getTotalProductCount(String keyword, Integer categoryId); // Added
    List<CategoryDTO> getAllCategories(); // Added for dropdown
}