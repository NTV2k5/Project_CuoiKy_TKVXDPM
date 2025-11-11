package presenters.UpdateUser;

import business.UpdateUser.UpdateUserOutputBoundary;
import business.UpdateUser.UpdateUserOutputData;

public class UpdateUserPresenter implements UpdateUserOutputBoundary {
    private UpdateUserOutputData outputData;

    @Override
    public void presentSuccess() {
        this.outputData = new UpdateUserOutputData(true, "Cập nhật thành công");
    }

    @Override
    public void presentError(String message) {
        this.outputData = new UpdateUserOutputData(false, message);
    }

    public UpdateUserOutputData getOutputData() {
        return outputData;
    }
}