// business/UpdateUser/UpdateUserUseCase.java
package business.UpdateUser;

import persistence.User.UserGateway;

public class UpdateUserUseCase implements UpdateUserInputBoundary {
    private final UserGateway gateway;

    public UpdateUserUseCase(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(UpdateUserInputData input, UpdateUserOutputBoundary presenter) {
        if (input.fullName == null || input.fullName.trim().isEmpty()) {
            presenter.presentError("Họ tên là bắt buộc");
            return;
        }
        try {
            gateway.updateUser(input.id, input.fullName, input.phone, input.roleId, input.isActive);
            presenter.presentSuccess();
        } catch (Exception e) {
            presenter.presentError("Cập nhật thất bại");
        }
    }
}