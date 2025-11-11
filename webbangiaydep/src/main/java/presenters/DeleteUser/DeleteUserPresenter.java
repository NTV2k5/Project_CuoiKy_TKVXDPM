package presenters.DeleteUser;

import business.DeleteUser.DeleteUserOutputBoundary;
import business.DeleteUser.DeleteUserOutputData;

public class DeleteUserPresenter implements DeleteUserOutputBoundary {
    private DeleteUserOutputData outputData;

    @Override
    public void presentSuccess() {
        this.outputData = new DeleteUserOutputData(true, "Xóa thành công");
    }

    @Override
    public void presentError(String message) {
        this.outputData = new DeleteUserOutputData(false, message);
    }

    public DeleteUserOutputData getOutputData() {
        return outputData;
    }
}