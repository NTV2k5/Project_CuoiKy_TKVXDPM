package business.SearchUser;

public class SearchUserInputData {
    public final String keyword;
    public final String role;
    public final int limit;

    public SearchUserInputData(String keyword, String role, int limit) {
        this.keyword = keyword;
        this.role = role;
        this.limit = limit;
    }
}