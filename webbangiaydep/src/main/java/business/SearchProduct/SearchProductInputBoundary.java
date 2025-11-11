// business/SearchProduct/SearchProductInputBoundary.java
package business.SearchProduct;

public interface SearchProductInputBoundary {
    void execute(SearchProductInputData input, SearchProductOutputBoundary presenter);
}