package presenters.AddUser;

import business.AddUser.AddUserOutputBoundary;
import business.AddUser.AddUserOutputData;

public class AddUserPresenter implements AddUserOutputBoundary {
    private AddUserOutputData outputData;

    @Override
    public void presentSuccess() {
        this.outputData = new AddUserOutputData(true, "Thêm người dùng thành công");
    }

    @Override
    public void presentError(String message) {
        this.outputData = new AddUserOutputData(false, message);
    }

    public AddUserOutputData getOutputData() {
        return outputData;
    }
}