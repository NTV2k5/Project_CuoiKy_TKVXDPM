// business/SearchCategory/SearchCategoryOutputData.java
package business.SearchCategory;

import java.util.List;

import persistence.Category.CategoryDTO;

public class SearchCategoryOutputData {
    private final List<CategoryDTO> categories;
    private final int totalCount;

    public SearchCategoryOutputData(List<CategoryDTO> categories, int totalCount) {
        this.categories = categories;
        this.totalCount = totalCount;
    }

    public List<CategoryDTO> getCategories() { return categories; }
    public int getTotalCount() { return totalCount; }
}