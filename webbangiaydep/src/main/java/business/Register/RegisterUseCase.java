// business/Register/RegisterUseCase.java
package business.Register;

import persistence.User.UserGateway;

public class RegisterUseCase implements RegisterInputBoundary {
    private final UserGateway userGateway;

    public RegisterUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public void execute(RegisterInputData input, RegisterOutputBoundary presenter) {
        if (input.getEmail() == null || input.getEmail().trim().isEmpty()) {
            presenter.present(new RegisterOutputData(false, "Email không được để trống"));
            return;
        }
        if (input.getPassword() == null || input.getPassword().length() < 3) {
            presenter.present(new RegisterOutputData(false, "Mật khẩu phải ít nhất 3 ký tự"));
            return;
        }
        if (input.getFullName() == null || input.getFullName().trim().isEmpty()) {
            presenter.present(new RegisterOutputData(false, "Họ tên không được để trống"));
            return;
        }

        if (userGateway.emailExists(input.getEmail())) {
            presenter.present(new RegisterOutputData(false, "Email đã tồn tại"));
            return;
        }

        long userId = userGateway.createUser(input.getEmail(), input.getPassword(), 2, input.getFullName(), input.getPhone());
        if (userId > 0) {
            presenter.present(new RegisterOutputData(true, "Đăng ký thành công!"));
        } else {
            presenter.present(new RegisterOutputData(false, "Lỗi khi tạo tài khoản"));
        }
    }
}