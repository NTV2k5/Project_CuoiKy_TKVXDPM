package business.DeleteUser;

import persistence.User.UserGateway;

public class DeleteUserUseCase implements DeleteUserInputBoundary {
    private final UserGateway gateway;

    public DeleteUserUseCase(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(DeleteUserInputData input, DeleteUserOutputBoundary presenter) {
        try {
            gateway.deleteUser(input.id);
            presenter.presentSuccess();
        } catch (Exception e) {
            presenter.presentError("Xóa người dùng thất bại");
        }
    }
}