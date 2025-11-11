// business/AddUser/AddUserUseCase.java
package business.AddUser;

import persistence.User.UserGateway;

public class AddUserUseCase implements AddUserInputBoundary {
    private final UserGateway gateway;

    public AddUserUseCase(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(AddUserInputData input, AddUserOutputBoundary presenter) {
        if (input.email == null || input.email.trim().isEmpty() ||
            input.password == null || input.password.trim().isEmpty()) {
            presenter.presentError("Email và mật khẩu là bắt buộc");
            return;
        }
        if (gateway.emailExists(input.email)) {
            presenter.presentError("Email đã tồn tại");
            return;
        }
        long id = gateway.createUser(input.email, input.password, input.roleId, input.fullName, input.phone);
        if (id > 0) {
            presenter.presentSuccess();
        } else {
            presenter.presentError("Thêm người dùng thất bại");
        }
    }
}