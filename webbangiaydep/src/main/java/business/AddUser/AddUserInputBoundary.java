package business.AddUser;

public interface AddUserInputBoundary {
    void execute(AddUserInputData input, AddUserOutputBoundary presenter);
}
