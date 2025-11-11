package presenters.SearchUser;

import java.util.List;

import business.SearchUser.SearchUserOutputBoundary;
import business.SearchUser.SearchUserOutputData;
import persistence.User.UserDTO;


public class SearchUserPresenter implements SearchUserOutputBoundary {
    private SearchUserOutputData outputData;

    @Override
    public void present(List<UserDTO> users, int totalCount) {
        this.outputData = new SearchUserOutputData(users, totalCount);
    }

    public SearchUserOutputData getOutputData() {
        return outputData;
    }
}