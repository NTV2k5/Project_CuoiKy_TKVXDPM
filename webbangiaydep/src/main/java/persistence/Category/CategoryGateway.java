// persistence/Category/CategoryGateway.java
package persistence.Category;

import java.util.List;

public interface CategoryGateway {
    long createCategory(String code, String name, String description, Integer parentId);
    void updateCategory(long id, String code, String name, String description, Integer parentId);
    void deleteCategory(long id);
    List<CategoryDTO> searchCategories(String keyword, Integer parentId, int limit);
    CategoryDTO getCategoryById(long id);
    List<CategoryDTO> getAllCategories();
    boolean codeExists(String code);
    boolean isCodeOwnedByCategory(String code, long categoryId);
    int getTotalCategoryCount(String keyword, Integer parentId);
}