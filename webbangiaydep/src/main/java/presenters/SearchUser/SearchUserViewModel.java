package presenters.SearchUser;

import java.util.List;

import persistence.User.UserDTO;

public class SearchUserViewModel {
    public final List<UserDTO> users;
    public final int totalCount;

    public SearchUserViewModel(List<UserDTO> users, int totalCount) {
        this.users = users;
        this.totalCount = totalCount;
    }
}