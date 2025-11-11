package business.UpdateUser;

public interface UpdateUserInputBoundary {
    void execute(UpdateUserInputData input, UpdateUserOutputBoundary presenter);
}