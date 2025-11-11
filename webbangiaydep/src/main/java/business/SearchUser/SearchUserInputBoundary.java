package business.SearchUser;

public interface SearchUserInputBoundary {
    void execute(SearchUserInputData input, SearchUserOutputBoundary presenter);
}