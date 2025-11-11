// business/SearchCategory/SearchCategoryInputBoundary.java
package business.SearchCategory;

public interface SearchCategoryInputBoundary {
    void execute(SearchCategoryInputData input, SearchCategoryOutputBoundary presenter);
}