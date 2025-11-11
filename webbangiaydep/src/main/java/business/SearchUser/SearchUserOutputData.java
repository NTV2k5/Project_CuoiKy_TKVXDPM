package business.SearchUser;

import java.util.List;

import persistence.User.UserDTO;

public class SearchUserOutputData {
    public final List<UserDTO> users;
    public final int totalCount;

    public SearchUserOutputData(List<UserDTO> users, int totalCount) {
        this.users = users;
        this.totalCount = totalCount;
    }
}