package business.SearchUser;

import java.util.List;

import persistence.User.UserDTO;
import persistence.User.UserGateway;

public class SearchUserUseCase implements SearchUserInputBoundary {
    private final UserGateway gateway;

    public SearchUserUseCase(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(SearchUserInputData input, SearchUserOutputBoundary presenter) {
        List<UserDTO> users = gateway.searchUsers(input.keyword, input.role, input.limit);
        int total = gateway.getTotalUserCount(input.keyword, input.role);
        presenter.present(users, total);
    }
}