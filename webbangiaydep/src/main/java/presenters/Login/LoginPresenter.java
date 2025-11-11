// presenters/Login/LoginPresenter.java
package presenters.Login;

import business.Login.LoginOutputBoundary;
import business.Login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private LoginViewModel viewModel;

    @Override
    public void present(LoginOutputData outputData) {
        this.viewModel = new LoginViewModel();
        viewModel.success = outputData.isSuccess();
        viewModel.message = outputData.getMessage();
        viewModel.userId = outputData.getUserId();
        viewModel.roleCode = outputData.getRoleCode();
    }

    public LoginViewModel getViewModel() {
        return viewModel;
    }
}