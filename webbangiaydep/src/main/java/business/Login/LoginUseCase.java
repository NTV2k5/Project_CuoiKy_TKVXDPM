// business/Login/LoginUseCase.java
package business.Login;

import persistence.User.UserGateway;

public class LoginUseCase implements LoginInputBoundary {
    private final UserGateway userGateway;

    public LoginUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public void execute(LoginInputData input, LoginOutputBoundary presenter) {
        if (input.getEmail() == null || input.getEmail().trim().isEmpty()) {
            presenter.present(new LoginOutputData(false, "Email không được để trống", null, null));
            return;
        }
        if (input.getPassword() == null || input.getPassword().trim().isEmpty()) {
            presenter.present(new LoginOutputData(false, "Mật khẩu không được để trống", null, null));
            return;
        }

        Long userId = userGateway.authenticate(input.getEmail(), input.getPassword());
        if (userId == null) {
            presenter.present(new LoginOutputData(false, "Email hoặc mật khẩu không đúng", null, null));
            return;
        }

        String roleCode = userGateway.getRoleCodeByUserId(userId);
        presenter.present(new LoginOutputData(true, "Đăng nhập thành công", userId, roleCode));
    }
}