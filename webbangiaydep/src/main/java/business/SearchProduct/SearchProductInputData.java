// business/SearchProduct/SearchProductInputData.java
package business.SearchProduct;

public class SearchProductInputData {
    private final String keyword;
    private final Integer categoryId;
    private final int limit;

    public SearchProductInputData(String keyword, Integer categoryId, int limit) {
        this.keyword = keyword;
        this.categoryId = categoryId;
        this.limit = limit;
    }

    public String getKeyword() { return keyword; }
    public Integer getCategoryId() { return categoryId; }
    public int getLimit() { return limit; }
}