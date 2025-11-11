package business.DeleteUser;

public interface DeleteUserInputBoundary {
    void execute(DeleteUserInputData input, DeleteUserOutputBoundary presenter);
}
