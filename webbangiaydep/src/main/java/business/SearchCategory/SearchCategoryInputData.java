// business/SearchCategory/SearchCategoryInputData.java
package business.SearchCategory;

public class SearchCategoryInputData {
    private final String keyword;
    private final Integer parentId;
    private final int limit;

    public SearchCategoryInputData(String keyword, Integer parentId, int limit) {
        this.keyword = keyword;
        this.parentId = parentId;
        this.limit = limit;
    }

    public String getKeyword() { return keyword; }
    public Integer getParentId() { return parentId; }
    public int getLimit() { return limit; }
}