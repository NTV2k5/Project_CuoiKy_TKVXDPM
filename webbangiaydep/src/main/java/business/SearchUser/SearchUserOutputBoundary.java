package business.SearchUser;

import java.util.List;

import persistence.User.UserDTO;

public interface SearchUserOutputBoundary {
    void present(List<UserDTO> users, int totalCount);
}