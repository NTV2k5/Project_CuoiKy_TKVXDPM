// presenters/SearchCategory/SearchCategoryViewModel.java
package presenters.SearchCategory;

import java.util.List;

import persistence.Category.CategoryDTO;

public class SearchCategoryViewModel {
    public List<CategoryDTO> categories;
    public int totalCount;
}