// presenters/Register/RegisterPresenter.java
package presenters.Register;

import business.Register.RegisterOutputBoundary;
import business.Register.RegisterOutputData;

public class RegisterPresenter implements RegisterOutputBoundary {
    private RegisterViewModel viewModel;

    @Override
    public void present(RegisterOutputData outputData) {
        this.viewModel = new RegisterViewModel();
        viewModel.success = outputData.isSuccess();
        viewModel.message = outputData.getMessage();
    }

    public RegisterViewModel getViewModel() {
        return viewModel;
    }
}